package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;
import br.com.enterprise.pytaco.util.StringUtil;

public class BolaoActivity extends BaseActivity {
    private TextView lblQtdFicha;
    private Clube clube;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            clube = (Clube) bundle.getSerializable(getString(R.string.clube));
        } else {
            clube = (Clube) savedInstanceState.getSerializable(getString(R.string.clube));
        }

        lblQtdFicha = findViewById(R.id.bolao_lblQtdFicha);
        ImageButton btnVoltar = findViewById(R.id.bolao_btnVoltar);
        ImageButton btnCriarBolao = findViewById(R.id.bolao_btnCriarBolao);
        ImageButton btnMeusBoloes = findViewById(R.id.bolao_btnMeusBoloes);
        ImageButton lblAvisos = findViewById(R.id.bolao_lblAvisos);
        ImageButton lblMembros = findViewById(R.id.bolao_lblMembros);
        ImageButton lblContador = findViewById(R.id.bolao_lblContador);
        ImageButton lblAdministrador = findViewById(R.id.bolao_lblAdministrador);

        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
        btnCriarBolao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCriarBolaoClick();
            }
        });
        btnMeusBoloes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMeusBoloesClick();
            }
        });
        lblAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblAvisosClick();
            }
        });
        lblMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblMembrosClick();
            }
        });
        lblContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblContadorClick();
            }
        });
        lblAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblAdministradorClick();
            }
        });
    }

    private void btnCriarBolaoClick() {

    }

    private void btnMeusBoloesClick() {

    }

    private void lblAvisosClick() {
        Intent intent = new Intent(this, AvisosActivity.class);
        intent.putExtra(getString(R.string.clube), clube);
        startActivity(intent);
    }

    private void lblMembrosClick() {
        Intent intent = new Intent(this, MembrosActivity.class);
        intent.putExtra(getString(R.string.clube), clube);
        startActivity(intent);
    }

    private void lblContadorClick() {
        Intent intent = new Intent(this, ContadorActivity.class);
        intent.putExtra(getString(R.string.clube), clube);
        startActivity(intent);
    }

    private void lblAdministradorClick() {
        Intent intent = new Intent(this, AdministracaoActivity.class);
        intent.putExtra(getString(R.string.clube), clube);
        startActivity(intent);
    }
}