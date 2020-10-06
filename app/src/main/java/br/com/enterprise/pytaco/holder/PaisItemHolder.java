package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class PaisItemHolder extends CustomViewHolder {

    private ImageView imgBandeira;
    private TextView lblNome;

    public PaisItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgBandeira() {
        return imgBandeira;
    }

    public TextView getLblNome() {
        return lblNome;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        imgBandeira = itemView.findViewById(R.id.pais_item_imgBandeira);
        lblNome = itemView.findViewById(R.id.pais_item_lblNome);
    }
}
