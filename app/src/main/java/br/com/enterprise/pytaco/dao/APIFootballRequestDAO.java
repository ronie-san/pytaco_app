package br.com.enterprise.pytaco.dao;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.HashMap;

import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.util.DateUtil;

public class APIFootballRequestDAO extends BasicRequestDAO {

    public APIFootballRequestDAO(BaseActivity activity) {
        super(activity);
        useKeyHeader = true;
        baseUrl = "https://api-football-v1.p.rapidapi.com/v2/";
    }

    //region TIMEZONE
    public void getTimeZone() {
        pGetRequest("timezone");
    }
    //endregion

    //region SEASONS
    public void getSeasons() {
        pGetRequest("seasons");
    }
    //endregion

    //region COUNTRIES
    public void getCountries() {
        pGetRequest("countries");
    }
    //endregion

    //region LEAGUES
    public void getAllLeagues() {
        pGetRequest("leagues");
    }

    public void getLeagueById(Integer idLeague) {
        pGetRequest("leagues/league/" + idLeague);
    }

    public void getLeagueByTeam(Integer idTeam) {
        pGetRequest("leagues/team/" + idTeam);
    }

    public void getLeagueByTeamSeason(Integer idTeam, Integer season) {
        pGetRequest("leagues/team/" + idTeam + "/" + season);
    }

    public void getLeagueBySearch(@NonNull String search) {
        pGetRequest("leagues/search/" + search.trim().replace(" ", "_"));
    }

    public void getLeaguesByCountry(@NonNull String country) {
        pGetRequest("leagues/country/" + country.trim());
    }

    public void getLeagueByCountrySeason(@NonNull String country, Integer season) {
        pGetRequest("leagues/country/" + country.trim() + "/" + season);
    }

    public void getLeagueByCode(@NonNull String code) {
        pGetRequest("leagues/country/" + code.trim());
    }

    public void getLeagueByCodeSeason(@NonNull String code, Integer season) {
        pGetRequest("leagues/country/" + code.trim() + "/" + season);
    }

    public void getLeagueBySeason(Integer season) {
        pGetRequest("leagues/season/" + season);
    }

    public void getAllSeasons(Integer idLeague, @Nullable Integer season) {
        pGetRequest("leagues/seasonsAvailable/" + idLeague + (season != null ? "/" + season : ""));
    }
    //endregion

    //region TEAMS
    public void getTeamById(Integer idTeam) {
        pGetRequest("teams/team/" + idTeam);
    }

    public void getTeamByLeague(Integer idLeague) {
        pGetRequest("teams/league/" + idLeague);
    }

    public void getTeamBySearch(@NonNull String search) {
        pGetRequest("teams/search/" + search.trim().replace(" ", "_"));
    }
    //endregion

    //region STATISTICS
    public void getStatistis(Integer idLeague, Integer idTeam, @Nullable Date endDate) {
        String date = "";

        if (endDate != null) {
            date = "/" + DateUtil.toAPIFormat(endDate);
        }

        pGetRequest("statistics/" + idLeague + "/" + idTeam + date);
    }


    public void getStatisticsByFixture(Integer idFixture) {
        pGetRequest("statistics/fixture/" + idFixture);
    }
    //endregion

    //region STANDINGS
    public void getStandings(Integer idLeague) {
        pGetRequest("leagueTable/" + idLeague);
    }
    //endregion

    //region FIXTURES
    public void getRoundsByLeague(Integer idLeague, @Nullable String current) {
        pGetRequest("fixtures/rounds/" + idLeague + (current != null ? "/" + current : ""));
    }

    public void getFixturesById(Integer idFixture, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/id/" + idFixture, map);
    }

    public void getAllFixtures() {
        pGetRequest("fixtures/live");
    }

    public void getFixturesByLeagues(Integer... leagues) {
        pGetRequest("fixtures/live/" + TextUtils.join("-", leagues));
    }

    public void getFixturesByDate(Date date/*, @Nullable String timezone*/) {
        HashMap<String, String> map = null;

//        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", "America/Belem");
//        }

        pGetRequest("fiztures/date/" + DateUtil.toAPIFormat(date), map);
    }

    public void getFixturesByLeague(Integer idLeague, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/league/" + idLeague, map);
    }

