package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.PytacosTrocadosItemHolder;
import br.com.enterprise.pytaco.pojo.PytacoTrocado;
import br.com.enterprise.pytaco.util.StringUtil;

public class PytacosTrocadosItemAdapter extends CustomRecyclerAdapter<PytacoTrocado, PytacosTrocadosItemHolder> {

    public PytacosTrocadosItemAdapter(BaseRecyclerActivity activity, List<PytacoTrocado> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected PytacosTrocadosItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new PytacosTrocadosItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull PytacoTrocado item, @NotNull PytacosTrocadosItemHolder holder) {
        holder.getLblQtdPytaco().setText(StringUtil.numberToStr(item.getQtdPytaco()));
        holder.getLblFicha().setText(StringUtil.numberToStr(item.getQtdFicha()));
        holder.getLblUsuario().setText(item.getNomeUsuario());
    }
}
