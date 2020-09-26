package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.FichaMovimentada;
import br.com.enterprise.pytaco.util.StringUtil;

public class FichasMovimentadasItemAdapter extends CustomAdapter<FichaMovimentada> {

    public FichasMovimentadasItemAdapter(List<FichaMovimentada> lst, Activity activity) {
        super(lst, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.lst_ficha_movimentada_item, parent, false);
        FichaMovimentada fichaMovimentada = lst.get(position);

        TextView lblUsuarioRecebeu = view.findViewById(R.id.ficha_movimentada_item_lblUsuarioRecebeu);
        TextView lblQtdFichaAnterior = view.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAnterior);
        TextView lblQtdFichaAtual = view.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAtual);
        TextView lblAcao = view.findViewById(R.id.ficha_movimentada_item_lblAcao);
        ImageView imgAcao = view.findViewById(R.id.ficha_movimentada_item_imgAcao);
        TextView lblQtdFichaMovimentada = view.findViewById(R.id.ficha_movimentada_item_lblQtdFichaMovimentada);
        TextView lblUsuarioEnvio = view.findViewById(R.id.ficha_movimentada_item_lblUsuarioEnvio);
        TextView lblQtdFichaAnteriorAdmin = view.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAnteriorAdmin);
        TextView lblQtdFichaAtualAdmin = view.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAtualAdmin);

        lblUsuarioRecebeu.setText(fichaMovimentada.getNomeUsuarioRecebimento());
        lblQtdFichaAnterior.setText(StringUtil.numberToStr(fichaMovimentada.getQtdFichaAnterior()));
        lblQtdFichaAtual.setText(StringUtil.numberToStr(fichaMovimentada.getQtdFichaAtual()));

        lblAcao.setText(fichaMovimentada.getAcao());
        if (fichaMovimentada.getAcao().equals("Retirada")) {
            imgAcao.setImageResource(R.drawable.img_retirada);
        } else if (fichaMovimentada.getAcao().equals("Envio")) {
            imgAcao.setImageResource(R.drawable.img_envio);
        }
        lblQtdFichaMovimentada.setText(StringUtil.numberToStr(fichaMovimentada.getQtdFichaMovimento()));

        lblUsuarioEnvio.setText(fichaMovimentada.getNomeUsuarioEnvio());
        lblQtdFichaAnteriorAdmin.setText(StringUtil.numberToStr(fichaMovimentada.getSaldoAnteriorAdmin()));
        lblQtdFichaAtualAdmin.setText(StringUtil.numberToStr(fichaMovimentada.getSaldoAtualAdmin()));

        return view;
    }
}
