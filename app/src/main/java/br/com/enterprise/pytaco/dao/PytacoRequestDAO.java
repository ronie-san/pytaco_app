package br.com.enterprise.pytaco.dao;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class PytacoRequestDAO extends BasicRequestDAO {

    public PytacoRequestDAO(BaseActivity activity) {
        super(activity);
        useKeyHeader = false;
//        baseUrl = "http://easycliente.com.br/pitaco/php/"; //ANTIGO
        baseUrl = "http://pytaco.com.br/pytaco/php/";
    }

    public void criarConta(String login, String senha, String celular) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailCadastro", login);
        map.put("senha", senha);
        map.put("celular", celular);
        activity.setPytacoRequest(PytacoRequestEnum.CRIAR_CONTA);
        pGetRequest("CriarUsuario.php", map);
    }

    public void login(String login, String senha) {
        HashMap<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("senha", senha);
        activity.setPytacoRequest(PytacoRequestEnum.LOGIN);
        pGetRequest("login.php", map);
    }

    public void listaClubes(int idUsuario, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_CLUBES);
        pGetRequest("ListaClubes.php", map);
    }

    public void listaAvisos(int idUsuario, int idClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("id_clube", String.valueOf(idClube));
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_AVISOS);
        pGetRequest("listaAvisos.php", map);
    }

    public void listaMembros(int idClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_clube", String.valueOf(idClube));
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_MEMBROS);
        pGetRequest("ListaMembros.php", map);
    }

    public void criarClube(int idUsuario, String chaveAcesso, String nomeClube, String descricaoClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("nomeClube", nomeClube);
        map.put("descricaoClube", descricaoClube);
        activity.setPytacoRequest(PytacoRequestEnum.CRIAR_CLUBE);
        pGetRequest("CriarClube.php", map);
    }

    public void associar(int idUsuario, String chaveAcesso, String codigoClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("codigoClube", String.valueOf(codigoClube));
        activity.setPytacoRequest(PytacoRequestEnum.ASSOCIAR);
        pGetRequest("Associar.php", map);
    }

