package br.com.enterprise.pytaco.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.adapter.CustomRecyclerAdapter;

public class JogoItemHolder extends CustomViewHolder {

    private ImageView imgJogo;
    private CheckBox chkMarcado;
    private TextView lblNomeLiga;
    private TextView lblData;
    private TextView lblVenue;
    private TextView lblHomeTeam;
    private TextView lblAwayTeam;

    public JogoItemHolder(@NonNull View itemView, CustomRecyclerAdapter.OnLstItemClickListener listener) {
        super(itemView, listener);
    }

    public ImageView getImgJogo() {
        return imgJogo;
    }

    public CheckBox getChkMarcado() {
        return chkMarcado;
    }

    public TextView getLblNomeLiga() {
        return lblNomeLiga;
    }

    public TextView getLblData() {
        return lblData;
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
        imgJogo = itemView.findViewById(R.id.jogo_item_imgJogo);
        chkMarcado = itemView.findViewById(R.id.jogo_item_chkMarcado);
        lblNomeLiga = itemView.findViewById(R.id.jogo_item_lblNomeLiga);
        lblData = itemView.findViewById(R.id.jogo_item_lblData);
        lblVenue = itemView.findViewById(R.id.jogo_item_lblLocal);
        lblHomeTeam = itemView.findViewById(R.id.jogo_item_lblHomeTeam);
        lblAwayTeam = itemView.findViewById(R.id.jogo_item_lblAwayTeam);
    }
}
