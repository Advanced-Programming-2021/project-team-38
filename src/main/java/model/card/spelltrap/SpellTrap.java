package model.card.spelltrap;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.SpellTrapCardInUse;

@Getter
@Setter
public class SpellTrap extends Card {
    protected PreSpellTrapCard myPreCard;
    private boolean isActivated;


    public SpellTrap(PreCard preCard) {
        super(preCard);
        myPreCard = (PreSpellTrapCard) preCard;
    }

    public SpellTrap setUpSpellTrap() {    //after instance creation it will fill the fields
        setName(myPreCard.getName());
        return this;
    }

    public boolean canActivateEffect(Board myBoard, Board rivalBoard, SpellTrapCardInUse thisCard) {

    }

    public void activateEffect(Board myBoard, Board rivalBoard, SpellTrapCardInUse thisCard) {

    }

    public void deactiveEffect(Board myBoard, Board rivalBoard, SpellTrapCardInUse thisCard) {

    }

    public void destroyThis(Board myBoard, Board rivalBoard, SpellTrapCardInUse thisCard) {

    }

    public void spinCard() {

    }


    public boolean areEffectPreparationsDone() {
        return true;//todo
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
