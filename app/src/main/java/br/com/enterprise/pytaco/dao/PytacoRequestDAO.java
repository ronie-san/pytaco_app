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

    public void checkVersion(String versao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("versao", versao);
        activity.setPytacoRequest(PytacoRequestEnum.CHECK_VERSION);
        pGetRequest("checkVersion.php", map);
    }

    public void geraChaveSeguranca() {
        activity.setPytacoRequest(PytacoRequestEnum.GERA_CHAVE_SEGURANCA);
        pGetRequest("geraChaveSeguranca.php");
    }

    public void listaClubes(int idUsuario, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.LISTA_CLUBES);
        pGetRequest("ListaClubes.php", map);
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

    public void associar(int idUsuario, String chaveAcesso, String codigoClube){
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("codigoClube", String.valueOf(codigoClube));
        activity.setPytacoRequest(PytacoRequestEnum.ASSOCIAR);
        pGetRequest("Associcar.php", map);
    }

    public void atualizaPytacosTelaInicial(int idUsuario){
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        activity.setPytacoRequest(PytacoRequestEnum.ATUALIZA_PYTACOS);
        pGetRequest("Associcar.php", map);
    }

    public void alteraSenha(int idUsuario, String senhaAtual, String senhaNova, String chaveAcesso){
        HashMap<String, String> map = new HashMap<>();
        map.put("UsuarioLogado", String.valueOf(idUsuario));
        map.put("SenhaAtual", senhaAtual);
        map.put("NovaSenha", senhaNova);
        map.put("ChaveAcesso", chaveAcesso);
        activity.setPytacoRequest(PytacoRequestEnum.ALTERAR_SENHA);
        pGetRequest("AlteraSenhaUsuario.php", map);
    }

    public void lembrarSenha(String usuario){
        HashMap<String, String> map = new HashMap<>();
        map.put("emailCadastro", usuario);
        activity.setPytacoRequest(PytacoRequestEnum.LEMBRAR_SENHA);
        pGetRequest("ativaConta/esqueciMinhaSenha.php", map);
    }
}
