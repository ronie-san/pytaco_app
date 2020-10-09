package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.JogoItemHolder;
import br.com.enterprise.pytaco.pojo.Jogo;
import br.com.enterprise.pytaco.util.DateUtil;

public class JogoItemAdapter extends CustomRecyclerAdapter<Jogo, JogoItemHolder> implements CustomRecyclerAdapter.OnLstItemClickListener {

    public JogoItemAdapter(BaseRecyclerActivity activity, List<Jogo> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected JogoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new JogoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull final Jogo item, @NotNull final JogoItemHolder holder) {
        if (item.getStatusShort().equals("NS")) {
            holder.getImgJogo().setImageResource(R.drawable.background_jogo);
        } else {
            holder.getImgJogo().setImageResource(R.drawable.background_jogo_cinza);
        }

        holder.getChkMarcado().setChecked(item.isMarcado());
        holder.getLblNomeLiga().setText(item.getLiga().getName());
        holder.getLblData().setText(DateUtil.toDefaultFormat(DateUtil.parse(item.getEventDate(), "yyyy-MM-dd")));
        holder.getLblHomeTeam().setText(item.getHomeTime().getName());
        holder.getLblAwayTeam().setText(item.getAwayTime().getName());
        holder.getLblVenue().setText(item.getLocal());
    }

    @Override
    public void onLstItemClick(int position) {

    }
}
