package br.com.enterprise.pytaco.activity.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.StringUtil;

public class ClubeItemAdapter extends CustomAdapter<Clube> {

    public ClubeItemAdapter(List<Clube> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_clube_item, parent, false);
        Clube clube = lst.get(position);

        TextView lblNome = view.findViewById(R.id.clube_item_lblNome);
        lblNome.setText(clube.getNome());

        TextView lblDescricao = view.findViewById(R.id.clube_item_lblDescricao);
        lblDescricao.setText(clube.getDescricao());

        TextView lblQtdFicha = view.findViewById(R.id.clube_item_lblQtdFicha);
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));

        return view;
    }
}
