package controller.game;


import exceptions.*;
import model.Player;
import model.card.Card;
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
    private GamePlayController controller;
    private ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

    {
        summonedInThisPhase = new ArrayList<>();
    }

    public MainPhaseController(GamePlayController controller) {
        this.controller = controller;
        this.player = controller.getCurrentPlayer();
    }

    private PreCard getSelectedPreCard() throws NoSelectedCard {
        PreCard selectedCard = this.controller.getCurrentPlayerSelectedCard();
        if (selectedCard == null) throw new NoSelectedCard();
        return selectedCard;
    }

    /* only used for changing the position of a monster card in the monster zone */
    private MonsterCardInUse getSelectedMonsterZoneCardInUse(PreCard selectedCard) throws UnableToChangePosition {
        if (!(selectedCard instanceof PreMonsterCard)) throw new UnableToChangePosition();
        MonsterCardInUse[] zone = player.getBoard().getMonsterZone();
        for (MonsterCardInUse monsterCardInUse : zone) {
            if (monsterCardInUse.getThisCard().getPreCardInGeneral().equals(selectedCard)) return monsterCardInUse;
        }
        throw new UnableToChangePosition();
    }


    /* this function doesn't handle flip summon */
    public void summonMonster() throws NoSelectedCard, CantDoActionWithCard, AlreadyDoneAction, NotEnoughTributes, BeingFull {
        PreCard selectedCard = getSelectedPreCard();
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


    public void changePosition(boolean isToBeAttackMode) throws NoSelectedCard, UnableToChangePosition, AlreadyDoneAction, AlreadyInWantedPosition {
        PreCard selectedCard = getSelectedPreCard();
        MonsterCardInUse monsterCardInUse = getSelectedMonsterZoneCardInUse(selectedCard);

        if (monsterCardInUse.isPositionChanged()) throw new AlreadyDoneAction("changed this card position");

        if (isToBeAttackMode) {
            if (monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(true);
        } else {
            if (!monsterCardInUse.isInAttackMode() || !monsterCardInUse.isFaceUp()) throw new AlreadyInWantedPosition();
            monsterCardInUse.setInAttackMode(false);
        }
    }

    public void flipSummon() throws NoSelectedCard, UnableToChangePosition, CantDoActionWithCard {
        PreCard selectedCard = getSelectedPreCard();
        MonsterCardInUse monsterCardInUse = getSelectedMonsterZoneCardInUse(selectedCard);
        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new CantDoActionWithCard("flip summon");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new CantDoActionWithCard("flip summon");

        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setAttacking(false);
        new SuccessfulAction("", "flip summoned");
    }

    public void setCard() throws NoSelectedCard, CantDoActionWithCard, BeingFull, AlreadyDoneAction {
        PreCard selectedCard = getSelectedPreCard();
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

        Card card = selectedCard.newCard();
        spellTrapCardInUse.setThisCard(card);
        new SuccessfulAction("", "set");
        //todo: the spell or trap card in use should be face down. there wasn't any field for it. will it be needed?
    }

    private SpellTrapCardInUse getSelectedSpellCardInUse(PreCard selectedCard) throws NoSelectedCard {
        if (selectedCard == null) throw new NoSelectedCard();
        SpellTrapCardInUse[] spellZone = player.getBoard().getSpellTrapZone();
        for (SpellTrapCardInUse spellTrapCardInUse : spellZone) {
            if (spellTrapCardInUse.getThisCard().getPreCardInGeneral().equals(selectedCard)) return spellTrapCardInUse;
        }
        return null;
    }

    public void activateEffect() throws NoSelectedCard, ActivateEffectNotSpell, CantDoActionWithCard, BeingFull, SpellPreparation, AlreadyActivatedEffect {
        PreCard selectedCard = getSelectedPreCard();
        if (!selectedCard.getCardType().equals(CardType.SPELL)) throw new ActivateEffectNotSpell();
        PreSpellTrapCard preSpell = (PreSpellTrapCard) selectedCard;
        SpellTrapCardInUse spellInUse = getSelectedSpellCardInUse(selectedCard);


//        //checking if the spell is in hand
        if (player.getHand().doesContainCard(preSpell)) {
            if (!preSpell.getIcon().equals(CardIcon.FIELD)) {
                //the card is in the hand. it goes to the board and gets activated
                if (player.getBoard().getFirstEmptyCardInUse(false) == null) throw new BeingFull("spell card zone");
                //todo : does the such a spell need any special preparation?

                SpellTrapCardInUse spellInUseToPutCard = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
                spellInUseToPutCard.setThisCard(preSpell.newCard());


                Print.print("spell activated");
            } else {
                //its the field spell. we should handle it!
            }
        } else if (spellInUse != null) {
            //means the spell is in the board
            SpellTrap spellCard = (SpellTrap) spellInUse.getThisCard();
            if (spellCard.isActivated()) throw new AlreadyActivatedEffect();
            if (!spellCard.areEffectPreparationsDone()) {
                throw new SpellPreparation();
            }
        } else throw new CantDoActionWithCard("activate effect of");


    }

    private boolean shouldGoToBoard(PreSpellTrapCard preSpell) {
        return true;//todo
    }
}
