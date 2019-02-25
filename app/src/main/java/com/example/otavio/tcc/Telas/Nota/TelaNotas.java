package com.example.otavio.tcc.Telas.Nota;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Nota;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaNotas;

public class TelaNotas extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Alterações descartadas!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        TextView txtNome = findViewById(R.id.txtNomeNota);
        txtNome.requestFocus();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        final EditText edNota = findViewById(R.id.edNota);
        Button btnSalvar = findViewById(R.id.btnSalvarNota);
        final TabelaNotas tabelaNotas = new TabelaNotas(getApplicationContext());

        try {
            String dados = tabelaNotas.carregaDadosPorID(1).getDescricao();
            edNota.setText(dados);
            edNota.setSelection(edNota.getText().length());
        } catch (Exception ignored) {
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dados = null;
                try {
                    dados = tabelaNotas.carregaDadosPorID(1).getID();
                } catch (Exception ignored) {
                }

                String texto = String.valueOf(edNota.getText());
                if (dados == null) {
                    Nota nota = new Nota();
                    nota.setID("1");
                    nota.setNome("NotaUnica");
                    nota.setDescricao(texto);
                    tabelaNotas.insereDado(nota);
                } else {
                    Nota nota = new Nota();
                    nota.setID("1");
                    nota.setNome("NotaUnica");
                    nota.setDescricao(texto);
                    tabelaNotas.alteraRegistro(nota);
                }
                Toast.makeText(getApplicationContext(), "Nota salva!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}
