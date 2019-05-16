package com.example.banksysteem.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.example.banksysteem.R;

/**
 * @author Inge
 */
public class RegistreerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);


        //fragment in framelayout zetten die als eerste getoond moet worden aan de gebruiker
        //als deze navigeert naar RegistreerActivty
        getSupportFragmentManager().beginTransaction().replace(R.id.registreeractivity_fragment_container, new GegevensRegistreerFragment()).commit();

    }
}
