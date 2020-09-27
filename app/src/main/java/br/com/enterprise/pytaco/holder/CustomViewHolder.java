package br.com.enterprise.pytaco.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public abstract class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CustomRecyclerAdapter.OnLstItemClickListener listener;

    public CustomViewHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        pFindViews(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onLstItemClick(getAdapterPosition());
    }

    protected abstract void pFindViews(@NonNull View itemView);
}
