package com.example.otavio.tcc.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ALARM_CLOCK = "alarm_clock";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent bootIntent = new Intent(context, FireAlarm.class);
        bootIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        bootIntent.putExtra(ALARM_CLOCK, intent.getSerializableExtra(ALARM_CLOCK));
        context.startActivity(bootIntent);

    }

}
