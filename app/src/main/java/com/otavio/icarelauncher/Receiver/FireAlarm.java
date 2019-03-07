package com.otavio.icarelauncher.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.Model.Historico;
import com.otavio.icarelauncher.R;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;
import com.otavio.icarelauncher.SQLite.TabelaHistorico;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class FireAlarm extends AppCompatActivity {

    private TabelaAlarmes tabelaAlarmes;
    private MediaPlayer mediaPlayer;
    private Vibrator vibration;
    private int id;
    private Alarme alarme;
    private final View.OnClickListener btnNaoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopPlayRing();
            stopVibrate();

            TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
            Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);
            int c = alarme.getContador();
            c += 1;

            if (c <= 5) {
                alarme.setContador(c);
            } else {
                alarme.setContador(0);
                alarme.setLigado("0");

                Intent contentIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getActivity
                        (getApplicationContext(), id, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
            }

            tabelaAlarmes.alteraRegistro(alarme);

            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    R.string.tome_agora,
                    Toast.LENGTH_LONG);
            toast.show();

            startAlarmN();

            finish();
        }
    };
    private final View.OnClickListener btnProntoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopPlayRing();
            stopVibrate();

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat format = new SimpleDateFormat("HH", Locale.getDefault());
            format.format(new Date());
            int hour = calendar.get(Calendar.HOUR);

            SimpleDateFormat formate = new SimpleDateFormat("mm", Locale.getDefault());
            formate.format(new Date());
            int minute = calendar.get(Calendar.MINUTE);

            String currentDate = DateFormat.getDateInstance().format(new Date());

            Historico historico = new Historico();
            historico.setNome(String.valueOf(alarme.getNome()));
            historico.setHoraRemedio(hour);
            historico.setMinutoRemedio(minute);
            historico.setDescricao(String.valueOf(alarme.getDescricao()));
            historico.setDataRemedio(currentDate);

            TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());
            tabelaHistorico.insereDado(historico);

            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    R.string.que_bom,
                    Toast.LENGTH_LONG);
            toast.show();

            int quantidade = Integer.parseInt(alarme.getQuantidade());
            quantidade -= 1;
            alarme.setQuantidade(String.valueOf(quantidade));
            alarme.setContador(0);
            tabelaAlarmes.alteraRegistro(alarme);

            if (quantidade == 0) {
                alarme.setLigado("0");
                tabelaAlarmes.alteraRegistro(alarme);

                Intent contentIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getActivity
                        (getApplicationContext(), id, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                pendingIntent.cancel();
            }

            startAlarmS();

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarme);

        id = getIntent().getIntExtra("ID", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            this.setTurnScreenOn(true);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
        alarme = tabelaAlarmes.carregaDadosPorID(id);

        startPlayingRing();
        startVibrate();

        String nome = alarme.getNome();
        TextView txtNome = findViewById(R.id.txtNomeAlarme);
        txtNome.setText(nome);
        TextView txtDescricao = findViewById(R.id.txtDescricaoFire);
        txtDescricao.setText(alarme.getDescricao());
        TextView txtContador = findViewById(R.id.txtContador);
        int c = alarme.getContador();
        if (c == 0) {
            txtContador.setText(R.string.primeiro_toque);
        } else {
            txtContador.setText(getResources().getString(R.string.demais_toques, c));
        }

        Button btnPronto = findViewById(R.id.btnPronto);
        btnPronto.setOnClickListener(btnProntoOnClickListener);

        Button btnNao = findViewById(R.id.btnNao);
        btnNao.setOnClickListener(btnNaoOnClickListener);

    }

    private void stopVibrate() {
        vibration.cancel();
    }

    private void startVibrate() {
        vibration = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = new long[]{1000, 1000, 1000, 1000};
        Objects.requireNonNull(vibration).vibrate(pattern, 2);
    }

    private void stopPlayRing() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void startPlayingRing() {
        RingtoneManager ringtoneManager = new RingtoneManager(this);
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        ringtoneManager.getCursor();

        Uri ringtoneUri = ringtoneManager.getRingtoneUri(1);
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Objects.requireNonNull(audioManager).setStreamVolume(AudioManager.STREAM_ALARM, 7, AudioManager.ADJUST_SAME);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, ringtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayRing();
        stopVibrate();
    }

    private void startAlarmS() {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("ID", this.id);
        int i = (int) calendar.getTimeInMillis();
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);

        int hourOfDay = Integer.valueOf(alarme.getHoraInicial());
        int minOfDay = Integer.valueOf(alarme.getMinInicial());

        String tempo = alarme.getTempo();
        int hora = Integer.parseInt(tempo.substring(0, 2));
        int min = Integer.parseInt(tempo.substring(3, 5));

        hourOfDay += hora;
        minOfDay += min;

        String dataSelected = alarme.getDataInicial();
        int ano = Integer.valueOf(dataSelected.substring(6, 10));
        int mes = Integer.valueOf(dataSelected.substring(3, 5)) - 1;
        int dia = Integer.valueOf(dataSelected.substring(0, 2));

        Calendar mycal = new GregorianCalendar(ano, mes, dia);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (minOfDay >= 60) {
            hourOfDay += 1;
            minOfDay = minOfDay - 60;
        }

        if (hourOfDay >= 24) {
            do {
                hourOfDay = hourOfDay - 24;
                dia += 1;
            } while (hourOfDay >= 24);
        }

        if (dia > daysInMonth) {
            dia = dia - daysInMonth;
            mes += 1;
        }

        if (mes > 11) {
            mes = mes - 11;
            ano = ano + 1;
        }

        String m = String.valueOf(minOfDay);
        if (minOfDay <= 9) {
            m = "0".concat(String.valueOf(minOfDay));
        }
        String h = String.valueOf(hourOfDay);
        if (hourOfDay <= 9) {
            h = "0".concat(String.valueOf(hourOfDay));
        }

        alarme.setHoraInicial(h);
        alarme.setMinInicial(m);
        tabelaAlarmes.alteraRegistro(alarme);

        Calendar alarm = Calendar.getInstance();
        alarm.set(ano, mes, dia, hourOfDay, minOfDay);
        long inicio = alarm.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);
    }

    private void startAlarmN() {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("ID", this.id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), this.id, intent, 0);

        SimpleDateFormat format = new SimpleDateFormat("HH", Locale.getDefault());
        format.format(new Date());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        SimpleDateFormat formate = new SimpleDateFormat("mm", Locale.getDefault());
        formate.format(new Date());
        int minOfDay = calendar.get(Calendar.MINUTE);

        minOfDay += 2;

        String dataSelected = alarme.getDataInicial();
        int ano = Integer.valueOf(dataSelected.substring(6, 10));
        int mes = Integer.valueOf(dataSelected.substring(3, 5)) - 1;
        int dia = Integer.valueOf(dataSelected.substring(0, 2));

        Calendar mycal = new GregorianCalendar(ano, mes, dia);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (minOfDay >= 60) {
            hourOfDay += 1;
            minOfDay = minOfDay - 60;
        }

        if (hourOfDay >= 24) {
            do {
                hourOfDay = hourOfDay - 24;
                dia += 1;
            } while (hourOfDay >= 24);
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
        alarm.set(ano, mes, dia, hourOfDay, minOfDay);
        long inicio = alarm.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }

}
