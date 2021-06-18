package model;

import controller.game.RoundController;
import model.card.PreCard;

public class Player {
    //TODO clone the deck when game is started

    private final String name;
    private final Deck deck;
    private int lifePoint;
    private Board board;
    //    private ArrayList<Card> unusedCards;
    private Hand hand;
    private final User owner;
    private final RoundController roundController;


    {
        this.hand = new Hand();
        this.board = new Board();
//        this.unusedCards = new ArrayList<>();
        board = new Board();
    }

    public Player(User owner, RoundController roundController) {
        this.owner = owner;
        this.name = owner.getNickName();
        this.deck = owner.getActiveDeck();
        this.lifePoint = 8000;
        this.roundController = roundController;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
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

    //    public ArrayList<Card> getUnusedCards() {
//        return unusedCards;
//    }
    public PreCard takeACardFromDeck() {
        return deck.getMainCards().remove(0);
    }

    public Hand getHand() {
        return this.hand;
    }

    public void increaseLifePoint(int increasingAmount) {
        this.lifePoint += increasingAmount;
    }

    public void decreaseLifePoint(int decreasingAmount) {
        this.lifePoint -= decreasingAmount;
    }
}
