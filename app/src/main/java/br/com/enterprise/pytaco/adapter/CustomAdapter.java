package br.com.enterprise.pytaco.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseActivity;
import br.com.enterprise.pytaco.pojo.BaseEntity;

public abstract class CustomAdapter<T extends BaseEntity> extends BaseAdapter {

    protected List<T> lst;
    protected BaseActivity activity;
    @LayoutRes
    protected int resource;
    protected View view;

    public CustomAdapter(List<T> lst, BaseActivity activity, @LayoutRes int resource) {
        this.lst = lst;
        this.activity = activity;
        this.resource = resource;
    }

    protected abstract void pSetItem(int position);

    public List<T> getLst() {
        return lst;
    }

    @Override
    public int getCount() {
        return lst == null ? 0 : lst.size();
    }

    @Override
    public Object getItem(int i) {
        return lst == null ? null : lst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lst == null ? -1 : lst.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = activity.getLayoutInflater().inflate(resource, parent, false);
        pSetItem(position);
        return view;
    }
}
