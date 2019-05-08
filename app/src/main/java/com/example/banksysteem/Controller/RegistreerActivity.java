package com.example.banksysteem.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import java.io.IOException;

public class RegistreerActivity extends AppCompatActivity {

    private EditText etVoornaam, etAchternaam, etAdres, etMail, etTelefoon,
            etBSN_KVK, etBedrijfsnaam;
    private DatabaseConnector db = new DatabaseConnector();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);

        String sql = "SELECT * FROM Beheer";
        try {
            db.sendRequest(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //invoervelden ophalen
        etVoornaam = findViewById(R.id.registreeractivity_etVoornaam);
        etAchternaam = findViewById(R.id.registreeractivity_etAchternaam);
        etAdres = findViewById(R.id.registreeractivity_etAdres);
        etMail = findViewById(R.id.registreeractivity_etEmail);
        etTelefoon = findViewById(R.id.registreeractivity_etTelefoonnummer);
        etBSN_KVK = findViewById(R.id.registreeractivity_etBSN_KVK);
        etBedrijfsnaam = findViewById(R.id.registreeractivity_etBedrijfsnaam);


        final RadioButton particulierRb = findViewById(R.id.registreeractivity_rbParticulier);
        final RadioButton bedrijfRb = findViewById(R.id.registreeractivity_rbBedrijf);
        RadioGroup radioGroup = findViewById(R.id.registreeractivity_radiogroup);

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

                    clearEdittext((ViewGroup) findViewById(R.id.registreerViewgroup));
                }
                if (bedrijfRb.isChecked()) {

                    etBedrijfsnaam.setVisibility(View.VISIBLE);
                    etBSN_KVK.setHint("Kvk nummer");
                    etAdres.setHint("Adres (Bedrijf)");
                    etTelefoon.setHint("Telefoonnummer (Bedrijf)");
                    etMail.setHint("Email (Bedrijf)");

                    clearEdittext((ViewGroup) findViewById(R.id.registreerViewgroup));
                }
            }
        });


        //button terug naar login scherm
        ImageButton btnTerug = findViewById(R.id.registreeractivity_btnTerug);
        btnTerug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    //methode om alle invoervelden van het type Edittext in dit scherm leeg te maken
    public void clearEdittext(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }
}
