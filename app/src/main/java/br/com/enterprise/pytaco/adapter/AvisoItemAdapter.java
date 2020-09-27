package br.com.enterprise.pytaco.adapter;

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
        TextView lblTitulo = view.findViewById(R.id.aviso_item_lblTitulo);
        TextView lblDescricao = view.findViewById(R.id.aviso_item_lblDescricao);
        TextView lblData = view.findViewById(R.id.aviso_item_lblData);
        TextView lblStatus = view.findViewById(R.id.aviso_item_lblStatus);

        if (aviso != null) {
            if (aviso.getStatus().equals("E")) {
                imgAviso.setImageResource(R.drawable.bola_vermelha);
            } else if (aviso.getStatus().equals("L")) {
                imgAviso.setImageResource(R.drawable.bola_cinza);
            }

            lblTitulo.setText(aviso.getTitulo());
            lblDescricao.setText(aviso.getDescricao());
            lblData.setText(aviso.getData());
            lblStatus.setText(aviso.getStatusExt());
        } else {
            lblTitulo.setText("");
            lblDescricao.setText("");
            lblData.setText("");
            lblStatus.setText("");
        }
    }
}
