package com.example.fitnessapp;

public class Koerperdaten {
    private double groesse;
    private double gewicht;
    private char geschlecht;
    private int alter;
    private int taeglicheNaehrwerte;
    private double bmi;

    /**
     * Berechnet den BMI einer Person anhand der Groesse und des Gewichtes
     */
   public void bmiBerechnen(){
       bmi = gewicht/Math.sqrt(groesse);
       System.out.println("BMI: " + bmi);
   }





    //________________getter und setter______________________
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

    public void setTaeglicheNaehrwerte(int taeglicheNaehrwerte) {
        this.taeglicheNaehrwerte = taeglicheNaehrwerte;
    }
}
