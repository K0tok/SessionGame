package com.nti.sessiongame.controller;

import com.nti.sessiongame.model.Dice;
import com.nti.sessiongame.model.GamePhase;
import com.nti.sessiongame.model.GameState;
import com.nti.sessiongame.model.Player;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerTest {

    private static final class RecordingListener implements GameEventListener {
        int lastTurn = -1;
        int lastDice = -1;
        int lastMovedTo = -1;
        String lastCellType;
        int skippedCount;
        int movedCount;

        @Override
        public void onTurnChanged(int playerIndex) {
            lastTurn = playerIndex;
        }

        @Override
        public void onDiceRolled(int playerIndex, int value) {
            lastDice = value;
        }

        @Override
        public void onTurnSkipped(int playerIndex) {
            skippedCount++;
        }

        @Override
        public void onMoved(int playerIndex, int fromCell, int toCell) {
            movedCount++;
            lastMovedTo = toCell;
        }

        @Override
        public void onCellEntered(int playerIndex, String cellType) {
            lastCellType = cellType;
        }

        @Override
        public void onGameWon(int winnerPlayerIndex) {
        }
    }

    private Player p0;
    private Player p1;
    private GameController controller;
    private RecordingListener listener;

    @Before
    public void setUp() {
        p0 = new Player(0, "A", 0xFFFF0000);
        p1 = new Player(1, "B", 0xFF0000FF);
        Dice dice = new Dice(new Random(42L));
        controller = new GameController(new GameState(Arrays.asList(p0, p1)), dice);
        listener = new RecordingListener();
        controller.setListener(listener);
    }

    @Test
    public void rollAndMoveChangesPositionAndNotifies() {
        int value = controller.rollAndMove();
        assertTrue(value >= 1 && value <= 6);
        assertEquals(value, p0.getPosition());
        assertEquals(value, listener.lastDice);
        assertEquals(1, listener.movedCount);
        assertEquals(GamePhase.RESOLVING_CELL, controller.getState().getPhase());
        assertEquals(controller.getState().getBoard().getCell(value).getType().name(), listener.lastCellType);
    }

    @Test
    public void completeCellResolutionPassesTurn() {
        controller.rollAndMove();
        controller.completeCellResolution();
        assertEquals(1, controller.getState().getCurrentPlayerIndex());
        assertEquals(1, listener.lastTurn);
        assertEquals(GamePhase.WAITING_ROLL, controller.getState().getPhase());
    }

    @Test
    public void skipNextRollDoesNotMovePlayer() {
        p0.setSkipNextRoll(true);
        int value = controller.rollAndMove();
        assertEquals(0, value);
        assertEquals(0, p0.getPosition());
        assertFalse(p0.isSkipNextRoll());
        assertEquals(1, listener.skippedCount);
        assertEquals(0, listener.movedCount);
        assertEquals(1, controller.getState().getCurrentPlayerIndex());
    }
}
