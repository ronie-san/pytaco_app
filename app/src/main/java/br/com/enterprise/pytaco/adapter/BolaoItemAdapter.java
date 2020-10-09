package br.com.enterprise.pytaco.adapter;

import android.view.View;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.BolaoItemHolder;
import br.com.enterprise.pytaco.pojo.Bolao;
import br.com.enterprise.pytaco.util.StringUtil;

public class BolaoItemAdapter extends CustomRecyclerAdapter<Bolao, BolaoItemHolder> {

    public BolaoItemAdapter(BaseRecyclerActivity activity, List<Bolao> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected BolaoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new BolaoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Bolao item, @NotNull BolaoItemHolder holder) {
        @ColorInt int cor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.white);

        switch (item.getStatus()) {
            //FINALIZADO
            case "0":
                holder.getImgBolao().setImageResource(R.drawable.bolao_finalizado);
                cor = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black);
                break;
            //ABERTO
            case "1":
                holder.getImgBolao().setImageResource(R.drawable.bolao_aberto);
                break;
            //EM ANDAMENTO
            case "2":
                holder.getImgBolao().setImageResource(R.drawable.bolao_em_andamento);
                break;
            default:
                break;
        }

        holder.getLblTituloValor().setTextColor(cor);
        holder.getLblValor().setText(StringUtil.numberToStr(item.getValor()));

        holder.getLblTituloNome().setTextColor(cor);
        holder.getLblNome().setTextColor(cor);
        holder.getLblNome().setText(item.getNome());
    }
}
