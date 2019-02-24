package com.example.otavio.tcc.Telas.Alarmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
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

import static android.app.Activity.RESULT_OK;

public class FragmentAlarmes extends Fragment {

    private List<Alarme> alarmeList;
    private Alarmes_Adapter viewAdapter;
    private RecyclerView recyclerView;

    public FragmentAlarmes() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarmes, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAlarmes);
        viewAdapter = new Alarmes_Adapter(getContext(), alarmeList);
        viewAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(viewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        final FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingAlarmes);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NovoAlarme.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                alarmeList = new ArrayList<>();
                TabelaAlarmes alarmes = new TabelaAlarmes(getContext());
                int i;
                int quant = alarmes.carregaDados().size() - 1;

                for (i = quant; i >= 0; i--) {
                    alarmeList.add(alarmes.carregaDados().get(i));
                }

                viewAdapter = new Alarmes_Adapter(getContext(), alarmeList);
                viewAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(viewAdapter);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmeList = new ArrayList<>();
        TabelaAlarmes alarmes = new TabelaAlarmes(getContext());
        int i;
        int quant = alarmes.carregaDados().size() - 1;

        for (i = quant; i >= 0; i--) {
            alarmeList.add(alarmes.carregaDados().get(i));
        }
    }

}