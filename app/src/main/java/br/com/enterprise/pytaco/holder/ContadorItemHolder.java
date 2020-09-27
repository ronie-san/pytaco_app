package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class ContadorItemHolder extends CustomViewHolder {

    private CheckBox chkMarcado;
    private TextView lblNome;
    private TextView lblQtdFicha;
    private TextView lblTipo;


    public ContadorItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public CheckBox getChkMarcado() {
        return chkMarcado;
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

    @Override
    protected void pFindViews(@NonNull View itemView) {
        chkMarcado = itemView.findViewById(R.id.contador_item_chkMarcado);
        lblNome = itemView.findViewById(R.id.contador_item_lblNome);
        lblQtdFicha = itemView.findViewById(R.id.contador_item_lblQtdFicha);
        lblTipo = itemView.findViewById(R.id.contador_item_lblTipo);
    }
}
