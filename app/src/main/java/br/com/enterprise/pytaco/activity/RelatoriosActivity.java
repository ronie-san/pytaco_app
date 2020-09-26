package br.com.enterprise.pytaco.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.util.StringUtil;

public class RelatoriosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        ImageButton btnVoltar = findViewById(R.id.relatorios_btnVoltar);
        final TextView lblPytacosTrocados = findViewById(R.id.relatorios_lblPytacosTrocados);
        TextView lblFichasMovimentadas = findViewById(R.id.relatorios_lblFichasMovimentadas);
        TextView lblFinanceiro = findViewById(R.id.relatorios_lblFinanceiro);

        lblPytacosTrocados.setText(StringUtil.textoSublinhado(lblPytacosTrocados.getText().toString()));
        lblPytacosTrocados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblPytacosTrocadosClick();
            }
        });
        lblFichasMovimentadas.setText(StringUtil.textoSublinhado(lblFichasMovimentadas.getText().toString()));
        lblFichasMovimentadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblFichasMovimentadasClick();
            }
        });
        lblFinanceiro.setVisibility(View.GONE);
        lblFinanceiro.setText(StringUtil.textoSublinhado(lblFinanceiro.getText().toString()));
        lblFinanceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblFinanceiroClick();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }

    private void lblFinanceiroClick() {
    }

    private void lblFichasMovimentadasClick() {
        Intent intent = new Intent(this, FichasMovimentadasActivity.class);
        startActivity(intent);
    }

    private void lblPytacosTrocadosClick() {
        Intent intent = new Intent(this, PytacosTrocadosActivity.class);
        startActivity(intent);
    }
}