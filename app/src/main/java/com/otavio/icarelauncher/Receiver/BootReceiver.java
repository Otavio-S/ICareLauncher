package com.otavio.icarelauncher.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BootReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())
                || "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())
                || "com.htc.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {

            Log.v("TEST", "Service loaded at start / Serviço Iniciado com sucesso OK 1");
            TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
            List<Alarme> alarmeList = tabelaAlarmes.carregaDados();

            int i;
            int quant = alarmeList.size() - 1;

            for (i = quant; i >= 0; i--) {
                if (alarmeList.get(i).getLigado().equals("1")) {
                    int id = Integer.parseInt(alarmeList.get(i).getID());
                    startAlarm(id);
                }
            }
            Log.v("TEST", "Service loaded at start / Serviço Iniciado com sucesso OK 2");

        }

    }

    private void startAlarm(int id) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        Calendar calendar = Calendar.getInstance();
        String dataSelected = alarme.getDataInicial();

        String horaInicial = String.valueOf(alarme.getHoraInicial());
        String minInicial = String.valueOf(alarme.getMinInicial());

        int h = Integer.parseInt(horaInicial);
        int m = Integer.parseInt(minInicial);

        SimpleDateFormat format = new SimpleDateFormat("HH", Locale.getDefault());
        format.format(new Date());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);


        SimpleDateFormat formate = new SimpleDateFormat("mm", Locale.getDefault());
        formate.format(new Date());
        int minOfDay = calendar.get(Calendar.MINUTE);

        String tempo = tabelaAlarmes.carregaDadosPorID(id).getTempo();
        int hora = Integer.parseInt(tempo.substring(0, 2));
        int min = Integer.parseInt(tempo.substring(3, 5));

        if (hourOfDay > h) {
            do {
                h += hora;
                m += min;
            } while (h < hourOfDay);

        } else if (hourOfDay == h) {
            if (minOfDay > m) {
                do {
                    h += hora;
                    m += min;
                } while (m < minOfDay);
            }
        }

        int ano = Integer.valueOf(dataSelected.substring(6, 10));
        int mes = Integer.valueOf(dataSelected.substring(3, 5)) - 1;
        int dia = Integer.valueOf(dataSelected.substring(0, 2));

        Calendar mycal = new GregorianCalendar(ano, mes, dia);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (m >= 60) {
            h += 1;
            m = m - 60;
        }

        if (h >= 24) {
            do {
                h = h - 24;
                dia += 1;
            } while (h >= 24);
        }

        if (dia > daysInMonth) {
            dia = dia - daysInMonth;
            mes += 1;
        }

        if (mes > 11) {
            mes = mes - 11;
            ano = ano + 1;
        }

        Calendar alarm = Calendar.getInstance();
        alarm.set(ano, mes, dia, h, m);
        long inicio = alarm.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }
}
