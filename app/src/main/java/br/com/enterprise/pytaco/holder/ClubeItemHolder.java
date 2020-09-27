package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class ClubeItemHolder extends CustomViewHolder {

    private TextView lblNome;
    private TextView lblDescricao;
    private TextView lblQtdFicha;

    public ClubeItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblNome() {
        return lblNome;
    }

    public TextView getLblDescricao() {
        return lblDescricao;
    }

    public TextView getLblQtdFicha() {
        return lblQtdFicha;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblNome = itemView.findViewById(R.id.clube_item_lblNome);
        lblDescricao = itemView.findViewById(R.id.clube_item_lblDescricao);
        lblQtdFicha = itemView.findViewById(R.id.clube_item_lblQtdFicha);
    }
}