//    public void atualizaPytacosTelaInicial(int idUsuario) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("id_usuario", String.valueOf(idUsuario));
//        activity.setPytacoRequest(PytacoRequestEnum.ATUALIZA_PYTACOS);
//        pGetRequest("AtualizaPytacosTelaInicial.php", map);
//    }

    public void alteraSenha(int idUsuario, String senhaAtual, String senhaNova, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("UsuarioLogado", String.valueOf(idUsuario));
        map.put("SenhaAtual", senhaAtual);
        map.put("NovaSenha", senhaNova);
        map.put("ChaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.ALTERAR_SENHA);
        pGetRequest("AlteraSenhaUsuario.php", map);
    }

    public void lembrarSenha(String usuario) {
        HashMap<String, String> map = new HashMap<>();
        map.put("emailCadastro", usuario);
        activity.setPytacoRequest(PytacoRequestEnum.LEMBRAR_SENHA);
        pGetRequest("ativaConta/esqueciMinhaSenha.php", map);
    }

    public void criarAviso(int idUsuario, int idClube, String titulo, String descricao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("id_clube", String.valueOf(idClube));
        map.put("titulo", titulo);
        map.put("descricao", descricao);
        activity.setPytacoRequest(PytacoRequestEnum.CRIAR_AVISO);
        pPostRequest("CriarAvisos.php", map);
    }

    public void alterarAviso(int idTabela) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_aviso_tabela", String.valueOf(idTabela));
        activity.setPytacoRequest(PytacoRequestEnum.ALTERAR_AVISO);
        pGetRequest("UpdateAvisoLido.php", map);
    }

    public void excluirAviso(int idTabela) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_aviso_tabela", String.valueOf(idTabela));
        activity.setPytacoRequest(PytacoRequestEnum.EXCLUIR_AVISO);
        pGetRequest("UpdateAviso.php", map);
    }

    public void buscaAgente(int idMembro, int idClube, String codConvite) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_membro", String.valueOf(idMembro));
        map.put("id_clube", String.valueOf(idClube));
        map.put("cod_convite", codConvite);
        activity.setPytacoRequest(PytacoRequestEnum.BUSCA_AGENTE);
        pGetRequest("DescobreAgenteMembro.php", map);
    }

    public void acaoMembro(int idMembro, int idClube, int idUsuario, String chaveAcesso, @NotNull PytacoRequestEnum acao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_membro", String.valueOf(idMembro));
        map.put("id_clube", String.valueOf(idClube));
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chave_acesso", chaveAcesso);

        switch (acao) {
            case ACEITAR_MEMBROS:
                map.put("acao", "aceita");
                break;
            case TORNAR_AGENTE:
                map.put("acao", "agent");
                break;
            case DESATIVAR_MEMBRO:
                map.put("acao", "desativar");
                break;
        }

        activity.setPytacoRequest(acao);
        pGetRequest("AtualizaStatusTipoMembro.php", map);
    }

    public void enviarFichas(int idUsuario, String chaveAcesso, int idClube, Double valor, String lstMembro, int qtdMembro, Double saldoAdmin) {
        HashMap<String, String> map = new HashMap<>();
        map.put("usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("ValorDigitado", StringUtil.numberToStr(valor));
        map.put("listaDeMembros", lstMembro);
        map.put("qtdMembrosSelecionados", StringUtil.numberToStr(qtdMembro));
        map.put("SaldoAdmin", StringUtil.numberToStr(saldoAdmin));
        activity.setPytacoRequest(PytacoRequestEnum.ENVIAR_FICHAS);
        pGetRequest("EnviarFichas.php", map);
    }

    public void retirarFichas(int idUsuario, String chaveAcesso, int idClube, Double valor, String lstMembro, int qtdMembro, Double saldoAdmin) {
        HashMap<String, String> map = new HashMap<>();
        map.put("usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("ValorDigitado", StringUtil.numberToStr(valor));
        map.put("listaDeMembros", lstMembro);
        map.put("qtdMembrosSelecionados", StringUtil.numberToStr(qtdMembro));
        map.put("SaldoAdmin", StringUtil.numberToStr(saldoAdmin));
        activity.setPytacoRequest(PytacoRequestEnum.RETIRAR_FICHAS);
        pGetRequest("RetirarFichas.php", map);
    }

    public void calcularFichas(int idUsuario, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.CALCULAR_FIHCAS);
        pGetRequest("checkCotacao.php", map);
    }

    public void trocarPytacos(int idUsuario, String chaveAcesso, int idClube, Double qtdPytaco, Double qtdFicha) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("clubeSelecionado", StringUtil.numberToStr(idClube));
        map.put("qtdPytacosDebitar", StringUtil.numberToStr(qtdPytaco));
        map.put("qtdFichasNovas", StringUtil.numberToStr(qtdFicha));
        activity.setPytacoRequest(PytacoRequestEnum.TROCAR_PYTACOS);
        pGetRequest("TrocarPytacos.php", map);
    }

    public void desfazerClube(int idUsuario, String chaveAcesso, int idClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("clubeSelecionado", StringUtil.numberToStr(idClube));
        activity.setPytacoRequest(PytacoRequestEnum.DESFAZER_CLUBE);
        pGetRequest("DesfazerClube.php", map);
    }

    public void sairClube(int idUsuario, String chaveAcesso, int idClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("clubeSelecionado", StringUtil.numberToStr(idClube));
        activity.setPytacoRequest(PytacoRequestEnum.SAIR_CLUBE);
        pGetRequest("SairDoClube.php", map);
    }

    public void listaPytacosTrocados(int idClube, String data) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("DataEscolhida", data);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_PYTACOS_TROCADOS);
        pGetRequest("ListaPytacosTrocados.php", map);
    }

    public void listaFichasMovimentadas(int idClube, String data) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("DataEscolhida", data);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_FICHAS_MOVIMENTADAS);
        pGetRequest("ListaFichasMovimentadas.php", map);
    }

    public void listaPacoteCompra() {
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_PACOTE_COMPRA);
        pGetRequest("ListaPacotes.php");
    }

    public void listaJogos(String data) {
        HashMap<String, String> map = new HashMap<>();
        map.put("data", data);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_JOGOS);
        pGetRequest("APIListaJogosSplash.php", map);
    }

    public void listaPaises() {
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_PAISES);
        pGetRequest("listaPaises.php");
    }

    public void listaLigas(String sigla) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_pais", sigla);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_LIGAS);
        pGetRequest("listaLigas.php", map);
    }

    public void criarBolao(int idUsuario, String chaveAcesso, int idClube, Double valorBolao, String lstJogo, Double perc, String nomeBolao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("ValorBolao", StringUtil.numberToStr(valorBolao));
        map.put("listaDeJogos", lstJogo);
        map.put("percentual_premiacao", StringUtil.numberToStr(perc));
        map.put("nomeBolao", nomeBolao);
        activity.setPytacoRequest(PytacoRequestEnum.CRIAR_BOLAO);
        pGetRequest("CriarBolao.php", map);
    }

    public void listaBoloes(int idClube, int filtro) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_clube", StringUtil.numberToStr(idClube));
        map.put("filtro", StringUtil.numberToStr(filtro));
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_BOLOES);
        pGetRequest("listarMeusBoloes.php", map);
    }

    public void listaJogosBolao(int idBolao, int idUsuario, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_do_bolao", StringUtil.numberToStr(idBolao));
        map.put("usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_JOGOS_BOLAO);
        pGetRequest("APIDetalhesJogos.php", map);
    }

    public void novaAposta(int idClube, int idBolao, int idUsuario, String chaveAcesso,
                           Double valorBolao, Double qtdFicha, @NotNull String[] lstAposta) {
        HashMap<String, String> map = new HashMap<>();
        map.put("fk_clube", StringUtil.numberToStr(idClube));
        map.put("fk_bolao", StringUtil.numberToStr(idBolao));
        map.put("usuario", StringUtil.numberToStr(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("qtdFichasUsuarioClube", StringUtil.numberToStr(qtdFicha));
        map.put("valorDoBolaoSelecionado", StringUtil.numberToStr(valorBolao));

        for (int i = 1; i < lstAposta.length; i++) {
            map.put("ap" + i, lstAposta[i]);
        }

        activity.setPytacoRequest(PytacoRequestEnum.NOVA_APOSTA);
        pGetRequest("NovaAposta.php", map);
    }

    public void listaAposta(int idBolao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("fk_bolao", StringUtil.numberToStr(idBolao));
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_APOSTAS);
        pGetRequest("listaApostasBolao.php", map);
    }
}