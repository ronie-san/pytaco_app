package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.com.enterprise.pytaco.R;

public class RelatoriosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        ImageButton btnVoltar = findViewById(R.id.relatorios_btnVoltar);
        ImageButton btnPytacosTrocados = findViewById(R.id.relatorios_btnPytacosTrocados);
        ImageButton btnFichasMovimentadas = findViewById(R.id.relatorios_btnFichasMovimentadas);
        ImageButton btnFinanceiro = findViewById(R.id.relatorios_btnFinanceiro);

        btnPytacosTrocados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPytacosTrocadosClick();
            }
        });

        btnFichasMovimentadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFichasMovimentadasClick();
            }
        });

        btnFinanceiro.setVisibility(View.GONE);
        btnFinanceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFinanceiroClick();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }

    private void btnFinanceiroClick() {
    }

    private void btnFichasMovimentadasClick() {
        Intent intent = new Intent(this, FichasMovimentadasActivity.class);
        startActivity(intent);
    }

    private void btnPytacosTrocadosClick() {
        Intent intent = new Intent(this, PytacosTrocadosActivity.class);
        startActivity(intent);
    }
}