package br.com.enterprise.pytaco.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.MaskEditUtil;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class CriarContaActivity extends BaseActivity {

    private EditText edtCelular;
    private EditText edtUsuario;
    private EditText edtSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        edtUsuario = findViewById(R.id.criar_conta_edtUsuario);
        edtSenha = findViewById(R.id.criar_conta_edtSenha);
        edtCelular = findViewById(R.id.criar_conta_edtCelular);
        edtCelular.addTextChangedListener(MaskEditUtil.mask(edtCelular, MaskEditUtil.FORMAT_FONE));
        ImageButton lblVoltar = findViewById(R.id.criar_clube_btnVoltar);
        lblVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblVoltarClick();
            }
        });
        final ImageButton btnCriarConta = findViewById(R.id.criar_conta_btnCriarConta);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCriarContaClick();
            }
        });
    }

    private void btnCriarContaClick() {
        if (pValidaCampos()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.criarConta(edtUsuario.getText().toString().trim(), edtSenha.getText().toString(), edtCelular.getText().toString());
        }
    }

    private boolean pValidaCampos() {
        if (edtUsuario.getText().toString().trim().equals("")) {
            return false;
        }

        if (edtSenha.getText().toString().trim().equals("")) {
            return false;
        }

        return !edtCelular.getText().toString().trim().equals("");
    }

    private void lblVoltarClick() {
        this.onBackPressed();
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            makeLongToast("Conta criada com sucesso!");
        }

        super.onJsonSuccess(response);
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();

            switch (response) {
                case "Cadastrou":
                    makeLongToast("Conta criada com sucesso!");
                    finish();
                    break;
                case "Cadastrado":
                    makeLongToast("Usuário já cadastrado!");
                    break;
                default:
                    makeLongToast("Não foi possível criar conta.");
            }
        }

        super.onSucess(response);
    }

    @Override
    public void onError(VolleyError error) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            //Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            makeLongToast("Não foi possível criar conta. Erro na comunicação.");
        }

        super.onError(error);
    }
}
