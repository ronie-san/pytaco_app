package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.enterprise.pytaco.R;

public class AdministracaoActivity extends BaseActivity {

    private TextView lblCodClube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracao);

        ImageButton btnVoltar = findViewById(R.id.administracao_btnVoltar);
        lblCodClube = findViewById(R.id.administracao_lblCodClube);
        ImageButton btnTrocarPytacos = findViewById(R.id.administracao_btnTrocarPytacos);
        ImageButton btnNotificacoes = findViewById(R.id.administracao_btnNotificacoes);
        ImageButton btnRelatorios = findViewById(R.id.administracao_btnRelatorios);
        ImageButton btnDesfazerClube = findViewById(R.id.administracao_btnDesfazerClube);
        ImageButton btnSairClube = findViewById(R.id.administracao_btnSairClube);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });

        btnTrocarPytacos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTrocarPytacosClick();
            }
        });

        btnNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNotificacoesClick();
            }
        });

        btnRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRelatoriosClick();
            }
        });

        btnDesfazerClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDesfazerClubeClick();
            }
        });

        btnSairClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSairClubeClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        lblCodClube.setText(clube.getCodClube());
    }

    private void btnSairClubeClick() {
        pStartActivity(SairClubeActivity.class);
    }

    private void btnDesfazerClubeClick() {
        pStartActivity(DesfazerClubeActivity.class);
    }

    private void btnRelatoriosClick() {
        pStartActivity(RelatoriosActivity.class);
    }

    private void btnNotificacoesClick() {
        pStartActivity(AvisosActivity.class);
    }

    private void btnTrocarPytacosClick() {
        pStartActivity(TrocarPytacosActivity.class);
    }
}