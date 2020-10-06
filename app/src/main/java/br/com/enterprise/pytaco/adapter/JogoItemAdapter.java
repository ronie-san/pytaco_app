package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.JogoItemHolder;
import br.com.enterprise.pytaco.pojo.Jogo;

public class JogoItemAdapter extends CustomRecyclerAdapter<Jogo, JogoItemHolder> {

    public JogoItemAdapter(BaseRecyclerActivity activity, List<Jogo> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected JogoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new JogoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull final Jogo item, @NotNull final JogoItemHolder holder) {
        holder.getLblNomeLiga().setText(item.getLiga().getName());
        holder.getLblStatus().setText(item.getStatusExt());
        holder.getLblHomeTeam().setText(item.getHomeTime().getName());
        holder.getLblAwayTeam().setText(item.getAwayTime().getName());
        holder.getLblVenue().setText(item.getLocal());
    }
}
