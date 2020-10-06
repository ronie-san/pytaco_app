package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class LigaItemHolder extends CustomViewHolder {

    private TextView lblNome;

    public LigaItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblNome() {
        return lblNome;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblNome = itemView.findViewById(R.id.liga_item_lblNome);
    }
}
