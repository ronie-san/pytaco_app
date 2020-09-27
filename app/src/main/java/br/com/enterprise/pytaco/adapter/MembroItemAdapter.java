package br.com.enterprise.pytaco.adapter;

import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class MembroItemAdapter extends CustomAdapter<Membro> {

    public MembroItemAdapter(List<Membro> lst, BaseActivity activity, @LayoutRes int resource) {
        super(lst, activity, resource);
    }

    @Override
    protected void pSetItem(int position) {
        Membro membro = lst.get(position);

        TextView lblNome = view.findViewById(R.id.membro_item_lblNome);
        lblNome.setText(membro.getNome());

        TextView lblQtdFicha = view.findViewById(R.id.membro_item_lblQtdFicha);
        lblQtdFicha.setText(StringUtil.numberToStr(membro.getQtdFicha()));

        TextView lblTipo = view.findViewById(R.id.membro_item_lblTipo);
        lblTipo.setText(membro.getTipoExt());

        TextView lblStatus = view.findViewById(R.id.membro_item_lblStatus);
        lblStatus.setText(membro.getStatusExt());
    }

}
