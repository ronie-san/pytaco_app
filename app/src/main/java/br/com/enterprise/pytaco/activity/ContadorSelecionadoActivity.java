package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.AvisoItemAdapter;
import br.com.enterprise.pytaco.activity.adapter.ContadorSelecionadoItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.Aviso;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorSelecionadoActivity extends BaseActivity {

    private EditText edtQtdFicha;
    private ContadorSelecionadoItemAdapter contadorSelecionadoItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_selecionado);
        List<Membro> lstMembro;

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            lstMembro = (ArrayList<Membro>) bundle.getSerializable(getString(R.string.lst_contador));
        } else {
            lstMembro = (ArrayList<Membro>) savedInstanceState.getSerializable(getString(R.string.lst_contador));
        }

        edtQtdFicha = findViewById(R.id.contador_selecionado_edtQtdFicha);
        ImageButton btnVoltar = findViewById(R.id.contador_selecionado_btnVoltar);
        TextView lblQtdFicha = findViewById(R.id.contador_selecionado_lblQtdFicha);
        ImageButton btnEnviarFicha = findViewById(R.id.contador_selecionado_btnEnviarFicha);
        ImageButton btnRetirarFicha = findViewById(R.id.contador_selecionado_btnRetirarFicha);
        ListView lsvContadores = findViewById(R.id.contador_selecionado_lsvContadores);
        contadorSelecionadoItemAdapter = new ContadorSelecionadoItemAdapter(lstMembro, this);
        lsvContadores.setAdapter(contadorSelecionadoItemAdapter);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
//        double total = 0;
//        for (Membro item : lstMembro) {
//            total += item.getQtdFicha();
//        }
        lblQtdFicha.setText(StringUtil.numberToStr(lstMembro.get(0).getClube().getQtdFicha()));

        btnEnviarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEnviarFichaClick();
            }
        });

        btnRetirarFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRetirarFichaClick();
            }
        });
    }

    @NotNull
    private String pGetLstMembros() {
        StringBuilder membros = new StringBuilder();
        for (int i = 0; i < contadorSelecionadoItemAdapter.getCount(); i++) {
            membros.append(contadorSelecionadoItemAdapter.getLst().get(i).getId());
            if (i < contadorSelecionadoItemAdapter.getCount() - 1) {
                membros.append(",");
            }
        }
        return membros.toString();
    }

    private boolean pValidaEnviarFicha() {
        return false;
        //NÃO PODE SER MAIOR QUE A QTD DE FICHA DO USUÁRIO NO CLUBE
        //A QUANTIDADE É POR USUÁRIO (SE ESCREVER 10 E TIVER 5 MEMBROS, O TOTAL SERÁ DE 50)

//        if (!edtQtdFicha.getText().toString().trim().isEmpty()) {
//            Double qtd = Double.parseDouble(edtQtdFicha.getText().toString().trim());
//            return false;
//        }
//
//        return false;
    }

    private boolean pValidaRetirarFicha() {
        //SE QUALQUER USUÁRIO TIVER NO CLUBE SALDO MENOR QUE A QTD ESCRITA, NÃO PODE DEIXAR
        //SALDOADMIN É O SALDO DO USUÁRIO NO CLUBE
        return false;
    }

    private void btnEnviarFichaClick() {
        if (pValidaRetirarFicha()) {

            //SALDOADMIN É O SALDO DO USUÁRIO NO CLUBE

//            PytacoRequestDAO request = new PytacoRequestDAO(this);
//            request.enviarFichas(lstMembro.get(0).getClube().getUsuario().getId(),
//                    lstMembro.get(0).getClube().getUsuario().getChaveAcesso(),
//                    lstMembro.get(0).getClube().getId(),
//                    Double.parseDouble(edtQtdFicha.getText().toString().trim()),
//                    pGetLstMembros(),
//                    lstMembro.size(),
//                    100.0);
        }
    }

    private void btnRetirarFichaClick() {
        if (pValidaRetirarFicha()) {
//SALDOADMIN É O SALDO DO USUÁRIO NO CLUBE
//            PytacoRequestDAO request = new PytacoRequestDAO(this);
//            request.retirarFichas(lstMembro.get(0).getClube().getUsuario().getId(),
//                    lstMembro.get(0).getClube().getUsuario().getChaveAcesso(),
//                    lstMembro.get(0).getClube().getId(),
//                    Double.parseDouble(edtQtdFicha.getText().toString().trim()),
//                    pGetLstMembros(),
//                    lstMembro.size(),
//                    100.0);
        }
    }

    private void pTrataRespostaEnviarFichas(String response) {

    }

    private void pTrataRespostaRetirarFichas(String response) {

    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case ENVIAR_FICHAS:
                pTrataRespostaEnviarFichas(response);
                break;
            case RETIRAR_FICHAS:
                pTrataRespostaRetirarFichas(response);
                break;
            default:
                break;
        }

        super.onSucess(response);
    }
}