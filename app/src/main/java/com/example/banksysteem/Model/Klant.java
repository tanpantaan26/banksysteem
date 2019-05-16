package com.example.banksysteem.Model;

import java.io.Serializable;

/**
 * @author Inge
 */
public class Klant implements Serializable {

    private String klantId;
    private String voornaam;
    private String achternaam;
    private String telefoonnummer;
    private String email;
    private String adres;
    private String gebruikersnaam;
    private String wachtwoord;
    private String bedrijfsnaam;

    public Klant(String klantId, String voornaam, String achternaam, String telefoonnummer,
                 String email, String adres, String gebruikersnaam, String wachtwoord) {
        this.klantId = klantId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoonnummer = telefoonnummer;
        this.email = email;
        this.adres = adres;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
    }

    public Klant(String klantId, String voornaam, String achternaam, String telefoonnummer, String email, String adres) {
        this.klantId = klantId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoonnummer = telefoonnummer;
        this.email = email;
        this.adres = adres;
    }

    public Klant(String klantId, String voornaam, String achternaam, String telefoonnummer, String email, String adres, String bedrijfsnaam) {
        this.klantId = klantId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoonnummer = telefoonnummer;
        this.email = email;
        this.adres = adres;
        this.bedrijfsnaam = bedrijfsnaam;
    }

    public String getKlantId() {
        return klantId;
    }

    public void setKlantId(String klantId) {
        this.klantId = klantId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getBedrijfsnaam() {
        return bedrijfsnaam;
    }

    public void setBedrijfsnaam(String bedrijfsnaam) {
        this.bedrijfsnaam = bedrijfsnaam;
    }
}