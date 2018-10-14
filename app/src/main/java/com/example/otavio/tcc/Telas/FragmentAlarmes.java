package com.example.otavio.tcc.Telas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.otavio.tcc.Adapter.Alarmes_Adapter;
import com.example.otavio.tcc.Model.Alarme;
import com.example.otavio.tcc.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlarmes extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<Alarme> alarmeList;

    public FragmentAlarmes() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alarmes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAlarmes);
        Alarmes_Adapter viewAdapter = new Alarmes_Adapter(getContext(), alarmeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(viewAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmeList = new ArrayList<>();
        

    }

}