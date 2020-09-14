package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.AvisoItemAdapter;
import br.com.enterprise.pytaco.pojo.Aviso;

public class AvisosActivity extends BaseActivity {

    private AvisoItemAdapter avisoItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        ListView lsvAvisos = findViewById(R.id.avisos_lsvAvisos);
        avisoItemAdapter = new AvisoItemAdapter(new ArrayList<Aviso>(), this);
        lsvAvisos.setAdapter(avisoItemAdapter);

        ImageButton btnVoltar = findViewById(R.id.avisos_btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }
}