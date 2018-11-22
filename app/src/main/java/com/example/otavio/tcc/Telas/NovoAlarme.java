package com.example.otavio.tcc.Telas;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.Receiver.AlarmReceiver;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.util.Calendar;
import java.util.Objects;

public class NovoAlarme extends Activity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_alarme);
        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);

        final TextView edNome = findViewById(R.id.edNome);
        final TextView edDescricao = findViewById(R.id.edDescricao);
        final TextView edHoraInicial = findViewById(R.id.edHoraInicial);
        final TextView edQuantidade = findViewById(R.id.edQuantidade);
        final TextView edTempo = findViewById(R.id.edTempo);
        final Switch aswitch = findViewById(R.id.switchLD);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        aswitch.setScaleX((float) 1.2);
        aswitch.setScaleY((float) 1.2);

        final TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarme alarme = new Alarme();
                alarme.setNome(String.valueOf(edNome.getText()));
                alarme.setDescricao(String.valueOf(edDescricao.getText()));
                alarme.setHoraInicial(String.valueOf(edHoraInicial.getText()));
                alarme.setQuantidade(String.valueOf(edQuantidade.getText()));
                alarme.setTempo(String.valueOf(edTempo.getText()));
                if (aswitch.isChecked()) {
                    alarme.setLigado("1");
                } else {
                    alarme.setLigado("0");
                }

                String insert = tabelaAlarmes.insereDado(alarme);

                if (insert.equals("Registro Inserido com sucesso")) {
                    startAlarm(1, 1, 1, 1);
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Alarme salvo",
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Alarme não salvo",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Cancelado",
                        Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }

    private void startAlarm(int horaInicial, int minInicial, int quantidade, int intervalo) {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(NovoAlarme.this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, horaInicial);
        calendar.set(Calendar.MINUTE, minInicial);

        long inicio = calendar.getTimeInMillis();

        intervalo = intervalo * 1000;

        Objects.requireNonNull(alarmMgr).setRepeating(AlarmManager.RTC_WAKEUP, inicio, intervalo, alarmIntent);
    }

}
