package br.com.enterprise.pytaco.adapter;

import android.app.Activity;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.enterprise.pytaco.pojo.BaseEntity;

public abstract class CustomAdapter<T extends BaseEntity> extends BaseAdapter {

    protected List<T> lst;
    protected Activity activity;

    public CustomAdapter(List<T> lst, Activity activity) {
        this.lst = lst;
        this.activity = activity;
    }

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
}
