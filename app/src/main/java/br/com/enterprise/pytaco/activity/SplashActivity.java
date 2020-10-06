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
import br.com.enterprise.pytaco.pojo.Fixture;
import br.com.enterprise.pytaco.pojo.League;
import br.com.enterprise.pytaco.pojo.Team;
import br.com.enterprise.pytaco.util.DateUtil;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class SplashActivity extends BaseActivity {

    private int qtdRequestListaJogos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lstFixture.clear();
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
                Fixture fixture = new Fixture();

                Team homeTeam = new Team();
                homeTeam.setId(item.getJSONObject("homeTeam").getInt("team_id"));
                homeTeam.setName(item.getJSONObject("homeTeam").getString("team_name"));
                fixture.setHomeTeam(homeTeam);

                Team awayTeam = new Team();
                homeTeam.setId(item.getJSONObject("awayTeam").getInt("team_id"));
                homeTeam.setName(item.getJSONObject("awayTeam").getString("team_name"));
                fixture.setAwayTeam(awayTeam);

                League league = new League();
                league.setId(item.getInt("league_id"));
                league.setName(item.getJSONObject("league").getString("name"));
                league.setCountry(item.getJSONObject("league").getString("country"));
                fixture.setLeague(league);

                fixture.setId(item.getInt("fixture_id"));
                fixture.setEventDate(item.getString("event_date"));

                fixture.setGoalsAwayTeam(item.isNull("goalsAwayTeam") ? null : item.getInt("goalsAwayTeam"));
                fixture.setGoalsHomeTeam(item.isNull("goalsHomeTeam") ? null : item.getInt("goalsHomeTeam"));
                fixture.setVenue(item.getString("venue"));
                fixture.setStatusShort(item.getString("statusShort"));
                lstFixture.add(fixture);
            }

            if (qtdRequestListaJogos < 4) {
                pListaJogos(DateUtil.addDay(DateUtil.getDate(), qtdRequestListaJogos + 1));
            } else {
                qtdRequestListaJogos = 0;
                pStartActivity(LoginActivity.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}