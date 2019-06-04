package com.example.banksysteem.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
<<<<<<< HEAD
import com.example.banksysteem.Model.Afspraak;
import com.example.banksysteem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
=======
import com.example.banksysteem.R;

<<<<<<< HEAD
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
>>>>>>> eccdff8f5054cfbefeabe6e0c403e80eaf0448ae
=======

/**
 * Deze klasse zorgt ervoor dat de klant kan inloggen of kan navigeren naar het registreerscherm of infoscherm
 * @author Inge
 * @version 1
 * @see InfoActivity
 * @see RegistreerActivity
 */
>>>>>>> c97e84f4fdd5b9451a5fe7f616f57ec8dae2cdff

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

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                DatabaseConnector db = new DatabaseConnector();
                try {
                    String SQL = "Select * from Beheer";
                    db.execute(SQL);
                    Object oResult = db.get();
                    System.out.println(oResult);
                    Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(oResult.toString()), Toast.LENGTH_LONG);
                    toast.show();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
            }
        });

                //Check gebruiker op goedkeuring

                private void gebruikergoedkeuring(Gebruikergoedkeuring) {

                final String sql = "Select * From Klant Where Gebruikersnaam = '  ', Wachtwoord =' ', isGoedgekeurd='1'";
                Log.d("Gebruikersnaam", "SQL statement: " + sql);
                try {
                DatabaseConnector db = new DatabaseConnector();
                db.execute(sql);
                Object oResult = db.get();

                String strResult = oResult.toString();
                String strResultReplace = strResult.replace("\"", "");
                Log.d("Gebruikersnaam", "strResult: " + strResultReplace);

                if (strResult.equals("msg:insert:succes")) {
                        Toast.makeText(getContext(), "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(getContext(), "U kunt niet inloggen, neem contact op met de bank.", Toast.LENGTH_SHORT).show();
                }


        //Launch Mainactivity screen when Login Button is clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

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

    private void getContext() {
    }

    private void getContext() {
    }

    public void getContext() {
        return context;
    }