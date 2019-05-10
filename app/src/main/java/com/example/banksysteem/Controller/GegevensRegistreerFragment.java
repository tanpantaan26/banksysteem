package com.example.banksysteem.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;
import com.example.banksysteem.Util.ValidateAanvraagInput;

import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String sql = "SELECT * FROM Beheer";
        try {
            db.execute(sql);
            Object oResult = db.get();
            Log.i("Registreeractivity", "sql result: " + oResult);

            String strResult = oResult.toString();
            JSONArray jResult = new JSONArray(strResult);
            Log.i("Registreeractivity", "sql result: " + oResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

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

                //check of de gebruiker een keuze heeft gemaakt tussen particulier en bedrijf
                if (radioGroup.getCheckedRadioButtonId() == -1){
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

                }

                //check of alle velden ingevuld zijn
                else if(valAvInput.emptyEdittext((ViewGroup) view.findViewById(R.id.gegevens_viewgroup))){
                    //check of email voldoet aan format aaa@bb.cc
                    if (!valAvInput.checkEmail(etMail.getText().toString())){
                        etMail.setError("Voer een geldige email in");
                    }

                    //validate naam
                    if(!valAvInput.validateLetters(etVoornaam.getText().toString())){
                        etVoornaam.setError("Voornaam mag alleen letters bevatten");
                    };
                    if(!valAvInput.validateLetters(etAchternaam.getText().toString())){
                        etAchternaam.setError("Achternaam mag alleen letters bevatten");
                    };

                    //validate bsn en kvk
                    checkKvkBsn(etBSN_KVK.getText().toString());
                    Toast.makeText(getContext(), "Alle tekstvelden zijn gevuld", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "lege velden", Toast.LENGTH_SHORT).show();
                }

                //getFragmentManager().beginTransaction().replace(R.id.registreeractivity_fragment_container, new AfspraakRegistreerFragment()).commit();
            }
        });
    }

    private void checkKvkBsn(String kvk_bsn){

        if (particulierRb.isChecked()){
            int bsn = Integer.parseInt(kvk_bsn);
            if(!valAvInput.isValidBSN(bsn)){
                etBSN_KVK.setError("Vul een geldige bsn in");
            }
        }
        if (bedrijfRb.isChecked()){
            if (kvk_bsn.length() != 8){
                etBSN_KVK.setError("Kvk moet 8 karakters lang zijn");
            }
        }

    }

}

