package com.nti.sessiongame.controller;

import com.nti.sessiongame.model.Cell;
import com.nti.sessiongame.model.Dice;
import com.nti.sessiongame.model.GamePhase;
import com.nti.sessiongame.model.GameState;
import com.nti.sessiongame.model.Player;

import java.util.ArrayList;
import java.util.List;

public final class GameController {

    private final GameState state;
    private final Dice dice;
    private GameEventListener listener;

    public GameController(List<Player> players) {
        this(new GameState(players), new Dice());
    }

    public GameController(GameState state, Dice dice) {
        this.state = state;
        this.dice = dice;
        this.state.setPhase(GamePhase.WAITING_ROLL);
    }

    public static GameController create(String[] names, int[] colors) {
        if (names == null || colors == null || names.length != colors.length) {
            throw new IllegalArgumentException("Names and colors must match");
        }
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            players.add(new Player(i, names[i], colors[i]));
        }
        return new GameController(players);
    }

    public void setListener(GameEventListener listener) {
        this.listener = listener;
    }

    public GameState getState() {
        return state;
    }

    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }

    /**
     * Бросок кубика и перемещение. Если игрок пропускает ход — кубик не бросается.
     *
     * @return значение кубика или 0, если ход пропущен
     */
    public int rollAndMove() {
        ensurePhase(GamePhase.WAITING_ROLL);

        Player player = state.getCurrentPlayer();
        int playerIndex = state.getCurrentPlayerIndex();

        if (player.isSkipNextRoll()) {
            player.setSkipNextRoll(false);
            notifyTurnSkipped(playerIndex);
            advanceTurn();
            return 0;
        }

        state.setPhase(GamePhase.MOVING);
        int value = dice.roll();
        notifyDiceRolled(playerIndex, value);

        int from = player.getPosition();
        int to = state.getBoard().move(from, value);
        player.setPosition(to);
        notifyMoved(playerIndex, from, to);

        Cell cell = state.getBoard().getCell(to);
        state.setPhase(GamePhase.RESOLVING_CELL);
        notifyCellEntered(playerIndex, cell.getType().name());
        return value;
    }

    /**
     * Вызвать после обработки клетки в UI, чтобы передать ход дальше.
     */
    public void completeCellResolution() {
        ensurePhase(GamePhase.RESOLVING_CELL);
        advanceTurn();
    }

    public void skipCurrentPlayerTurn() {
        ensurePhase(GamePhase.WAITING_ROLL);
        Player player = state.getCurrentPlayer();
        player.setSkipNextRoll(false);
        notifyTurnSkipped(state.getCurrentPlayerIndex());
        advanceTurn();
    }

    private void advanceTurn() {
        state.advanceToNextPlayer();
        state.setPhase(GamePhase.WAITING_ROLL);
        notifyTurnChanged(state.getCurrentPlayerIndex());
    }

    private void ensurePhase(GamePhase expected) {
        if (state.getPhase() != expected) {
            throw new IllegalStateException(
                    "Expected phase " + expected + " but was " + state.getPhase());
        }
    }

    private void notifyTurnChanged(int playerIndex) {
        if (listener != null) {
            listener.onTurnChanged(playerIndex);
        }
    }

    private void notifyDiceRolled(int playerIndex, int value) {
        if (listener != null) {
            listener.onDiceRolled(playerIndex, value);
        }
    }

    private void notifyTurnSkipped(int playerIndex) {
        if (listener != null) {
            listener.onTurnSkipped(playerIndex);
        }
    }

    private void notifyMoved(int playerIndex, int fromCell, int toCell) {
        if (listener != null) {
            listener.onMoved(playerIndex, fromCell, toCell);
        }
    }

    private void notifyCellEntered(int playerIndex, String cellType) {
        if (listener != null) {
            listener.onCellEntered(playerIndex, cellType);
        }
    }
}
