package com.example.banksysteem.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.Model.Klant;
import com.example.banksysteem.R;
import com.example.banksysteem.Util.ValidateAanvraagInput;

/**
 * Klasse waarin de gebruiker zijn gegevens voor een registratie in kan vullen. Deze gegevens worden
 * vervolgens gecontroleerd en als alles goed is dan wordt de gebruiker doorgestuurd naar het scherm
 * om een afspraak te maken bij de bank om nieuwe klant te worden.
 *
 * @author Inge
 * @version 1
 * @see AfspraakRegistreerFragment
 * @see ValidateAanvraagInput
 */
public class GegevensRegistreerFragment extends Fragment {

    private EditText etVoornaam, etAchternaam, etAdres, etMail, etTelefoon,
            etBSN_KVK, etBedrijfsnaam;
    private RadioButton particulierRb, bedrijfRb;
    private ValidateAanvraagInput valAvInput = new ValidateAanvraagInput();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gegevens_registreer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //invoervelden ophalen
        etVoornaam = view.findViewById(R.id.gegevens_registreer_etVoornaam);
        etAchternaam = view.findViewById(R.id.gegevens_registreer_etAchternaam);
        etAdres = view.findViewById(R.id.gegevens_registreer_etAdres);
        etMail = view.findViewById(R.id.gegevens_registreer_etEmail);
        etTelefoon = view.findViewById(R.id.gegevens_registreer_etTelefoonnummer);
        etBSN_KVK = view.findViewById(R.id.gegevens_registreer_etBSN_KVK);
        etBedrijfsnaam = view.findViewById(R.id.gegevens_registreer_etBedrijfsnaam);

        //radiobuttons ophalen
        particulierRb = view.findViewById(R.id.gegevens_registreer_rbParticulier);
        bedrijfRb = view.findViewById(R.id.gegevens_registreer_rbBedrijf);
        final RadioGroup radioGroup = view.findViewById(R.id.gegevens_registreer_radiogroup);

