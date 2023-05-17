package com.example.fitnessapp;

import java.io.Serializable;

public class Koerperdaten implements Serializable {
    private double groesse; // in m
    private double gewicht; // in kg
    private String geschlecht;
    private double alter;
    private Naehrwerte tagesUmsatz = new Naehrwerte(0,0,0,0);
    private double bmi;

    /**
     * Berechnet den BMI einer Person anhand der Groesse und des Gewichtes
     */


   public void tagesUmsatzBerechnen(){
       // Kalorien
       switch (geschlecht) {
           case "weiblich" ->
                   tagesUmsatz.setKcal((int) (655 + (9.6 * gewicht) + (1.8 * groesse * 100) - (4.7 * alter)));
           case "männlich" ->
                   tagesUmsatz.setKcal((int) (66.5 + (13.7 * gewicht) + (5.0 * groesse * 100) - (6.8 * alter)));
       }
       tagesUmsatz.setKohlenhydrate((int) (tagesUmsatz.getKcal() * 0.5 / 4.1));
       tagesUmsatz.setProtein((int) (tagesUmsatz.getKcal()* 0.2 / 4.1));
       tagesUmsatz.setFett((int) (tagesUmsatz.getKcal() * 0.3 / 9.3));
   }


    //________________getter und setter______________________//
    public double getBMI(){
        System.out.println("Gewicht: " + gewicht);
        System.out.println("Groesse: " + groesse);
        bmi = gewicht / (groesse * groesse);
        System.out.println("BMI: " + bmi);
        return bmi;
    }
    public double getGroesse() {
        return groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public double getAlter() {
        return alter;
    }

    public void setKoerperdaten(double groesse, double gewicht, double alter, String geschlecht){
       this.groesse = groesse;
       this.gewicht = gewicht;
       this.alter = alter;
       this.geschlecht = geschlecht;
    }


    public Naehrwerte getTagesUmsatz() {
        return tagesUmsatz;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public void setAlter(double alter) {
        this.alter = alter;
    }

    @Override
    public String toString() {
        return "Koerperdaten{" +
                "groesse=" + groesse +
                ", gewicht=" + gewicht +
                ", geschlecht=" + geschlecht +
                ", alter=" + alter +
                ", tagesUmsatzKcal=" + tagesUmsatz +
                ", bmi=" + bmi +
                '}';
    }
}
