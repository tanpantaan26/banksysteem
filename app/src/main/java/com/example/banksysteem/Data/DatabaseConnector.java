package com.example.banksysteem.Data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class DatabaseConnector extends AsyncTask {

    private String host = "http://biginterest.vorrink.net";

    /*
    Default method to directly send sql statements to the webservice

    Usage:
    DatabaseConnector db = new DatabaseConnector();
    String sql = "select * from klant";
    String result = db.sendRequest(sql);
     */
    public String sendRequest(String sql) throws IOException {
        URL url = new URL(host);
        HttpURLConnection client = (HttpURLConnection) url.openConnection();
        client.setDoOutput(true);
        client.setDoInput(true);
        client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        client.setRequestMethod("POST");

        client.connect();

        JSONObject request = new JSONObject();

        request.put("Request", "SQL");
        request.put("SQL", sql);

        OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
        String output = request.toString();
        writer.write(output);
        writer.flush();
        writer.close();

        InputStream input = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        client.disconnect();

        Log.i("DatabaseConnector", "Sql result: " +  result);


        return result.toString();
    }

    /*
    Default requestLogin method to check the database for username and password.
    And a third parameter for login in as a 'Gebruiker' instead of a 'Beheer' as is default on this application
    True for Gebruiker
    False for Beheer

    Usage:
    DatabaseConnector db = new DatabaseConnector;
    boolean isGebruiker = true;
    boolean result = db.requestLogin("admin", "admin", isGebruiker);
     */
    public boolean requestLogin(String Gebruikersnaam, String Wachtwoord, boolean isGebruiker) throws IOException {
        URL url = new URL(host);
        HttpURLConnection client = (HttpURLConnection) url.openConnection();
        client.setDoOutput(true);
        client.setDoInput(true);
        client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        client.setRequestMethod("POST");

        client.connect();

        JSONObject request = new JSONObject();

        request.put("Request", "Login");
        request.put("Gebruikersnaam", Gebruikersnaam);
        request.put("Wachtwoord", Wachtwoord);
        request.put("isGebruiker", isGebruiker);

        OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
        String output = request.toString();
        writer.write(output);
        writer.flush();
        writer.close();

        InputStream input = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        client.disconnect();

        String strResult = result.toString().replace("\"", "");
        System.out.println(strResult);
        if(strResult.equals("msg:login:succes"))
            return true;
        else
            return false;
    }

    /*
    Default requestLogin method to check the database for username and password.

    Usage:
    DatabaseConnector db = new DatabaseConnector;
    boolean result = db.requestLogin("admin", "admin");
     */
    public boolean requestLogin(String Gebruikersnaam, String Wachtwoord){
        try {
            return requestLogin(Gebruikersnaam, Wachtwoord, false);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            return sendRequest(objects[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

