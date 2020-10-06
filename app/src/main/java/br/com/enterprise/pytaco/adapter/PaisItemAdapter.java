package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.PaisItemHolder;
import br.com.enterprise.pytaco.pojo.Pais;

public class PaisItemAdapter extends CustomRecyclerAdapter<Pais, PaisItemHolder> {

    public PaisItemAdapter(BaseRecyclerActivity activity, List<Pais> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected PaisItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new PaisItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Pais item, @NotNull PaisItemHolder holder) {
        String sigla = item.getSigla().toLowerCase();

        switch (sigla){
            case "ar":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_ar);
                break;
            case "be":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_be);
                break;
            case "bg":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_bg);
                break;
            case "br":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_br);
                break;
            case "de":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_de);
                break;
            case "es":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_es);
                break;
            case "fr":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_fr);
                break;
            case "gb":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_gb);
                break;
            case "it":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_it);
                break;
            case "jp":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_jp);
                break;
            case "nl":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_nl);
                break;
            case"no":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_no);
                break;
            case "pt":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_pt);
                break;
            case "rs":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_rs);
                break;
            case "tr":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_tr);
                break;
            case "ua":
                holder.getImgBandeira().setImageResource(R.drawable.bandeira_ua);
                break;
            default:
                break;
        }

        holder.getLblNome().setText(item.getNome());
    }
}
