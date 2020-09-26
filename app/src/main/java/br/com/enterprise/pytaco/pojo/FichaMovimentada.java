package br.com.enterprise.pytaco.pojo;

public class FichaMovimentada extends BaseEntity {

    private String nomeUsuarioEnvio;
    private String nomeUsuarioRecebimento;
    private Double qtdFichaAnterior;
    private Double qtdFichaAtual;
    private Double qtdFichaMovimento;
    private Double saldoAtualAdmin;
    private String acao;

    public String getNomeUsuarioEnvio() {
        return nomeUsuarioEnvio;
    }

    public void setNomeUsuarioEnvio(String nomeUsuarioEnvio) {
        this.nomeUsuarioEnvio = nomeUsuarioEnvio;
    }

    public String getNomeUsuarioRecebimento() {
        return nomeUsuarioRecebimento;
    }

    public void setNomeUsuarioRecebimento(String nomeUsuarioRecebimento) {
        this.nomeUsuarioRecebimento = nomeUsuarioRecebimento;
    }

    public Double getQtdFichaAnterior() {
        return qtdFichaAnterior;
    }

    public void setQtdFichaAnterior(Double qtdFichaAnterior) {
        this.qtdFichaAnterior = qtdFichaAnterior;
    }

    public Double getQtdFichaAtual() {
        return qtdFichaAtual;
    }

    public void setQtdFichaAtual(Double qtdFichaAtual) {
        this.qtdFichaAtual = qtdFichaAtual;
    }

    public Double getQtdFichaMovimento() {
        return qtdFichaMovimento;
    }

    public void setQtdFichaMovimento(Double qtdFichaMovimento) {
        this.qtdFichaMovimento = qtdFichaMovimento;
    }

    public Double getSaldoAtualAdmin() {
        return saldoAtualAdmin;
    }

    public void setSaldoAtualAdmin(Double saldoAtualAdmin) {
        this.saldoAtualAdmin = saldoAtualAdmin;
    }

    public Double getSaldoAnteriorAdmin() {
        return saldoAnteriorAdmin;
    }

    public void setSaldoAnteriorAdmin(Double saldoAnteriorAdmin) {
        this.saldoAnteriorAdmin = saldoAnteriorAdmin;
    }

    private Double saldoAnteriorAdmin;

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }
}