    public void getFixturesByLeagueDate(Integer idLeague, Date date, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/league/" + idLeague + "/" + DateUtil.toAPIFormat(date), map);
    }

    public void getFixturesByLeagueDate(Integer idLeague, String round, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/league/" + idLeague + "/" + round, map);
    }

    public void getFixturesByTeam(Integer idTeam, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/team/" + idTeam, map);
    }

    public void getFixturesByTeamLeague(Integer idTeam, Integer idLeague, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/team/" + idTeam + "/" + idLeague, map);
    }

    public void getAllHeadToHead(Integer idTeam, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetRequest("fixtures/h2h/" + idTeam + "/" + idTeam, map);
    }
    //endregion

    //region EVENTS
    public void getEventsByFixture(Integer idFixture) {
        pGetRequest("events/" + idFixture);
    }
    //endregion

    //region LINEUPS
    public void getLineupsByFixture(Integer idFixture) {
        pGetRequest("lineups/" + idFixture);
    }
    //endregion

    //region PREDICTIONS
    public void getPredictionsByFixture(Integer idFixture) {
        pGetRequest("predictions/" + idFixture);
    }
    //endregion

    //region COACHS
    public void getCoachById(Integer idCoach) {
        pGetRequest("coachs/coach/" + idCoach);
    }

    public void getCoachByTeam(Integer idTeam) {
        pGetRequest("coachs/team/" + idTeam);
    }

    public void getCoachsBySearch(String search) {
        pGetRequest("coachs/search/" + search);
    }
    //endregion

    //region PLAYERS
    public void getAllPlayers() {
        pGetRequest("players/seasons");
    }

    public void getPlayersSquadByTeamSeason(Integer idTeam, String season) {
        pGetRequest("players/squad/" + idTeam + "/" + season);
    }

    public void getPlayerById(Integer idPlayer) {
        pGetRequest("players/player/" + idPlayer);
    }

    public void getPlayerByIdSeason(Integer idPlayer, String season) {
        pGetRequest("players/player/" + idPlayer + "/" + season);
    }

    public void getPlayersByTeam(Integer idTeam) {
        pGetRequest("players/team/" + idTeam);
    }

    public void getPlayersByTeamSeason(Integer idTeam, String season) {
        pGetRequest("players/team/" + idTeam + "/" + season);
    }

    public void getPlayersByFixture(Integer idFixture) {
        pGetRequest("players/fixture/" + idFixture);
    }
    //endregion

    //region TOPSCORERS
    public void getTopScorersByLeague(Integer idLeague) {
        pGetRequest("topscorers/" + idLeague);
    }
    //endregion

    //region TRANSFERS
    public void getTransfersByPlayer(Integer idPlayer) {
        pGetRequest("transfers/player/" + idPlayer);
    }

    public void getTransfersByTeam(Integer idTeam) {
        pGetRequest("transfers/team/" + idTeam);
    }
    //endregion

    //region ODDS
    public void getAllBookmakers() {
        pGetRequest("odds/bookmakers");
    }

    public void getBookmakerById(Integer idBookmaker) {
        pGetRequest("odds/bookmakers/" + idBookmaker);
    }

    public void getAllLabels() {
        pGetRequest("odds/labels");
    }

    public void getLabelById(Integer idLabel) {
        pGetRequest("odds/labels/" + idLabel);
    }

    public void getOddsByFixture(Integer idFixture) {
        pGetRequest("odds/fixture/" + idFixture);
    }

    public void getOddsByFixtureBookmaker(Integer idFixture, Integer idBookmaker) {
        pGetRequest("odds/fixture/" + idFixture + "/bookmaker/" + idBookmaker);
    }

    public void getOddsByFixtureLabel(Integer idFixture, Integer idLabel) {
        pGetRequest("odds/fixture/" + idFixture + "/label/" + idLabel);
    }

    public void getOddsByLeague(Integer idLeague) {
        pGetRequest("odds/league/" + idLeague);
    }

    public void getOddsByLeagueBookmaker(Integer idLeague, Integer idBookmaker) {
        pGetRequest("odds/league/" + idLeague + "/bookmaker/" + idBookmaker);
    }

    public void getOddsByLeagueLabel(Integer idLeague, Integer idLabel) {
        pGetRequest("odds/league/" + idLeague + "/label/" + idLabel);
    }
    //endregion
}

