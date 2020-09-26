package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.FichaMovimentada;

public class FichasMovimentadasItemAdapter extends CustomAdapter<FichaMovimentada> {

    public FichasMovimentadasItemAdapter(List<FichaMovimentada> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_ficha_movimentada_item, parent, false);
        return view;
    }
}
