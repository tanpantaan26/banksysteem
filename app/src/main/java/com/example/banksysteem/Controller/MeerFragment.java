package com.example.banksysteem.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.banksysteem.R;

/**
 * Deze klasse bevat een navigatie dmv cardviews
 * @author Inge
 * @version 1
 */
public class MeerFragment extends Fragment {

    private String gebruikersnaam;
    private String wachtwoord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meer_fragment, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gebruikersnaam = getArguments().getString("Gebruikersnaam");
        wachtwoord = getArguments().getString("Wachtwoord");

        //menu optie om leningen/rekeningen en deposito's aan te vragen
        CardView cvAanvraagLening = view.findViewById(R.id.meerfragment_cvAanvraag);
        cvAanvraagLening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AanvraagActivity.class);
                intent.putExtra("Gebruikersnaam", gebruikersnaam);
                intent.putExtra("Wachtwoord", wachtwoord);
                startActivity(intent);
            }
        });

    }
}
