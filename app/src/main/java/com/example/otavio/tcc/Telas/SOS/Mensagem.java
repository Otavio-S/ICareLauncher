package com.example.otavio.tcc.Telas.SOS;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.SOS;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaSOS;

public class Mensagem extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        final TextView txtMensagem = findViewById(R.id.edWrite);
        Button btnSalvar = findViewById(R.id.btnSalvarWrite);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txtMensagem.getText().toString();
                TabelaSOS tabelaSOS = new TabelaSOS(getApplicationContext());

                String id = null;
                try {
                    id = tabelaSOS.carregaDadosPorID(2).getId();

                    SOS sos = new SOS();
                    sos.setId("2");
                    sos.setMensagem(text);

                    tabelaSOS.insereDado(sos);
                    Toast.makeText(getApplicationContext(), "Mensagem salva!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    SOS sos = new SOS();
                    sos.setId("2");
                    sos.setMensagem(text);

                    tabelaSOS.alteraRegistro(sos);
                    Toast.makeText(getApplicationContext(), "Mensagem atualizada!", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });

    }
}
