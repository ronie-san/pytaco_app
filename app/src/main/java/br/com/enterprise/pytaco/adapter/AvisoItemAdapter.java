package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.Aviso;

public class AvisoItemAdapter extends CustomAdapter<Aviso> {

    public AvisoItemAdapter(List<Aviso> lst, BaseActivity activity, @LayoutRes int resource) {
        super(lst, activity, resource);
    }

    @Override
    protected void pSetItem(int position) {
        Aviso aviso = lst.get(position);

        ImageView imgAviso = view.findViewById(R.id.aviso_item_imgAviso);
        if (aviso != null) {
            if (aviso.getStatus().equals("E")) {
                imgAviso.setImageResource(R.drawable.bola_vermelha);
            } else if (aviso.getStatus().equals("L")) {
                imgAviso.setImageResource(R.drawable.bola_cinza);
            }
        }

        TextView lblTitulo = view.findViewById(R.id.aviso_item_lblTitulo);
        lblTitulo.setText(aviso.getTitulo());

        TextView lblDescricao = view.findViewById(R.id.aviso_item_lblDescricao);
        lblDescricao.setText(aviso.getDescricao());

        TextView lblData = view.findViewById(R.id.aviso_item_lblData);
        lblData.setText(aviso.getData());

        TextView lblStatus = view.findViewById(R.id.aviso_item_lblStatus);
        lblStatus.setText(aviso.getStatusExt());
    }
}
