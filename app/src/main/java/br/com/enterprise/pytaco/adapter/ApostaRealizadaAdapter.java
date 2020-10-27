package br.com.enterprise.pytaco.adapter;

import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.ApostaRealizadaHolder;
import br.com.enterprise.pytaco.pojo.Aposta;
import br.com.enterprise.pytaco.util.StringUtil;

public class ApostaRealizadaAdapter extends CustomRecyclerAdapter<Aposta, ApostaRealizadaHolder> {

    public ApostaRealizadaAdapter(BaseRecyclerActivity activity, List<Aposta> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected ApostaRealizadaHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new ApostaRealizadaHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull Aposta item, @NotNull ApostaRealizadaHolder holder) {
        holder.getLblId().setText(StringUtil.numberToStr(item.getId()));
        holder.getLblUsuario().setText(item.getNomeUsuario());
        holder.getLblQtdPonto().setText(StringUtil.numberToStr(item.getPontos()) + " pontos");
    }
}
