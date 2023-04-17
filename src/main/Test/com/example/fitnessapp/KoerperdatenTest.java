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
        koerperdaten = new Koerperdaten(1.7);
    }

    @Test
    public void testHeight(){
        assertEquals(1.7, koerperdaten.getGroesse());
    }
}
