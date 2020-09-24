package br.com.enterprise.pytaco.pojo;

public class Clube extends EntidadeBase {

    private String nome;
    private String descricao;
    private Double qtdPytaco;
    private Double qtdFicha;
    private String codClube;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCodClube() {
        return codClube;
    }

    public void setCodClube(String codClube) {
        this.codClube = codClube;
    }
}
