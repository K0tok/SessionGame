package com.nti.sessiongame.model;

public final class Cell {

    private final int index;
    private final CellType type;

    public Cell(int index, CellType type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public CellType getType() {
        return type;
    }
}
