package br.com.enterprise.pytaco.adapter;

import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.StringUtil;

public class ClubeItemAdapter extends CustomAdapter<Clube> {

    public ClubeItemAdapter(List<Clube> lst, BaseActivity activity, @LayoutRes int resource) {
        super(lst, activity, resource);
    }

    @Override
    protected void pSetItem(int position) {
        Clube clube = lst.get(position);

        TextView lblNome = view.findViewById(R.id.clube_item_lblNome);
        lblNome.setText(clube.getNome());

        TextView lblDescricao = view.findViewById(R.id.clube_item_lblDescricao);
        lblDescricao.setText(clube.getDescricao());

        TextView lblQtdFicha = view.findViewById(R.id.clube_item_lblQtdFicha);
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));
    }
}
