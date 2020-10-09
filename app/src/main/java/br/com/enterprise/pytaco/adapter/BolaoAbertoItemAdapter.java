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
    protected void pSetViewProperties(@NotNull DetalheJogo item, @NotNull final BolaoAbertoItemHolder holder) {
        CompoundButton.OnCheckedChangeListener onCheckChange = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NotNull CompoundButton compoundButton, boolean b) {
                pSetColor(compoundButton, b ? android.R.color.black : android.R.color.white);

                if (compoundButton.equals(holder.getBtnAwayTeam())) {
                    holder.getBtnEmpate().setChecked(false);
                    holder.getBtnHomeTeam().setChecked(false);
                } else if (compoundButton.equals(holder.getBtnEmpate())) {
                    holder.getBtnAwayTeam().setChecked(false);
                    holder.getBtnHomeTeam().setChecked(false);
                } else if (compoundButton.equals(holder.getBtnHomeTeam())) {
                    holder.getBtnEmpate().setChecked(false);
                    holder.getBtnAwayTeam().setChecked(false);
                }
            }
        };

        holder.getBtnAwayTeam().setOnCheckedChangeListener(onCheckChange);
        holder.getBtnEmpate().setOnCheckedChangeListener(onCheckChange);
        holder.getBtnHomeTeam().setOnCheckedChangeListener(onCheckChange);
    }

    private void pSetColor(@NotNull CompoundButton compoundButton, @ColorRes int color) {
        compoundButton.setTextColor(ContextCompat.getColor(compoundButton.getContext(), color));
    }

}
