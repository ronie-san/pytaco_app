package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.FichaMovimentadaItemHolder;
import br.com.enterprise.pytaco.pojo.FichaMovimentada;
import br.com.enterprise.pytaco.util.StringUtil;

public class FichaMovimentadaItemAdapter extends CustomRecyclerAdapter<FichaMovimentada, FichaMovimentadaItemHolder> {

    public FichaMovimentadaItemAdapter(BaseRecyclerActivity activity, List<FichaMovimentada> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected FichaMovimentadaItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new FichaMovimentadaItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull FichaMovimentada item, @NotNull FichaMovimentadaItemHolder holder) {
        holder.getLblUsuarioRecebeu().setText(item.getNomeUsuarioRecebimento());
        holder.getLblQtdFichaAnterior().setText(StringUtil.numberToStr(item.getQtdFichaAnterior()));
        holder.getLblQtdFichaAtual().setText(StringUtil.numberToStr(item.getQtdFichaAtual()));
        holder.getLblAcao().setText(item.getAcao());


        if (item.getAcao().equals("Retirada")) {
            holder.getImgAcao().setImageResource(R.drawable.img_retirada);
        } else if (item.getAcao().equals("Envio")) {
            holder.getImgAcao().setImageResource(R.drawable.img_envio);
        }
        holder.getLblQtdFichaMovimentada().setText(StringUtil.numberToStr(item.getQtdFichaMovimento()));
        holder.getLblUsuarioEnvio().setText(item.getNomeUsuarioEnvio());
        holder.getLblQtdFichaAnteriorAdmin().setText(StringUtil.numberToStr(item.getSaldoAnteriorAdmin()));
        holder.getLblQtdFichaAtualAdmin().setText(StringUtil.numberToStr(item.getSaldoAtualAdmin()));
    }
}
