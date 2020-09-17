package br.com.enterprise.pytaco.pojo;

public class Membro extends EntidadeBase {

    private String nome;
    private Double qtdFicha;
    private String codClube;
    private String tipo;
    private String status;
    private Clube clube;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getQtdFicha() {
        return qtdFicha;
    }

    public void setQtdFicha(Double qtdFicha) {
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

    public String getCodClube() {
        return codClube;
    }

    public void setCodClube(String codClube) {
        this.codClube = codClube;
    }

    public Clube getClube() {
        return clube;
    }

    public void setClube(Clube clube) {
        this.clube = clube;
    }

    public String getStatusExt() {
        switch (this.status) {
            case "0":
                return "Inativo";
            case "1":
                return "Ativo";
            case "4":
                return "Aguardando aprovação";
            default:
                return "";
        }
    }

    public String getTipoExt() {
        switch (this.tipo) {
            case "1":
                return "Normal";
            case "2":
                return "Agente";
            case "3":
                return "Administrador";
            default:
                return "";
        }
    }
}
