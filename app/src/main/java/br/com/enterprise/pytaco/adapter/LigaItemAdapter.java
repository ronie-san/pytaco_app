package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.LigaItemHolder;
import br.com.enterprise.pytaco.pojo.League;

public class LigaItemAdapter extends CustomRecyclerAdapter<League, LigaItemHolder> {

    public LigaItemAdapter(BaseRecyclerActivity activity, List<League> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected LigaItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new LigaItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull League item, @NotNull LigaItemHolder holder) {
        holder.getLblNome().setText(item.getName());
    }
}