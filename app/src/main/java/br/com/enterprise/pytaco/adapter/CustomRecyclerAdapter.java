package br.com.enterprise.pytaco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.pojo.BaseEntity;

public abstract class CustomRecyclerAdapter<T extends BaseEntity, R extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<R> {

    private List<T> lst;
    @LayoutRes
    private int itemLayout;
    private OnLstItemClickListener listener;

    public CustomRecyclerAdapter(final BaseRecyclerActivity activity, List<T> lst, @LayoutRes int itemLayout) {
        this.lst = lst;
        this.itemLayout = itemLayout;
        this.listener = activity;

        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if (CustomRecyclerAdapter.this.lst == null || CustomRecyclerAdapter.this.lst.size() == 0) {
                    activity.getRecyclerView().setVisibility(View.GONE);
                    activity.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                } else {
                    activity.getRecyclerView().setVisibility(View.VISIBLE);
                    activity.findViewById(android.R.id.empty).setVisibility(View.GONE);
                }
            }
        });
    }

    public List<T> getLst() {
        return lst;
    }

    protected abstract R pCreateHolder(View view, OnLstItemClickListener listener);

    protected abstract void pSetViewProperties(T item, R holder);

    @NonNull
    @Override
    public R onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return pCreateHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull R holder, int position) {
        T item = lst.get(holder.getAdapterPosition());
        pSetViewProperties(item, holder);
    }

    @Override
    public int getItemCount() {
        return lst == null ? 0 : lst.size();
    }

    public interface OnLstItemClickListener {
        void onLstItemClick(int position);
    }
}
