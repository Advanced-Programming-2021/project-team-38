package model.card;

import java.util.ArrayList;

public abstract class PreCard {
    private static ArrayList<PreCard> allPreCards;
    protected String name;
    protected CardType cardType;
    protected String description;
    protected int price;
    private int level;

    static {
        allPreCards = new ArrayList<>();
    }

    protected PreCard(String name, String type) {
        this.name = name;
        this.cardType = CardType.valueOf(type.toUpperCase());
    }

    public static ArrayList<PreCard> getAllPreCards() {
        return allPreCards;
    }

    public int getPrice() {
        return price;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getDescription() {
        return description;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public abstract Card newCard(String name);

    public static PreCard findCard(String name) {
        for (PreCard preCard : allPreCards) {
            if (preCard.getName().equals(name))
                return preCard;
        }
        return null;
    }

    @Override
    public String toString() {
        return getName() + ": " + getDescription();
    }

}
