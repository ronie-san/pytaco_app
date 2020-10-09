package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class BolaoItemHolder extends CustomViewHolder {

    private ImageView imgBolao;
    private TextView lblTituloValor;
    private TextView lblValor;
    private TextView lblTituloNome;
    private TextView lblNome;

    public BolaoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgBolao() {
        return imgBolao;
    }

    public TextView getLblTituloValor() {
        return lblTituloValor;
    }

    public TextView getLblValor() {
        return lblValor;
    }

    public TextView getLblTituloNome() {
        return lblTituloNome;
    }

    public TextView getLblNome() {
        return lblNome;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        imgBolao = itemView.findViewById(R.id.meus_boloes_item_imgBolao);
        lblTituloValor = itemView.findViewById(R.id.meus_boloes_item_lblTituloValor);
        lblValor = itemView.findViewById(R.id.meus_boloes_item_lblQtdFicha);
        lblTituloNome = itemView.findViewById(R.id.meus_boloes_item_lblTituloNome);
        lblNome = itemView.findViewById(R.id.meus_boloes_item_lblNomeBolao);
    }
}
