package br.com.enterprise.pytaco.dao;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import br.com.enterprise.pytaco.activity.IActivity;

public class APIFootballRequestDAO extends BasicRequestDAO {

    public APIFootballRequestDAO(IActivity activity) {
        super(activity);
        useKeyHeader = true;
        baseUrl = "https://api-football-v1.p.rapidapi.com/v2/";
    }

    //region TIMEZONE
    public void getTimeZone() {
        pGetJsonRequest("timezone");
    }
    //endregion

    //region SEASONS
    public void getSeasons() {
        pGetJsonRequest("seasons");
    }
    //endregion

    //region COUNTRIES
    public void getCountries() {
        pGetJsonRequest("countries");
    }
    //endregion

    //region LEAGUES
    public void getAllLeagues() {
        pGetJsonRequest("leagues");
    }

    public void getLeagueById(Integer idLeague) {
        pGetJsonRequest("leagues/league/" + idLeague);
    }

    public void getLeagueByTeam(Integer idTeam) {
        pGetJsonRequest("leagues/team/" + idTeam);
    }

    public void getLeagueByTeamSeason(Integer idTeam, Integer season) {
        pGetJsonRequest("leagues/team/" + idTeam + "/" + season);
    }

    public void getLeagueBySearch(@NonNull String search) {
        pGetJsonRequest("leagues/search/" + search.trim().replace(" ", "_"));
    }

    public void getLeaguesByCountry(@NonNull String country) {
        pGetJsonRequest("leagues/country/" + country.trim());
    }

    public void getLeagueByCountrySeason(@NonNull String country, Integer season) {
        pGetJsonRequest("leagues/country/" + country.trim() + "/" + season);
    }

    public void getLeagueByCode(@NonNull String code) {
        pGetJsonRequest("leagues/country/" + code.trim());
    }

    public void getLeagueByCodeSeason(@NonNull String code, Integer season) {
        pGetJsonRequest("leagues/country/" + code.trim() + "/" + season);
    }

    public void getLeagueBySeason(Integer season) {
        pGetJsonRequest("leagues/season/" + season);
    }

    public void getAllSeasons(Integer idLeague, @Nullable Integer season) {
        pGetJsonRequest("leagues/seasonsAvailable/" + idLeague + (season != null ? "/" + season : ""));
    }
    //endregion

    //region TEAMS
    public void getTeamById(Integer idTeam) {
        pGetJsonRequest("teams/team/" + idTeam);
    }

    public void getTeamByLeague(Integer idLeague) {
        pGetJsonRequest("teams/league/" + idLeague);
    }

    public void getTeamBySearch(@NonNull String search) {
        pGetJsonRequest("teams/search/" + search.trim().replace(" ", "_"));
    }
    //endregion

    //region STATISTICS
    public void getStatistis(Integer idLeague, Integer idTeam, @Nullable Date endDate) {
        String date = "";

        if (endDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = "/" + sdf.format(endDate);
        }

        pGetJsonRequest("statistics/" + idLeague + "/" + idTeam + date);
    }


    public void getStatisticsByFixture(Integer idFixture) {
        pGetJsonRequest("statistics/fixture/" + idFixture);
    }
    //endregion

    //region STANDINGS
    public void getStandings(Integer idLeague) {
        pGetJsonRequest("leagueTable/" + idLeague);
    }
    //endregion

    //region FIXTURES
    public void getRoundsByLeague(Integer idLeague, @Nullable String current) {
        pGetJsonRequest("fixtures/rounds/" + idLeague + (current != null ? "/" + current : ""));
    }

    public void getFixturesById(Integer idFixture, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/id/" + idFixture, map);
    }

    public void getAllFixtures() {
        pGetJsonRequest("fixtures/live");
    }

    public void getFixturesByLeagues(Integer... leagues) {
        pGetJsonRequest("fixtures/live/" + TextUtils.join("-", leagues));
    }

    public void getFixturesByDate(Date date, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        pGetJsonRequest("fiztures/date/" + sdf.format(date), map);
    }

    public void getFixturesByLeague(Integer idLeague, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/league/" + idLeague, map);
    }

    public void getFixturesByLeagueDate(Integer idLeague, Date date, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        pGetJsonRequest("fixtures/league/" + idLeague + "/" + sdf.format(date), map);
    }

    public void getFixturesByLeagueDate(Integer idLeague, String round, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/league/" + idLeague + "/" + round, map);
    }

