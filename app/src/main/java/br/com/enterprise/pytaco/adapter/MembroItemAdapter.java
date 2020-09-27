package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.MembroItemHolder;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class MembroItemAdapter extends CustomRecyclerAdapter<Membro, MembroItemHolder> {

    public MembroItemAdapter(BaseRecyclerActivity activity, List<Membro> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected MembroItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new MembroItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Membro item, @NotNull MembroItemHolder holder) {
        holder.getLblNome().setText(item.getNome());
        holder.getLblQtdFicha().setText(StringUtil.numberToStr(item.getQtdFicha()));
        holder.getLblTipo().setText(item.getTipoExt());
        holder.getLblStatus().setText(item.getStatusExt());
    }
}
