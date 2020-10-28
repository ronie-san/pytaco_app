package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.JogoItemAdapter;
import br.com.enterprise.pytaco.pojo.Jogo;

public class JogosActivity extends BaseRecyclerActivity {

    private EditText edtValor;
    private EditText edtPerc;
    private JogoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);

        RecyclerView lsvJogos = getRecyclerView();
        ImageButton btnVerJogos = findViewById(R.id.jogos_btnVerJogos);
        edtValor = findViewById(R.id.jogos_edtValor);
        edtPerc = findViewById(R.id.jogos_edtPerc);

        adapter = new JogoItemAdapter(this, pGetLstJogos(), R.layout.lst_jogo_item);
        lsvJogos.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnVerJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVerJogosClick();
            }
        });
    }

    private void btnVerJogosClick() {
        if (pValidaInfo()) {
            Intent intent = new Intent(this, JogosSelecionadosActivity.class);
            intent.putExtra(getString(R.string.valor_bolao), Double.parseDouble(edtValor.getText().toString().trim()));
            intent.putExtra(getString(R.string.percentual_bolao), Double.parseDouble(edtPerc.getText().toString().trim()));
            startActivity(intent);
        }
    }

    private boolean pValidaInfo() {
        try {
            double valorBolao = Double.parseDouble(edtValor.getText().toString().trim());
            double perc = Double.parseDouble(edtPerc.getText().toString().trim());
            return lstJogoSelecionado.size() > 0 && valorBolao > 0 && perc > 0 && perc < 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @NotNull
    private List<Jogo> pGetLstJogos() {
        List<Jogo> lst = new ArrayList<>();

        for (Jogo item : lstJogo) {
            if (item.getLiga().getId().equals(liga.getId())) {
                lst.add(item);
            }
        }

        return lst;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return findViewById(R.id.jogos_lsvJogos);
    }


    @Override
    public void onLstItemClick(int position) {
        Jogo item = adapter.getLst().get(position);

        if (adapter.getLst().get(position).getStatusShort().equals("NS")) {
            item.setMarcado(!item.isMarcado());

            if (item.isMarcado()) {
                if (lstJogoSelecionado.size() < 10) {
                    lstJogoSelecionado.add(item);
                } else {
                    pShowOkDialog("Atenção", "Máximo de 10 jogos por bolão");
                    item.setMarcado(false);
                }
            } else {
                lstJogoSelecionado.remove(item);
            }
        } else {
            item.setMarcado(false);
        }

        adapter.notifyItemChanged(position);
    }
}