    public void getFixturesByTeam(Integer idTeam, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/team/" + idTeam, map);
    }

    public void getFixturesByTeamLeague(Integer idTeam, Integer idLeague, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/team/" + idTeam + "/" + idLeague, map);
    }

    public void getAllHeadToHead(Integer idTeam, @Nullable String timezone) {
        HashMap<String, String> map = null;

        if (timezone != null) {
            map = new HashMap<>();
            map.put("timezone", timezone);
        }

        pGetJsonRequest("fixtures/h2h/" + idTeam + "/" + idTeam, map);
    }
    //endregion

    //region EVENTS
    public void getEventsByFixture(Integer idFixture) {
        pGetJsonRequest("events/" + idFixture);
    }
    //endregion

    //region LINEUPS
    public void getLineupsByFixture(Integer idFixture) {
        pGetJsonRequest("lineups/" + idFixture);
    }
    //endregion

    //region PREDICTIONS
    public void getPredictionsByFixture(Integer idFixture) {
        pGetJsonRequest("predictions/" + idFixture);
    }
    //endregion

    //region COACHS
    public void getCoachById(Integer idCoach) {
        pGetJsonRequest("coachs/coach/" + idCoach);
    }

    public void getCoachByTeam(Integer idTeam) {
        pGetJsonRequest("coachs/team/" + idTeam);
    }

    public void getCoachsBySearch(String search) {
        pGetJsonRequest("coachs/search/" + search);
    }
    //endregion

    //region PLAYERS
    public void getAllPlayers() {
        pGetJsonRequest("players/seasons");
    }

    public void getPlayersSquadByTeamSeason(Integer idTeam, String season) {
        pGetJsonRequest("players/squad/" + idTeam + "/" + season);
    }

    public void getPlayerById(Integer idPlayer) {
        pGetJsonRequest("players/player/" + idPlayer);
    }

    public void getPlayerByIdSeason(Integer idPlayer, String season) {
        pGetJsonRequest("players/player/" + idPlayer + "/" + season);
    }

    public void getPlayersByTeam(Integer idTeam) {
        pGetJsonRequest("players/team/" + idTeam);
    }

    public void getPlayersByTeamSeason(Integer idTeam, String season) {
        pGetJsonRequest("players/team/" + idTeam + "/" + season);
    }

    public void getPlayersByFixture(Integer idFixture) {
        pGetJsonRequest("players/fixture/" + idFixture);
    }
    //endregion

    //region TOPSCORERS
    public void getTopScorersByLeague(Integer idLeague) {
        pGetJsonRequest("topscorers/" + idLeague);
    }
    //endregion

    //region TRANSFERS
    public void getTransfersByPlayer(Integer idPlayer) {
        pGetJsonRequest("transfers/player/" + idPlayer);
    }

    public void getTransfersByTeam(Integer idTeam) {
        pGetJsonRequest("transfers/team/" + idTeam);
    }
    //endregion

    //region ODDS
    public void getAllBookmakers() {
        pGetJsonRequest("odds/bookmakers");
    }

    public void getBookmakerById(Integer idBookmaker) {
        pGetJsonRequest("odds/bookmakers/" + idBookmaker);
    }

    public void getAllLabels() {
        pGetJsonRequest("odds/labels");
    }

    public void getLabelById(Integer idLabel) {
        pGetJsonRequest("odds/labels/" + idLabel);
    }

    public void getOddsByFixture(Integer idFixture) {
        pGetJsonRequest("odds/fixture/" + idFixture);
    }

    public void getOddsByFixtureBookmaker(Integer idFixture, Integer idBookmaker) {
        pGetJsonRequest("odds/fixture/" + idFixture + "/bookmaker/" + idBookmaker);
    }

    public void getOddsByFixtureLabel(Integer idFixture, Integer idLabel) {
        pGetJsonRequest("odds/fixture/" + idFixture + "/label/" + idLabel);
    }

    public void getOddsByLeague(Integer idLeague) {
        pGetJsonRequest("odds/league/" + idLeague);
    }

    public void getOddsByLeagueBookmaker(Integer idLeague, Integer idBookmaker) {
        pGetJsonRequest("odds/league/" + idLeague + "/bookmaker/" + idBookmaker);
    }

    public void getOddsByLeagueLabel(Integer idLeague, Integer idLabel) {
        pGetJsonRequest("odds/league/" + idLeague + "/label/" + idLabel);
    }
    //endregion
}

