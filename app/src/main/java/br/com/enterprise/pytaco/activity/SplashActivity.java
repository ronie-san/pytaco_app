package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Usuario;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class SplashActivity extends BaseActivity {

    private String nomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String PREFS_NAME = "PrefsFile";
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        nomeUsuario = preferences.getString("usuario", "");
        String senha = preferences.getString("senha", "");

        if (nomeUsuario != null && !nomeUsuario.isEmpty() && senha != null && !senha.isEmpty()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.login(nomeUsuario, senha);
        } else {
            pStartActivityClearTop(LoginActivity.class);
        }
    }

    @Override
    public void onSucess(String response) {
        if (this.pytacoRequestEnum.equals(PytacoRequestEnum.LOGIN)) {
            pytacoRequestEnum = PytacoRequestEnum.NONE;

            try {
                JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);

                if (!resp.getString("id_usuario").equals("")) {
                    usuario = new Usuario();
                    usuario.setId(Integer.parseInt(resp.getString("id_usuario")));
                    usuario.setChaveAcesso(resp.getString("chaveacesso"));
                    usuario.setNome(nomeUsuario);
                    usuario.setQtdPytaco(Double.parseDouble(resp.getString("qtdpytacosglobal")));
                    usuario.setQtdFicha(Double.parseDouble(resp.getString("qtdfichasglobal")));
                    usuario.setCodUsuario(resp.getString("codusuarioglobal"));

                    Intent intent = new Intent(this, ClubesActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException ignored) {
                pStartActivityClearTop(LoginActivity.class);
            }
        } else {
            pytacoRequestEnum = PytacoRequestEnum.NONE;
            pStartActivityClearTop(LoginActivity.class);
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        pytacoRequestEnum = PytacoRequestEnum.NONE;
        pStartActivityClearTop(LoginActivity.class);
    }
}