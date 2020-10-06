package br.com.enterprise.pytaco.activity;

import android.os.Bundle;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Jogo;
import br.com.enterprise.pytaco.pojo.Liga;
import br.com.enterprise.pytaco.pojo.Time;
import br.com.enterprise.pytaco.util.DateUtil;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class SplashActivity extends BaseActivity {

    private int qtdRequestListaJogos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lstJogo.clear();
        pListaJogos(DateUtil.addDay(DateUtil.getDate()));
    }

    @Override
    public void onStartRequest() {
        pDisableScreen();
    }

    @Override
    public void onSucess(String response) {
        if (!isDestroyed()) {
            pTrataRespostaListaJogos(response);
        }
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        pytacoRequestEnum = PytacoRequestEnum.NONE;
        qtdRequestListaJogos = 0;
        makeShortToast(error.getMessage());
        pStartActivity(LoginActivity.class);
    }

    private void pListaJogos(Date data) {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaJogos(DateUtil.toAPIFormat(data));
    }

    private void pTrataRespostaListaJogos(@NotNull String response) {
        try {
            ++qtdRequestListaJogos;
            JSONArray resp = new JSONObject(response).getJSONObject("api").getJSONArray("fixtures");

            for (int i = 0; i < resp.length(); i++) {
                JSONObject item = resp.getJSONObject(i);
                Jogo jogo = new Jogo();

                Time homeTime = new Time();
                homeTime.setId(item.getJSONObject("homeTeam").getInt("team_id"));
                homeTime.setName(item.getJSONObject("homeTeam").getString("team_name"));
                jogo.setHomeTime(homeTime);

                Time awayTime = new Time();
                awayTime.setId(item.getJSONObject("awayTeam").getInt("team_id"));
                awayTime.setName(item.getJSONObject("awayTeam").getString("team_name"));
                jogo.setAwayTime(awayTime);

                Liga league = new Liga();
                league.setId(item.getInt("league_id"));
                league.setName(item.getJSONObject("league").getString("name"));
                league.setCountry(item.getJSONObject("league").getString("country"));
                jogo.setLiga(league);

                jogo.setId(item.getInt("fixture_id"));
                jogo.setEventDate(item.getString("event_date"));
                jogo.setGolsTimeAway(item.isNull("goalsAwayTeam") ? null : item.getInt("goalsAwayTeam"));
                jogo.setGolsTimeHome(item.isNull("goalsHomeTeam") ? null : item.getInt("goalsHomeTeam"));
                jogo.setLocal(item.getString("venue"));
                jogo.setStatusShort(item.getString("statusShort"));
                lstJogo.add(jogo);
            }

            if (qtdRequestListaJogos < 4) {
                pListaJogos(DateUtil.addDay(DateUtil.getDate(), qtdRequestListaJogos + 1));
            } else {
                qtdRequestListaJogos = 0;
                pStartActivity(LoginActivity.class);
            }
        } catch (JSONException ignored) {
        }
    }
}