package br.com.enterprise.pytaco.activity;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public abstract class BaseRecyclerActivity extends BaseActivity implements CustomRecyclerAdapter.OnLstItemClickListener {

    public abstract RecyclerView getRecyclerView();

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        T v = super.findViewById(id);

        if (v instanceof RecyclerView) {
            if (((RecyclerView) v).getLayoutManager() == null) {
                ((RecyclerView) v).setLayoutManager(new LinearLayoutManager(this));
            }

            if (((RecyclerView) v).getItemDecorationCount() == 0) {
                ((RecyclerView) v).addItemDecoration(new DividerItemDecoration(this, 0) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        if (parent.getAdapter() != null && parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                            outRect.bottom = 10;
                        }
                    }
                });
            }
        }

        return v;
    }
}
