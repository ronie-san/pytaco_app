package br.com.enterprise.pytaco.adapter;

import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.enterprise.pytaco.activity.BaseRecyclerActivity;
import br.com.enterprise.pytaco.holder.BolaoAbertoItemHolder;
import br.com.enterprise.pytaco.pojo.DetalheJogo;

public class BolaoAbertoItemAdapter extends CustomRecyclerAdapter<DetalheJogo, BolaoAbertoItemHolder> {

    public BolaoAbertoItemAdapter(BaseRecyclerActivity activity, List<DetalheJogo> lst, int itemLayout) {
        super(activity, lst, itemLayout);
    }

    @Override
    protected BolaoAbertoItemHolder pCreateHolder(View view, OnLstItemClickListener listener) {
        return new BolaoAbertoItemHolder(view, listener);
    }

    @Override
    protected void pSetViewProperties(@NotNull final DetalheJogo item, @NotNull final BolaoAbertoItemHolder holder) {
        CompoundButton.OnCheckedChangeListener onChange = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pSetColor(compoundButton, b ? android.R.color.black : android.R.color.white);
                item.setIdAposta((String) compoundButton.getTag());
            }
        };

        holder.getBtnHomeTeam().setText(item.getNome());
        holder.getBtnHomeTeam().setTag(item.getIdTeam());
        holder.getBtnHomeTeam().setOnCheckedChangeListener(onChange);

        holder.getBtnEmpate().setTag("0");
        holder.getBtnEmpate().setOnCheckedChangeListener(onChange);

        holder.getBtnAwayTeam().setText(item.getNomeOtherTeam());
        holder.getBtnAwayTeam().setTag(item.getIdOtherTeam());
        holder.getBtnAwayTeam().setOnCheckedChangeListener(onChange);

        if (item.getIdTeam().equals("")) {
            holder.getBtnHomeTeam().setEnabled(false);
            holder.getBtnEmpate().setEnabled(false);
            holder.getBtnAwayTeam().setEnabled(false);
        } else {
            holder.getBtnHomeTeam().setEnabled(true);
            holder.getBtnEmpate().setEnabled(true);
            holder.getBtnAwayTeam().setEnabled(true);
        }
    }

    private void pSetColor(@NotNull CompoundButton compoundButton, @ColorRes int color) {
        compoundButton.setTextColor(ContextCompat.getColor(compoundButton.getContext(), color));
    }

}
