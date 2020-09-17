package br.com.enterprise.pytaco.pojo;

public class Usuario extends EntidadeBase {

    private String nome;
    private String celular;
    private String chaveAcesso;
    private Double qtdPytaco;
    private Double qtdFicha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public Double getQtdPytaco() {
        return qtdPytaco;
    }

    public void setQtdPytaco(Double qtdPytaco) {
        this.qtdPytaco = qtdPytaco;
    }

    public Double getQtdFicha() {
        return qtdFicha;
    }

    public void setQtdFicha(Double qtdFicha) {
        this.qtdFicha = qtdFicha;
    }
}
