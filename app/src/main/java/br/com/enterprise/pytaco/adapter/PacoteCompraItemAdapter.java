package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.PacoteCompraItemHolder;
import br.com.enterprise.pytaco.pojo.PacoteCompra;
import br.com.enterprise.pytaco.util.StringUtil;

public class PacoteCompraItemAdapter extends CustomRecyclerAdapter<PacoteCompra, PacoteCompraItemHolder> {

    public PacoteCompraItemAdapter(BaseRecyclerActivity activity, List<PacoteCompra> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected PacoteCompraItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new PacoteCompraItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull PacoteCompra item, @NotNull PacoteCompraItemHolder holder) {
        holder.getLblDescricao().setText(item.getDescricao());
        holder.getLblResumo().setText(item.getResumo());
        String sb = "R$ " +
                StringUtil.priceToStr(item.getValor());
        holder.getlblValor().setText(sb);
    }
}
