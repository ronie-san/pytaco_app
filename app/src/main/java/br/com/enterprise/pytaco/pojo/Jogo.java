package br.com.enterprise.pytaco.pojo;

public class Jogo extends BaseEntity {

    private Liga liga;
    private String eventDate;
    private String statusShort;
    private String local;
    private Integer golsTimeHome;
    private Integer golsTimeAway;
    private Time homeTime;
    private Time awayTime;

    public Liga getLiga() {
        return liga;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatusShort() {
        return statusShort;
    }

    public void setStatusShort(String statusShort) {
        this.statusShort = statusShort;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getGolsTimeHome() {
        return golsTimeHome;
    }

    public void setGolsTimeHome(Integer golsTimeHome) {
        this.golsTimeHome = golsTimeHome;
    }

    public Integer getGolsTimeAway() {
        return golsTimeAway;
    }

    public void setGolsTimeAway(Integer golsTimeAway) {
        this.golsTimeAway = golsTimeAway;
    }

    public Time getHomeTime() {
        return homeTime;
    }

    public void setHomeTime(Time homeTime) {
        this.homeTime = homeTime;
    }

    public Time getAwayTime() {
        return awayTime;
    }

    public void setAwayTime(Time awayTime) {
        this.awayTime = awayTime;
    }

    public String getStatusExt() {
        switch (statusShort) {
            case "NS":
                return "Marcado";
            case "PST":
                return "Adiado";
            case "CANC":
                return "Cancelado";
            default:
                return "";
        }
    }
}
