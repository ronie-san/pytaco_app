package br.com.enterprise.pytaco.pojo;

public class PytacoTrocado extends BaseEntity {

    private String nomeUsuario;
    private Double qtdFicha;
    private Double qtdPytaco;

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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
