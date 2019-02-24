package com.example.otavio.tcc.Telas.Alarmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

public class VisualizarHistorico extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_historico);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        TextView txtNome = findViewById(R.id.txtNomeAlarm);
        TextView txtDescricao = findViewById(R.id.txtDes);
        TextView txtHora = findViewById(R.id.txtHoraR);

        final TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());

        Historico historico = tabelaHistorico.carregaDadosPorID(id);

        txtNome.setText(historico.getNome());
        txtDescricao.setText(historico.getDescricao());
        txtHora.setText(historico.getHorarioRemedio());

    }

}
