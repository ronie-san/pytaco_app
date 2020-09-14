package br.com.enterprise.pytaco.pojo;

public class Membro extends EntidadeBase {

    private String nome;
    private Integer qtdFicha;
    private String tipo;
    private String status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdFicha() {
        return qtdFicha;
    }

    public void setQtdFicha(Integer qtdFicha) {
        this.qtdFicha = qtdFicha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
