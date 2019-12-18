package br.com.enterprise.pytaco.dao;

import java.util.HashMap;

import br.com.enterprise.pytaco.activity.IActivity;

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
        pGetRequest("CriarUsuario.php", map);
    }

    public void login(String login, String senha) {
        HashMap<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("senha", senha);
        pGetJsonRequest("login.php", map);
    }

    public void checkVersion(String versao) {
        HashMap<String, String> map = new HashMap<>();
        map.put("versao", versao);
        pGetRequest("checkVersion.php", map);
    }

    public void geraChaveSeguranca() {
        pGetRequest("geraChaveSeguranca.php");
    }

    public void listaClubes(int idUsuario, String chaveAcesso) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        pGetRequest("ListaClubes.php", map);
    }

    public void criarClube(int idUsuario, String chaveAcesso, String nomeClube, String descricaoClube) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("nomeClube", nomeClube);
        map.put("descricaoClube", descricaoClube);
        pGetRequest("CriarClube.php", map);
    }

    public void associar(int idUsuario, String chaveAcesso, String codigoClube){
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        map.put("chaveAcesso", chaveAcesso);
        map.put("codigoClube", String.valueOf(codigoClube));
        pGetRequest("Associcar.php", map);
    }

    public void atualizaPytacosTelaInicial(int idUsuario){
        HashMap<String, String> map = new HashMap<>();
        map.put("id_usuario", String.valueOf(idUsuario));
        pGetRequest("Associcar.php", map);
    }
}
