package com.example.fitnessapp;

import java.io.Serializable;

public class Koerperdaten implements Serializable {
    private double groesse; // in m
    private double gewicht; // in kg
    private String geschlecht;
    private int alter;
    private Naehrwerte tagesUmsatz = new Naehrwerte(0,0,0,0);
    private double bmi;

    /**
     * Berechnet den BMI einer Person anhand der Groesse und des Gewichtes
     */


   public void tagesUmsatzBerechnen(){
       // Kalorien
       switch (geschlecht){
           case "weiblich":
               tagesUmsatz.setKcal((int) (655 + (9.6 * gewicht) + (1.8 * groesse * 100) - (4.7 * alter)));
               break;
           case "männlich":
               tagesUmsatz.setKcal((int) (66.5 + (13.7 * gewicht) + (5.0 * groesse*100) - (6.8 * alter)));
               break;
       }
       tagesUmsatz.setKohlenhydrate((int) (tagesUmsatz.getKcal() * 0.5 / 4.1));
       tagesUmsatz.setProtein((int) (tagesUmsatz.getKcal()* 0.2 / 4.1));
       tagesUmsatz.setFett((int) (tagesUmsatz.getKcal() * 0.3 / 9.3));
   }


    //________________getter und setter______________________//
    public double getBMI(){
        System.out.println("Gewicht: " + gewicht);
        System.out.println("groesse: " + groesse);
        bmi = gewicht/(groesse * groesse);
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

    public int getAlter() {
        return alter;
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

    public void setAlter(int alter) {
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
