package com.nti.sessiongame.model;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiceTest {

    @Test
    public void rollUsesInjectedRandom() {
        Dice dice = new Dice(new Random(1L));
        int value = dice.roll();
        assertTrue(value >= 1 && value <= 6);
        assertEquals(new Dice(new Random(1L)).roll(), value);
    }
}
