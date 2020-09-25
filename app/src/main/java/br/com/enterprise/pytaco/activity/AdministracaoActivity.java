package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.StringUtil;

public class AdministracaoActivity extends BaseActivity {

    private TextView lblCodClube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracao);

        ImageButton btnVoltar = findViewById(R.id.administracao_btnVoltar);
        lblCodClube = findViewById(R.id.administracao_lblCodClube);
        TextView lblTrocarPytacos = findViewById(R.id.administracao_lblTrocarPytacos);
        TextView lblNotificacoes = findViewById(R.id.administracao_lblNotificacoes);
        TextView lblRelatorios = findViewById(R.id.administracao_lblRelatorios);
        TextView lblDesfazerClube = findViewById(R.id.administracao_lblDesfazerClube);
        TextView lblSairClube = findViewById(R.id.administracao_lblSairClube);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });

        lblTrocarPytacos.setText(StringUtil.textoSublinhado(lblTrocarPytacos.getText().toString()));
        lblTrocarPytacos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblTrocarPytacosClick();
            }
        });

        lblNotificacoes.setText(StringUtil.textoSublinhado(lblNotificacoes.getText().toString()));
        lblNotificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblNotificacoesClick();
            }
        });

        lblRelatorios.setText(StringUtil.textoSublinhado(lblRelatorios.getText().toString()));
        lblRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblRelatoriosClick();
            }
        });

        lblDesfazerClube.setText(StringUtil.textoSublinhado(lblDesfazerClube.getText().toString()));
        lblDesfazerClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblDesfazerClubeClick();
            }
        });

        lblSairClube.setText(StringUtil.textoSublinhado(lblSairClube.getText().toString()));
        lblSairClube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblSairClubeClick();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        lblCodClube.setText(clube.getCodClube());
    }

    private void lblSairClubeClick() {
    }

    private void lblDesfazerClubeClick() {
        Intent intent = new Intent(this, DesfazerClubeActivity.class);
        startActivity(intent);
    }

    private void lblRelatoriosClick() {
    }

    private void lblNotificacoesClick() {
        Intent intent = new Intent(this, AvisosActivity.class);
        startActivity(intent);
    }

    private void lblTrocarPytacosClick() {
        Intent intent = new Intent(this, TrocarPytacosActivity.class);
        startActivity(intent);
    }
}