package com.nti.sessiongame.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameStateTest {

    @Test
    public void advanceToNextPlayerCycles() {
        Player p0 = new Player(0, "A", 0xFF000000);
        Player p1 = new Player(1, "B", 0xFFFFFFFF);
        GameState state = new GameState(Arrays.asList(p0, p1));

        assertEquals(0, state.getCurrentPlayerIndex());
        state.advanceToNextPlayer();
        assertEquals(1, state.getCurrentPlayerIndex());
        state.advanceToNextPlayer();
        assertEquals(0, state.getCurrentPlayerIndex());
    }

    @Test
    public void setWinnerFinishesGame() {
        Player p0 = new Player(0, "A", 0xFF000000);
        Player p1 = new Player(1, "B", 0xFFFFFFFF);
        GameState state = new GameState(Arrays.asList(p0, p1));

        assertNull(state.getWinnerPlayerId());
        state.setWinnerPlayerId(1);
        assertEquals(Integer.valueOf(1), state.getWinnerPlayerId());
        assertEquals(GamePhase.FINISHED, state.getPhase());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTooFewPlayers() {
        new GameState(Collections.singletonList(new Player(0, "Solo", 0xFF000000)));
    }
}
