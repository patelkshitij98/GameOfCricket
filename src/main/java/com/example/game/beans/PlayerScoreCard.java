package com.example.game.beans;

import lombok.Data;

public @Data
class PlayerScoreCard {

    BattingCard battingCard;
    BowlingCard bowlingCard;

    public PlayerScoreCard() {
        this.battingCard = new BattingCard();
        this.bowlingCard = new BowlingCard();
    }

}
