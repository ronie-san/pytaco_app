package br.com.enterprise.pytaco.pojo;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

    private Integer id;
    private String nome;
    private String celular;
    private String chaveAcesso;
    private Integer qtdPytaco;
    private Integer qtdFicha;
    private List<Clube> lstClube;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Clube> getLstClube() {
        return lstClube;
    }

    public void setLstClube(List<Clube> lstClube) {
        this.lstClube = lstClube;
    }
}
