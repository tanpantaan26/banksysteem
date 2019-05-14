package com.example.banksysteem.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    JSONArray jResult;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profiel_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = new DatabaseConnector();
        String sql = "select * from klant";
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
                usernameView.setText(jResult.getString(0));
                Log.i("e", jResult.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        voornaamView =  getView().findViewById(R.id.voornaamView);
            try {
                voornaamView.setText(jResult.getString(0));
                Log.i("e", jResult.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        achternaamView = getView().findViewById(R.id.achternaamView);
        try {
            achternaamView.setText(jResult.getString(0));
            Log.i("e", jResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        adresView = getView().findViewById(R.id.adresView);
        try {
            adresView.setText(jResult.getString(0));
            Log.i("e", jResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        telefoonView = getView().findViewById(R.id.telefoonView);
        try {
            telefoonView.setText(jResult.getString(0));
            Log.i("e", jResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailView = getView().findViewById(R.id.emailView);
        try {
            emailView.setText(jResult.getString(0));
            Log.i("e", jResult.toString());
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
