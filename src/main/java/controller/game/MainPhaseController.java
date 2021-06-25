package controller.game;


import model.Player;
import model.card.Card;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.CardIcon;
import model.card.spelltrap.SpellTrap;
import view.exceptions.*;
import view.messageviewing.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class MainPhaseController {
    private final Player player;
    private final RoundController controller;


    public final ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

    {
        summonedInThisPhase = new ArrayList<>();
    }

    public MainPhaseController(RoundController controller) {
        this.controller = controller;
        this.player = controller.getCurrentPlayer();
    }


    public ArrayList<CardInUse> getSummonedInThisPhase() {
        return summonedInThisPhase;
    }

    private Card getSelectedCard() throws NoSelectedCard {
        if (this.controller.getSelectedCard() == null) throw new NoSelectedCard();
        return this.controller.getSelectedCard();
    }

    /* this function doesn't handle flip summon */
    public void summonMonster() throws NoSelectedCard, CantDoActionWithCard, AlreadyDoneAction, NotEnoughTributes, BeingFull {
        Card selectedCard = getSelectedCard();
        /* checking the errors */
        if (!player.getHand().doesContainCard(selectedCard) || !(selectedCard instanceof Monster))
            throw new CantDoActionWithCard("summon");
        if (player.getBoard().isMonsterZoneFull()) throw new BeingFull("monster card zone");

        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        SummonController summonController = new SummonController(monsterCardInUse, (Monster) selectedCard, controller, summonedInThisPhase);
        summonController.normal();
    }

    public void changePosition(boolean isToBeAttackMode)
            throws NoSelectedCard, AlreadyDoneAction, AlreadyInWantedPosition, CantDoActionWithCard {

        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof Monster)) throw new CantDoActionWithCard("change position of");
        CardInUse cardInUse = player.getBoard().getCellOf(selectedCard);
        if (!(cardInUse instanceof MonsterCardInUse)) throw new CantDoActionWithCard("change position of");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) cardInUse;

        if (monsterCardInUse.isPositionChanged()) throw new AlreadyDoneAction("changed this card position");

        if (isToBeAttackMode) {
            if (monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(true);
        } else {
            if (!monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(false);
        }
    }

    public void flipSummon() throws NoSelectedCard, CantDoActionWithCard {
        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof Monster)) throw new CantDoActionWithCard("flip summon");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getCellOf(selectedCard);
        if (monsterCardInUse == null) throw new CantDoActionWithCard("flip summon");

        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new CantDoActionWithCard("flip summon");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new CantDoActionWithCard("flip summon");

        monsterCardInUse.flipSummon();
        new SuccessfulAction("", "flip summoned");
    }

    public void setCard() throws NoSelectedCard, CantDoActionWithCard, BeingFull, AlreadyDoneAction {
        Card selectedCard = getSelectedCard();
        if (!player.getHand().doesContainCard(selectedCard)) throw new CantDoActionWithCard("set");
        if (selectedCard instanceof Monster) setMonster((Monster) selectedCard);
        if (selectedCard instanceof SpellTrap) setSpellTrap((SpellTrap) selectedCard);
    }

    private void setMonster(Monster selectedCard) throws BeingFull, AlreadyDoneAction {
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");
        if (!summonedInThisPhase.isEmpty()) throw new AlreadyDoneAction("summoned/set");

        monsterCardInUse.setACardInCell(selectedCard);
        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setInAttackMode(false);
        new SuccessfulAction("", "set");
    }

    private void setSpellTrap(SpellTrap selectedCard) throws BeingFull {
        SpellTrapCardInUse spellTrapCardInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
        if (spellTrapCardInUse == null) throw new BeingFull("spell card zone");

        spellTrapCardInUse.setThisCard(selectedCard);
        spellTrapCardInUse.setFaceUp(false);
        new SuccessfulAction("", "set");
        //todo: the spell or trap card in use should be face down. but will it be needed?
    }

    //todo: I think "of currentplayer " isn't needed. check with hasti
    public void activateEffect(boolean ofCurrentPlayer)
            throws NoSelectedCard, ActivateEffectNotSpell, CantDoActionWithCard, BeingFull, SpellPreparation, AlreadyActivatedEffect, NoCardFound, InvalidTributeAddress, NotAppropriateCard, CloneNotSupportedException, InvalidSelection, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {

        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof SpellTrap)) throw new ActivateEffectNotSpell();
        if (ofCurrentPlayer && player.getHand().doesContainCard(selectedCard)) {
            activateEffectFromHand((SpellTrap) selectedCard);
        } else activateEffectFromBoard(ofCurrentPlayer);
    }

    private void activateEffectFromBoard(boolean ofCurrentPlayer)
            throws NoSelectedCard, ActivateEffectNotSpell, AlreadyActivatedEffect, SpellPreparation, CloneNotSupportedException, BeingFull, NotAppropriateCard, InvalidSelection, InvalidTributeAddress, NoCardFound, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {
        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof SpellTrap)) throw new ActivateEffectNotSpell();
        SpellTrap spellCard = (SpellTrap) selectedCard;
        SpellTrapCardInUse cardInUse = (SpellTrapCardInUse) this.controller.getSelectedCardInUse();

        if (spellCard.isActivated()) throw new AlreadyActivatedEffect();
        cardInUse.activateMyEffect();

        Print.print("spell " + spellCard.name + "  is activated");
    }

    //the input is either a field spell or a spell that we should first set and then activate
    private void activateEffectFromHand(SpellTrap spell) throws BeingFull {
        if (spell == null) return;
        if (spell.getMyPreCard().getIcon().equals(CardIcon.FIELD)) {
            //it is a field spell inside hand and we want to send it to the field zone
            CardInUse fieldCell = player.getBoard().getFieldCell();
            if (fieldCell != null) {
                fieldCell.resetCell();
                fieldCell.sendToGraveYard();
            }
            player.getBoard().getFieldCell().resetCell();
            player.getBoard().getFieldCell().setACardInCell(spell);

        } else {
            //the spell is in hand and we should send it to the board and activate it
            SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
            if (spellInUse == null) throw new BeingFull("spell card zone");

            spellInUse.setACardInCell(spell);
            spellInUse.activateMyEffect();
            Print.print("spell activated");
        }
    }
}
