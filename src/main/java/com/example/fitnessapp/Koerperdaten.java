package com.example.fitnessapp;

public class Koerperdaten {
    private double groesse; // in m
    private double gewicht; // in kg
    private char geschlecht;
    private int alter;
    private int taeglicheNaehrwerte;

    private double tagesUmsatzKcal;
    private double bmi;

    /**
     * Berechnet den BMI einer Person anhand der Groesse und des Gewichtes
     */
   public void bmiBerechnen(){
       bmi = gewicht/Math.sqrt(groesse);
       System.out.println("BMI: " + bmi);
   }

   public void tagesUmsatzBerechnen(){
       // Kalorien
       switch (geschlecht){
           case 'w':
               tagesUmsatzKcal = 655 + (9.6 * gewicht) + (1.8 * groesse * 100) - (4.7 * alter);
               break;
           case 'm':
               tagesUmsatzKcal =  66.5 + (13.7 * gewicht) + (5.0 * groesse*100) - (6.8 * alter);
               break;
       }
   }


    //________________getter und setter______________________//
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


    public double getBmi() {
        return bmi;
    }

    public double getTagesUmsatzKcal() {
        return tagesUmsatzKcal;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public void setGeschlecht(char geschlecht) {
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
                ", tagesUmsatzKcal=" + tagesUmsatzKcal +
                ", bmi=" + bmi +
                '}';
    }
}
