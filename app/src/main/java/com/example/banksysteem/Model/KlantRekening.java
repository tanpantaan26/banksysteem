package com.example.banksysteem.Model;

public class KlantRekening {

    private String klantId;
    private String rekeningNummer;
    private String rolnaam;

    public KlantRekening(String klantId, String rekeningNummer, String rolnaam) {
        this.klantId = klantId;
        this.rekeningNummer = rekeningNummer;
        this.rolnaam = rolnaam;
    }

    public String getKlantId() {
        return klantId;
    }

    public void setKlantId(String klantId) {
        this.klantId = klantId;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public String getRolnaam() {
        return rolnaam;
    }

    public void setRolnaam(String rolnaam) {
        this.rolnaam = rolnaam;
    }
}

