package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.StringUtil;

public class LoginActivity extends BaseActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private EditText edtUsuario;
    private EditText edtSenha;
    private CheckBox chkLembrar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnTeste = findViewById(R.id.login_btnTeste);
        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TesteActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnEntrar = findViewById(R.id.login_btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEntrarClick();
            }
        });

        ImageButton btnCriarConta = findViewById(R.id.login_btnCriarConta);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCriarContaClick();
            }
        });

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        chkLembrar = findViewById(R.id.login_chkLembrar);
        chkLembrar.setChecked(preferences.getBoolean("lembrar", false));

        edtUsuario = findViewById(R.id.login_edtUsuario);
        edtUsuario.setText(preferences.getString("usuario", ""));

        edtSenha = findViewById(R.id.login_edtSenha);
        edtSenha.setText(preferences.getString("senha", ""));

        TextView lblEsqueciSenha = findViewById(R.id.login_lblEsqueciSenha);
        lblEsqueciSenha.setText(StringUtil.textoSublinhado(getString(R.string.esqueci_senha)));
        lblEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblEsqueciSenhaClick();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pCancelDialog();
        pEnableScreen();
    }

    private void btnCriarContaClick() {
        Intent intent = new Intent(this, CriarContaActivity.class);
        startActivity(intent);
    }

    private void lblEsqueciSenhaClick() {
        if (!edtUsuario.getText().toString().trim().isEmpty()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.lembrarSenha(edtUsuario.getText().toString().trim());
        }
    }

    private void btnEntrarClick() {
        if (pValidaCampos()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.login(edtUsuario.getText().toString(), edtSenha.getText().toString());
        }
    }

    private boolean pValidaCampos() {
        if (edtUsuario.getText().toString().trim().equals("")) {
            edtUsuario.requestFocus();
            makeShortToast("Digite seu usuário.");
            return false;
        }

        if (edtSenha.getText().toString().trim().equals("")) {
            edtSenha.requestFocus();
            makeShortToast("Digite sua senha.");
            return false;
        }

        return true;
    }

    private void pTrataRespostaLogin(@NotNull String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            if (!resp.getString("id_usuario").equals("")) {
                if (chkLembrar.isChecked()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("usuario", edtUsuario.getText().toString());
                    editor.putString("senha", edtSenha.getText().toString());
                    editor.putBoolean("lembrar", chkLembrar.isChecked());
                    editor.apply();
                } else {
                    preferences.edit().clear().apply();
                }

                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(resp.getString("id_usuario")));
                usuario.setChaveAcesso(resp.getString("chaveacesso"));
                usuario.setNome(edtUsuario.getText().toString().trim());
                usuario.setQtdPytaco(Double.parseDouble(resp.getString("qtdpytacosglobal")));
                usuario.setQtdFicha(Double.parseDouble(resp.getString("qtdfichasglobal")));
                usuario.setCodUsuario(resp.getString("codusuarioglobal"));

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(getString(R.string.usuario), usuario);
                startActivity(intent);
            } else {
                pCancelDialog();
                pEnableScreen();
                makeLongToast("Usuário e/ou senha inválidos.");
            }
        } catch (JSONException e) {
            pCancelDialog();
            pEnableScreen();
            makeLongToast("Não foi possível entrar. Houve erro na autenticação");
        }
    }

    private void pTrataRespostaLembrarSenha(String response) {
        pCancelDialog();
        pEnableScreen();
        makeLongToast("Um SMS com nova senha será enviado");
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LEMBRAR_SENHA:
                    pTrataRespostaLembrarSenha(response);
                    break;
                case LOGIN:
                    pTrataRespostaLogin(response);
                    break;
                default:
                    makeLongToast("Requisição não reconhecida: " + pytacoRequestEnum.toString());
                    break;
            }
        }

        super.onSucess(response);
    }
}
