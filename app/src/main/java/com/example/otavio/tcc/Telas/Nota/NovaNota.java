package com.example.otavio.tcc.Telas.Nota;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Nota;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaNotas;

import java.util.Objects;

public class NovaNota extends FragmentActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_nota);
        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);

        final TextView txtNome = findViewById(R.id.txtNomeNotaEd);
        final TextView txtNota = findViewById(R.id.txtDescricaoNota);
        Button btnSalvar = findViewById(R.id.btnSalvarNota);
        Button btnCancelar = findViewById(R.id.btnCancelarNota);

        final TabelaNotas tabelaNotas = new TabelaNotas(getApplicationContext());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nota nota = new Nota();
                nota.setNome(String.valueOf(txtNome.getText()));
                nota.setDescricao(String.valueOf(txtNota.getText()));

                String insert = tabelaNotas.insereDado(nota);

                if (insert.equals("Registro Inserido com sucesso")) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Nota salva",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Nota não salva",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Cancelado",
                        Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }

}
