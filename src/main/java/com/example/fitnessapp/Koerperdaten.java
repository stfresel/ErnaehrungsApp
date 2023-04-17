package com.example.fitnessapp;

public class Koerperdaten {
    private double groesse;
    private double gewicht;
    private char geschlecht;
    private int alter;
    private int taeglicheNaehrwerte;
    private double bmi;

    public Koerperdaten(double groesse) {
        this.groesse = groesse;
    }

    public Koerperdaten(double groesse, double gewicht, char geschlecht, int alter, int taeglicheNaehrwerte) {
        this.groesse = groesse;
        this.gewicht = gewicht;
        this.geschlecht = geschlecht;
        this.alter = alter;
        this.taeglicheNaehrwerte = taeglicheNaehrwerte;
        this.bmi = gewicht/Math.sqrt(groesse);
    }

    public double getGroesse() {
        return groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public int getTaeglicheNaehrwerte() {
        return taeglicheNaehrwerte;
    }

    public double getBmi() {
        return bmi;
    }
}
