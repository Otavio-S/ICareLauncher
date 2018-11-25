package com.example.otavio.tcc.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FireAlarm extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibration;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarme);

        Intent intent = getIntent();
        String nome = intent.getStringExtra("Nome");
        this.id = getIntent().getIntExtra("ID", 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
        List<Alarme> alarmes = tabelaAlarmes.carregaDadosPorID(this.id);
        int quantidade = Integer.parseInt(alarmes.get(0).getQuantidade());

        if (quantidade != 0) {
            quantidade = quantidade - 1;
            tabelaAlarmes.atualizaContador(String.valueOf(id), quantidade);
            //startPlayingRing();
            startVibrate();

            TextView txtNome = findViewById(R.id.txtNomeAlarme);
            txtNome.setText(nome);

            Button btnPronto = findViewById(R.id.btnPronto);
            btnPronto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopPlayRing();
                    stopVibrate();

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Que bom!",
                            Toast.LENGTH_LONG);
                    toast.show();

                    finish();
                }
            });

            Button btnNao = findViewById(R.id.btnNao);
            btnNao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopPlayRing();
                    stopVibrate();

                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Tome seu remédio agora!",
                            Toast.LENGTH_LONG);
                    toast.show();

                    finish();
                }
            });
        } else {
            this.onDestroy();
        }
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

        //Uri ringtoneUri = ringtoneManager.getRingtoneUri();
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Objects.requireNonNull(audioManager).setStreamVolume(AudioManager.STREAM_ALARM, 7, AudioManager.ADJUST_SAME);
        mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(this, ringtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), this.id, myIntent, 0);

        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

        stopPlayRing();
        stopVibrate();

    }

}
