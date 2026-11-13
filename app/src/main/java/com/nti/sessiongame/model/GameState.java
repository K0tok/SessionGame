package com.nti.sessiongame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameState {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;

    private final Board board;
    private final List<Player> players;
    private int currentPlayerIndex;
    private GamePhase phase;
    private Integer winnerPlayerId;

    public GameState(List<Player> players) {
        this(players, Board.createDefault());
    }

    public GameState(List<Player> players, Board board) {
        if (players == null || players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException(
                    "Players count must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS);
        }
        this.board = board;
        this.players = Collections.unmodifiableList(new ArrayList<>(players));
        this.currentPlayerIndex = 0;
        this.phase = GamePhase.WAITING_ROLL;
        this.winnerPlayerId = null;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            throw new IndexOutOfBoundsException("Invalid player index: " + currentPlayerIndex);
        }
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public Integer getWinnerPlayerId() {
        return winnerPlayerId;
    }

    public void setWinnerPlayerId(Integer winnerPlayerId) {
        this.winnerPlayerId = winnerPlayerId;
        this.phase = GamePhase.FINISHED;
    }

    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return player;
            }
        }
        throw new IllegalArgumentException("Player not found: " + id);
    }
}
