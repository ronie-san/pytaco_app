package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class BolaoAbertoItemHolder extends CustomViewHolder {

    private RadioButton btnHomeTeam;
    private RadioButton btnEmpate;
    private RadioButton btnAwayTeam;

    public BolaoAbertoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public RadioButton getBtnHomeTeam() {
        return btnHomeTeam;
    }

    public RadioButton getBtnEmpate() {
        return btnEmpate;
    }

    public RadioButton getBtnAwayTeam() {
        return btnAwayTeam;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        btnHomeTeam = itemView.findViewById(R.id.bolao_aberto_item_btnHomeTeam);
        btnEmpate = itemView.findViewById(R.id.bolao_aberto_item_btnEmpate);
        btnAwayTeam = itemView.findViewById(R.id.bolao_aberto_item_btnAwayTeam);
    }
}
