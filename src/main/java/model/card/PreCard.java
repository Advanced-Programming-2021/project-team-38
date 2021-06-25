package model.card;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class PreCard {
    protected static ArrayList<PreCard> allPreCards;
    //    public ArrayList<String> nameOfWatchers;
    protected String name;
    protected CardType cardType;    // monster or trap or spell
    protected String description;
    protected int price;

    static {
        allPreCards = new ArrayList<>();
    }

    public static ArrayList<PreCard> getAllPreCards() {
        return allPreCards;
    }

    public abstract Card newCard();

    public static PreCard findCard(String name) {
        for (PreCard preCard : allPreCards) {
            if ((preCard.getName().toUpperCase()).equals(name.toUpperCase())) //todo: check
                return preCard;
        }
        return null;
    }

    @Override
    public String toString() {
        return getName() + ": " + getDescription();
    }

}
