package com.example.banksysteem.Controller;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.Model.Afspraak;
import com.example.banksysteem.Model.Rekening;
import com.example.banksysteem.R;
import com.example.banksysteem.Util.AanvraagSoort;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AanvraagActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    private ArrayList<String> producten;
    private Spinner spinnerTijden;
    private EditText etDatepicker;
    private Calendar calendar;
    private DatePickerDialog datepicker;
    private AfspraakRegistreerFragment afspraakRegistreerFragment = new AfspraakRegistreerFragment();
    private ArrayList<String> afspraakTijden = new ArrayList<>();
    private String afspraaksoort;
    private ArrayList<Rekening> rekeningenKlant;
    private ArrayList<Afspraak> afsprakenKlant;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aanvraag_activity);


        //Lijst met producten waar de klant een aanvraag voor kan doen
        producten = new ArrayList<>();
        producten.add(AanvraagSoort.BETAALREKENING);
        producten.add(AanvraagSoort.SPAARREKENING);
        producten.add(AanvraagSoort.DEPOSITO);
        producten.add(AanvraagSoort.PERSOONLIJKE_LENING);
        producten.add(AanvraagSoort.DOORLOPEND_KREDIET);

        final ListView lv = findViewById(R.id.aanvraagsoortFragment_listview);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, producten);

        lv.setAdapter(arrayAdapter);

        //haal het soort product op waar de gebruiker op geklikt heeft
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                afspraaksoort = (String) lv.getItemAtPosition(i);
                Log.d("AanvraagActivity", "AfspraakSoort: " + afspraaksoort);

            }
        });

        etDatepicker = findViewById(R.id.afspraakfragment_etDatum);
        final String datumVandaag = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etDatepicker.setText(datumVandaag);


        spinnerTijden = findViewById(R.id.afsrpaakfragment_spinnerTijden);

        ///////////////////////////datepicker///////////////////////////////////
        calendar = Calendar.getInstance();
        //set date listener on datepickerdialog to get the date the user selected
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                String dag = "";

                if (dayOfMonth < 10) {
                    dag = "0" + dayOfMonth;
                } else {
                    dag = String.valueOf(dayOfMonth);
                }

                String maand = "";

                if (monthOfYear + 1 < 10) {
                    maand = "0" + (monthOfYear + 1);
                } else {
                    maand = String.valueOf((monthOfYear + 1));
                }
                String datum = dag + "-" + maand + "-" + year;


                //gekozenDatum = dayOfMonth + "-" + (monthOfYear + 1 < 10 ? ("0" + (monthOfYear + 1)) : monthOfYear + 1) + "-" + year;
                etDatepicker.setText(datum);
            }
        };

        datepicker = new DatePickerDialog(this, date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datepicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

        //show datepicker if user selects edittext datum
        etDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datepicker.show();

            }
        });

        datepicker.getDatePicker().init(2019, 04, 15, this);


        ImageButton btnVersturen = findViewById(R.id.aanvraagsoort_btnNaarAfspraak);
        btnVersturen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check of de gebruiker een keuze heeft gemaakt voor een soort product
                if (afspraaksoort == null) {
                    Toast.makeText(getApplicationContext(), "Maak een keuze", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), afspraaksoort, Toast.LENGTH_SHORT).show();
                }

                ArrayList<Rekening> rekeningen = haalRekeningenKlantOp();

                //check of de klant al een lening heeft
                if (afspraaksoort.equals(AanvraagSoort.DOORLOPEND_KREDIET) || afspraaksoort.equals(AanvraagSoort.PERSOONLIJKE_LENING)) {
                    checkKlantLening(rekeningen);
                }
                //check of de klant schuld heeft
                checkKlantSchuld(rekeningen);

                ArrayList<Afspraak> afspraken = haalAfsprakenKlantOp();
                Log.d("AanvraagAfspraak", "afspraken klant vanaf vandaag: " + afspraken);
                //check of de klant al aanvragen heeft
                //Als klant al een aanvraag voor een lening of doorlopend krediet heeft dan mag deze
                //geen aanvraag doen

                //Als de klant al een aanvraag heeft voor aangeklikte soort dan mag deze daar geen aanvraag voor doen
                //maar wel voor ander soort (tenzij lening)

                //voeg afspraak toe aan database

            }
        });


    }

    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        Log.d("Afspraak", "Ondatechanged aangeroepen");

        String dag = "";

        if (datePicker.getDayOfMonth() < 10) {
            dag = "0" + datePicker.getDayOfMonth();
        } else {
            dag = String.valueOf(datePicker.getDayOfMonth());
        }

        String maand = "";

        if (datePicker.getMonth() + 1 < 10) {
            maand = "0" + (datePicker.getMonth() + 1);
        } else {
            maand = String.valueOf((datePicker.getMonth() + 1));
        }
        String datum = datePicker.getDayOfMonth() + "-" + maand + "-" + datePicker.getYear();


        afspraakRegistreerFragment.vulArrayList(afspraakTijden);
        final String sql = "SELECT * FROM Afspraak WHERE datum = \"" + datum + "\"";
        Log.d("Afspraak", "SQL statement: " + sql);
        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            String strResultReplace = strResult.replace("\"", "");
            Log.d("Afspraak", "strResult: " + strResultReplace);

            if (strResultReplace.equals("msg:select:empty")) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, afspraakTijden);
                spinnerTijden.setAdapter(adapter);

            } else {

                Log.d("Afspraak", "strResultTijden: " + strResult);

                JSONArray jsonArray = new JSONArray(strResult);
                ArrayList<String> tijdenBezet = new ArrayList<>();

                for (int teller = 0; teller < jsonArray.length(); teller++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(teller);

                    String tijd = (String) jsonObject.get("Tijd");
                    tijdenBezet.add(tijd);

                    Log.d("Afspraakfragment", "opgehaalde json: " + tijd);
                }

                Log.d("Afspraak", "Tijdenbezet" + tijdenBezet);
                //vergelijk met afsrpaaktijden en verwijder in afsrpaaktijden de tijden uit de database
                afspraakTijden.removeAll(tijdenBezet);
                Log.d("Afspraak", "Tijden beschikbaar" + afspraakTijden);

                if (afspraakTijden.size() > 0) {
                    //Vul de spinner met de tijden die nog wel beschikbaar zijn die datum
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, afspraakTijden);
                    spinnerTijden.setAdapter(adapter);
                } else {
                    Toast.makeText(this, "Er zijn geen tijden beschikbaar op deze datum", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //TODO: geef klantid van ingelogde klant mee
    private ArrayList<Rekening> haalRekeningenKlantOp() {


        String sql = "SELECT * FROM KlantRekening JOIN Rekening ON " +
                "KlantRekening.RekeningRekeningnummer = Rekening.Rekeningnummer " +
                "WHERE KlantklantID = '217740078'";

        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            String strResultReplace = strResult.replace("\"", "");
            Log.d("Aanvraag", "strResult: " + strResultReplace);

            if (strResultReplace.equals("msg:select:empty")) {
                Toast.makeText(this, "Er is iets misgegaan", Toast.LENGTH_SHORT).show();

            } else {

                Log.d("Aanvraag", "strResultRekeningen: " + strResult);

                rekeningenKlant = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(strResult);


                for (int teller = 0; teller < jsonArray.length(); teller++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(teller);

                    String rekeningsoort = jsonObject.getString("RekeningsoortSoort");
                    String rekeningnummer = jsonObject.getString("Rekeningnummer");
                    Double rente = jsonObject.getDouble("Rente");
                    Double saldo = jsonObject.getDouble("Saldo");

                    Rekening rekening = new Rekening(rekeningnummer, rekeningsoort, rente, saldo);
                    rekeningenKlant.add(rekening);

                    Log.d("Aanvraag", "opgehaalde json: " + rekening.getRekeningSoort());
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rekeningenKlant;
    }

    private void checkKlantLening(ArrayList<Rekening> rekeningenKlant) {


        for (Rekening rek : rekeningenKlant) {
            if (rek.getRekeningSoort().contains(AanvraagSoort.DOORLOPEND_KREDIET) || rek.getRekeningSoort().contains(AanvraagSoort.PERSOONLIJKE_LENING)) {
                Toast.makeText(this, "U heeft al een lening", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Alles oke", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkKlantSchuld(ArrayList<Rekening> rekeningenKlant) {


        for (Rekening rek : rekeningenKlant) {
            if (Double.toString(rek.getSaldo()).contains("-")) {
                Toast.makeText(this, "U heeft schulden", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "U heeft geen schulden", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private ArrayList<Afspraak> haalAfsprakenKlantOp() {

        //haal alle afspraken van een klant op uit de database
        String sql = "SELECT * FROM Afspraak WHERE KlantklantID = '217740078'";

        try {

            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            String strResultReplace = strResult.replace("\"", "");
            Log.d("AanvraagAfspraken", "strResult: " + strResultReplace);

            if (strResultReplace.equals("msg:select:empty")) {
                Toast.makeText(this, "Er is iets misgegaan", Toast.LENGTH_SHORT).show();

            } else {

                Log.d("AanvraagAfspraken", "strResultAfsrpaken: " + strResult);

                afsprakenKlant = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(strResult);

                for (int teller = 0; teller < jsonArray.length(); teller++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(teller);

                    Date date = dateFormat.parse(jsonObject.getString("Datum"));
                    String tijd = jsonObject.getString("Tijd");
                    String afspraaksoort = jsonObject.getString("Soort");

                    if (!date.before(new Date())) {
                        String datum = dateFormat.format(date);

                        Afspraak afspraak = new Afspraak(datum, tijd, afspraaksoort);
                        afsprakenKlant.add(afspraak);

                    }

                    Log.d("AanvraagAfspraken", "opgehaalde json: " + afsprakenKlant);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return afsprakenKlant;
    }
}


