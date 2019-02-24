package com.example.otavio.tcc.Telas.Alarmes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.otavio.tcc.Adapter.Adapter_Tabs;
import com.example.otavio.tcc.R;

public class TelaAlarmes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter_Tabs adapter = new Adapter_Tabs(getSupportFragmentManager(), false);
        adapter.addFrag(new FragmentAlarmes(), "Alarme");
        adapter.addFrag(new FragmentHistorico(), "Histórico");
        viewPager.setAdapter(adapter);
    }

}
