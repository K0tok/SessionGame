package com.nti.sessiongame.controller;

/**
 * Контракт событий логики → UI.
 */
public interface GameEventListener {

    void onTurnChanged(int playerIndex);

    void onMoved(int playerIndex, int fromCell, int toCell);

    void onCellEntered(int playerIndex, String cellType);

    void onGameWon(int winnerPlayerIndex);
}
