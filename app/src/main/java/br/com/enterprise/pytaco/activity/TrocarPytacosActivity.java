package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.util.StringUtil;

public class TrocarPytacosActivity extends BaseActivity {

    private TextView lblQtdPytaco;
    private TextView lblQtdFicha;
    private EditText edtQtdPytaco;
    private TextView lblQtdPytacoTrocar;
    private TextView lblTituloFichaReceber;
    private TextView lblQtdFichaReceber;
    private ImageButton btnConfirmar;
    private ImageButton btnTrocar;
    private ImageButton btnDesfazer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_pytacos);

        lblQtdPytaco = findViewById(R.id.trocar_pytacos_lblQtdPytaco);
        lblQtdFicha = findViewById(R.id.trocar_pytacos_lblQtdFicha);
        edtQtdPytaco = findViewById(R.id.trocar_pytacos_edtQtdPytaco);
        lblQtdPytacoTrocar = findViewById(R.id.trocar_pytacos_lblQtdPytacoTrocar);
        lblTituloFichaReceber = findViewById(R.id.trocar_pytacos_lblTituloFichaReceber);
        lblQtdFichaReceber = findViewById(R.id.trocar_pytacos_lblQtdFichaReceber);
        btnTrocar = findViewById(R.id.trocar_pytacos_btnTrocar);
        btnConfirmar = findViewById(R.id.trocar_pytacos_btnConfirmar);
        btnDesfazer = findViewById(R.id.trocar_pytacos_btnDesfazer);

        lblQtdPytaco.setText(StringUtil.numberToStr(usuario.getQtdPytaco()));
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));
        pAlteraVisao(true);

        btnTrocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTrocarClick();
            }
        });

        btnDesfazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDesfazerClick();
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnConfirmarClick();
            }
        });
    }

    private void pAlteraVisao(boolean trocar) {
        int visible = trocar ? View.VISIBLE : View.GONE;
        int gone = !trocar ? View.VISIBLE : View.GONE;

        edtQtdPytaco.setVisibility(visible);
        lblQtdPytacoTrocar.setVisibility(gone);
        btnTrocar.setVisibility(visible);
        lblQtdFichaReceber.setVisibility(gone);
        lblTituloFichaReceber.setVisibility(gone);
        btnConfirmar.setVisibility(gone);
        btnDesfazer.setVisibility(gone);
    }

    private void btnConfirmarClick() {
        pHideKeyboard();

        if (pValidaQtdPytaco()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.trocarPytacos(usuario.getId(), usuario.getChaveAcesso(), clube.getId(), StringUtil.strToNumber(edtQtdPytaco.getText().toString().trim()), Double.parseDouble(lblQtdFichaReceber.getText().toString().trim()));
        }
    }

    private void btnDesfazerClick() {
        pAlteraVisao(true);
        pFocusEdit(edtQtdPytaco);
    }

    private boolean pValidaQtdPytaco() {
        String qtd = edtQtdPytaco.getText().toString().trim();
        return !qtd.isEmpty() && StringUtil.strToNumber(qtd) < usuario.getQtdPytaco();
    }

    private void btnTrocarClick() {
        pHideKeyboard();

        if (pValidaQtdPytaco()) {
            PytacoRequestDAO request = new PytacoRequestDAO(this);
            request.calcularFichas(usuario.getId(), usuario.getChaveAcesso());
        }
    }

    private void pTrataRespostaCalcularFichas(String response) {
        //ALTERAR PORQUE RETORNA UM MULTIPLICADOR
        if (response != null && !response.isEmpty() && !response.toUpperCase().equals("ERRO")) {
            pAlteraVisao(false);
            lblQtdPytacoTrocar.setText(edtQtdPytaco.getText());
            double qtdFichaReceber = StringUtil.strToNumber(response.trim()) * StringUtil.strToNumber(edtQtdPytaco.getText().toString().trim());
            lblQtdFichaReceber.setText(StringUtil.numberToStr(qtdFichaReceber));
        } else {
            makeLongToast("Operação não permitida");
        }
    }

    private void pTrataRespostaTrocarPytacos(String response) {
        try {
            JSONObject resp = new JSONObject(response).getJSONArray("entry").getJSONObject(0);
            usuario.setChaveAcesso(resp.getString("chaveacesso"));
            usuario.setQtdPytaco(StringUtil.strToNumber(resp.getString("novosaldopytacos")));
            clube.setQtdFicha(StringUtil.strToNumber(resp.getString("novosaldofichas")));
            lblQtdPytaco.setText(resp.getString("novosaldopytacos"));
            lblQtdFicha.setText(resp.getString("novosaldofichas"));
            pAlteraVisao(true);
            edtQtdPytaco.setText("");
        } catch (JSONException e) {
            makeLongToast("Não foi possível ler a resposta");
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelDialog();
        pEnableScreen();

        switch (pytacoRequestEnum) {
            case CALCULAR_FIHCAS:
                pTrataRespostaCalcularFichas(response);
                break;
            case TROCAR_PYTACOS:
                pTrataRespostaTrocarPytacos(response);
            default:
                break;
        }

        super.onSucess(response);
    }
}