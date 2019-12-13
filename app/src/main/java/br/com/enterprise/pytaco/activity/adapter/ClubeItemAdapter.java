package br.com.enterprise.pytaco.activity.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;

public class ClubeItemAdapter extends BaseAdapter {

    private List<Clube> lstClube;
    private Activity activity;

    public ClubeItemAdapter(List<Clube> lstClube, Activity activity){
        this.lstClube = lstClube;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return lstClube == null ? 0 : lstClube.size();
    }

    @Override
    public Object getItem(int position) {
        return lstClube.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lstClube.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_clube_item, parent, false);
        Clube clube = lstClube.get(position);

        TextView lblNome = view.findViewById(R.id.clube_item_lblNome);
        lblNome.setText(clube.getNome());

        TextView lblDescricao = view.findViewById(R.id.clube_item_lblDescricao);
        lblDescricao.setText(clube.getDescricao());

        TextView lblQtdPytaco = view.findViewById(R.id.clube_item_lblQtdPytaco);
        lblQtdPytaco.setText(clube.getQtdPytaco().toString());

        TextView lblQtdFicha = view.findViewById(R.id.clube_item_lblQtdFicha);
        lblQtdFicha.setText(clube.getQtdFicha().toString());

        return view;
    }
}
