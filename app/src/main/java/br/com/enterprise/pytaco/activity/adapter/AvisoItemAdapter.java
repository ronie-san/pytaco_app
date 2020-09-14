package br.com.enterprise.pytaco.activity.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Aviso;

public class AvisoItemAdapter extends CustomAdapter<Aviso> {

    public AvisoItemAdapter(List<Aviso> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_aviso_item, parent, false);
        Aviso aviso = lst.get(position);

        TextView lblTitulo = view.findViewById(R.id.aviso_item_lblTitulo);
        lblTitulo.setText(aviso.getTitulo());

        TextView lblDescricao = view.findViewById(R.id.aviso_item_lblDescricao);
        lblDescricao.setText(aviso.getDescricao());

        TextView lblData = view.findViewById(R.id.aviso_item_lblData);
        lblData.setText(aviso.getData());

        TextView lblStatus = view.findViewById(R.id.aviso_item_lblStatus);
        lblStatus.setText(aviso.getStatus());

        return view;
    }
}
