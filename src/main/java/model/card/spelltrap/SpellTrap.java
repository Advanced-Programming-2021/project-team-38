package model.card.spelltrap;

import model.card.Card;
import model.card.CardType;

public class SpellTrap extends Card {
    private String name;
    private CardType cardType;
    private String description;
    private int price;
    private CardStatus status;
    private CardIcon icon;

    public SpellTrap(CardType cardType, String description, int price, CardStatus status, CardIcon icon) {
        this.cardType = cardType;
        this.description = description;
        this.price = price;
        this.status = status;
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public void setIcon(CardIcon icon) {
        this.icon = icon;
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

    public int getPrice() {
        return price;
    }

    public CardStatus getStatus() {
        return status;
    }

    public CardIcon getIcon() {
        return icon;
    }
}
