package com.example.otavio.tcc.Telas.Nota;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Nota;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaNotas;

import java.util.List;
import java.util.Objects;

public class EditarNotas extends FragmentActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_notas);
        Objects.requireNonNull(getActionBar()).setHomeButtonEnabled(true);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        final TextView txtNome = findViewById(R.id.txtNomeNotaEd);
        final TextView txtNota = findViewById(R.id.txtDescricaoNotaEd);
        Button btnSalvar = findViewById(R.id.btnSalvarNotaEd);
        Button btnCancelar = findViewById(R.id.btnCancelarNotaEd);

        final TabelaNotas tabelaNotas = new TabelaNotas(getApplicationContext());

        List<Nota> notaList = tabelaNotas.carregaDadosPorID(id);

        txtNome.setText(notaList.get(0).getNome());
        txtNota.setText(notaList.get(0).getDescricao());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nota nota = new Nota();
                nota.setID(String.valueOf(id));
                nota.setNome(String.valueOf(txtNome.getText()));
                nota.setDescricao(String.valueOf(txtNota.getText()));

                String altera = tabelaNotas.alteraRegistro(nota);

                if (altera.equals("Registro atualizado com sucesso")) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Alterações Salvas",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Cancelado",
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
