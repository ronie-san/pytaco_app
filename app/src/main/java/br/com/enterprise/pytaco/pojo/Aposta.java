package br.com.enterprise.pytaco.pojo;

public class Aposta extends BaseEntity {

    private Integer pontos;
    private String nomeUsuario;
    private Double valorBolao;
    private Double percentualPremiacao;

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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
