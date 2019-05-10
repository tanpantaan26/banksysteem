package com.example.banksysteem.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import org.json.JSONArray;

import java.io.IOException;

public class RegistreerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);

        //fragment in framelayout zetten die als eerste getoond moet worden aan de gebruiker
        //als deze navigeert naar RegistreerActivty
        getSupportFragmentManager().beginTransaction().replace(R.id.registreeractivity_fragment_container, new GegevensRegistreerFragment()).commit();

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
}
