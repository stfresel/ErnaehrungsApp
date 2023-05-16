package com.example.fitnessapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KoerperdatenTest {

    private Koerperdaten koerperdaten = null;

    @BeforeEach
    public void init(){
        koerperdaten = new Koerperdaten();
    }

    @Test
    public void testTagesUmsatzBerechnen(){
        koerperdaten.setGroesse(1.7);
        koerperdaten.setGewicht(60);
        koerperdaten.setAlter(20);
        koerperdaten.setGeschlecht("weiblich");
        koerperdaten.tagesUmsatzBerechnen();
        assertEquals(1443.0, koerperdaten.getTagesUmsatz().getKcal());

        koerperdaten.setGeschlecht("männlich");
        koerperdaten.tagesUmsatzBerechnen();
        assertEquals(1602.0, koerperdaten.getTagesUmsatz().getKcal());

    }

}
