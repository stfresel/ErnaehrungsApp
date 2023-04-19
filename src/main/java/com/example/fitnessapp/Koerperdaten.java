package com.example.fitnessapp;

public class Koerperdaten {
    private double groesse;
    private double gewicht;
    private char geschlecht;
    private int alter;

    private int tagesUmsatzKcal;
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
               tagesUmsatzKcal = ((int) (665 + (9.6 * gewicht) + (1.8 * groesse*100) - (4.7 * alter)));
               break;
           case 'm':
               tagesUmsatzKcal = ((int) (66.5 + (13.7 * gewicht) + (5.0 * groesse*100) - (6.8 * alter)));
               break;
       }
       // fette
       //tagesUmsatz.setFett((int)(tagesUmsatz.getKcal()*0.25));
       //System.out.println("Fette: " + tagesUmsatz.getFett());


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
