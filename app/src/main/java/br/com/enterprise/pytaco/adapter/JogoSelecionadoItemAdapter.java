package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.JogoSelecionadoItemHolder;
import br.com.enterprise.pytaco.pojo.Jogo;

public class JogoSelecionadoItemAdapter extends CustomRecyclerAdapter<Jogo, JogoSelecionadoItemHolder> {

    public JogoSelecionadoItemAdapter(BaseRecyclerActivity activity, List<Jogo> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected JogoSelecionadoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new JogoSelecionadoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Jogo item, @NotNull JogoSelecionadoItemHolder holder) {
        holder.getLblTimeHome().setText(item.getHomeTime().getName());
        holder.getLblTimeAway().setText(item.getAwayTime().getName());
    }
}
