package com.example.banksysteem.Model;

/**
 * @author Inge
 */
public class Afspraak {

    private String klantId;
    private String datum;
    private String tijd;
    private String afspraakSoort;

    public Afspraak(String klantId, String datum, String tijd, String afspraakSoort) {
        this.klantId = klantId;
        this.datum = datum;
        this.tijd = tijd;
        this.afspraakSoort = afspraakSoort;
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

    public String getAfspraakSoort() {
        return afspraakSoort;
    }

    public void setAfspraakSoort(String afspraakSoort) {
        this.afspraakSoort = afspraakSoort;
    }
}

