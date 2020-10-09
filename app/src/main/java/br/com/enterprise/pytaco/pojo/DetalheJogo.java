package br.com.enterprise.pytaco.pojo;

public class DetalheJogo extends BaseEntity {

    private String nome;
    private Integer idOtherTeam;
    private String nomeOtherTeam;
    private Integer gols;
    private Integer golsOtherTeam;
    private Integer pontosJogo;
    private Integer pontosProrrogacao;
    private Integer pontosPenaltis;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdOtherTeam() {
        return idOtherTeam;
    }

    public void setIdOtherTeam(Integer idOtherTeam) {
        this.idOtherTeam = idOtherTeam;
    }

    public String getNomeOtherTeam() {
        return nomeOtherTeam;
    }

    public void setNomeOtherTeam(String nomeOtherTeam) {
        this.nomeOtherTeam = nomeOtherTeam;
    }

    public Integer getGols() {
        return gols;
    }

    public void setGols(Integer gols) {
        this.gols = gols;
    }

    public Integer getGolsOtherTeam() {
        return golsOtherTeam;
    }

    public void setGolsOtherTeam(Integer golsOtherTeam) {
        this.golsOtherTeam = golsOtherTeam;
    }

    public Integer getPontosJogo() {
        return pontosJogo;
    }

    public void setPontosJogo(Integer pontosJogo) {
        this.pontosJogo = pontosJogo;
    }

    public Integer getPontosProrrogacao() {
        return pontosProrrogacao;
    }

    public void setPontosProrrogacao(Integer pontosProrrogacao) {
        this.pontosProrrogacao = pontosProrrogacao;
    }

    public Integer getPontosPenaltis() {
        return pontosPenaltis;
    }

    public void setPontosPenaltis(Integer pontosPenaltis) {
        this.pontosPenaltis = pontosPenaltis;
    }
}
