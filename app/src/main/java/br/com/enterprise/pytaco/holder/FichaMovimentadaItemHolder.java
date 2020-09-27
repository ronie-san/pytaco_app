package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class FichaMovimentadaItemHolder extends CustomViewHolder {

    private TextView lblUsuarioRecebeu;
    private TextView lblQtdFichaAnterior;
    private TextView lblQtdFichaAtual;
    private TextView lblAcao;
    private ImageView imgAcao;
    private TextView lblQtdFichaMovimentada;
    private TextView lblUsuarioEnvio;
    private TextView lblQtdFichaAnteriorAdmin;
    private TextView lblQtdFichaAtualAdmin;

    public FichaMovimentadaItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblUsuarioRecebeu() {
        return lblUsuarioRecebeu;
    }

    public TextView getLblQtdFichaAnterior() {
        return lblQtdFichaAnterior;
    }

    public TextView getLblQtdFichaAtual() {
        return lblQtdFichaAtual;
    }

    public TextView getLblAcao() {
        return lblAcao;
    }

    public ImageView getImgAcao() {
        return imgAcao;
    }

    public TextView getLblQtdFichaMovimentada() {
        return lblQtdFichaMovimentada;
    }

    public TextView getLblUsuarioEnvio() {
        return lblUsuarioEnvio;
    }

    public TextView getLblQtdFichaAnteriorAdmin() {
        return lblQtdFichaAnteriorAdmin;
    }

    public TextView getLblQtdFichaAtualAdmin() {
        return lblQtdFichaAtualAdmin;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblUsuarioRecebeu = itemView.findViewById(R.id.ficha_movimentada_item_lblUsuarioRecebeu);
        lblQtdFichaAnterior = itemView.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAnterior);
        lblQtdFichaAtual = itemView.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAtual);
        lblAcao = itemView.findViewById(R.id.ficha_movimentada_item_lblAcao);
        imgAcao = itemView.findViewById(R.id.ficha_movimentada_item_imgAcao);
        lblQtdFichaMovimentada = itemView.findViewById(R.id.ficha_movimentada_item_lblQtdFichaMovimentada);
        lblUsuarioEnvio = itemView.findViewById(R.id.ficha_movimentada_item_lblUsuarioEnvio);
        lblQtdFichaAnteriorAdmin = itemView.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAnteriorAdmin);
        lblQtdFichaAtualAdmin = itemView.findViewById(R.id.ficha_movimentada_item_lblQtdFichaAtualAdmin);
    }
}
