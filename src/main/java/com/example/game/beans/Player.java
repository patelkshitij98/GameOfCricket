package com.example.game.beans;

import lombok.Data;

@Data
public class Player {

    private int id;
    private Category category;
    private int batsmanRating;
    private int bowlerRating;

    public enum Category {
        BATSMAN,BOWLER
    }

    public Player(int id, Category category, int batsmanRating, int bowlerRating) {
        this.id = id;
        this.category = category;
        this.batsmanRating = batsmanRating;
        this.bowlerRating = bowlerRating;
    }
}
