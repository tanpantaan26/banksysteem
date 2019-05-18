package com.example.banksysteem.Controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * Deze klasse zorgt ervoor dat de klant een aanvraag kan doen om een nieuwe rekening of lening te openen.
 *
 * @author Inge
 * @version 1
 * @see AanvraagSoort
 */
public class AanvraagActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    private Spinner spinnerTijden;
    private EditText etDatepicker;
    private DatePickerDialog datepicker;
    private AfspraakRegistreerFragment afspraakRegistreerFragment = new AfspraakRegistreerFragment();
    private ArrayList<String> afspraakTijden = new ArrayList<>();
    private String afspraaksoort;
    private ArrayList<Rekening> rekeningenKlant;
    private ArrayList<String> afsprakenKlant;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aanvraag_activity);


        //Lijst met producten waar de klant een aanvraag voor kan doen
        ArrayList<String> producten = new ArrayList<>();
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
        Calendar calendar = Calendar.getInstance();
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

                ArrayList<Rekening> rekeningen = haalRekeningenKlantOp();
                //check of de klant al aanvragen heeft
                ArrayList<String> afspraken = haalAfsprakenKlantOp();
                Log.d("AanvraagAfspraak", "afspraken klant vanaf vandaag: " + afspraken);


                //check of de gebruiker een keuze heeft gemaakt voor een soort product
                if (afspraaksoort == null) {
                    AlertDialog("Kies het product waar u een aanvraag voor wilt doen");
                } else if (checkKlantLening(rekeningen)) {
                    AlertDialog("U heeft al een lening");
                } else if (checkKlantSchuld(rekeningen)) {
                    AlertDialog("U kunt geen aanvraag doen omdat u schuld heeft");
                } else if (checkAanvragen(afspraken)) {
                    AlertDialog("U heeft al een aanvraag voor een " + afspraaksoort);
                } else {
                    Log.d("AanvraagActivity", "else aangeroepen");
                    //voeg afspraak toe aan database
                    Afspraak afspraak = new Afspraak("217740078", etDatepicker.getText().toString(), spinnerTijden.getSelectedItem().toString(),
                            afspraaksoort);
                    Log.d("AanvraagActivity", "Nieuwe afspraak: " + afspraak.getDatum() + afspraak.getTijd() + afspraak.getAfspraakSoort());

                    if (afspraakRegistreerFragment.insertAfspraak(afspraak)) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext(), R.style.AlertDialogTheme);
                        builder1.setMessage("Uw aanvraag is verzonden");

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), MeerFragment.class);
                                        startActivity(intent);

                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        AlertDialog("Er is iets misgegaan");
                    }
                }
            }
        });
    }

    /**
     * Deze methode zorgt ervoor dat iedere keer als de gebruiker een nieuwe datum in de datepicker heeft
     * gekozen, er in de database gecontroleerd wordt welke tijden niet meer beschikbaar zijn voor die datum.
     *
     * @param datePicker De datepicker waar de gebruiker een datum uit kiest.
     * @param i
     * @param i1
     * @param i2
     */
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
                    AlertDialog("Er zijn geen tijden meer beschikbaar voor deze datum");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: geef klantid van ingelogde klant mee

    /**
     * Deze methode haalt de rekeningen van de ingelogde klant op om controles mee uit te voeren voor
     * een aanvraag gedaan kan worden in de methodes checkKlantLening() en checkKlantSchuld().
     *
     * @return Arraylist met de rekeningen van de klant.
     */
    private ArrayList<Rekening> haalRekeningenKlantOp() {

        Log.d("Aanvraag", "HaalRekeningenKlantOp aangeroepen");
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

    /**
     * Deze methode controleert of de ingelogde klant al een lening heeft.
     *
     * @param rekeningenKlant De lijst met rekeningen van de ingelogde klant.
     * @return true als de klant al een lening heeft, false als dit niet zo is.
     */
    private boolean checkKlantLening(ArrayList<Rekening> rekeningenKlant) {

        Log.d("Aanvraag", "CheckKlantLening aangeroepen");
        if (afspraaksoort.equals(AanvraagSoort.PERSOONLIJKE_LENING) || afspraaksoort.equals(AanvraagSoort.DOORLOPEND_KREDIET)) {
            for (Rekening rek : rekeningenKlant) {
                if (rek.getRekeningSoort().contains(AanvraagSoort.DOORLOPEND_KREDIET) || rek.getRekeningSoort().contains(AanvraagSoort.PERSOONLIJKE_LENING)) {
                    return true;

                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Deze methode controleert of een klant schuld heeft.
     *
     * @param rekeningenKlant De lijst met rekeningen van de ingelogde klant
     * @return true als de klant schuld heeft, false als dit niet zo is.
     */
    private boolean checkKlantSchuld(ArrayList<Rekening> rekeningenKlant) {

        Log.d("Aanvraag", "CheckKlantSchuld aangeroepen");
        for (Rekening rek : rekeningenKlant) {
            if (Double.toString(rek.getSaldo()).contains("-")) {
                return true;

            } else {
                return false;
            }
        }
        return false;

    }

    /**
     * Deze methode haalt alle afspraken van de klant op en filtert de afspraken uit het verleden uit de lijst.
     * Op de overgebleven afspraken wordt controle uitgevoerd in checkAanvragen() voordat de klant een aanvraag mag doen.
     *
     * @return ArrayList met afspraken die vanaf vandaag en in de toekomst liggen.
     */
    private ArrayList<String> haalAfsprakenKlantOp() {
        Log.d("Aanvraag", "HaalAfsprakenKlantOp aangeroepen");
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
                Log.d("HaalAfspraken op", "Er zijn nog geen afspraken");
                afsprakenKlant = new ArrayList<>();

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

                        afsprakenKlant.add(afspraaksoort);

                    }

                    Log.d("AanvraagAfspraken", "opgehaalde json: " + afsprakenKlant);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return afsprakenKlant;
    }

    /**
     * Deze methode checkt welke aanvragen de klant al heeft lopen. De klant mag per soort maar 1 afspraak hebben.
     *
     * @param afsprakenKlant De lijst met afspraken van de ingelogde klant.
     * @return true als de klant voor de geselecteerde soort rekening/lening al een afspraak heeft, false als dit niet zo is.
     */
    private boolean checkAanvragen(ArrayList<String> afsprakenKlant) {

        Log.d("Aanvraag", "CheckAanvragen aangeroepen");
        if (afsprakenKlant.isEmpty()) {
            return false;
        }


        switch (afspraaksoort) {
            case AanvraagSoort.BETAALREKENING:
                if (afsprakenKlant.contains(AanvraagSoort.BETAALREKENING)) {
                    return true;
                }
                break;
            case AanvraagSoort.SPAARREKENING:
                if (afsprakenKlant.contains(AanvraagSoort.SPAARREKENING)) {
                    return true;
                }
                break;
            case AanvraagSoort.DEPOSITO:
                if (afsprakenKlant.contains(AanvraagSoort.DEPOSITO)) {
                    return true;
                }
                break;
            case AanvraagSoort.PERSOONLIJKE_LENING:
                if (afsprakenKlant.contains(AanvraagSoort.PERSOONLIJKE_LENING) || afsprakenKlant.contains(AanvraagSoort.DOORLOPEND_KREDIET)) {
                    return true;
                }
                break;
            case AanvraagSoort.DOORLOPEND_KREDIET:
                if (afsprakenKlant.contains(AanvraagSoort.DOORLOPEND_KREDIET) || afsprakenKlant.contains(AanvraagSoort.PERSOONLIJKE_LENING)) {
                    return true;
                }
                break;

        }
        return false;
    }

    /**
     * Deze methode zorgt voor het maken van een AlertDialog
     *
     * @param message De tekst die in de AlertDialog weergegeven moet worden.
     */
    private void AlertDialog(String message) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder1.setMessage(message);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}


