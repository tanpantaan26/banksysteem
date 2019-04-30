package com.example.banksysteem;

import java.util.Date;

public class Aanvraag {

    String voornaam;
    String achternaam;
    String telefoonnummer;
    String adres;
    String bsn;
    String eMail;
    String kvkNummer;
    Date datumAfspraak;

    public Aanvraag() {
        voornaam = new String();
        achternaam = new String();
        telefoonnummer = new String();
        adres = new String();
        bsn = new String();
        eMail = new String();
        kvkNummer = new String();
        datumAfspraak = new Date();
        }

}
