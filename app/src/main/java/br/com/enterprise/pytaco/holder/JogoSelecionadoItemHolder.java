package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class JogoSelecionadoItemHolder extends CustomViewHolder {

    private TextView lblTimeHome;
    private TextView lblTimeAway;

    public JogoSelecionadoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblTimeHome() {
        return lblTimeHome;
    }

    public TextView getLblTimeAway() {
        return lblTimeAway;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblTimeHome = itemView.findViewById(R.id.jogo_selecionado_item_lblTimeHome);
        lblTimeAway = itemView.findViewById(R.id.jogo_selecionado_item_lblTimeAway);
    }
}
