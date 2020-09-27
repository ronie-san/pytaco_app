package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class AvisoItemHolder extends CustomViewHolder {

    private ImageView imgAviso;
    private TextView lblTitulo;
    private TextView lblDescricao;
    private TextView lblData;
    private TextView lblStatus;

    public AvisoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgAviso() {
        return imgAviso;
    }

    public TextView getLblTitulo() {
        return lblTitulo;
    }

    public TextView getLblDescricao() {
        return lblDescricao;
    }

    public TextView getLblData() {
        return lblData;
    }

    public TextView getLblStatus() {
        return lblStatus;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        imgAviso = itemView.findViewById(R.id.aviso_item_imgAviso);
        lblTitulo = itemView.findViewById(R.id.aviso_item_lblTitulo);
        lblDescricao = itemView.findViewById(R.id.aviso_item_lblDescricao);
        lblData = itemView.findViewById(R.id.aviso_item_lblData);
        lblStatus = itemView.findViewById(R.id.aviso_item_lblStatus);
    }
}
