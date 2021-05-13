package model.card.spelltrap;

import model.card.Card;
import model.card.CardType;
import model.card.PreCard;

import java.util.ArrayList;

public class PreSpellTrapCard extends PreCard {
    private static final ArrayList<PreSpellTrapCard> allSpellTrapCards;
    private CardStatus status;  //limit
    private CardIcon icon;

    static {
        allSpellTrapCards = new ArrayList<>();
    }

    public PreSpellTrapCard(String name, String type) {
        super(name, type);
        allSpellTrapCards.add(this);
        //TODO enter info of the card
    }

    public CardStatus getStatus() {
        return status;
    }

    public static PreSpellTrapCard findSTCard(String name) {
        for (PreSpellTrapCard preSTCard : allSpellTrapCards) {
            if (preSTCard.name.equals(name))
                return preSTCard;
        }
        return null;
    }

    @Override
    public Card newCard(String name) {
        return null;
    }
}
