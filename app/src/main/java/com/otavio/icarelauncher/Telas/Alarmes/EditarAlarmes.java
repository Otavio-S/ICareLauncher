package com.otavio.icarelauncher.Telas.Alarmes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.otavio.icarelauncher.Adapter.Alarmes_Adapter;
import com.otavio.icarelauncher.Masks.MaskTime;
import com.otavio.icarelauncher.Model.Alarme;
import com.otavio.icarelauncher.Picker.DatePicker;
import com.otavio.icarelauncher.Picker.TimePicker;
import com.otavio.icarelauncher.R;
import com.otavio.icarelauncher.Receiver.AlarmReceiver;
import com.otavio.icarelauncher.SQLite.TabelaAlarmes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class EditarAlarmes extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), R.string.alteracoes_descartadas, Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_editar_alarme);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_initialize));

        AdView adView = findViewById(R.id.adViewEdAlarm);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        final EditText edNome = findViewById(R.id.edNomeAlarme);
        final EditText edDescricao = findViewById(R.id.edDescricaoAlarme);
        final EditText edData = findViewById(R.id.edDataAlarme);
        final EditText edHora = findViewById(R.id.edHoraAlarme);
        final EditText edRepetir = findViewById(R.id.edRepetirAlarme);
        final EditText edTempo = findViewById(R.id.edTempoAlarme);
        final Switch aswitch = findViewById(R.id.switchLD);

        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        final TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());

        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        edNome.setText(alarme.getNome());
        edDescricao.setText(alarme.getDescricao());
        edData.setText(alarme.getDataInicial());
        edHora.setText(String.valueOf(alarme.getHoraInicial()).concat(":").concat(String.valueOf(alarme.getMinInicial())));
        edRepetir.setText(alarme.getQuantidade());
        edTempo.setText(alarme.getTempo());
        if (alarme.getLigado().equals("1")) {
            aswitch.setChecked(true);
        } else {
            aswitch.setChecked(false);
        }

        edData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        edHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        edTempo.addTextChangedListener(new MaskTime("##:##"));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String horario = String.valueOf(edHora.getText());
                String hora = horario.substring(0, 2);
                String minuto = horario.substring(3, 5);

                Alarme alarme = new Alarme();
                alarme.setID(String.valueOf(id));
                alarme.setNome(String.valueOf(edNome.getText()));
                alarme.setDescricao(String.valueOf(edDescricao.getText()));
                alarme.setDataInicial(String.valueOf(edData.getText()));
                alarme.setHoraInicial(hora);
                alarme.setMinInicial(minuto);
                alarme.setQuantidade(String.valueOf(edRepetir.getText()));
                alarme.setTempo(String.valueOf(edTempo.getText()));
                if (aswitch.isChecked()) {
                    alarme.setLigado("1");
                } else {
                    alarme.setLigado("0");
                }

                String altera = tabelaAlarmes.alteraRegistro(alarme);

                if (altera.equals("Registro atualizado com sucesso")) {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            R.string.alteracoes_salvas,
                            Toast.LENGTH_SHORT);
                    toast.show();

                    Intent contentIntent = new Intent(getApplicationContext(), EditarAlarmes.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity
                            (getApplicationContext(), id, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    pendingIntent.cancel();

                    startAlarm(id);

                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            R.string.cancelado,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

                List<Alarme> alarmeList = new ArrayList<>();
                TabelaAlarmes alarmes = new TabelaAlarmes(getApplicationContext());
                int i;
                int quant = alarmes.carregaDados().size() - 1;

                for (i = quant; i >= 0; i--) {
                    alarmeList.add(alarmes.carregaDados().get(i));
                }

                Alarmes_Adapter viewAdapter = new Alarmes_Adapter(getApplicationContext(), alarmeList);
                viewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                });
                viewAdapter.notifyDataSetChanged();

                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        R.string.cancelado,
                        Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }

    private void startAlarm(int id) {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);

        TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());
        Alarme alarme = tabelaAlarmes.carregaDadosPorID(id);

        String dataSelected = alarme.getDataInicial();

        String horaInicial = String.valueOf(alarme.getHoraInicial());
        String minInicial = String.valueOf(alarme.getMinInicial());

        int ano = Integer.valueOf(dataSelected.substring(6, 10));
        int mes = Integer.valueOf(dataSelected.substring(3, 5)) - 1;
        int dia = Integer.valueOf(dataSelected.substring(0, 2));

        Calendar alarm = Calendar.getInstance();
        alarm.set(ano, mes, dia, Integer.valueOf(horaInicial), Integer.valueOf(minInicial));
        long inicio = alarm.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }

}