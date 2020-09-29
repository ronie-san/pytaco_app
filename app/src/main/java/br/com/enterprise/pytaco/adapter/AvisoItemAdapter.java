package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.AvisoItemHolder;
import br.com.enterprise.pytaco.pojo.Aviso;

public class AvisoItemAdapter extends CustomRecyclerAdapter<Aviso, AvisoItemHolder> {

    public AvisoItemAdapter(BaseRecyclerActivity activity, List<Aviso> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected AvisoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new AvisoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Aviso item, AvisoItemHolder holder) {
        if (item.getStatus().equals("E")) {
            holder.getImgAviso().setImageResource(R.drawable.bola_vermelha);
        } else if (item.getStatus().equals("L")) {
            holder.getImgAviso().setImageResource(R.drawable.bola_cinza);
        }

        holder.getLblTitulo().setText(item.getTitulo());
        holder.getLblDescricao().setText(item.getDescricao());
        holder.getLblData().setText(item.getData());
        holder.getLblStatus().setText(item.getStatusExt());
    }
}
