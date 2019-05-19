package com.example.banksysteem.Controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.banksysteem.Data.DatabaseConnector;
import com.example.banksysteem.R;

import java.util.concurrent.ExecutionException;

public class BoekingFragment extends Fragment {
    public Button verzend;
    public Double saldoZend;
    public DatabaseConnector dbz;
    public DatabaseConnector dbo;
    public DatabaseConnector dbv;
    public Object oResult;
    public Object zResult;
    public String sql;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.boeking_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        verzend = getView().findViewById(R.id.verzend);
        verzend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbz = new DatabaseConnector();
                sql = "SELECT Saldo FROM Rekening WHERE Rekeningnummer = '2147483647'";
                dbz.execute(sql);
                try {
                    zResult = dbz.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                saldoZend = Double.valueOf(zResult.toString());

                Editable ontvangerInput = getView().findViewById(R.id.saldoInput);
                String ontvanger = ontvangerInput.toString();

                dbo = new DatabaseConnector();
                sql = "SELECT Saldo FROM Rekening WHERE Rekeningnummer = '"+ontvanger+"'";
                dbo.execute(sql);
                try {
                    oResult = dbo.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int o1StrResult = Integer.parseInt(oResult.toString());
                Editable saldoInput = getView().findViewById(R.id.saldoInput);
                int saldoUit = Integer.parseInt(saldoInput.toString());
                int saldoZend = o1StrResult + saldoUit;
                dbv = new DatabaseConnector();
                sql = "UPATE Rekening SET Saldo = '"+saldoZend+"' WHERE Rekeningnummer = '87676767'";
                dbv.execute(sql);

                ontvangerInput = getView().findViewById(R.id.ontvangerInput);
                ontvanger = ontvangerInput.toString();

                dbo = new DatabaseConnector();
                sql = "SELECT Saldo FROM Rekening WHERE Rekeningnummer = '"+ontvanger+"'";
                dbo.execute(sql);
                try {
                    oResult = dbo.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                o1StrResult = Integer.parseInt(oResult.toString());
                saldoInput = getView().findViewById(R.id.saldoInput);
                saldoUit = Integer.parseInt(saldoInput.toString());
                saldoZend = o1StrResult + saldoUit;
                dbv = new DatabaseConnector();
                sql = "UPATE Rekening SET Saldo = '"+saldoZend+"' WHERE Rekeningnummer = '"+ontvanger+"'";
                dbv.execute(sql);
                Toast.makeText(getActivity().getApplicationContext(), "Verstuurd", Toast.LENGTH_LONG).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}