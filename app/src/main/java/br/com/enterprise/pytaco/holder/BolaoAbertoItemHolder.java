package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class BolaoAbertoItemHolder extends CustomViewHolder {

    private ToggleButton btnHomeTeam;
    private ToggleButton btnEmpate;
    private ToggleButton btnAwayTeam;

    public BolaoAbertoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ToggleButton getBtnHomeTeam() {
        return btnHomeTeam;
    }

    public ToggleButton getBtnEmpate() {
        return btnEmpate;
    }

    public ToggleButton getBtnAwayTeam() {
        return btnAwayTeam;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        btnHomeTeam = itemView.findViewById(R.id.bolao_aberto_item_btnHomeTeam);
        btnEmpate = itemView.findViewById(R.id.bolao_aberto_item_btnEmpate);
        btnAwayTeam = itemView.findViewById(R.id.bolao_aberto_item_btnAwayTeam);
    }
}