        //Check welke radiobutton is geselecteerd en plaats de juiste hint teksten en tekstvelden
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (particulierRb.isChecked()) {

                    etBedrijfsnaam.setVisibility(View.INVISIBLE);

                    etAdres.setHint("Adres");
                    etTelefoon.setHint("Telefoonnummer");
                    etMail.setHint("Email");
                    etBSN_KVK.setHint("BSN");

                    valAvInput.clearEdittext((ViewGroup) view.findViewById(R.id.gegevens_viewgroup));
                    etBedrijfsnaam.setText("b1");
                }
                if (bedrijfRb.isChecked()) {

                    etBedrijfsnaam.setVisibility(View.VISIBLE);
                    etBSN_KVK.setHint("Kvk nummer");
                    etAdres.setHint("Adres (Bedrijf)");
                    etTelefoon.setHint("Telefoonnummer (Bedrijf)");
                    etMail.setHint("Email (Bedrijf)");


                    valAvInput.clearEdittext((ViewGroup) view.findViewById(R.id.gegevens_viewgroup));
                }
            }
        });

        //button aanvraag versturen
        ImageButton btnAanvraagVersturen = view.findViewById(R.id.gegevens_registreer_btnVerstuurAanvraag);
        btnAanvraagVersturen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check of de gebruiker een keuze heeft gemaakt tussen particulier en bedrijf,
                // zo niet geef dan een melding
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext(), R.style.AlertDialogTheme);
                    builder1.setMessage("Maak een keuze voor particulier of bedrijf");

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                } else if (!valAvInput.emptyEdittext((ViewGroup) view.findViewById(R.id.gegevens_viewgroup))) {

                } else if (!valAvInput.checkEmail(etMail.getText().toString())) {
                    etMail.setError("Vul een geldige email in");
                } else if (!valAvInput.checkAdres(etAdres.getText().toString())) {
                    etAdres.setError("Vul een geldig adres in");
                } else if (!valAvInput.checkAlphaNumeric(etBedrijfsnaam.getText().toString())) {
                    etBedrijfsnaam.setError("Vul een geldige bedrijfsnaam in");
                } else if (!valAvInput.checkTelefoonnummer(etTelefoon.getText().toString())) {
                    etTelefoon.setError("Vul een geldig telefoonnummer in");
                } else if (!valAvInput.validateLetters(etVoornaam.getText().toString())) {
                    etVoornaam.setError("Voornaam mag alleen letters bevatten");
                } else if (!valAvInput.validateLetters(etAchternaam.getText().toString())) {
                    etAchternaam.setError("Achternaam mag alleen letters bevatten");
                } else if (!valAvInput.checkKvkBsn(etBSN_KVK.getText().toString(), particulierRb, bedrijfRb)) {
                    if (particulierRb.isChecked()) {
                        etBSN_KVK.setError("Vul een geldige bsn in");

                    }
                    if (bedrijfRb.isChecked()) {
                        etBSN_KVK.setError("Kvk moet 8 tekens lang zijn");

                    }
                } else if (!staatOpZwarteLijst(etBSN_KVK.getText().toString())) {

                } else if (!isAlKlant(etBSN_KVK.getText().toString())) {

                } else {

                    //ingevulde waarden ophalen en in klant object zetten om door te sturen naar afspraak scherm
                    String klantId = etBSN_KVK.getText().toString();
                    String voornaam = etVoornaam.getText().toString();
                    String achternaam = etAchternaam.getText().toString();
                    String telefoonnummer = etTelefoon.getText().toString();
                    String email = etMail.getText().toString();
                    String adres = etAdres.getText().toString();
                    String bedrijfsnaam = etBedrijfsnaam.getText().toString();

                    if (particulierRb.isChecked()) {
                        bedrijfsnaam = "";
                    }

                    Klant klant = new Klant(klantId, voornaam, achternaam, telefoonnummer, email, adres, bedrijfsnaam);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Bundle klantGegevens = new Bundle();
                    klantGegevens.putSerializable("klantGegevens", klant);
                    AfspraakRegistreerFragment afspraakFragment = new AfspraakRegistreerFragment();
                    afspraakFragment.setArguments(klantGegevens);
                    fragmentManager.beginTransaction().replace(R.id.registreeractivity_fragment_container, afspraakFragment).commit();

                    Log.d("GegevensRegistreer", "klant: " + klant.getKlantId());


                }

            }

        });
    }


    /**
     * Deze methode checkt of de klant al bestaat in de database. Als de klant al bestaat hoeft deze niet
     * nog een keer te registreren.
     *
     * @param klantId De bsn of kvk nummer die bij de registratie gegevens ingevuld wordt. Dit is namelijk de
     *                primary key in de tabel klant.
     * @return boolean true of false. Als de klant nog niet bestaat in de database, dan is de return waarde true en
     * als de klant al wel bestaat false.
     */
    private boolean isAlKlant(String klantId) {


        String sql = "SELECT * FROM Klant WHERE Klantid = '" + klantId + "';";
        String strResult = "";

        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            strResult = oResult.toString();
            strResult = strResult.replace("\"", "");

            Log.d("isAlKlant", "strResult: " + strResult);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strResult.equals("msg:select:empty")) {
            return true;
        } else {
            AlertDialog("U bent al bekend bij de bank");
            return false;
        }


    }

    private boolean staatOpZwarteLijst(String klantId) {
        String sql = "SELECT * FROM ZwarteLijst WHERE KlantklantID = '" + klantId + "';";
        String strResult = "";
        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            strResult = oResult.toString();
            strResult = strResult.replace("\"", "");

            Log.d("isAlKlant", "strResult: " + strResult);


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (strResult.equals("msg:select:empty")) {
            return true;
        } else {
            AlertDialog("U bent afgewezen om een aanvraag te doen");
            return false;
        }

    }

    /**
     * Deze methode zorgt voor het maken van een AlertDialog die ervoor zorgt dat de klant teruggestuurd wordt
     * naar het login scherm als de klant geen registratie mag doen.
     *
     * @param message De tekst die in de AlertDialog weergegeven moet worden.
     */
    private void AlertDialog(String message) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder1.setMessage(message);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}