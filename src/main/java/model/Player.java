package model;

public class Player {
    //TODO clone the deck when game is started

    private final String name;
    private int gameScore;
    private final Deck deck;
    private int lifePoint;
    private Board board;
    //    private ArrayList<Card> unusedCards;
    private Hand hand;

    {
        this.hand = new Hand();
        this.board = new Board();
//        this.unusedCards = new ArrayList<>();
        board = new Board();
    }

    public Player(User user) {
        this.name = user.getNickName();
        this.deck = user.getActiveDeck();
        this.lifePoint = 8000;
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

//    public ArrayList<Card> getUnusedCards() {
//        return unusedCards;
//    }

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