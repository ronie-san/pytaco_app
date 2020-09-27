package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class PytacosTrocadosItemHolder extends CustomViewHolder {

    private TextView lblQtdPytaco;
    private TextView lblFicha;
    private TextView lblUsuario;

    public PytacosTrocadosItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblQtdPytaco() {
        return lblQtdPytaco;
    }

    public TextView getLblFicha() {
        return lblFicha;
    }

    public TextView getLblUsuario() {
        return lblUsuario;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblQtdPytaco = itemView.findViewById(R.id.pytacos_trocados_item_lblQtdPytaco);
        lblFicha = itemView.findViewById(R.id.pytacos_trocados_item_lblQtdFicha);
        lblUsuario = itemView.findViewById(R.id.pytacos_trocados_item_lblUsuario);
    }
}
