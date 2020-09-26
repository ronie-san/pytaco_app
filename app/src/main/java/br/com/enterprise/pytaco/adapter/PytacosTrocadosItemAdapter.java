package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.PytacoTrocado;
import br.com.enterprise.pytaco.util.StringUtil;

public class PytacosTrocadosItemAdapter extends CustomAdapter<PytacoTrocado> {

    public PytacosTrocadosItemAdapter(List<PytacoTrocado> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PytacoTrocado pytacoTrocado = getLst().get(position);
        View view = activity.getLayoutInflater().inflate(R.layout.lst_pytacos_trocados_item, parent, false);
        TextView lblQtdPytaco = view.findViewById(R.id.pytacos_trocados_item_lblQtdPytaco);
        TextView lblFicha = view.findViewById(R.id.pytacos_trocados_item_lblQtdFicha);
        TextView lblUsuario = view.findViewById(R.id.pytacos_trocados_item_lblUsuario);

        lblQtdPytaco.setText(StringUtil.numberToStr(pytacoTrocado.getQtdPytaco()));
        lblFicha.setText(StringUtil.numberToStr(pytacoTrocado.getQtdFicha()));
        lblUsuario.setText(pytacoTrocado.getNomeUsuario());
        return view;
    }
}
