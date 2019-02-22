package com.example.otavio.tcc.Picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.otavio.tcc.R;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, dayOfMonth);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

        EditText edDataAlarme = Objects.requireNonNull(getActivity()).findViewById(R.id.edDataAlarme);

        month += 1;
        String hora = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month, year);
        edDataAlarme.setText(hora);
    }
}