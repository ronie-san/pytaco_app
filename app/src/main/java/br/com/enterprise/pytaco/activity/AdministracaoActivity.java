package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.pojo.Clube;

public class AdministracaoActivity extends BaseActivity {

    private Clube clube;

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

        final ImageButton btnVoltar = findViewById(R.id.administracao_btnVoltar);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }
}