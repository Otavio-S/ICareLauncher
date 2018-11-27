package com.example.otavio.tcc.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ALARM_CLOCK = "alarm_clock";

    @Override
    public void onReceive(Context context, Intent intent) {

        String nomeRemedio = intent.getStringExtra("Nome");
        int id = intent.getIntExtra("ID", 0);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
        List<Alarme> alarmeList = tabelaAlarmes.carregaDadosPorID(id);
        String ligado;

        try {
            ligado = alarmeList.get(0).getLigado();
        } catch (Exception e) {
            ligado = "A";
        }

        int quant = Integer.parseInt(alarmeList.get(0).getQuantidade());

        Intent bootIntent = new Intent(context, FireAlarm.class);
        if (ligado.equals("1") & quant != 0) {

            bootIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            bootIntent.putExtra("Nome", nomeRemedio);
            bootIntent.putExtra("ID", id);
            bootIntent.putExtra(ALARM_CLOCK, intent.getSerializableExtra(ALARM_CLOCK));
            context.startActivity(bootIntent);

        } else {
            tabelaAlarmes.alteraSituacao(String.valueOf(id), "0");
        }

    }

}
