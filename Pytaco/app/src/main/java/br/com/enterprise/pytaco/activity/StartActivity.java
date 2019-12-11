package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import br.com.enterprise.pytaco.R;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImageButton btnEntrar = findViewById(R.id.start_btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEntrarClick();
            }
        });
        ImageButton btnCriarConta = findViewById(R.id.start_btnCriarConta);
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCriarConta();
            }
        });
    }

    private void btnCriarConta() {
        Intent intent = new Intent(this, CriarContaActivity.class);
        startActivity(intent);
    }

    private void btnEntrarClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
