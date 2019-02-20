package com.example.otavio.tcc.Receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;
import com.example.otavio.tcc.Telas.Alarmes.NovoAlarme;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("ID", 0);

        String lig = "0";
        String count = "0";

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        try {
            lig = alarme.getLigado();
            count = alarme.getQuantidade();
        } catch (Exception ignored) {
        }

        if (lig.equals("0") || count.equals("0")) {
            alarme.setLigado("0");
            tabelaAlarmes.alteraRegistro(alarme);

            Intent contentIntent = new Intent(context, NovoAlarme.class);
            PendingIntent pendingIntent = PendingIntent.getActivity
                    (context, id, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
        } else {
            Intent bootIntent = new Intent(context, FireAlarm.class);
            bootIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            bootIntent.putExtra("ID", id);
            context.startActivity(bootIntent);
        }

        //Intent contentIntent = new Intent(context, NovoAlarme.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity
        //        (context, id, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //pendingIntent.cancel();

    }

}
