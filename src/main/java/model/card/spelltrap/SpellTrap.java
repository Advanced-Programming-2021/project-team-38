package model.card.spelltrap;

import model.card.Card;
import model.card.CardType;
import model.card.PreCard;
import model.card.monster.Monster;

public class SpellTrap extends Card {
    protected PreSpellTrapCard myPreCard;

    public SpellTrap(PreCard preCard) {
        super(preCard);
        myPreCard = (PreSpellTrapCard) preCard;
    }

    public SpellTrap setUpSpellTrap() {    //after instance creation it will fill the fields
        setName(myPreCard.getName());
        return this;
    }

    @Override
    public void setName(String name) {
        if (getName() == null)
            super.setName(name);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new SpellTrap(preCardInGeneral);
    }
}
