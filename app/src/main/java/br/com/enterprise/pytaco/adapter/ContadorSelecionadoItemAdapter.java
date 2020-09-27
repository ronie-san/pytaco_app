package br.com.enterprise.pytaco.adapter;

import android.view.View;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.ContadorSelecionadoItemHolder;
import br.com.enterprise.pytaco.pojo.Membro;
import br.com.enterprise.pytaco.util.StringUtil;

public class ContadorSelecionadoItemAdapter extends CustomRecyclerAdapter<Membro, ContadorSelecionadoItemHolder> {

    public ContadorSelecionadoItemAdapter(BaseRecyclerActivity activity, List<Membro> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected ContadorSelecionadoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new ContadorSelecionadoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(Membro item, ContadorSelecionadoItemHolder holder) {
        holder.getLblNome().setText(item.getNome());
        holder.getLblQtdFicha().setText(StringUtil.numberToStr(item.getQtdFicha()));
        holder.getLblTipo().setText(item.getTipoExt());
    }
}
