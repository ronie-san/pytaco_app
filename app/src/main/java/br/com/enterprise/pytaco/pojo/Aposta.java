package br.com.enterprise.pytaco.pojo;

public class Aposta extends BaseEntity {

    private Integer pontos;
    private Integer idUsuario;
    private Double valorBolao;
    private Double percentualPremiacao;

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getValorBolao() {
        return valorBolao;
    }

    public void setValorBolao(Double valorBolao) {
        this.valorBolao = valorBolao;
    }

    public Double getPercentualPremiacao() {
        return percentualPremiacao;
    }

    public void setPercentualPremiacao(Double percentualPremiacao) {
        this.percentualPremiacao = percentualPremiacao;
    }
}
