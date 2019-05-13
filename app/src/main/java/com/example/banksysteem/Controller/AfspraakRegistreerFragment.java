package com.example.banksysteem.Controller;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.Model.Afspraak;
import com.example.banksysteem.Model.Klant;
import com.example.banksysteem.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AfspraakRegistreerFragment extends Fragment {

    private EditText etDatepicker;
    private Calendar calendar;
    private Klant klant;
    private DatabaseConnector db = new DatabaseConnector();
    private String gekozenDatum;
    private DatePickerDialog datepicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle klantGegevens = this.getArguments();
        klant = (Klant) klantGegevens.getSerializable("klantGegevens");

        return inflater.inflate(R.layout.afspraak_registreer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //arraylist met beschikbare tijden
        ArrayList<String> afspraakTijden = new ArrayList<>();
        afspraakTijden.add("09:00");
        afspraakTijden.add("10:00");
        afspraakTijden.add("11:00");


        etDatepicker = view.findViewById(R.id.afspraakfragment_etDatum);
        String datumVandaag = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etDatepicker.setText(datumVandaag);

        final Spinner spinnerTijden = view.findViewById(R.id.afsrpaakfragment_spinnerTijden);

        ///////////////////////////datepicker///////////////////////////////////
        calendar = Calendar.getInstance();
        //set date listener on datepickerdialog to get the date the user selected
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                gekozenDatum = dayOfMonth + "-" + (monthOfYear + 1 < 10 ? ("0" + (monthOfYear + 1)) : monthOfYear + 1) + "-" + year;
                etDatepicker.setText(gekozenDatum);
            }
        };

        //show datepicker if user selects edittext datum
        etDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker = new DatePickerDialog(view.getContext(), date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                datepicker.show();

            }
        });




        //haal datums op die in de tabel afspraak zitten en kijk welke tijden er al bezet zijn.
        //Zet de correcte tijden in de spinner
        //Als er geen tijden meer zijn voor een datum -> voeg datum toe aan datums die niet meer gekozen kunnen worden
        final String sql = "SELECT * FROM Afspraak WHERE datum = \"" + etDatepicker.getText().toString() + "\"";
        Log.d("Afspraak", "SQL statement: " + sql);
        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            String strResultReplace = strResult.replace("\"", "");
            Log.d("Afspraak", "strResult: " + strResultReplace);

            if (strResultReplace.equals("msg:select:empty")) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, afspraakTijden);
                spinnerTijden.setAdapter(adapter);

            } else {

                Log.d("Afspraak", "strResultTijden: " + strResult);

                JSONArray jsonArray = new JSONArray(strResult);
                ArrayList<String> tijdenBezet = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    String tijd = (String) jsonObject.get("Tijd");
                    tijdenBezet.add(tijd);

                    Log.d("Afspraakfragment", "opgehaalde json: " + tijd);
                }

                //vergelijk met afsrpaaktijden en verwijder in afsrpaaktijden de tijden uit de database
                afspraakTijden.removeAll(tijdenBezet);

                if (afspraakTijden.size() > 0){
                    //Vul de spinner met de tijden die nog wel beschikbaar zijn die datum
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, afspraakTijden);
                    spinnerTijden.setAdapter(adapter);
                }
                else {

                }



                //TODO: als de lijst leeg is (of alle tijden al voorkomen in de database -> zet de datum op disabled
                //TODO: datum disablenen = toevoegen aan lijst Calender[] days

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        final Button btnVersturen = view.findViewById(R.id.afspraakfragment_btnVersturen);
        btnVersturen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //voeg klant en afspraak toe aan database
                Afspraak afspraak = new Afspraak(klant.getKlantId(), etDatepicker.getText().toString(), spinnerTijden.getSelectedItem().toString());

                Log.d("Afspraak", "Afspraak gegevens: " + afspraak.getDatum() + afspraak.getTijd() + afspraak.getKlantId());

                //insertKlant(klant);
                //insertAfspraak(afspraak);
            }
        });


    }

    private void insertKlant(Klant k) {

        String sql = "INSERT INTO Klant(klantID, Voornaam, Achternaam, Telefoon, Email, Adres, Bedrijfsnaam, isGoedgekeurd) " +
                "VALUES('" + k.getKlantId() + "','" + k.getVoornaam() + "','" + k.getAchternaam()
                + "','" + k.getTelefoonnummer() + "','" + k.getEmail() + "','" + k.getAdres() + "','" + k.getBedrijfsnaam() + "'," + 0 + ");";

        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Log.d("Afspraak", "Db.execute aangeroepen");
            Object oResult = db.get();

            String strResult = oResult.toString();
            strResult = strResult.replace("\"", "");

            Log.d("AfspraakFr:insertKlant", "strResult: " + strResult);

            if (strResult.equals("msg:insert:succes")) {
                Toast.makeText(getContext(), "Klant succesvol toegevoegd", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Er is iets misgegaan", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.d("Afspraak", "Er is iets misgegaan");
            e.printStackTrace();
        }

    }

    private void insertAfspraak(Afspraak af) {

        String sql = "INSERT INTO Afspraak VALUES('" + af.getDatum() + "','" + af.getTijd() + "','" + af.getKlantId() + "');";

        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            strResult = strResult.replace("\"", "");

            Log.d("Afspraak:insertAfsrpaak", "strResult: " + strResult);

            if (strResult.equals("msg:insert:succes")) {
                Toast.makeText(getContext(), "Afspraak succesvol toegevoegd", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Er is iets misgegaan", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

