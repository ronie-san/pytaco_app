package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.PytacoRequest;

public class MainActivity extends BaseActivity implements IActivity {

    private int idUsuario;
    private String chaveAcesso;
    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private AlertDialog dialogNovoClube;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblQtdPytacoGlobal = findViewById(R.id.main_lblQtdPytacoGlobal);
        lblQtdFichaGlobal = findViewById(R.id.main_lblQtdFichaGlobal);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            idUsuario = Integer.parseInt(bundle.getString("idUsuario"));
            chaveAcesso = bundle.getString("chaveAcesso");
            lblQtdPytacoGlobal.setText(bundle.getString("qtdPytacoGlobal"));
            lblQtdFichaGlobal.setText(bundle.getString("qtdFichaGlobal"));
        }
        else{
            idUsuario = Integer.parseInt(savedInstanceState.getString("idUsuario"));
            chaveAcesso = savedInstanceState.getString("chaveAcesso");
            lblQtdPytacoGlobal.setText(savedInstanceState.getString("qtdPytacoGlobal"));
            lblQtdFichaGlobal.setText(savedInstanceState.getString("qtdFichaGlobal"));
        }

        final ImageButton btnNovoClube = findViewById(R.id.main_btnNovoClube);
        btnNovoClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNovoClubeClick();
            }
        });
    }

    private void btnNovoClubeClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_criar_clube, null);
        final ImageButton btnVoltar = view.findViewById(R.id.criar_clube_btnVoltar);

        dialogNovoClube = builder.create();
        dialogNovoClube.setCancelable(true);
        dialogNovoClube.setCanceledOnTouchOutside(true);
        dialogNovoClube.setView(view, 0, 0, 0, 0);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNovoClube.cancel();
            }
        });

        final TextView lblNomeClube = view.findViewById(R.id.criar_clube_edtNomeClube);
        final TextView edtDescricaoClube = view.findViewById(R.id.criar_clube_edtDescricaoClube);

        final ImageButton btnCriarClube = view.findViewById(R.id.criar_clube_btnCriarClube);
        btnCriarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PytacoRequest request = new PytacoRequest(MainActivity.this);
                request.criarClube(idUsuario, chaveAcesso, lblNomeClube.getText().toString(), edtDescricaoClube.getText().toString());
            }
        });
        dialogNovoClube.show();
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        pCancelDialog();
        pEnableScreen();

        if(dialogNovoClube.isShowing()){
            dialogNovoClube.cancel();
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if(dialogNovoClube.isShowing()){
            dialogNovoClube.cancel();
        }
    }

    @Override
    public void onError(VolleyError error) {
        pCancelDialog();
        pEnableScreen();
        Toast.makeText(this, "Não foi possível criar clube... " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartRequest() {
        pDisableScreen();
        pShowProgress();
    }
}
