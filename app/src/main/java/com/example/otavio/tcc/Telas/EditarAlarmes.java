package com.example.otavio.tcc.Telas;

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
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

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
        Objects.requireNonNull(getActionBar()).setHomeButtonEnabled(true);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("ID", 0);

        final TextView edNome = findViewById(R.id.edNome);
        final TextView edDescricao = findViewById(R.id.edDescricao);
        final TextView txtHora = findViewById(R.id.txtHora);
        final TextView txtMin = findViewById(R.id.txtMin);
        final TextView edQuantidade = findViewById(R.id.edQuantidade);
        final TextView edTempo = findViewById(R.id.edTempo);
        final TextView txtTempo = findViewById(R.id.txtTempo);
        final Switch aswitch = findViewById(R.id.switchLD);
        Button btnHora = findViewById(R.id.btnHoraEd);
        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        aswitch.setScaleX((float) 1.2);
        aswitch.setScaleY((float) 1.2);
        txtHora.setVisibility(View.GONE);
        txtMin.setVisibility(View.GONE);

        final TabelaAlarmes tabelaAlarmes = new TabelaAlarmes(getApplicationContext());

        List<Alarme> alarmeList = tabelaAlarmes.carregaDadosPorID(id);

        edNome.setText(alarmeList.get(0).getNome());
        edDescricao.setText(alarmeList.get(0).getDescricao());
        edQuantidade.setText(alarmeList.get(0).getQuantidade());
        edTempo.setText(alarmeList.get(0).getTempo());
        String horaEscolhida = "Hora Escolhida: ".concat(String.valueOf(alarmeList.get(0).getHoraInicial())).concat(":").concat(String.valueOf(alarmeList.get(0).getMinInicial()));
        txtTempo.setText(horaEscolhida);
        if (alarmeList.get(0).getLigado().equals("1")) {
            aswitch.setChecked(true);
        } else {
            aswitch.setChecked(false);
        }

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePicker();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarme alarme = new Alarme();
                alarme.setID(String.valueOf(id));
                alarme.setNome(String.valueOf(edNome.getText()));
                alarme.setDescricao(String.valueOf(edDescricao.getText()));
                alarme.setHoraInicial(Integer.valueOf(String.valueOf(txtHora.getText())));
                alarme.setMinInicial(Integer.valueOf(String.valueOf(txtMin.getText())));
                alarme.setQuantidade(String.valueOf(edQuantidade.getText()));
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
                } else {
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            "Cancelado",
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
            }
        });


    }

}