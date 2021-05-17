package model.card;

import lombok.Getter;

@Getter
public abstract class Card {
    protected String name;
    protected Card instance;
    protected PreCard preCardInGeneral;

    //by me
    public Card(PreCard preCard) {
        preCardInGeneral = preCard;
    }

    public Card getInstance() { //TODO to be complete
        return null;
    }

    public void setInstance(Card instance) {
        this.instance = instance;
    }

    @Override
    protected abstract Object clone() throws CloneNotSupportedException;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //end

    public static Card getCardByName(String cardName) {
        return null;
    }

}
