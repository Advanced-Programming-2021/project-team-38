package model;

import model.card.Card;

import java.util.ArrayList;

public class Player {
    private String name;
    private int gameScore;
    private Deck deck;
    private int lifePoint;
    private Board board;
    private ArrayList<Card> unusedCards;

    public Player(User user) {

    }

    public String getName() {
        return name;
    }

    public int getGameScore() {
        return gameScore;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Card> getUnusedCards() {
        return unusedCards;
    }

    public void increaseLifePoint(int increasingAmount) {

    }

    public void decreaseLifePoint(int decreasingAmount) {

    }
}
