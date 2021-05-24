package controller.game;


import exceptions.*;
import model.Player;
import model.card.CardType;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.CardIcon;
import model.card.spelltrap.PreSpellTrapCard;
import model.card.spelltrap.SpellTrap;
import view.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

class MainPhaseController {
    private Player player;
    private RoundController controller;
    private ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

    {
        summonedInThisPhase = new ArrayList<>();
    }

    public MainPhaseController(RoundController controller) {
        this.controller = controller;
        this.player = controller.getCurrentPlayer();
    }

    private PreCard getSelectedPossiblePreCard(String action) throws NoSelectedCard, CantDoActionWithCard {
        if (controller.isAnyCardSelected()) {
            PreCard selectedCard = this.controller.getSelectedPreCard();
            if (selectedCard == null) throw new CantDoActionWithCard(action);
            return selectedCard;
        } else throw new NoSelectedCard();
    }

    private CardInUse getSelectedPossibleCardInUse(String action) throws CantDoActionWithCard, NoSelectedCard {
        if (controller.isAnyCardSelected()) {
            CardInUse selectedCard = this.controller.getSelectedCardInUse();
            if (selectedCard == null) throw new CantDoActionWithCard(action);
            return selectedCard;
        } else throw new NoSelectedCard();
    }


    /* this function doesn't handle flip summon */
    public void summonMonster() throws NoSelectedCard, CantDoActionWithCard, AlreadyDoneAction, NotEnoughTributes, BeingFull {
        PreCard selectedCard = getSelectedPossiblePreCard("summon");
        /* checking the errors */

        if (!player.getHand().doesContainCard(selectedCard)
                || !(selectedCard instanceof PreMonsterCard)) {
            throw new CantDoActionWithCard("summon");
        }
        if (player.getBoard().isMonsterZoneFull()) throw new BeingFull("monster card zone");

        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        SummonController summonController = new SummonController(monsterCardInUse, (PreMonsterCard) selectedCard, controller, summonedInThisPhase);
        summonController.run();
    }

    public void changePosition(boolean isToBeAttackMode) throws NoSelectedCard, AlreadyDoneAction, AlreadyInWantedPosition, CantDoActionWithCard {

        CardInUse selectedCard = getSelectedPossibleCardInUse("change position of");
        if (!(selectedCard instanceof MonsterCardInUse)) throw new CantDoActionWithCard("change position of");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) selectedCard;

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

        CardInUse selectedCard = getSelectedPossibleCardInUse("change position of");
        if (!(selectedCard instanceof MonsterCardInUse)) throw new CantDoActionWithCard("change position of");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) selectedCard;

        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new CantDoActionWithCard("flip summon");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new CantDoActionWithCard("flip summon");

        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setAttacking(false);
        new SuccessfulAction("", "flip summoned");
    }

    public void setCard() throws NoSelectedCard, CantDoActionWithCard, BeingFull, AlreadyDoneAction {
        PreCard selectedCard = getSelectedPossiblePreCard("set");
        if (!player.getHand().doesContainCard(selectedCard)) throw new CantDoActionWithCard("set");
        if (selectedCard instanceof PreMonsterCard) setMonster((PreMonsterCard) selectedCard);
        if (selectedCard instanceof PreSpellTrapCard) setSpellTrap((PreSpellTrapCard) selectedCard);
    }

    private void setMonster(PreMonsterCard selectedCard) throws BeingFull, AlreadyDoneAction {
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");
        if (!summonedInThisPhase.isEmpty()) throw new AlreadyDoneAction("summoned/set");//todo: fine?( in tests )

        Monster monster = (Monster) selectedCard.newCard();
        monsterCardInUse.setThisCard(monster);
        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setInAttackMode(false);
        new SuccessfulAction("", "set");
    }

    private void setSpellTrap(PreSpellTrapCard selectedCard) throws BeingFull {
        SpellTrapCardInUse spellTrapCardInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
        if (spellTrapCardInUse == null) throw new BeingFull("spell card zone");

        SpellTrap spellTrap = (SpellTrap) selectedCard.newCard();
        spellTrapCardInUse.setThisCard(spellTrap);
        new SuccessfulAction("", "set");
        //todo: the spell or trap card in use should be face down. there wasn't any field for it. will it be needed?
    }

    public void activateEffect() throws NoSelectedCard, ActivateEffectNotSpell, CantDoActionWithCard, BeingFull, SpellPreparation, AlreadyActivatedEffect {
        PreSpellTrapCard preSpell;
        try { //if the card is pre ( e.g. in hand)
            PreCard selectedCard = getSelectedPossiblePreCard("activate effect");

            if (!selectedCard.getCardType().equals(CardType.SPELL)) throw new ActivateEffectNotSpell();
            preSpell = (PreSpellTrapCard) selectedCard;

            if (player.getHand().doesContainCard(preSpell)) {
                if (preSpell.getIcon().equals(CardIcon.FIELD)) {
                    //it is a field spell
                    //todo: we should send the previous field zone card to the graveyard and put this spell there
                } else {
                    //the spell is in hand and we should send it to the board
                    SpellTrapCardInUse spellInUseToPutCard = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
                    if (spellInUseToPutCard == null) throw new BeingFull("spell card zone");

                    SpellTrap spellCard = (SpellTrap) preSpell.newCard();
//                    if (!spellCard.areEffectPreparationsDone()) throw new SpellPreparation();//todo: the function needed an input
                    spellInUseToPutCard.setThisCard(spellCard);
                    spellCard.setActivated(true);
                    Print.print("spell activated");
                }
                return;
            }
        } catch (CantDoActionWithCard cantDoActionWithCard) {
            //the selected card wasn't pre. it is card in use. so it's in the board
            CardInUse cardInUse = getSelectedPossibleCardInUse("activate effect of");
            if (!(cardInUse instanceof SpellTrapCardInUse)) throw new ActivateEffectNotSpell();
            SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) cardInUse;
            SpellTrap spellCard = (SpellTrap) spellInUse.getThisCard();
            if (spellCard.isActivated()) throw new AlreadyActivatedEffect();
//            if (!spellCard.areEffectPreparationsDone()) throw new SpellPreparation(); //todo: the function needed an input

            spellCard.setActivated(true);
            Print.print("spell activated");
            return;
        }
        throw new CantDoActionWithCard("activate effect of");

    }

}
