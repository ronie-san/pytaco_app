package br.com.enterprise.pytaco.pojo;

public class Clube extends EntidadeBase {

    private String nome;
    private String descricao;
    private Integer qtdPytaco;
    private Integer qtdFicha;
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
