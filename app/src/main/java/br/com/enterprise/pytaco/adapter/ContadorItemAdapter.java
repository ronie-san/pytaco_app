package br.com.enterprise.pytaco.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorItemAdapter extends CustomAdapter<Membro> {

    public ContadorItemAdapter(List<Membro> lst, BaseActivity activity, @LayoutRes int resource) {
        super(lst, activity, resource);
    }

    @Override
    protected void pSetItem(int position) {
        final Membro membro = lst.get(position);

        final CheckBox chkMarcado = view.findViewById(R.id.contador_item_chkMarcado);
        chkMarcado.setChecked(membro.isMarcado());
        chkMarcado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                membro.setMarcado(chkMarcado.isChecked());
            }
        });

        TextView lblNome = view.findViewById(R.id.contador_item_lblNome);
        lblNome.setText(membro.getNome());

        TextView lblQtdFicha = view.findViewById(R.id.contador_item_lblQtdFicha);
        lblQtdFicha.setText(StringUtil.numberToStr(membro.getQtdFicha()));

        TextView lblTipo = view.findViewById(R.id.contador_item_lblTipo);
        lblTipo.setText(membro.getTipoExt());

//        TextView lblStatus = view.findViewById(R.id.contador_item_lblStatus);
//        lblStatus.setText(membro.getStatusExt());
    }
}
