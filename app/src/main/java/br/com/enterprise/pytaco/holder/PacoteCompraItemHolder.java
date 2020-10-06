package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class PacoteCompraItemHolder extends CustomViewHolder {

    private ImageView imgPacote;
    private TextView lblDescricao;
    private TextView lblResumo;
    private TextView lblValor;

    public PacoteCompraItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgPacote() {
        return imgPacote;
    }

    public TextView getLblDescricao() {
        return lblDescricao;
    }

    public TextView getLblResumo() {
        return lblResumo;
    }

    public TextView getlblValor() {
        return lblValor;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        imgPacote = itemView.findViewById(R.id.pacote_compra_item_imgPacote);
        lblDescricao = itemView.findViewById(R.id.pacote_compra_item_lblDescricao);
        lblResumo = itemView.findViewById(R.id.pacote_compra_item_lblResumo);
        lblValor = itemView.findViewById(R.id.pacote_compra_item_lblValor);
    }
}
