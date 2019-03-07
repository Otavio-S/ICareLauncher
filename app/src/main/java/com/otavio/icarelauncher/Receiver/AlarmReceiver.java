package com.otavio.icarelauncher.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;
import com.otavio.icarelauncher.Telas.Alarmes.NovoAlarme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        //if (Objects.requireNonNull(intent.getAction()).equals("com.test.intent.action.ALARM")) {
        int id = intent.getIntExtra("ID", 0);

        String lig = "0";
        String count = "0";
        int rep = 0;

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        try {
            lig = alarme.getLigado();
            count = alarme.getQuantidade();
            rep = alarme.getContador();
        } catch (Exception ignored) {
        }

        if (lig.equals("0") || count.equals("0") || rep > 5) {
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

    }

    private void startAlarm(int id) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        long d = 0;
        Calendar calendar = Calendar.getInstance();
        String dataSelected = alarme.getDataInicial();

        Date c = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dataAtual = df.format(c);

        String horaInicial = String.valueOf(alarme.getHoraInicial());
        String minInicial = String.valueOf(alarme.getMinInicial());

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaInicial));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minInicial));
        long inicio = calendar.getTimeInMillis();

        Date dateF, dateI;
        long diff;
        try {
            dateF = df.parse(dataSelected);
            dateI = df.parse(dataAtual);
            diff = dateF.getTime() - dateI.getTime();

            if (diff < 0) {
                do {
                    int newD = Integer.valueOf(dataSelected.substring(0, 2));
                    int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (newD + 1 > daysInMonth) {
                        int newM = Integer.valueOf(dataSelected.substring(3, 5));
                        dataSelected = "01".concat("-").concat(String.valueOf(newM)).concat(dataSelected.substring(5));
                    } else {
                        newD += 1;
                        dataSelected = String.valueOf(newD).concat(dataSelected.substring(2));
                    }
                    dateF = df.parse(dataSelected);
                    dateI = df.parse(dataAtual);
                    diff = dateF.getTime() - dateI.getTime();
                } while (diff < 0);
            }

            d = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        inicio = inicio + (86400000L * d);  //Add d days

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }

}
