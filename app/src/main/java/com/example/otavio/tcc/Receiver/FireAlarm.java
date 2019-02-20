package com.example.otavio.tcc.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FireAlarm extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibration;
    private int id;
    private TabelaAlarmes tabelaAlarmes;
    private Alarme alarme;

    private View.OnClickListener btnProntoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopPlayRing();
            stopVibrate();

            Historico historico = new Historico();
            historico.setNome(String.valueOf(alarme.getNome()));
            historico.setQuantidade(String.valueOf(alarme.getQuantidade()));
            historico.setTempo(String.valueOf(alarme.getTempo()));
            historico.setDescricao(String.valueOf(alarme.getDescricao()));

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH", Locale.getDefault());
            format.format(new Date());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            SimpleDateFormat formate = new SimpleDateFormat("mm", Locale.getDefault());
            formate.format(new Date());
            int minute = calendar.get(Calendar.MINUTE);

            String horario = String.valueOf(hour).concat(":").concat(String.valueOf(minute));
            historico.setHorarioRemedio(horario);

            TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());
            String s = (tabelaHistorico.insereDado(historico));

            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Que bom!",
                    Toast.LENGTH_LONG);
            toast.show();

            int quantidade = Integer.parseInt(alarme.getQuantidade());
            quantidade -= 1;
            alarme.setQuantidade(String.valueOf(quantidade));
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
    private View.OnClickListener btnNaoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopPlayRing();
            stopVibrate();

            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Tome seu remédio agora!",
                    Toast.LENGTH_LONG);
            toast.show();

            startAlarmN();

            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarme);

        id = getIntent().getIntExtra("ID", 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
        alarme = tabelaAlarmes.carregaDadosPorID(id);

        startPlayingRing();
        startVibrate();

        String nome = alarme.getNome();
        TextView txtNome = findViewById(R.id.txtNomeAlarme);
        txtNome.setText(nome);

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

        int hourOfDay = alarme.getHoraInicial();
        int minOfDay = alarme.getMinInicial();

        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);
        int t = Integer.parseInt(alarme.getTempo());

        minOfDay += t;

        if (minOfDay >= 60) {
            hourOfDay += 1;
            minOfDay = minOfDay - 60;
        }

        if (hourOfDay == 24) {
            hourOfDay = 0;
        }

        alarme.setHoraInicial(hourOfDay);
        alarme.setMinInicial(minOfDay);
        tabelaAlarmes.alteraRegistro(alarme);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minOfDay);

        long hora = calendar.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, hora, alarmIntent);
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

        SimpleDateFormat formate = new SimpleDateFormat("ss", Locale.getDefault());
        formate.format(new Date());
        int minOfDay = calendar.get(Calendar.MINUTE);

        minOfDay += 2;

        if (minOfDay >= 60) {
            hourOfDay += 1;
            minOfDay = minOfDay - 60;
        }

        if (hourOfDay == 24) {
            hourOfDay = 0;
        }

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minOfDay);

        long hora = calendar.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, hora, alarmIntent);

    }

}
