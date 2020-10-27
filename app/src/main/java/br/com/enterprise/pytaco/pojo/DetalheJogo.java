package br.com.enterprise.pytaco.pojo;

import java.util.Objects;

public class DetalheJogo extends BaseEntity {

    private String idTeam;
    private String nome;
    private String status;
    private String idOtherTeam;
    private String nomeOtherTeam;
    private String gols;
    private String golsOtherTeam;
    private String pontosJogo;
    private String pontosProrrogacao;
    private String pontosPenaltis;
    private String idAposta;

    public DetalheJogo() {
        this.idAposta = "";
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdOtherTeam() {
        return idOtherTeam;
    }

    public void setIdOtherTeam(String idOtherTeam) {
        this.idOtherTeam = idOtherTeam;
    }

    public String getNomeOtherTeam() {
        return nomeOtherTeam;
    }

    public void setNomeOtherTeam(String nomeOtherTeam) {
        this.nomeOtherTeam = nomeOtherTeam;
    }

    public String getGols() {
        return gols;
    }

    public void setGols(String gols) {
        this.gols = gols;
    }

    public String getGolsOtherTeam() {
        return golsOtherTeam;
    }

    public void setGolsOtherTeam(String golsOtherTeam) {
        this.golsOtherTeam = golsOtherTeam;
    }

    public String getPontosJogo() {
        return pontosJogo;
    }

    public void setPontosJogo(String pontosJogo) {
        this.pontosJogo = pontosJogo;
    }

    public String getPontosProrrogacao() {
        return pontosProrrogacao;
    }

    public void setPontosProrrogacao(String pontosProrrogacao) {
        this.pontosProrrogacao = pontosProrrogacao;
    }

    public String getPontosPenaltis() {
        return pontosPenaltis;
    }

    public void setPontosPenaltis(String pontosPenaltis) {
        this.pontosPenaltis = pontosPenaltis;
    }

    public String getIdAposta() {
        return idAposta;
    }

    public void setIdAposta(String idAposta) {
        this.idAposta = idAposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DetalheJogo that = (DetalheJogo) o;
        return idTeam.equals(that.idTeam) &&
                idOtherTeam.equals(that.idOtherTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idTeam, idOtherTeam);
    }
}
