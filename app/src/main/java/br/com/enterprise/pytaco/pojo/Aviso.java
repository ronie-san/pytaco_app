package br.com.enterprise.pytaco.pojo;

import java.util.Objects;

public class Aviso extends EntidadeBase {

    private String titulo;
    private String status;
    private String data;
    private Integer idTabela;
    private String descricao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(Integer idTabela) {
        this.idTabela = idTabela;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatusExt() {
        switch (this.status) {
            case "E":
                return "Não lido";
            case "L":
                return "Lido";
            default:
                return "";
        }
    }
}
