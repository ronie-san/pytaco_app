package br.com.enterprise.pytaco.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

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
                pStartActivity(TesteActivity.class);
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
        usuario = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        pCancelLoading();
        pEnableScreen();
    }

    private void btnCriarContaClick() {
        pStartActivity(CriarContaActivity.class);
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
                SharedPreferences.Editor editor = preferences.edit();

                if (chkLembrar.isChecked()) {
                    editor.putString("usuario", edtUsuario.getText().toString());
                    editor.putString("senha", edtSenha.getText().toString());
                    editor.putBoolean("lembrar", chkLembrar.isChecked());
                } else {
                    if (preferences.contains("usuario")) {
                        editor.remove("usuario");
                    }

                    if (preferences.contains("senha")) {
                        editor.remove("senha");
                    }

                    if (preferences.contains("lembrar")) {
                        editor.remove("lembrar");
                    }
                }

                editor.apply();

                usuario = new Usuario();
                usuario.setId(Integer.parseInt(resp.getString("id_usuario")));
                usuario.setChaveAcesso(resp.getString("chaveacesso"));
                usuario.setNome(edtUsuario.getText().toString().trim());
                usuario.setQtdPytaco(StringUtil.strToNumber(resp.getString("qtdpytacosglobal")));
                usuario.setQtdFicha(StringUtil.strToNumber(resp.getString("qtdfichasglobal")));
                usuario.setCodUsuario(resp.getString("codusuarioglobal"));
                
                pStartActivity(ClubesActivity.class);
            } else {
                pCancelLoading();
                pEnableScreen();
                makeLongToast("Usuário e/ou senha inválidos.");
            }
        } catch (JSONException e) {
            pCancelLoading();
            pEnableScreen();
            makeLongToast("Não foi possível entrar. Houve erro na autenticação");
        }
    }

    private void pTrataRespostaLembrarSenha() {
        pCancelLoading();
        pEnableScreen();
        makeLongToast("Um SMS com nova senha será enviado");
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LEMBRAR_SENHA:
                    pTrataRespostaLembrarSenha();
                    break;
                case LOGIN:
                    pTrataRespostaLogin(response);
                    break;
                default:
                    break;
            }
        }

        super.onSucess(response);
    }
}