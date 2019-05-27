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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class BoekingFragment extends Fragment {
    public Button verzend;
    public Double saldoZend;
    public Double saldoOntvang;
    public Double saldoUit;
    public DatabaseConnector dbz;
    public DatabaseConnector dbo;
    public DatabaseConnector dbv;
    public DatabaseConnector dbs;
    public Object zResult;
    public Object oResult;
    public String sql;
    public String jsonSaldoZend;
    public String jsonSaldoOntvang;
    JSONArray jResult;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.boeking_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        verzend = getView().findViewById(R.id.verzend);
        verzend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText saldoInput = getView().findViewById(R.id.saldoInput);
                saldoUit = Double.valueOf(saldoInput.getText().toString());

                EditText ontvangerInput = getView().findViewById(R.id.ontvangerInput);
                String ontvanger = ontvangerInput.toString();

                dbz = new DatabaseConnector();
                sql = "SELECT Saldo FROM Rekening WHERE Rekeningnummer = '123456789'";
                dbz.execute(sql);
                try {
                    zResult = dbz.get();
                    String strResult = zResult.toString();
                    jResult = new JSONArray(strResult);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jResult.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jResult.get(i);
                        jsonSaldoZend = (String) jsonObject.get("Saldo");

                        Log.i("e", jResult.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                saldoZend = Double.valueOf(jsonSaldoZend);

                Double saldoZendNieuw = saldoZend - saldoUit;
                dbv = new DatabaseConnector();
                sql = "UPATE Rekening SET Saldo = '"+saldoZendNieuw+"' WHERE Rekeningnummer = '123456789'";
                dbv.execute(sql);


                dbo = new DatabaseConnector();
                sql = "SELECT Saldo FROM Rekening WHERE Rekeningnummer = '"+ontvanger+"'";
                dbo.execute(sql);
                try {
                    oResult = dbo.get();
                    String strOResult = oResult.toString();
                    jResult = new JSONArray(strOResult);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jResult.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) jResult.get(i);
                        jsonSaldoOntvang = (String) jsonObject.get("Saldo");

                        Log.i("e", jResult.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                saldoOntvang = Double.valueOf(jsonSaldoOntvang);

                Double saldoOntvangNieuw = saldoOntvang + saldoUit;
                dbs = new DatabaseConnector();
                sql = "UPATE Rekening SET Saldo = '"+saldoOntvangNieuw+"' WHERE Rekeningnummer = '"+ontvanger+"'";
                dbs.execute(sql);

                Toast.makeText(getActivity().getApplicationContext(), "Verstuurd", Toast.LENGTH_LONG).show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}