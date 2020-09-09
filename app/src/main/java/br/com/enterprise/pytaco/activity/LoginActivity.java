package br.com.enterprise.pytaco.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class LoginActivity extends BaseActivity implements IActivity {

    private static final String PREFS_NAME = "PrefsFile";
    private EditText edtUsuario;
    private EditText edtSenha;
    private CheckBox chkLembrar;
    private SharedPreferences preferences;
    private PytacoRequestEnum pytacoRequestEnum = PytacoRequestEnum.NONE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        lblEsqueciSenha.setText(Html.fromHtml(String.format(getString(R.string.esqueci_senha))));
        lblEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblEsqueciSenhaClick();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    public PytacoRequestEnum getPytacoRequest() {
        return pytacoRequestEnum;
    }

    @Override
    public void onJsonSuccess(JSONObject response) {
        if (!this.isDestroyed()) {
            try {
                if (!response.getJSONArray("entry").getJSONObject(0).getString("id_usuario").equals("")) {
                    if (chkLembrar.isChecked()) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("usuario", edtUsuario.getText().toString());
                        editor.putString("senha", edtSenha.getText().toString());
                        editor.putBoolean("lembrar", chkLembrar.isChecked());
                        editor.apply();
                    } else {
                        preferences.edit().clear().apply();
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    Usuario usuario = new Usuario();
                    usuario.setId(Integer.parseInt(response.getJSONArray("entry").getJSONObject(0).getString("id_usuario")));
                    usuario.setChaveAcesso(response.getJSONArray("entry").getJSONObject(0).getString("chaveacesso"));
                    usuario.setQtdPytaco(Integer.parseInt(response.getJSONArray("entry").getJSONObject(0).getString("qtdpytacosglobal")));
                    usuario.setQtdFicha(Integer.parseInt(response.getJSONArray("entry").getJSONObject(0).getString("qtdfichasglobal")));
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } else {
                    pCancelDialog();
                    pEnableScreen();
                    makeLongToast("Usuário e/ou senha inválidos.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                pCancelDialog();
                pEnableScreen();
                makeLongToast("Não foi possível entrar. Houve erro na autenticação");
            }
        }
    }

    @Override
    public void onSucess(String response) {
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

    @Override
    public Context getContext() {
        return this;
    }
}
