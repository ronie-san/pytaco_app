package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Jogo;
import br.com.enterprise.pytaco.util.StringUtil;

public class BolaoActivity extends BaseActivity {

    private TextView lblQtdFicha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolao);

        lblQtdFicha = findViewById(R.id.bolao_lblQtdFicha);
        ImageButton btnCriarBolao = findViewById(R.id.bolao_btnCriarBolao);
        ImageButton btnMeusBoloes = findViewById(R.id.bolao_btnMeusBoloes);
        ImageButton btnAvisos = findViewById(R.id.bolao_btnAvisos);
        ImageButton btnMembros = findViewById(R.id.bolao_btnMembros);
        ImageButton btnContador = findViewById(R.id.bolao_btnContador);
        ImageButton btnAdministrador = findViewById(R.id.bolao_btnAdministrador);

        btnCriarBolao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCriarBolaoClick();
            }
        });
//        btnCriarBolao.setVisibility(pGetVisible(pIsAgenteAdmin()));

        btnMeusBoloes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMeusBoloesClick();
            }
        });

        btnAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAvisosClick();
            }
        });

        btnMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMembrosClick();
            }
        });
//        btnMembros.setVisibility(pGetVisible(pIsAgenteAdmin()));

        btnContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnContadorClick();
            }
        });
//        btnContador.setVisibility(pGetVisible(pIsAgenteAdmin()));

        btnAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAdministradorClick();
            }
        });
//        btnAdministrador.setVisibility(pGetVisible(pIsAgenteAdmin()));
    }

    private boolean pIsAgenteAdmin() {
        return clube.getTipoUsuario().equals("2") || clube.getTipoUsuario().equals("3");
    }

    @Override
    protected void onStart() {
        super.onStart();
        lblQtdFicha.setText(StringUtil.numberToStr(clube.getQtdFicha()));
        lstJogoSelecionado.clear();

        for (Jogo jogo : lstJogo) {
            jogo.setMarcado(false);
        }
    }

    private void btnCriarBolaoClick() {
        pStartActivity(CriarBolaoActivity.class);
    }

    private void btnMeusBoloesClick() {
        pStartActivity(MeusBoloesActivity.class);
    }

    private void btnAvisosClick() {
        Intent intent = new Intent(this, AvisosActivity.class);
        startActivity(intent);
    }

    private void btnMembrosClick() {
        Intent intent = new Intent(this, MembrosActivity.class);
        startActivity(intent);
    }

    private void btnContadorClick() {
        Intent intent = new Intent(this, ContadorActivity.class);
        startActivity(intent);
    }

    private void btnAdministradorClick() {
        Intent intent = new Intent(this, AdministracaoActivity.class);
        startActivity(intent);
    }
}