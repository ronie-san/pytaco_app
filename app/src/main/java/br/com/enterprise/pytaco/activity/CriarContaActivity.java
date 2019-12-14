package br.com.enterprise.pytaco.activity;

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
import br.com.enterprise.pytaco.util.PytacoRequest;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class CriarContaActivity extends BaseActivity implements IActivity {

    private EditText edtCelular;
    private EditText edtUsuario;
    private EditText edtSenha;
    private PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

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
            PytacoRequest request = new PytacoRequest(this);
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
    public PytacoRequestEnum getPytacoRequest() {
        return pytacoRequestEnum;
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();

            switch (response) {
                case "Cadastrou":
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case "Cadastrado":
                    Toast.makeText(this, "Usuário já cadastrado!", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(this, "Não foi possível criar conta.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onError(VolleyError error) {
        if (!this.isDestroyed()) {
            pCancelDialog();
            pEnableScreen();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStartRequest() {
        pDisableScreen();
        pShowProgress();
    }
}
