package com.example.otavio.tcc.Telas.Alarmes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.otavio.tcc.Adapter.Alarmes_Adapter;
import com.example.otavio.tcc.Masks.MaskTime;
import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.Picker.DatePicker;
import com.example.otavio.tcc.Picker.TimePicker;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.Receiver.AlarmReceiver;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class EditarAlarmes extends FragmentActivity {

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alarmes);

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
                            "Alterações Salvas",
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
                            "Cancelado",
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
                    @Override
                    public void onChanged() {
                        super.onChanged();
                    }
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
                        "Cancelado",
                        Toast.LENGTH_SHORT);
                toast.show();
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

        String data = alarme.getDataInicial();
        String ano = data.substring(6, 10);
        String mes = data.substring(3, 5);
        String dia = data.substring(0, 2);

        String horaInicial = String.valueOf(alarme.getHoraInicial());
        String minInicial = String.valueOf(alarme.getMinInicial());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, Integer.parseInt(ano));
        calendar.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaInicial));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minInicial));

        long inicio = calendar.getTimeInMillis();

        Objects.requireNonNull(alarmMgr).setExact(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);

    }

}