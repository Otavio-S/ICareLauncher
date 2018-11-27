package com.example.otavio.tcc.Picker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.example.otavio.tcc.R;

import java.util.Calendar;
import java.util.Objects;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView txtHora = Objects.requireNonNull(getActivity()).findViewById(R.id.txtHora);
        TextView txtMin = getActivity().findViewById(R.id.txtMin);
        TextView txtTempo = getActivity().findViewById(R.id.txtTempo);

        String horaEscolhida = "Hora Escolhida: ".concat(String.valueOf(hourOfDay)).concat(":").concat(String.valueOf(minute));
        txtTempo.setText(horaEscolhida);
        txtHora.setText(String.valueOf(hourOfDay));
        txtMin.setText(String.valueOf(minute));
    }


}