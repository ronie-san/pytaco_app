package br.com.enterprise.pytaco.adapter;

import android.view.View;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.PacoteCompraItemHolder;
import br.com.enterprise.pytaco.pojo.PacoteCompra;

public class PacoteCompraItemAdapter extends CustomRecyclerAdapter<PacoteCompra, PacoteCompraItemHolder> {

    public PacoteCompraItemAdapter(BaseRecyclerActivity activity, List<PacoteCompra> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected PacoteCompraItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new PacoteCompraItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(PacoteCompra item, PacoteCompraItemHolder holder) {

    }
}
