package br.com.enterprise.pytaco.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.JogoItemAdapter;
import br.com.enterprise.pytaco.pojo.Jogo;

public class JogosActivity extends BaseRecyclerActivity {

    private JogoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogos);

        RecyclerView lsvJogos = getRecyclerView();
        final ImageButton btnVerJogos = findViewById(R.id.jogos_btnVerJogos);

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
        if (lstJogoSelecionado.size() > 0) {

        }
    }

    @NotNull
    private List<Jogo> pGetLstJogos() {
        List<Jogo> lst = new ArrayList<>();

        for (Jogo item : lstJogo) {
            if (item.getLiga().getName().equals(liga.getName())) {
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
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Atenção");
        alert.setPositiveButton("OK", null);

        if (adapter.getLst().get(position).getStatusShort().equals("NS")) {
            boolean existe = false;
            int i = 0;

            while (i < lstJogoSelecionado.size() && !existe) {
                if (adapter.getLst().get(position).getId().equals(lstJogoSelecionado.get(i).getId())) {
                    existe = true;
                }

                i++;
            }

            if (!existe) {
                alert.setMessage("Jogo selecionado com sucesso");
                lstJogoSelecionado.add(adapter.getLst().get(position));
            } else {
                alert.setMessage("Jogo já selecionado");
            }
        } else {
            alert.setMessage("Este jogo foi " + adapter.getLst().get(position).getStatusExt());
        }

        alert.show();
    }
}