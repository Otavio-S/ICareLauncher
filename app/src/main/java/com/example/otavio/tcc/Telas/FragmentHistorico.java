package com.example.otavio.tcc.Telas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.otavio.tcc.Adapter.Historico_Adapter;
import com.example.otavio.tcc.Model.Historico;
import com.example.otavio.tcc.R;
import com.example.otavio.tcc.SQLite.TabelaHistorico;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistorico extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private List<Historico> historicoList;

    public FragmentHistorico() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_historico, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistorico);
        Historico_Adapter viewAdapter = new Historico_Adapter(getContext(), historicoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historicoList = new ArrayList<>();
        TabelaHistorico historico = new TabelaHistorico(getContext());
        int i;
        int quant = historico.carregaDados().size();

        for (i = 0; i < quant; i++) {
            historicoList.add(historico.carregaDados().get(i));
        }
    }

}
