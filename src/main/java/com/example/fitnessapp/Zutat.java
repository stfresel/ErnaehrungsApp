package com.example.fitnessapp;

import java.io.Serializable;

/**
 * <h2>Klasse: Zutat</h2>
 * Die Klasse Zutat beinhaltet den Namen, die Menge und die Nährwerte.
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
     * Gibt die Nährwerte einer zutat an.
     */
    private Naehrwerte naehrwerte;

    public Zutat(){
    }

    public Zutat(String name, int menge, Naehrwerte naehrwerte) {
        this.name = name;
        this.menge = menge;
        this.naehrwerte = naehrwerte;
    }
    //______________getter und setter______________

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public Naehrwerte getNaehrwerte() {
        return naehrwerte;
    }

    public void setNaehrwerte(Naehrwerte naehrwerte) {
        this.naehrwerte = naehrwerte;
    }

    @Override
    public String toString() {
        return name +
                " (menge: " + menge +
                ", " + naehrwerte + ")";
    }
}