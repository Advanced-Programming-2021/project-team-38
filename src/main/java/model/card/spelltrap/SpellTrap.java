package model.card.spelltrap;

import controller.game.GamePlayController;
import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Player;
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

    public boolean areEffectPreparationsDone(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {
        return true;
        return false; //todo: returned something to be able to run!
    }

    public void activateEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {

    }

    public void deactiveEffect(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {

    }

    public void destroyThis(Player myPlayer, Player rivalPlayer, SpellTrapCardInUse thisCard, GamePlayController gamePlay) {

    }

    public void spinCard() {

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
