package model.card.spelltrap;

import model.card.Card;
import model.card.CardType;
import model.card.Pre;

public class PreSpellTrapCard implements Pre {
    private String name;
    private CardType cardType;
    private String description;
    private int price;
    private CardStatus status;
    private CardIcon icon;

    @Override
    public Card makeCard() {
        return new SpellTrap(cardType, description, price, status, icon);
    }

    @Override
    public int getPrice() {
        return price;
    }
}
