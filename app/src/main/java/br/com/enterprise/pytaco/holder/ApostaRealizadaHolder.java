package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class ApostaRealizadaHolder extends CustomViewHolder {

    TextView lblId;
    TextView lblUsuario;
    TextView lblQtdPonto;

    public ApostaRealizadaHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblId() {
        return lblId;
    }

    public TextView getLblUsuario() {
        return lblUsuario;
    }

    public TextView getLblQtdPonto() {
        return lblQtdPonto;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblId = itemView.findViewById(R.id.aposta_realizada_item_lblId);
        lblUsuario = itemView.findViewById(R.id.aposta_realizada_item_lblUsuario);
        lblQtdPonto = itemView.findViewById(R.id.aposta_realizada_item_lblQtdPonto);
    }
}
