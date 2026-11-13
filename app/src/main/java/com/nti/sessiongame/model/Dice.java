package com.nti.sessiongame.model;

import java.util.Random;

public final class Dice {

    private final Random random;

    public Dice() {
        this(new Random());
    }

    public Dice(Random random) {
        this.random = random;
    }

    public int roll() {
        return random.nextInt(6) + 1;
    }
}
