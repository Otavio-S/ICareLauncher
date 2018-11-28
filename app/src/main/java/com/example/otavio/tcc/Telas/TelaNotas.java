package com.example.otavio.tcc.Telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.otavio.tcc.Adapter.Notas_Adapter;
import com.example.otavio.tcc.Model.Nota;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaNotas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TelaNotas extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        Objects.requireNonNull(getActionBar()).setHomeButtonEnabled(true);

        List<Nota> notaList = new ArrayList<>();
        TabelaNotas notas = new TabelaNotas(getApplicationContext());
        int i;
        int quant = notas.carregaDados().size();

        for (i = 0; i < quant; i++) {
            notaList.add(notas.carregaDados().get(i));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotas);
        Notas_Adapter viewAdapter = new Notas_Adapter(getApplicationContext(), notaList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingNotas);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, NovaNota.class);
                startActivity(intent);
            }
        });

    }
}
