package com.nti.sessiongame.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BoardTest {

    @Test
    public void defaultBoardHasTwentyCellsAndStartAtZero() {
        Board board = Board.createDefault();
        assertEquals(Board.CELL_COUNT, board.getCells().size());
        assertEquals(CellType.START, board.getCell(0).getType());
    }

    @Test
    public void moveWrapsAroundRing() {
        Board board = Board.createDefault();
        assertEquals(3, board.move(18, 5));
        assertEquals(19, board.moveBack(1, 2));
    }

    @Test
    public void getCellNormalizesNegativeIndex() {
        Board board = Board.createDefault();
        assertSame(board.getCell(19), board.getCell(-1));
    }
}
