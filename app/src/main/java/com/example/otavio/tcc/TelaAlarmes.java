package com.example.otavio.tcc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

public class TelaAlarmes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getActionBar().setElevation(8);
        getWindow().setStatusBarColor(Color.rgb(130, 100, 80));
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_alarmes);


    }
}
