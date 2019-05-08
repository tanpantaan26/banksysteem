package com.example.banksysteem.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banksysteem.R;

public class ProfielFragment extends Fragment {
    TextView usernameView;
    TextView voornaamView;
    TextView achternaamView;
    TextView adresView;
    TextView telefoonView;
    TextView emailView;
    Button editButton;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profiel_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        usernameView = getView().findViewById(R.id.usernameView);
        voornaamView =  getView().findViewById(R.id.voornaamView);
        achternaamView = getView().findViewById(R.id.achternaamView);
        adresView = getView().findViewById(R.id.adresView);
        telefoonView = getView().findViewById(R.id.telefoonView);
        emailView = getView().findViewById(R.id.emailView);
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
