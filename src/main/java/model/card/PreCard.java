package model.card;

import model.Deck;

import java.util.ArrayList;
import java.util.Locale;

public abstract class PreCard {
    private static ArrayList<PreCard> allPreCards;
    protected String name;
    protected CardType cardType;
    protected String description;
    protected int price;

    static {
        allPreCards = new ArrayList<>();
    }

    public PreCard(String name, String type) {
        this.name = name;
        this.cardType = CardType.valueOf(type.toUpperCase());
    }

    public int getPrice() {
        return price;
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

    protected abstract Card newCard(String name);

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
