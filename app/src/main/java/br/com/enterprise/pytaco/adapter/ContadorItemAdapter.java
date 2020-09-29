package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.ContadorItemHolder;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorItemAdapter extends CustomRecyclerAdapter<Membro, ContadorItemHolder> {

    public ContadorItemAdapter(BaseRecyclerActivity activity, List<Membro> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected ContadorItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new ContadorItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull final Membro item, @NotNull final ContadorItemHolder holder) {
        holder.getChkMarcado().setChecked(item.isMarcado());
        holder.getChkMarcado().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setMarcado(holder.getChkMarcado().isChecked());
            }
        });

        holder.getLblNome().setText(item.getNome());
        holder.getLblQtdFicha().setText(StringUtil.numberToStr(item.getQtdFicha()));
        holder.getLblTipo().setText(item.getTipoExt());
    }
}
