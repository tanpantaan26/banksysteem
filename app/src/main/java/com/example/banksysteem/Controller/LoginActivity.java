package com.example.banksysteem.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;

import com.example.banksysteem.R;

import java.util.concurrent.ExecutionException;


/**
 * Deze klasse zorgt ervoor dat de klant kan inloggen of kan navigeren naar het registreerscherm of infoscherm
 *
 * @author Inge
 * @version 1
 * @see InfoActivity
 * @see RegistreerActivity
 */


public class LoginActivity extends AppCompatActivity {

    private DatabaseConnector db = new DatabaseConnector();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        //login button
        ImageButton btnLogin = findViewById(R.id.loginactivity_BtnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gebruikersnaam en wachtwoord ophalen
                final EditText etGebruikersnaam = findViewById(R.id.loginactivity_EtGebruikersnaam);
                EditText etWachtwoord = findViewById(R.id.loginactivity_EtWachtwoord);

                final String gebruikersnaam = etGebruikersnaam.getText().toString();
                String wachtwoord = etWachtwoord.getText().toString();

                //check invoer gebruikersnaam en wachtwoord
                if (TextUtils.isEmpty(gebruikersnaam)) {
                    etGebruikersnaam.setError("Vul uw gebruikersnaam in");
                } else if (TextUtils.isEmpty(wachtwoord)) {
                    etWachtwoord.setError("Vul uw wachtwoord in");
                } else {

                    if(gebruikergoedkeuring(etGebruikersnaam.getText().toString(), etWachtwoord.getText().toString())){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Gebruikersnaam", gebruikersnaam);
                        intent.putExtra("Wachtwoord", wachtwoord);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "U kunt niet inloggen, neem contact op met de bank.", Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });


        //button registreren
        Button btnRegistreren = findViewById(R.id.loginactivity_BtnRegistreren);
        btnRegistreren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RegistreerActivity.class);
                startActivity(intent);

            }
        });

        //meer info tekstview
        TextView tvInfo = findViewById(R.id.loginactivity_TvInfo);
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);

            }
        });

    }

    //Check gebruiker op goedkeuring

    private boolean gebruikergoedkeuring(String Gebruikersnaam, String wachtwoord) {

        final String sql = "Select * From Klant Where Gebruikersnaam = '" + Gebruikersnaam + "' AND " +
                "Wachtwoord = '" + wachtwoord + "' AND " + "isGoedgekeurd = 1";
        Log.d("Gebruikersnaam", "SQL statement: " + sql);
        try {
            DatabaseConnector db = new DatabaseConnector();
            db.execute(sql);
            Object oResult = db.get();

            String strResult = oResult.toString();
            String strResultReplace = strResult.replace("\"", "");
            Log.d("Gebruikersnaam", "strResult: " + strResultReplace);

            if (!strResult.contains("msg:select:empty")) {
                Toast.makeText(getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
