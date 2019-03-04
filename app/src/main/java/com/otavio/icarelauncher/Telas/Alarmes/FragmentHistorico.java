package com.otavio.icarelauncher.Telas.Alarmes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.otavio.icarelauncher.Adapter.Historico_Adapter;
import com.otavio.icarelauncher.Model.Historico;
import com.otavio.icarelauncher.R;
import com.otavio.icarelauncher.SQLite.TabelaHistorico;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistorico extends Fragment {

    private Historico_Adapter viewAdapter;
    private RecyclerView recyclerView;
    private List<Historico> historicoList;

    public FragmentHistorico() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHistorico);
        viewAdapter = new Historico_Adapter(getContext(), historicoList);
        viewAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(viewAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        MobileAds.initialize(getContext(), getString(R.string.banner_ad_initialize));

        AdView adView = view.findViewById(R.id.adViewHistorico);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        historicoList = new ArrayList<>();
        TabelaHistorico historico = new TabelaHistorico(getContext());
        int i;
        int quant = historico.carregaDados().size() - 1;

        for (i = quant; i >= 0; i--) {
            historicoList.add(historico.carregaDados().get(i));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_historico, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1Historico:

                historicoList = new ArrayList<>();
                TabelaHistorico tabelaHistorico = new TabelaHistorico(getContext());
                tabelaHistorico.deletaTudo();

                int i;
                int quant = tabelaHistorico.carregaDados().size() - 1;

                historicoList.clear();
                for (i = quant; i >= 0; i--) {
                    historicoList.add(tabelaHistorico.carregaDados().get(i));
                }

                viewAdapter = new Historico_Adapter(getContext(), historicoList);
                viewAdapter.notifyItemRangeRemoved(0, quant);
                recyclerView.setAdapter(viewAdapter);

                return true;
            default:
                return false;

        }

    }

}
