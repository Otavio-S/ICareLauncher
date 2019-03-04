package com.otavio.icarelauncher.Telas.Alarmes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.otavio.icarelauncher.Masks.MaskTime;
import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.Picker.DatePicker;
import com.otavio.icarelauncher.Picker.TimePicker;
import com.otavio.icarelauncher.R;
import com.otavio.icarelauncher.Receiver.AlarmReceiver;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NovoAlarme extends AppCompatActivity {

    private final View.OnClickListener btnSalvarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());

            EditText edNome = findViewById(R.id.edNomeAlarme);
            EditText edDescricao = findViewById(R.id.edDescricaoAlarme);
            EditText edData = findViewById(R.id.edDataAlarme);
            EditText edHora = findViewById(R.id.edHoraAlarme);
            EditText edRepetir = findViewById(R.id.edRepetirAlarme);
            EditText edTempo = findViewById(R.id.edTempoAlarme);
            Switch aswitch = findViewById(R.id.switchLD);

            Alarme alarme = new Alarme();
            String insert = "";
            String tempo = "";
            try {
                String horario = String.valueOf(edHora.getText());
                String hora = horario.substring(0, 2);
                String minuto = horario.substring(3, 5);
                tempo = String.valueOf(edTempo.getText());

                alarme.setNome(String.valueOf(edNome.getText()));
                alarme.setDescricao(String.valueOf(edDescricao.getText()));
                alarme.setDataInicial(String.valueOf(edData.getText()));
                alarme.setHoraInicial(hora);
                alarme.setMinInicial(minuto);
                alarme.setQuantidade(String.valueOf(edRepetir.getText()));
                alarme.setTempo(tempo);

                if (aswitch.isChecked()) {
                    alarme.setLigado("1");
                } else {
                    alarme.setLigado("0");
                }
                insert = tabelaAlarmes.insereDado(alarme);

            } catch (Exception e) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        R.string.preencha,
                        Toast.LENGTH_SHORT);
                toast.show();
            }

            if (insert.equals("Registro Inserido com sucesso")) {
                int id = tabelaAlarmes.UltimoID();

                startAlarm(id);

                int h = Integer.valueOf(tempo.substring(0, 2));
                int m = Integer.valueOf(tempo.substring(3, 5));

                if (m == 0) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.alarme_ira_tocar_h, h),
                            Toast.LENGTH_LONG);
                    toast.show();
                } else if (h == 0) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.alarme_ira_tocar_m, m),
                            Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.alarme_cada, h, m),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                Intent intent = new Intent(getApplicationContext(), FragmentAlarmes.class);
                setResult(RESULT_OK, intent);
                finish();

            }
        }
    };
    private final View.OnClickListener btnCancelarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    R.string.cancelado,
                    Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), R.string.cancelado, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_alarme);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_initialize));

        AdView adView = findViewById(R.id.adViewNovoAlarm);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        EditText edDataAlarme = findViewById(R.id.edDataAlarme);
        EditText edHoraAlarme = findViewById(R.id.edHoraAlarme);
        EditText edTempoAlarme = findViewById(R.id.edTempoAlarme);

        edDataAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        edHoraAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        edTempoAlarme.addTextChangedListener(new MaskTime("##:##"));

        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(btnSalvarOnClickListener);

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(btnCancelarOnClickListener);

    }

    private void startAlarm(int id) {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
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
            d = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        inicio = inicio + (86400000L * d);  //Add d days

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }

}
