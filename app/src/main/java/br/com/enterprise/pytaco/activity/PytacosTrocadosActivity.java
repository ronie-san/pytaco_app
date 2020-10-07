package br.com.enterprise.pytaco.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.PytacosTrocadosItemAdapter;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;
import br.com.enterprise.pytaco.pojo.PytacoTrocado;
import br.com.enterprise.pytaco.util.DateUtil;
import br.com.enterprise.pytaco.util.PytacoRequestEnum;
import br.com.enterprise.pytaco.util.StringUtil;

public class PytacosTrocadosActivity extends BaseRecyclerActivity {

    private EditText edtData;
    private PytacosTrocadosItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytacos_trocados);

        edtData = findViewById(R.id.pytacos_trocados_edtData);
        TextView lblAnterior = findViewById(R.id.pytacos_trocados_lblAnterior);
        TextView lblProximo = findViewById(R.id.pytacos_trocados_lblProximo);
        RecyclerView lsvPytacosTrocados = getRecyclerView();

        adapter = new PytacosTrocadosItemAdapter(this, new ArrayList<PytacoTrocado>(), R.layout.lst_pytacos_trocados_item);
        lsvPytacosTrocados.setAdapter(adapter);

        edtData.setText(DateUtil.getStrDate());
        edtData.setInputType(InputType.TYPE_NULL);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtDataClick();
            }
        });

        lblAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblAnteriorClick();
            }
        });
        lblProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblProximoClick();
            }
        });

        pListaPytacosTrocados();
    }

    private void lblAnteriorClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date, -1);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaPytacosTrocados();
    }

    private void edtDataClick() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.parse(edtData.getText().toString()));
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);


        DatePickerDialog calendario = new DatePickerDialog(this, R.style.DateDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtData.setText(DateUtil.formarData(i2, i1 + 1, i));
                pListaPytacosTrocados();
            }
        }, ano, mes, dia);

        calendario.show();
    }

    private void lblProximoClick() {
        Date date = DateUtil.parse(edtData.getText().toString());
        date = DateUtil.addDay(date);
        edtData.setText(DateUtil.toDefaultFormat(date));
        pListaPytacosTrocados();
    }

    private void pListaPytacosTrocados() {
        PytacoRequestDAO request = new PytacoRequestDAO(this);
        request.listaPytacosTrocados(clube.getId(), edtData.getText().toString().trim());
    }

    private void pTrataRespostaListaPytacosTrocados(String response) {
        try {
            adapter.getLst().clear();
            JSONArray resp = new JSONObject(response).getJSONArray("entry");

            if (resp.length() > 0 && !resp.getJSONObject(0).getString("id_log").isEmpty()) {
                for (int i = 0; i < resp.length(); i++) {
                    JSONObject item = resp.getJSONObject(i);
                    PytacoTrocado pytacoTrocado = new PytacoTrocado();
                    pytacoTrocado.setId(Integer.parseInt(item.getString("id_log")));
                    pytacoTrocado.setNomeUsuario(item.getString("nomeUsuario"));
                    pytacoTrocado.setQtdFicha(StringUtil.strToNumber(item.getString("qtdFichasNovas")));
                    pytacoTrocado.setQtdPytaco(StringUtil.strToNumber(item.getString("qtdPytacosTrocados")));
                    adapter.getLst().add(pytacoTrocado);
                }
            }
        } catch (JSONException ignored) {
        } finally {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSucess(String response) {
        pCancelLoading();
        pEnableScreen();

        if (pytacoRequestEnum.equals(PytacoRequestEnum.LISTA_PYTACOS_TROCADOS)) {
            pTrataRespostaListaPytacosTrocados(response);
        }

        super.onSucess(response);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.pytacos_trocados_lsvPytacosTrocados);
    }

    @Override
    public void onLstItemClick(int position) {

    }
}