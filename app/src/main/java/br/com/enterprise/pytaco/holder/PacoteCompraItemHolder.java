package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class PacoteCompraItemHolder extends CustomViewHolder {

    private ImageView imgPacote;
    private ImageButton btnComprar;

    public PacoteCompraItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgPacote() {
        return imgPacote;
    }

    public ImageButton getBtnComprar() {
        return btnComprar;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        imgPacote = itemView.findViewById(R.id.pacote_compra_item_imgPacote);
        btnComprar = itemView.findViewById(R.id.pacote_compra_item_btnComprar);
    }
}
