package model.card;

import lombok.Getter;

import java.util.HashMap;

@Getter
public abstract class PreCard {
    protected static HashMap<PreCard, Card> allPreCardsInstances;
    protected String name;
    protected CardType cardType;    // monster or trap or spell
    protected String description;
    protected int price;

    static {
        allPreCardsInstances = new HashMap<>();
    }

    public static HashMap<PreCard, Card> getAllPreCardsInstances() {
        return allPreCardsInstances;
    }

    public abstract Card newCard();

    public static PreCard findCard(String name) {
        for (PreCard preCard : allPreCardsInstances.keySet()) {
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
