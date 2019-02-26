package com.example.otavio.tcc.Telas.SOS;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.otavio.tcc.Model.SOS;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaSOS;

import java.util.Objects;

public class Mensagem extends AppCompatActivity {

    private EditText edMensagem;
    private String mens;
    private TabelaSOS tabelaSOS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        edMensagem = findViewById(R.id.edWrite);
        Button btnSalvar = findViewById(R.id.btnSalvarWrite);

        tabelaSOS = new TabelaSOS(getApplicationContext());

        mens = "";
        try {
            mens = tabelaSOS.carregaDadosPorID(2).getMensagem();
            edMensagem.setText(mens);
            edMensagem.setSelection(edMensagem.getText().length());
        } catch (Exception ignored) {
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edMensagem.getText().toString();

                if (mens == null || mens.equals("")) {
                    SOS sos = new SOS();
                    sos.setId("2");
                    sos.setNumero("0");
                    sos.setMensagem(text);

                    System.out.println(tabelaSOS.insereDado(sos));
                    Toast.makeText(getApplicationContext(), R.string.mensagem_salva, Toast.LENGTH_SHORT).show();
                } else {
                    SOS sos = new SOS();
                    sos.setId("2");
                    sos.setNumero("0");
                    sos.setMensagem(text);

                    tabelaSOS.alteraRegistro(sos);
                    Toast.makeText(getApplicationContext(), R.string.mensagem_atualizada, Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}
