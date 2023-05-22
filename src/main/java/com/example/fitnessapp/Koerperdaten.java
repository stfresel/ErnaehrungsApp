package com.example.fitnessapp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * Beinhaltet alle wichtigen Daten über den Körper.
 * Anhand der Daten wird der BMI und die täglichen Nährwerte berechnet.
 */

public class Koerperdaten implements Serializable {
    /**
     * Gibt die Körpergröße in Meter an.
     */
    private double groesse;

    /**
     * Gibt das Körpergewicht in Kilogramm an.
     */
    private double gewicht;

    /**
     * Gibt das Geschlecht der Person an.
     */
    private String geschlecht;

    /**
     * Gibt das Geburtsdatum der Person an.
     */
    private LocalDate birthday;

    /**
     * Gibt die ideale Menge der täglichen Nährwerte an.
     */
    private Naehrwerte tagesUmsatz = new Naehrwerte(0,0,0,0);

    /**
     * Gibt den Body-Mass-Index an.
     */
    private double bmi;


    /**
     * Berechnet die täglichen Nährwerte.
     * Die Methode berechnet die Menge der Nährwerte, die eine Person am Tag zu sich nehmen sollte.
     * Bei der Berechnung des Kalorienbedarfs gibt es unterschiedliche Formeln für Frauen und Männer.
     */
    public void tagesUmsatzBerechnen(){
       // Kalorien
       switch (geschlecht) {
           case "weiblich" ->
                   tagesUmsatz.setKcal((int) Math.round(655 + (9.6 * gewicht) + (1.8 * groesse * 100) - (4.7 * getAlter())));
           case "männlich" ->
                   tagesUmsatz.setKcal((int) Math.round(66.5 + (13.7 * gewicht) + (5.0 * groesse * 100) - (6.8 * getAlter())));
       }
       tagesUmsatz.setKohlenhydrate((int) Math.round(tagesUmsatz.getKcal() * 0.5 / 4.1));
       tagesUmsatz.setProtein((int) Math.round(tagesUmsatz.getKcal()* 0.2 / 4.1));
       tagesUmsatz.setFette((int) Math.round(tagesUmsatz.getKcal() * 0.3 / 9.3));
   }


    //________________getter und setter______________________//

    /**
     * Berechnet den Body-Mass-Index.
     * Diese Methode berechnet den BMI anhand der Größe und des Gewichtes einer Person.
     *
     * @return Der Rückgabewert entspricht dem BMI der Person. Dabei wird dieser auf zwei Nachkommastellen gerundet.
     */
    public double getBMI(){
        System.out.println("Gewicht: " + gewicht);
        System.out.println("Groesse: " + groesse);
        bmi = gewicht / (groesse * groesse);
        System.out.println("BMI: " + bmi);

        // um auf zwei Nachkommastellen zu runden
        return Math.round(bmi * 100.0)/100.0;
    }

    /**
     * Gibt die Größe der Person zurück.
     * @return Größe in Meter
     */
    public double getGroesse() {
        return groesse;
    }

    /**
     * Gibt das Gewicht der Person zurück.
     * @return Gewicht in Kilogramm
     */
    public double getGewicht() {
        return gewicht;
    }

    /**
     * Gibt das Geschlecht der Person zurück.
     * @return String mit dem Geschlecht (männlich oder weiblich)
     */
    public String getGeschlecht() {
        return geschlecht;
    }

    /**
     * Berechnet das Alter der Person anhand des Geburtsdatums.
     * @return Alter in Jahren
     */
    public double getAlter() {
        System.out.println("alter: " + Period.between(birthday, LocalDate.now()).getYears());
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    /**
     * Setzt die Körperdaten einer Person.
     * @param groesse neue Größe in Meter
     * @param gewicht neues Gewicht in Kilogramm
     * @param gebDate neues Geburtstagsdatum
     * @param geschlecht neues Geschlecht (männlich oder weiblich)
     */

    public void setKoerperdaten(double groesse, double gewicht, LocalDate gebDate, String geschlecht){
       this.groesse = groesse;
       this.gewicht = gewicht;
       this.birthday = gebDate;
       this.geschlecht = geschlecht;
    }


    /**
     * Gibt den Tagesumsatz einer Person zurück.
     * @return Tagesumsatz der Nährwerte
     */
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

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gibt das Geburtsdatum des Benutzers zurück.
     * @return LocalDate Geburtsdatum
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Koerperdaten{" +
                "groesse=" + groesse +
                ", gewicht=" + gewicht +
                ", geschlecht=" + geschlecht +
                ", alter=" + getAlter() +
                ", tagesUmsatzKcal=" + tagesUmsatz +
                ", bmi=" + bmi +
                '}';
    }
}
