package br.com.enterprise.pytaco.activity;

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

    private Clube clube;
    private TextView lblCodClube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracao);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            clube = (Clube) bundle.getSerializable(getString(R.string.clube));
        } else {
            clube = (Clube) savedInstanceState.getSerializable(getString(R.string.clube));
        }

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

        lblCodClube.setText(clube.getCodClube());

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

    private void lblSairClubeClick() {
    }

    private void lblDesfazerClubeClick() {
    }

    private void lblRelatoriosClick() {
    }

    private void lblNotificacoesClick() {
    }

    private void lblTrocarPytacosClick() {
    }
}