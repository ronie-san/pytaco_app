package br.com.enterprise.pytaco.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.activity.adapter.AvisoItemAdapter;
import br.com.enterprise.pytaco.activity.adapter.MembroItemAdapter;
import br.com.enterprise.pytaco.pojo.Aviso;
import br.com.enterprise.pytaco.pojo.Membro;

public class MembrosActivity extends BaseActivity {

    private MembroItemAdapter membroItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros);

        ListView lsvMembros = findViewById(R.id.membros_lsvMembros);
        membroItemAdapter = new MembroItemAdapter(new ArrayList<Membro>(), this);
        lsvMembros.setAdapter(membroItemAdapter);

        ImageButton btnVoltar = findViewById(R.id.membros_btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnVoltarClick();
            }
        });
    }
}