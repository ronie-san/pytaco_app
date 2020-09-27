package br.com.enterprise.pytaco.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.enterprise.pytaco.R;
import br.com.enterprise.pytaco.dao.PytacoRequestDAO;

public class TesteActivity extends Activity {

    private TextView lblTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);
        findViewById(R.id.main_btnTeste).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTesteClick();
            }
        });

        lblTeste = findViewById(R.id.main_lblTeste);
    }

    private void btnTesteClick() {

    }
}
