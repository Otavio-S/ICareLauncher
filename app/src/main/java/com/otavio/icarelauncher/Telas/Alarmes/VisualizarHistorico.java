package com.otavio.icarelauncher.Telas.Alarmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.otavio.icarelauncher.Model.Historico;
import com.otavio.icarelauncher.R;
import com.otavio.icarelauncher.SQLite.TabelaHistorico;

import java.util.Objects;

public class VisualizarHistorico extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_historico);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_initialize));

        AdView adView = findViewById(R.id.adViewVisualiazarHistorico);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        TextView txtNome = findViewById(R.id.txtNomeAlarm);
        TextView txtDescricao = findViewById(R.id.txtDes);
        TextView txtHora = findViewById(R.id.txtHoraR);
        TextView txtData = findViewById(R.id.txtDataR);

        final TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());

        Historico historico = tabelaHistorico.carregaDadosPorID(id);

        int hora = historico.getHoraRemedio();
        int min = historico.getMinutoRemedio();
        System.out.println("HORAAAAAAAAAAAA:::::::::::::::::::::::" + hora);
        System.out.println("MINUTOOOOOOOOOO:::::::::::::::::::::::" + min);
        String m = String.valueOf(min);
        if (min <= 9) {
            m = "0".concat(m);
        }

        txtNome.setText(historico.getNome());
        txtDescricao.setText(historico.getDescricao());
        txtHora.setText(String.valueOf(hora).concat(":").concat(m));
        txtData.setText(historico.getDataRemedio());

    }

}
