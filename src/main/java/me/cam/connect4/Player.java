package me.cam.connect4;

import java.util.UUID;

public class Player {

    private UUID user;
    private boolean playing;
    private boolean turn;
    private boolean playerOne;

    public Player(UUID user) {
        this.user = user;
        this.playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public UUID getUser() {
        return user;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
}