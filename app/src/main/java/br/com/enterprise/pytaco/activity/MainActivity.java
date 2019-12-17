package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.content.Context;
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
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.activity.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class MainActivity extends BaseActivity implements IActivity {

    private Usuario usuario;
    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private AlertDialog dialogNovoClube;
    private PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblQtdPytacoGlobal = findViewById(R.id.main_lblQtdPytacoGlobal);
        lblQtdFichaGlobal = findViewById(R.id.main_lblQtdFichaGlobal);
        usuario = new Usuario();

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            usuario = (Usuario) bundle.getSerializable("usuario");
        } else {
            usuario = (Usuario) savedInstanceState.getSerializable("usuario");
        }

        lblQtdPytacoGlobal.setText(usuario.getQtdPytaco().toString());
        lblQtdFichaGlobal.setText(usuario.getQtdFicha().toString());

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

        final TextView edtNomeClube = view.findViewById(R.id.criar_clube_edtNomeClube);
        final TextView edtDescricaoClube = view.findViewById(R.id.criar_clube_edtDescricaoClube);

        final ImageButton btnCriarClube = view.findViewById(R.id.criar_clube_btnCriarClube);
        btnCriarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNomeClube.getText().toString().trim().equals("") && !edtDescricaoClube.getText().toString().trim().equals("")) {
                    PytacoRequestDAO request = new PytacoRequestDAO(MainActivity.this);
                    request.criarClube(usuario.getId(), usuario.getChaveAcesso(), edtNomeClube.getText().toString(), edtDescricaoClube.getText().toString());
                }
            }
        });

        dialogNovoClube.show();
    }

    @Override
    public PytacoRequestEnum getPytacoRequest() {
        return pytacoRequestEnum;
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        pCancelDialog();
        pEnableScreen();

        if (dialogNovoClube.isShowing()) {
            dialogNovoClube.cancel();
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        if (dialogNovoClube.isShowing()) {
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

    @Override
    public Context getContext() {
        return this;
    }
}
