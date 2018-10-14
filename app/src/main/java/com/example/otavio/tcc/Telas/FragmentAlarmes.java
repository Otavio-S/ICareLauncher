package com.example.otavio.tcc.Telas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.otavio.tcc.Adapter.Alarmes_Adapter;
import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaAlarmes;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlarmes extends Fragment {

    private List<Alarme> alarmeList = new ArrayList<>();

    public FragmentAlarmes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CarregaAlarmes();
        iniciarRecyclerView();
        return inflater.inflate(R.layout.fragment_alarmes, container, false);
    }

    private void CarregaAlarmes() {
        TabelaAlarmes alarmes = new TabelaAlarmes(getContext());
        int i;
        int quant = alarmes.carregaDados().size();
        for (i = 0; i < quant; i++) {
            Alarme alarme = alarmes.carregaDados().get(i);
            alarmeList.add(i, alarme);
        }

    }

    private void iniciarRecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerViewAlarmes);
        Alarmes_Adapter adapter = new Alarmes_Adapter(alarmeList);

        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}