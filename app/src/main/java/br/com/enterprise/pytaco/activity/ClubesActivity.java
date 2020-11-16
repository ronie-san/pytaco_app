package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.ClubeItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.DialogView;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class ClubesActivity extends BaseRecyclerActivity {

    private TextView lblQtdPytacoGlobal;
    private TextView lblQtdFichaGlobal;
    private DialogView dialogNovoClube;
    private DialogView dialogAlterarSenha;
    private DialogView dialogAssociarClube;
    private ClubeItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_clubes);
        } catch (Exception e) {
            makeLongToast(e.getMessage());
        }

        lblQtdPytacoGlobal = findViewById(R.id.clubes_lblQtdPytacoGlobal);
        lblQtdFichaGlobal = findViewById(R.id.clubes_lblQtdFichaGlobal);

        RecyclerView lsvClubes = getRecyclerView();
        adapter = new ClubeItemAdapter(this, new ArrayList<Clube>(), R.layout.lst_clube_item);
        lsvClubes.setAdapter(adapter);

        ImageButton btnNovoClube = findViewById(R.id.clubes_btnNovoClube);
        btnNovoClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNovoClubeClick();
            }
        });

        ImageButton btnAlterarSenha = findViewById(R.id.clubes_btnAlterarSenha);
        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAlterarSenhaClick();
            }
        });

        ImageButton btnAssociarClube = findViewById(R.id.clubes_btnAssociarClube);
        btnAssociarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAssociarClubeClick();
            }
        });

        ImageButton btnCompras = findViewById(R.id.clubes_btnCompras);
        btnCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnComprasClick();
            }
        });

        ImageButton btnBolao = findViewById(R.id.clubes_btnBolao);
        btnBolao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBolaoClick();
            }
        });
    }

    private void btnComprasClick() {
//        makeShortToast("Função indisponível no momento");
        pStartActivity(CompraActivity.class);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.clubes_lsvClubes);
    }

    @Override
    public void onLstItemClick(int position) {
        clube = adapter.getLst().get(position);
        pStartActivity(BolaoActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!pExisteDialogAberto()) {
            lblQtdPytacoGlobal.setText(StringUtil.numberToStr(usuario.getQtdPytaco()));
            lblQtdFichaGlobal.setText(StringUtil.numberToStr(usuario.getQtdFicha()));
            pListaClubes();
        }
    }

    private boolean pExisteDialogAberto() {
        return (dialogLoading != null && dialogLoading.getDialog().isShowing()) ||
                (dialogAssociarClube != null && dialogAssociarClube.getDialog().isShowing()) ||
                (dialogAlterarSenha != null && dialogAlterarSenha.getDialog().isShowing()) ||
                (dialogNovoClube != null && dialogNovoClube.getDialog().isShowing());
    }

    private void btnBolaoClick() {
        clube = new Clube();
        clube.setId(1);
        clube.setQtdFicha(usuario.getQtdFicha());
        pStartActivity(MeusBoloesActivity.class);
    }

    private void btnAssociarClubeClick() {
        dialogAssociarClube = createDialog(R.layout.dialog_associar_clube);

        final TextView edtCodClube = dialogAssociarClube.findViewById(R.id.associar_clube_edtCodClube);
        final ImageButton btnAssociar = dialogAssociarClube.findViewById(R.id.associar_clube_btnAssociar);

        btnAssociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCodClube.getText().toString().trim().isEmpty()) {
                    PytacoRequestDAO request = new PytacoRequestDAO(ClubesActivity.this);
                    request.associar(usuario.getId(), usuario.getChaveAcesso(), edtCodClube.getText().toString().trim());
                }
            }
        });

        dialogAssociarClube.showDialog();
    }

    private void btnAlterarSenhaClick() {
        dialogAlterarSenha = createDialog(R.layout.dialog_alterar_senha);

        final TextView edtSenhaAtual = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaAtual);
        final TextView edtSenhaNova = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaNova);
        final TextView edtSenhaConfirmada = dialogAlterarSenha.findViewById(R.id.alterar_senha_edtSenhaConfirmada);
        final ImageButton btnAlterarSenha = dialogAlterarSenha.findViewById(R.id.alterar_senha_btnAlterarSenha);

        btnAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSenhaAtual.getText().toString().isEmpty() && !edtSenhaNova.getText().toString().isEmpty() && !edtSenhaConfirmada.getText().toString().isEmpty()) {
                    PytacoRequestDAO request = new PytacoRequestDAO(ClubesActivity.this);
                    request.alteraSenha(usuario.getId(), edtSenhaAtual.getText().toString(), edtSenhaNova.getText().toString(), usuario.getChaveAcesso());
                }
            }
        });

        dialogAlterarSenha.showDialog();
    }

    private void btnNovoClubeClick() {
        dialogNovoClube = createDialog(R.layout.dialog_criar_clube);

        final TextView edtNomeClube = dialogNovoClube.findViewById(R.id.criar_clube_edtNomeClube);
        final TextView edtDescricaoClube = dialogNovoClube.findViewById(R.id.criar_clube_edtDescricaoClube);
        final ImageButton btnCriarClube = dialogNovoClube.findViewById(R.id.criar_clube_btnCriarClube);

        btnCriarClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNomeClube.getText().toString().trim().equals("") && !edtDescricaoClube.getText().toString().trim().equals("")) {
                    PytacoRequestDAO request = new PytacoRequestDAO(ClubesActivity.this);
                    request.criarClube(usuario.getId(), usuario.getChaveAcesso(), edtNomeClube.getText().toString(), edtDescricaoClube.getText().toString());
                }
            }
        });

        dialogNovoClube.showDialog();
    }

    private void pTrataRespostaAlterarSenha(@NotNull String response) {
        if (dialogAlterarSenha != null && dialogAlterarSenha.dialogShowing()) {
            dialogAlterarSenha.cancelDialog();
        }

        if (response.equals("ErroLogin")) {
            //senha atual errada
            makeLongToast("Senhas não coincidentes.");
        } else {
            usuario.setChaveAcesso(response);
        }
    }

    private void pTrataRespostaCriarClube(@NotNull String response) {
        if (dialogNovoClube != null && dialogNovoClube.dialogShowing()) {
            dialogNovoClube.cancelDialog();
        }

        switch (response) {
            case "Cadastrado":
                makeLongToast("Clube já cadastrado");
                break;
            case "Insuficiente":
                makeLongToast("Saldo de pytacos insuficiente");
                break;
            default:
                usuario.setChaveAcesso(response);
                break;
        }
    }

    private void pTrataRespostaListaClubes(@NotNull String response) {
        try {
            clube = null;
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (!resp.getJSONObject(0).getString("id_clube").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    if (!resp.getJSONObject(i).getString("id_clube").isEmpty()) {
                        Clube clube = new Clube();
                        clube.setId(Integer.parseInt(resp.getJSONObject(i).getString("id_clube")));
                        clube.setNome(resp.getJSONObject(i).getString("nomeclube"));
                        clube.setDescricao(resp.getJSONObject(i).getString("descricaoclube"));
                        clube.setQtdFicha(StringUtil.strToNumber(resp.getJSONObject(i).getString("qtdfichas")));
                        clube.setCodClube(resp.getJSONObject(i).getString("codigousuario"));
                        clube.setTipoUsuario(resp.getJSONObject(i).getString("tipousuario"));
                        adapter.getLst().add(clube);
                    }
                }
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    private void pTrataRespostaAssociarClube(String response) {
        if (dialogNovoClube != null && dialogAssociarClube.dialogShowing()) {
            dialogAssociarClube.cancelDialog();
        }

        if (response.equals("NaoAchei")) {
            makeLongToast("Clube não encontrado");
        } else {
            usuario.setChaveAcesso(response);
            makeLongToast("Associação em análise!");
        }
    }

    private void pListaClubes() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaClubes(usuario.getId(), usuario.getChaveAcesso());
    }

    @Override
    public void onError(@NotNull VolleyError error) {
        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_CLUBES)) {
            adapter.getLst().clear();
            adapter.notifyDataSetChanged();
        }

        super.onError(error);
    }

    @Override
    public void onSucess(String response) {
        if (!this.isDestroyed()) {
            switch (pytacoRequestEnum) {
                case LISTA_CLUBES:
                    pCancelLoading();
                    pEnableScreen();
                    pTrataRespostaListaClubes(response);
                    super.onSucess(response);
                    break;
                case ASSOCIAR:
                    pCancelLoading();
                    pEnableScreen();
                    pTrataRespostaAssociarClube(response);
                    super.onSucess(response);
                    break;
                case CRIAR_CLUBE:
                    pTrataRespostaCriarClube(response);
                    pListaClubes();
                    break;
                case ALTERAR_SENHA:
                    pCancelLoading();
                    pEnableScreen();
                    pTrataRespostaAlterarSenha(response);
                    super.onSucess(response);
                    break;
                default:
                    pCancelLoading();
                    pEnableScreen();
                    super.onSucess(response);
                    break;
            }
        }
    }
}