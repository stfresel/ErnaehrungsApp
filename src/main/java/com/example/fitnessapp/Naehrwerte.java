package com.example.fitnessapp;

import java.io.Serializable;

/**
 * In der Klasse kann man Kalorien, Kohlenhydrate, Proteine und Fette setzten und verändern.
 */
public class Naehrwerte implements Serializable {
    /**
     * Gibt die Menge der Kalorien an.
     */
    private int kcal;

    /**
     * Gibt die Menge der Fette an.
     */
    private int fette;

    /**
     * Gibt die Menge der Kohlenhydrate an.
     */
    private int kohlenhydrate;

    /**
     * Gibt die Menge der Proteine an.
     */
    private int protein;

    public Naehrwerte(int kcal, int fette, int kohlenhydrate, int protein) {
        this.kcal = kcal;
        this.fette = fette;
        this.kohlenhydrate = kohlenhydrate;
        this.protein = protein;
    }

    //_________________getter und setter__________________________//

    /**
     * Setzt die Kalorien.
     * @param kcal Menge der Kalorien
     */

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    /**
     * Setzt die Fette.
     * @param fette Menge der Fette
     */
    public void setFette(int fette) {
        this.fette = fette;
    }

    /**
     * Setzt die Kohlenhydrate.
     * @param kohlenhydrate Menge der Kohlenhydrate
     */
    public void setKohlenhydrate(int kohlenhydrate) {
        this.kohlenhydrate = kohlenhydrate;
    }

    /**
     * Setzt die Proteine.
     * @param proteine Menge der Kolorien
     */
    public void setProtein(int proteine) {
        this.protein = proteine;
    }

    /**
     * Gibt die Kalorien zurück.
     * @return Menge der Kalorien
     */
    public int getKcal() {
        return kcal;
    }

    /**
     * Gibt die Fette zurück.
     * @return Menge der Fette
     */
    public int getFette() {
        return fette;
    }

    /**
     * Gibt die Kohlenhydrate zurück.
     * @return Menge der Kohlenhydrate
     */
    public int getKohlenhydrate() {
        return kohlenhydrate;
    }

    /**
     * Gibt die Proteine zurück.
     * @return Menge der Proteine
     */
    public int getProtein() {
        return protein;
    }

    @Override
    public String toString() {
        return "Kcal: " + kcal +
                ", Kohlenhydrate: " + kohlenhydrate +
                ", Protein: " + protein +
                ", Fette: " + fette;
    }
}
