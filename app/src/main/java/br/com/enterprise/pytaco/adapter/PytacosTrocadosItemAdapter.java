package br.com.enterprise.pytaco.adapter;

import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.PytacoTrocado;
import br.com.enterprise.pytaco.util.StringUtil;

public class PytacosTrocadosItemAdapter extends CustomAdapter<PytacoTrocado> {

    public PytacosTrocadosItemAdapter(List<PytacoTrocado> lst, BaseActivity activity, @LayoutRes int resource) {
        super(lst, activity, resource);
    }

    @Override
    protected void pSetItem(int position) {
        PytacoTrocado pytacoTrocado = getLst().get(position);
        TextView lblQtdPytaco = view.findViewById(R.id.pytacos_trocados_item_lblQtdPytaco);
        TextView lblFicha = view.findViewById(R.id.pytacos_trocados_item_lblQtdFicha);
        TextView lblUsuario = view.findViewById(R.id.pytacos_trocados_item_lblUsuario);

        lblQtdPytaco.setText(StringUtil.numberToStr(pytacoTrocado.getQtdPytaco()));
        lblFicha.setText(StringUtil.numberToStr(pytacoTrocado.getQtdFicha()));
        lblUsuario.setText(pytacoTrocado.getNomeUsuario());
    }

}
