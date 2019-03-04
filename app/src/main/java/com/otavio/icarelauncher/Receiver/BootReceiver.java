package com.otavio.icarelauncher.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BootReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {

            this.context = context;

            TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(context);
            List<Alarme> alarmeList = tabelaAlarmes.carregaDados();

            int i;
            int quant = alarmeList.size() - 1;

            for (i = quant; i >= 0; i--) {
                if (alarmeList.get(i).getLigado().equals("1")) {
                    int h = Integer.parseInt(alarmeList.get(i).getHoraInicial());
                    int m = Integer.parseInt(alarmeList.get(i).getMinInicial());

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat format = new SimpleDateFormat("HH", Locale.getDefault());
                    format.format(new Date());
                    int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

                    SimpleDateFormat formate = new SimpleDateFormat("mm", Locale.getDefault());
                    formate.format(new Date());
                    int minOfDay = calendar.get(Calendar.MINUTE);

                    if (h < hourOfDay || m < minOfDay) {
                        Alarme alarme = tabelaAlarmes.carregaDadosPorID(i);

                        String tempo = alarme.getTempo();
                        int hora = Integer.parseInt(tempo.substring(0, 2));
                        int min = Integer.parseInt(tempo.substring(3, 5));

                        do {
                            h += hora;
                        } while (h <= hourOfDay);

                        do {
                            m += min;
                        } while (m <= minOfDay);

                        alarme.setHoraInicial(String.valueOf(h));
                        alarme.setMinInicial(String.valueOf(m));

                        tabelaAlarmes.alteraRegistro(alarme);
                    }

                    int id = Integer.parseInt(alarmeList.get(i).getID());
                    startAlarm(id);

                }
            }

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
