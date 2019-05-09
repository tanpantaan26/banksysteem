package com.example.banksysteem.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import org.json.JSONArray;

public class ProfielFragment extends Fragment {
    public TextView usernameView;
    public TextView voornaamView;
    public TextView achternaamView;
    public TextView adresView;
    public TextView telefoonView;
    public TextView emailView;
    public Button editButton;
    DatabaseConnector db;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profiel_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = new DatabaseConnector();
        usernameView = getView().findViewById(R.id.usernameView);
            String sql = "select KlantID from KLANT where Voornaam = John and Achternaam = Doe";
            try {
                db.sendRequest(sql);
                Object oResult = db.get();
                String strResult = oResult.toString();
                JSONArray jResult = new JSONArray(strResult);
                usernameView.setText(""+jResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        voornaamView =  getView().findViewById(R.id.voornaamView);
            sql = "select Voornaam from KLANT where KlantID = 123456789";
            try {
                db.sendRequest(sql);
                Object oResult = db.get();
                String strResult = oResult.toString();
                JSONArray jResult = new JSONArray(strResult);
                voornaamView.setText(""+jResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        achternaamView = getView().findViewById(R.id.achternaamView);
        sql = "select Achternaam from KLANT where KlantID = 123456789";
        try {
            db.sendRequest(sql);
            Object oResult = db.get();
            String strResult = oResult.toString();
            JSONArray jResult = new JSONArray(strResult);
            achternaamView.setText(""+jResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adresView = getView().findViewById(R.id.adresView);
        sql = "select Adres from KLANT where KlantID = 123456789";
        try {
            db.sendRequest(sql);
            Object oResult = db.get();
            String strResult = oResult.toString();
            JSONArray jResult = new JSONArray(strResult);
            adresView.setText(""+jResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        telefoonView = getView().findViewById(R.id.telefoonView);
        sql = "select Telefoon from KLANT where KlantID = 123456789";
        try {
            db.sendRequest(sql);
            Object oResult = db.get();
            String strResult = oResult.toString();
            JSONArray jResult = new JSONArray(strResult);
            telefoonView.setText(""+jResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailView = getView().findViewById(R.id.emailView);
        sql = "select Email from KLANT where KlantID = 123456789";
        try {
            db.sendRequest(sql);
            Object oResult = db.get();
            String strResult = oResult.toString();
            JSONArray jResult = new JSONArray(strResult);
            emailView.setText(""+jResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editButton = getView().findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Gewijzigd",Toast.LENGTH_LONG).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

}
