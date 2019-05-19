package com.example.banksysteem.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

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
                }
                else if (TextUtils.isEmpty(wachtwoord)) {
                    etWachtwoord.setError("Vul uw wachtwoord in");
                }
                else {

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
}
