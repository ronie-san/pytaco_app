package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class MembroItemAdapter extends CustomAdapter<Membro> {

    public MembroItemAdapter(List<Membro> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_membro_item, parent, false);
        Membro membro = lst.get(position);

        TextView lblNome = view.findViewById(R.id.membro_item_lblNome);
        lblNome.setText(membro.getNome());

        TextView lblQtdFicha = view.findViewById(R.id.membro_item_lblQtdFicha);
        lblQtdFicha.setText(StringUtil.numberToStr(membro.getQtdFicha()));

        TextView lblTipo = view.findViewById(R.id.membro_item_lblTipo);
        lblTipo.setText(membro.getTipoExt());

        TextView lblStatus = view.findViewById(R.id.membro_item_lblStatus);
        lblStatus.setText(membro.getStatusExt());

        return view;
    }
}
