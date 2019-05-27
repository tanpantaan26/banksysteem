package com.example.banksysteem.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfielFragment extends Fragment {
    public EditText usernameView;
    public EditText voornaamView;
    public EditText achternaamView;
    public EditText adresView;
    public EditText telefoonView;
    public EditText emailView;
    public Button editButton;
    DatabaseConnector db;
    DatabaseConnector dat;
    JSONArray jResult;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profiel_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = new DatabaseConnector();
        String sql = "select * from Klant";
        try {
            db.execute(sql);
            Object oResult = db.get();
            String strResult = oResult.toString();
            jResult = new JSONArray(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        usernameView = getView().findViewById(R.id.usernameView);
            try {
                for (int i = 0; i < jResult.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jResult.get(i);
                        String userName = (String) jsonObject.get("Gebruikersnaam");
                        usernameView.setText(""+ userName);

                        Log.i("e", jResult.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                }catch (Exception e) {
                    e.printStackTrace();}

        voornaamView =  getView().findViewById(R.id.voornaamView);
            try {
                for (int i = 0; i < jResult.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jResult.get(i);
                        String firstName = (String) jsonObject.get("Voornaam");
                        voornaamView.setText(""+ firstName);

                        Log.i("e", jResult.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();}

        achternaamView =  getView().findViewById(R.id.achternaamView);
        try {
            for (int i = 0; i < jResult.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jResult.get(i);
                    String lastName = (String) jsonObject.get("Achternaam");
                    achternaamView.setText(""+ lastName);

                    Log.i("e", jResult.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();}

        adresView = getView().findViewById(R.id.adresView);
        try {
            for (int i = 0; i < jResult.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jResult.get(i);
                    String adres = (String) jsonObject.get("Adres");
                    adresView.setText(""+ adres);

                    Log.i("e", jResult.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        telefoonView = getView().findViewById(R.id.telefoonView);
        try {
            for (int i = 0; i < jResult.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jResult.get(i);
                    String telefoon = (String) jsonObject.get("Telefoon");
                    telefoonView.setText(""+ telefoon);

                    Log.i("e", jResult.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();}

        emailView = getView().findViewById(R.id.emailView);
        try {
            for (int i = 0; i < jResult.length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jResult.get(i);
                    String email = (String) jsonObject.get("Email");
                    emailView.setText(""+ email);

                    Log.i("e", jResult.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        editButton = getView().findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dat = new DatabaseConnector();
                String user = String.valueOf(usernameView.getText());
                Editable voornaamInput = voornaamView.getText();
                Editable achternaamInput = achternaamView.getText();
                Editable adresInput = adresView.getText();
                Editable telefoonInput = telefoonView.getText();
                Editable emailInput = emailView.getText();
                String sql= "UPDATE Klant SET Voornaam = '"+voornaamInput+"', Achternaam = '"+achternaamInput+"', Adres = '"+adresInput+"', Telefoon = '"+telefoonInput+"', Email = '"+emailInput+"' WHERE Gebruikersnaam = '"+user+"'";
                dat.execute(sql);
                Toast.makeText(getActivity().getApplicationContext(),"Gewijzigd",Toast.LENGTH_LONG).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

}
