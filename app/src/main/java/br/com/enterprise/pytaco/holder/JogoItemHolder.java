package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class JogoItemHolder extends CustomViewHolder {

    private TextView lblNomeLiga;
    private TextView lblStatus;
    private TextView lblVenue;
    private TextView lblHomeTeam;
    private TextView lblAwayTeam;

    public JogoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public TextView getLblNomeLiga() {
        return lblNomeLiga;
    }

    public TextView getLblStatus() {
        return lblStatus;
    }

    public TextView getLblVenue() {
        return lblVenue;
    }

    public TextView getLblHomeTeam() {
        return lblHomeTeam;
    }

    public TextView getLblAwayTeam() {
        return lblAwayTeam;
    }

    @Override
    protected void pFindViews(@NonNull View itemView) {
        lblNomeLiga = itemView.findViewById(R.id.jogo_item_lblNomeLiga);
        lblStatus = itemView.findViewById(R.id.jogo_item_lblStatus);
        lblVenue = itemView.findViewById(R.id.jogo_item_lblLocal);
        lblHomeTeam = itemView.findViewById(R.id.jogo_item_lblHomeTeam);
        lblAwayTeam = itemView.findViewById(R.id.jogo_item_lblAwayTeam);
    }
}
