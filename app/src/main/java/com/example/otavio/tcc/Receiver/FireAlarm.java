package com.example.otavio.tcc.Receiver;

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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class FireAlarm extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_alarme);

        Intent intent = getIntent();
        final String nome = intent.getStringExtra("Nome");
        int id = getIntent().getIntExtra("ID", 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        final TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
        final List<Alarme> alarmes = tabelaAlarmes.carregaDadosPorID(id);
        int quantidade = Integer.parseInt(alarmes.get(0).getQuantidade());

        quantidade = quantidade - 1;
        tabelaAlarmes.atualizaContador(String.valueOf(id), quantidade);
        startPlayingRing();
        startVibrate();

        TextView txtNome = findViewById(R.id.txtNomeAlarme);
        txtNome.setText(nome);

        Button btnPronto = findViewById(R.id.btnPronto);
        btnPronto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayRing();
                stopVibrate();

                Historico historico = new Historico();
                historico.setNome(String.valueOf(alarmes.get(0).getNome()));
                historico.setDataInicial("1");
                historico.setDataFinal("1");
                historico.setHoraInicial(String.valueOf(alarmes.get(0).getHoraInicial()));
                historico.setQuantidade(String.valueOf(alarmes.get(0).getQuantidade()));
                historico.setTempo(String.valueOf(alarmes.get(0).getTempo()));
                historico.setDescricao(String.valueOf(alarmes.get(0).getDescricao()));

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                String tempo = String.valueOf(hour).concat(":").concat(String.valueOf(minute));
                historico.setHorarioRemedio(String.valueOf(tempo));

                TabelaHistorico tabelaHistorico = new TabelaHistorico(getApplicationContext());
                String s = (tabelaHistorico.insereDado(historico));

                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Que bom! " + s,
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

        stopPlayRing();
        stopVibrate();

    }

}
