package com.example.otavio.tcc.Telas.Alarmes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.Picker.TimePicker;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.Receiver.AlarmReceiver;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.util.Calendar;
import java.util.Objects;

public class NovoAlarme extends FragmentActivity {

    private View.OnClickListener btnSalvarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());

            TextView edNome = findViewById(R.id.edNome);
            TextView edDescricao = findViewById(R.id.edDescricao);
            TextView edQuantidade = findViewById(R.id.edQuantidade);
            TextView edTempo = findViewById(R.id.edTempo);
            TextView txtHora = findViewById(R.id.txtHora);
            TextView txtMin = findViewById(R.id.txtMin);
            TextView txtTempo = findViewById(R.id.txtTempo);
            Switch aswitch = findViewById(R.id.switchLD);

            Alarme alarme = new Alarme();
            alarme.setNome(String.valueOf(edNome.getText()));
            alarme.setDescricao(String.valueOf(edDescricao.getText()));
            alarme.setHoraInicial(Integer.valueOf(String.valueOf(txtHora.getText())));
            alarme.setMinInicial(Integer.valueOf(String.valueOf(txtMin.getText())));
            alarme.setQuantidade(String.valueOf(edQuantidade.getText()));
            alarme.setTempo(String.valueOf(edTempo.getText()));
            alarme.setContador(Integer.valueOf(String.valueOf(edQuantidade.getText())));
            if (aswitch.isChecked()) {
                alarme.setLigado("1");
            } else {
                alarme.setLigado("0");
            }

            String insert = tabelaAlarmes.insereDado(alarme);

            if (insert.equals("Registro Inserido com sucesso")) {
                int id = tabelaAlarmes.UltimoID();

                startAlarm(id, alarme.getNome(), alarme.getHoraInicial(), alarme.getMinInicial(), Integer.parseInt(alarme.getTempo()));
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Alarme salvo",
                        Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = new Intent(getApplicationContext(), FragmentAlarmes.class);
                setResult(RESULT_OK, intent);
                // finish method is requires if this Activity was started by startActivityForResult
                finish();

            } else {
                Toast toast = Toast.makeText(
                        getApplicationContext(),
                        "Alarme não salvo",
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            finish();
        }
    };
    private View.OnClickListener btnCancelarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(
                    getApplicationContext(),
                    "Cancelado",
                    Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_alarme);
        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView txtHora = findViewById(R.id.txtHora);
        TextView txtMin = findViewById(R.id.txtMin);
        Switch aswitch = findViewById(R.id.switchLD);

        Button btnHora = findViewById(R.id.btnHora);
        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(btnSalvarOnClickListener);

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(btnCancelarOnClickListener);

        aswitch.setScaleX((float) 1.2);
        aswitch.setScaleY((float) 1.2);
        txtHora.setVisibility(View.GONE);
        txtMin.setVisibility(View.GONE);

    }

    private void startAlarm(int id, String remedio, int horaInicial, int minInicial, int intervalo) {
        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(NovoAlarme.this, AlarmReceiver.class);
        intent.putExtra("Nome", remedio);
        intent.putExtra("ID", id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), id, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, horaInicial);
        calendar.set(Calendar.MINUTE, minInicial);

        long inicio = calendar.getTimeInMillis();

        intervalo = intervalo * 60 * 1000;

        Objects.requireNonNull(alarmMgr).setRepeating(AlarmManager.RTC_WAKEUP, inicio, intervalo, alarmIntent);
    }

}
