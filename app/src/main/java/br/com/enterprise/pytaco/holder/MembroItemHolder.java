package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class MembroItemHolder extends CustomViewHolder {

    private TextView lblNome;
    private TextView lblQtdFicha;
    private TextView lblTipo;
    private TextView lblStatus;

    public MembroItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblNome() {
        return lblNome;
    }

    public TextView getLblQtdFicha() {
        return lblQtdFicha;
    }

    public TextView getLblTipo() {
        return lblTipo;
    }

    public TextView getLblStatus() {
        return lblStatus;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblNome = itemView.findViewById(R.id.membro_item_lblNome);
        lblQtdFicha = itemView.findViewById(R.id.membro_item_lblQtdFicha);
        lblTipo = itemView.findViewById(R.id.membro_item_lblTipo);
        lblStatus = itemView.findViewById(R.id.membro_item_lblStatus);
    }
}
