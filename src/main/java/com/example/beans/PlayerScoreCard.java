package com.example.beans;

public class PlayerScoreCard {

    PlayerBattingScoreCard playerBattingScoreCard;
    PlayerBowlingScoreCard playerBowlingScoreCard;

    public PlayerScoreCard() {
        this.playerBattingScoreCard = new PlayerBattingScoreCard();
        this.playerBowlingScoreCard = new PlayerBowlingScoreCard();
    }

    public PlayerBattingScoreCard getPlayerBattingScoreCard() {
        return playerBattingScoreCard;
    }

    public void setPlayerBattingScoreCard(PlayerBattingScoreCard playerBattingScoreCard) {
        this.playerBattingScoreCard = playerBattingScoreCard;
    }

    public PlayerBowlingScoreCard getPlayerBowlingScoreCard() {
        return playerBowlingScoreCard;
    }

    public void setPlayerBowlingScoreCard(PlayerBowlingScoreCard playerBowlingScoreCard) {
        this.playerBowlingScoreCard = playerBowlingScoreCard;
    }
}
