package com.example.fitnessapp;

import java.io.Serializable;

/**
 * Ermöglicht es verschiedene Zutaten zu erstellen, um die Nährwerte je nach zutat einzugeben.
 */
public class Zutat implements Serializable {

    /**
     * Gibt den Namen einer Zutat an.
     */
    private String name;

    /**
     * Gibt die Menge einer Zutat an.
     */
    private int menge;

    /**
     * Gibt die Nährwerte einer Zutat an.
     */
    private Naehrwerte naehrwerte;

    public Zutat(){
    }

    /**
     *
     * @param name Name der Zutat
     * @param menge Menge der Nährwertangabe
     * @param naehrwerte Nährwerte der Zutat
     */
    public Zutat(String name, int menge, Naehrwerte naehrwerte) {
        this.name = name;
        this.menge = menge;
        this.naehrwerte = naehrwerte;
    }
    //______________getter und setter______________

    /**
     * Gibt den Namen der Zutat zurück
     * @return String mit dem Namen der Zutat
     */
    public String getName() {
        return name;
    }

    //public void setName(String name) {
    //    this.name = name;
    //}

    /**
     * Gibt die Menge der Zutat zurück.
     * @return Menge der Zutat und Nährwertangaben
     */
    public int getMenge() {
        return menge;
    }

    //public void setMenge(int menge) {
    //    this.menge = menge;
    //}

    /**
     * Gibt die Nährwerte der Zutat zurück.
     * @return String mit den Nährwerten der Zutat
     */
    public Naehrwerte getNaehrwerte() {
        return naehrwerte;
    }


    //public void setNaehrwerte(Naehrwerte naehrwerte) {
    //    this.naehrwerte = naehrwerte;
    //}

    @Override
    public String toString() {
        return name +
                " (menge: " + menge +
                ", " + naehrwerte + ")";
    }
}