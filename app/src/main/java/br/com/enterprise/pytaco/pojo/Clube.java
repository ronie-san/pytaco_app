package br.com.enterprise.pytaco.pojo;

import java.io.Serializable;

public class Clube implements Serializable {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer qtdPytaco;
    private Integer qtdFicha;
    private Integer idUsuario;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
