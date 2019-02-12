package com.example.otavio.tcc.Telas.Alarmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

import java.util.List;
import java.util.Objects;

public class VisualizarHistorico extends FragmentActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_historico);
        Objects.requireNonNull(getActionBar()).setHomeButtonEnabled(true);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        TextView txtNome = findViewById(R.id.txtNomeAlarm);
        TextView txtDescricao = findViewById(R.id.txtDes);
        TextView txtHora = findViewById(R.id.txtHoraR);

        final TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());

        List<Historico> historicoList = tabelaHistorico.carregaDadosPorID(id);

        txtNome.setText(historicoList.get(0).getNome());
        txtDescricao.setText(historicoList.get(0).getDescricao());
        txtHora.setText(historicoList.get(0).getHorarioRemedio());

    }

}
