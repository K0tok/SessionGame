package com.nti.sessiongame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Board {

    public static final int CELL_COUNT = 20;

    private final List<Cell> cells;

    public Board() {
        this.cells = createDefaultLayout();
    }

    public Board(List<Cell> cells) {
        if (cells.size() != CELL_COUNT) {
            throw new IllegalArgumentException("Board must have exactly " + CELL_COUNT + " cells");
        }
        this.cells = Collections.unmodifiableList(new ArrayList<>(cells));
    }

    public static Board createDefault() {
        return new Board();
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Cell getCell(int index) {
        return cells.get(normalize(index));
    }

    public int move(int fromPosition, int steps) {
        return normalize(fromPosition + steps);
    }

    public int moveBack(int fromPosition, int steps) {
        return normalize(fromPosition - steps);
    }

    public static int normalize(int index) {
        int mod = index % CELL_COUNT;
        return mod < 0 ? mod + CELL_COUNT : mod;
    }

    private static List<Cell> createDefaultLayout() {
        CellType[] layout = {
                CellType.START,
                CellType.EXAM,
                CellType.EVENT,
                CellType.PRACTICE,
                CellType.SABOTAGE,
                CellType.EXAM,
                CellType.BREAK,
                CellType.BLITZ,
                CellType.EVENT,
                CellType.EXAM,
                CellType.PRACTICE,
                CellType.SABOTAGE,
                CellType.EXAM,
                CellType.BREAK,
                CellType.BLITZ,
                CellType.EVENT,
                CellType.EXAM,
                CellType.PRACTICE,
                CellType.SABOTAGE,
                CellType.BLITZ
        };

        List<Cell> result = new ArrayList<>(CELL_COUNT);
        for (int i = 0; i < CELL_COUNT; i++) {
            result.add(new Cell(i, layout[i]));
        }
        return Collections.unmodifiableList(result);
    }
}
