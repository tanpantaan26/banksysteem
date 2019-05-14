package com.example.banksysteem.Model;

public class Afspraak {

    private String klantId;
    private String datum;
    private String tijd;

    public Afspraak(String klantId, String datum, String tijd) {
        this.klantId = klantId;
        this.datum = datum;
        this.tijd = tijd;
    }

    public String getKlantId() {
        return klantId;
    }

    public void setKlantId(String klantId) {
        this.klantId = klantId;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }
}

