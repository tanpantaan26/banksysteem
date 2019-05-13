package com.example.banksysteem.Controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.Model.Klant;
import com.example.banksysteem.R;
import com.example.banksysteem.Util.ValidateAanvraagInput;


public class GegevensRegistreerFragment extends Fragment {

    private EditText etVoornaam, etAchternaam, etAdres, etMail, etTelefoon,
            etBSN_KVK, etBedrijfsnaam;
    private DatabaseConnector db = new DatabaseConnector();
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


        particulierRb = view.findViewById(R.id.gegevens_registreer_rbParticulier);
        bedrijfRb = view.findViewById(R.id.gegevens_registreer_rbBedrijf);
        final RadioGroup radioGroup = view.findViewById(R.id.gegevens_registreer_radiogroup);

        //Check welke radiobutton is geselecteerd en plaats de juiste hint teksten en kvk/bsn
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (particulierRb.isChecked()) {

                    etBedrijfsnaam.setVisibility(View.INVISIBLE);

                    etBSN_KVK.setHint("BSN");
                    etAdres.setHint("Adres");
                    etTelefoon.setHint("Telefoonnummer");
                    etMail.setHint("Email");

                    valAvInput.clearEdittext((ViewGroup) view.findViewById(R.id.gegevens_viewgroup));
                    etBedrijfsnaam.setText("1");
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
        Button btnAanvraagVersturen = view.findViewById(R.id.gegevens_registreer_btnVerstuurAanvraag);
        btnAanvraagVersturen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check of de gebruiker een keuze heeft gemaakt tussen particulier en bedrijf,
                // zo niet geef dan een melding
                if (radioGroup.getCheckedRadioButtonId() == -1) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
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
                    Toast.makeText(getContext(), "Vul alle velden in", Toast.LENGTH_LONG).show();
                } else if (!valAvInput.checkEmail(etMail.getText().toString())) {
                    etMail.setError("Vul een geldige mail in");
                } else if (!valAvInput.validateLetters(etVoornaam.getText().toString())) {
                    etVoornaam.setError("Voornaam mag alleen letters bevatten");
                } else if (!valAvInput.validateLetters(etAchternaam.getText().toString())) {
                    etAchternaam.setError("Achternaam mag alleen letters bevatten");
                } /*else if (!checkKvkBsn(etBSN_KVK.getText().toString())) {
                    if (particulierRb.isChecked()) {
                        etBSN_KVK.setError("Vul een geldige bsn in");

                    }
                    if (bedrijfRb.isChecked()) {
                        etBSN_KVK.setError("Kvk moet 8 tekens lang zijn");

                    }
                }*/ else if (!isAlKlant(etBSN_KVK.getText().toString())) {

                } else {

                    //ingevulde waarden ophalen en in klant object zetten om door te sturen naar afspraak scherm
                    String klantId = etBSN_KVK.getText().toString();
                    String voornaam = etVoornaam.getText().toString();
                    String achternaam = etAchternaam.getText().toString();
                    String telefoonnummer = etTelefoon.getText().toString();
                    String email = etMail.getText().toString();
                    String adres = etAdres.getText().toString();
                    String bedrijfsnaam = etBedrijfsnaam.toString();

                    if (particulierRb.isChecked()) {
                        bedrijfsnaam = "";
                    }

                    Klant klant = new Klant(klantId, voornaam, achternaam, telefoonnummer, email, adres, bedrijfsnaam);

                    Log.d("GegevensRegistreer", "klant: " + klant.getKlantId());

                    String sql = "SELECT * FROM ZwarteLijst WHERE KlantklantID = '" + klantId + "';";
                    try {
                        DatabaseConnector db2 = new DatabaseConnector();
                        db2.execute(sql);
                        Object oResult = db.get();

                        String strResult = oResult.toString();
                        strResult = strResult.replace("\"", "");
                        Log.d("Registreeractivity", "strResult: " + strResult);

                        if (strResult.equals("msg:select:empty")) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                            Bundle klantGegevens = new Bundle();
                            klantGegevens.putSerializable("klantGegevens", klant);
                            AfspraakRegistreerFragment afspraakFragment = new AfspraakRegistreerFragment();
                            afspraakFragment.setArguments(klantGegevens);
                            fragmentManager.beginTransaction().replace(R.id.registreeractivity_fragment_container, afspraakFragment).commit();

                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                            builder1.setMessage("U bent afgewezen om klant te worden bij deze bank");

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        });
    }

    //check of bsn voldoet aan 11 proef en of kvk niet langer is dan 8 tekens
    private boolean checkKvkBsn(String kvk_bsn) {

        if (particulierRb.isChecked()) {
            int bsn = Integer.parseInt(kvk_bsn);
            if (!valAvInput.isValidBSN(bsn)) {
                return false;
            }
        }
        if (bedrijfRb.isChecked()) {
            if (kvk_bsn.length() != 8) {
                return false;
            }
        }
        return true;
    }


    private boolean isAlKlant(String klantId) {


        String sql = "SELECT * FROM Klant WHERE Klantid = '" + klantId + "';";
        String strResult = "";

        try {

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
            Toast.makeText(getContext(), "U bent al bekend bij de bank", Toast.LENGTH_LONG).show();
            return false;
        }


    }

}