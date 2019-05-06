package com.example.banksysteem.Model;

public class Rekening {

    private String rekeningNummer;
    private String rekeningSoort;
    private double rente;
    private double saldo;

    public Rekening(String rekeningNummer, String rekeningSoort, double rente, double saldo) {
        this.rekeningNummer = rekeningNummer;
        this.rekeningSoort = rekeningSoort;
        this.rente = rente;
        this.saldo = saldo;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public String getRekeningSoort() {
        return rekeningSoort;
    }

    public void setRekeningSoort(String rekeningSoort) {
        this.rekeningSoort = rekeningSoort;
    }

    public double getRente() {
        return rente;
    }

    public void setRente(double rente) {
        this.rente = rente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
