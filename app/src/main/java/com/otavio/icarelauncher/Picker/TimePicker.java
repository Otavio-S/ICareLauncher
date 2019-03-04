package com.otavio.icarelauncher.Picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;

import com.otavio.icarelauncher.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.PickerTheme, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        EditText edHoraAlarme = Objects.requireNonNull(getActivity()).findViewById(R.id.edHoraAlarme);

        String hora = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
        edHoraAlarme.setText(hora);
    }


}