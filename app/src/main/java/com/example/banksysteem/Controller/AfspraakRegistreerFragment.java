package com.example.banksysteem.Controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.banksysteem.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AfspraakRegistreerFragment extends Fragment {

    private EditText etDatepicker, etTimepicker;
    private Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.afspraak_registreer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDatepicker = view.findViewById(R.id.afspraak_etDatum);
        etTimepicker = view.findViewById(R.id.afspraak_etTijd);


        ///////////////////////////datepicker///////////////////////////////////
        calendar = Calendar.getInstance();
        //set date listener on datepickerdialog to get the date the user selected
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear +1) + "-" + year;
                etDatepicker.setText(date);

            }
        };

        //show datepicker if user selects edittext datum
        etDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(view.getContext(), date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        ///////////////////////////timepicker///////////////////////////////////

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = hour + ":" + minute;
                etTimepicker.setText(time);
            }
        };

        etTimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        view.getContext(),
                        time,
                        Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

    }

}
