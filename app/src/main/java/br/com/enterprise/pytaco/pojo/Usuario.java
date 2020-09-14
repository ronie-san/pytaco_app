package br.com.enterprise.pytaco.pojo;

public class Usuario extends EntidadeBase {

    private String nome;
    private String celular;
    private String chaveAcesso;
    private Integer qtdPytaco;
    private Integer qtdFicha;

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

    public Integer getQtdPytaco() {
        return qtdPytaco;
    }

    public void setQtdPytaco(Integer qtdPytaco) {
        this.qtdPytaco = qtdPytaco;
    }

    public Integer getQtdFicha() {
        return qtdFicha;
    }

    public void setQtdFicha(Integer qtdFicha) {
        this.qtdFicha = qtdFicha;
    }
}
