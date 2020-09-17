package br.com.enterprise.pytaco.dao;

import java.util.HashMap;

import br.com.enterprise.pytaco.activity.IActivity;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;

public class PytacoRequestDAO extends BasicRequestDAO {

    public PytacoRequestDAO(IActivity activity) {
        super(activity);
        useKeyHeader = false;
        baseUrl = "http://easycliente.com.br/pitaco/php/";
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
        pGetJsonRequest("login.php", map);
    }

//    public void checkVersion(String versao) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("versao", versao);
//        activity.setPytacoRequest(PytacoRequestEnum.CHECK_VERSION);
//        pGetRequest("checkVersion.php", map);
//    }
//
//    public void geraChaveSeguranca() {
//        activity.setPytacoRequest(PytacoRequestEnum.GERA_CHAVE_SEGURANCA);
//        pGetRequest("geraChaveSeguranca.php");
//    }

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
        pGetJsonRequest("CriarAvisos.php", map);
    }

    public void alterarAviso(int idAviso, String titulo, String descricao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_aviso_tabela", String.valueOf(idAviso));
        map.put("titulo", titulo);
        map.put("descricao", descricao);
        activity.setPytacoRequest(PytacoRequestEnum.ALTERAR_AVISO);
        pGetJsonRequest("UpdateAvisoLido.php", map);
    }

    public void excluirAviso(int idAviso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_aviso_tabela", String.valueOf(idAviso));
        activity.setPytacoRequest(PytacoRequestEnum.EXCLUIR_AVISO);
        pGetJsonRequest("UpdateAviso.php", map);
    }

    public void buscaAgente(int idMembro, int idClube, String codConvite) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_membro", String.valueOf(idMembro));
        map.put("id_clube", String.valueOf(idClube));
        map.put("cod_convite", codConvite);
        activity.setPytacoRequest(PytacoRequestEnum.BUSCA_AGENTE);
        pGetJsonRequest("DescobreAgenteMembroSelecionado.php", map);
    }
}
