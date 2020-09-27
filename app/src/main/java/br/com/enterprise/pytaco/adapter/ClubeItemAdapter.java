package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.ClubeItemHolder;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.StringUtil;

public class ClubeItemAdapter extends CustomRecyclerAdapter<Clube, ClubeItemHolder> {

    public ClubeItemAdapter(BaseRecyclerActivity activity, List<Clube> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected ClubeItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new ClubeItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Clube item, @NotNull ClubeItemHolder holder) {
        holder.getLblNome().setText(item.getNome());
        holder.getLblDescricao().setText(item.getDescricao());
        holder.getLblQtdFicha().setText(StringUtil.numberToStr(item.getQtdFicha()));
    }

}
