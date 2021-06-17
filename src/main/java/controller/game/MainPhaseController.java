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
import view.messageviewing.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class MainPhaseController {
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
        if (!player.getHand().doesContainCard(selectedCard) || !(selectedCard instanceof PreMonsterCard))
            throw new CantDoActionWithCard("summon");
        if (player.getBoard().isMonsterZoneFull()) throw new BeingFull("monster card zone");

        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) player.getBoard().getFirstEmptyCardInUse(true);
        SummonController summonController = new SummonController(monsterCardInUse, (PreMonsterCard) selectedCard, controller, summonedInThisPhase);
        summonController.normal();
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
        CardInUse selectedCard = getSelectedPossibleCardInUse("flip summon");
        if (!(selectedCard instanceof MonsterCardInUse)) throw new CantDoActionWithCard("flip summon");
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) selectedCard;

        if (summonedInThisPhase.contains(monsterCardInUse))
            throw new CantDoActionWithCard("flip summon");
        if (monsterCardInUse.isFaceUp() || monsterCardInUse.isInAttackMode())
            throw new CantDoActionWithCard("flip summon");

        monsterCardInUse.setFaceUp(true);
        monsterCardInUse.setAttacking(true);
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

    public void activateEffect(boolean ofCurrentPlayer) throws NoSelectedCard, ActivateEffectNotSpell, CantDoActionWithCard, BeingFull, SpellPreparation, AlreadyActivatedEffect, NoCardFound, InvalidTributeAddress, NotAppropriateCard, CloneNotSupportedException, InvalidSelection, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {
        PreSpellTrapCard preSpell;
        try { //if the card is pre ( e.g. in hand)
            PreCard selectedCard = getSelectedPossiblePreCard("activate effect of");
            if (!selectedCard.getCardType().equals(CardType.SPELL)) throw new ActivateEffectNotSpell();
            preSpell = (PreSpellTrapCard) selectedCard;
            if (ofCurrentPlayer && player.getHand().doesContainCard(preSpell)) {
                activateEffectFromHand(preSpell);
                return;
            }
        } catch (CantDoActionWithCard cantDoActionWithCard) {
            //the selected card wasn't pre. it is card in use. so it's in the board
            activateEffectFromBoard(ofCurrentPlayer);
            return;
        }
        throw new CantDoActionWithCard("activate effect of");
    }

    private void activateEffectFromBoard(boolean ofCurrentPlayer) throws CantDoActionWithCard, NoSelectedCard, ActivateEffectNotSpell, AlreadyActivatedEffect, SpellPreparation, CloneNotSupportedException, BeingFull, NotAppropriateCard, InvalidSelection, InvalidTributeAddress, NoCardFound, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {
        CardInUse cardInUse = getSelectedPossibleCardInUse("activate effect of");
        if (!(cardInUse instanceof SpellTrapCardInUse)) throw new ActivateEffectNotSpell();
        SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) cardInUse;
        SpellTrap spellCard = (SpellTrap) spellInUse.getThisCard();

        if (spellCard.isActivated()) throw new AlreadyActivatedEffect();

        Player activator, activatorRival;
        if (ofCurrentPlayer) {
            activator = controller.getCurrentPlayer();
            activatorRival = controller.getRival();
        } else {
            activator = controller.getRival();
            activatorRival = controller.getCurrentPlayer();
        }

        if (!spellCard.areEffectPreparationsDone(activator, activatorRival, spellInUse, controller)) {
            if (spellCard.getName().equals("Advanced Ritual Art")) throw new InvalidRitualPreparations();
            throw new SpellPreparation();
        }

        spellCard.activateEffect(activator, activatorRival, spellInUse, controller);
        if (spellCard.isShouldDieAfterActivated())
            this.controller.sendToGraveYard(spellInUse);

//            Print.print("spell activated"); //todo: check if needed
    }

    private void activateEffectFromHand(PreSpellTrapCard preSpell) throws BeingFull, SpellPreparation, NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction, CantDoActionWithCard {
        if (preSpell == null) return;
        if (preSpell.getIcon().equals(CardIcon.FIELD)) {
            //it is a field spell inside hand and we want to send it to the field zone
            PreCard fieldSpell = player.getBoard().getFieldCard();
            if (fieldSpell != null) {
                controller.sendToGraveYard(fieldSpell);
            }
            player.getBoard().setFieldCard(preSpell);
            //todo: should I print anything? , is the effect of the field spell checked automatically or should I add it to sth?
        } else {
            //the spell is in hand and we should send it to the board and activate it
            SpellTrapCardInUse spellInUseToPutCard = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
            if (spellInUseToPutCard == null) throw new BeingFull("spell card zone");
            SpellTrap spellCard = (SpellTrap) preSpell.newCard();

            if (!spellCard.areEffectPreparationsDone(controller.getCurrentPlayer(), controller.getRival(), spellInUseToPutCard, controller)) {
                if (spellCard.getName().equals("Advanced Ritual Art")) throw new InvalidRitualPreparations();
                throw new SpellPreparation();
            }
            spellInUseToPutCard.setThisCard(spellCard);
            spellCard.activateEffect(controller.getCurrentPlayer(), controller.getRival(), spellInUseToPutCard, controller);
//            Print.print("spell activated"); //todo: check if needed
        }
    }


    //returns true if the ritual summon is cancelled
    public boolean handleRitualSummon() throws BeingFull, CantDoActionWithCard {
//        PreMonsterCard monster = this.controller.getDuelMenuController().getRitualSummonCommand();
        Monster monster = this.controller.getDuelMenuController().getRitualSummonCommand();
        if (monster == null) return true;
        if (controller.getDuelMenuController().askToEnterSummon()) return true;
        ArrayList<MonsterCardInUse> tributeMonsters;
        while (true) {
            tributeMonsters = this.controller.getDuelMenuController().getTributes();
            if (tributeMonsters == null) return true;
            if (areTributeLevelsFine(tributeMonsters, monster.getLevel())) break;
            Print.print("Selected monsters don’t match with ritual summon requirements!");
        }
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) controller.getCurrentPlayer().getBoard().getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");

        SummonController summonController = new SummonController(monsterCardInUse, monster, controller, this.summonedInThisPhase);
        summonController.ritualSummon(tributeMonsters);
        return false;
    }

    private boolean areTributeLevelsFine(ArrayList<MonsterCardInUse> tributeMonsters, int level) {
        if (tributeMonsters == null) return false;
        ArrayList<Integer> levels = new ArrayList<>();
        for (MonsterCardInUse monsterCardInUse : tributeMonsters) {
            Card card = monsterCardInUse.getThisCard();
            if (card instanceof Monster) {
                PreMonsterCard preMonsterCard = (PreMonsterCard) monsterCardInUse.getThisCard().getPreCardInGeneral();
                levels.add(preMonsterCard.getLevel());
            }
        }
        return canFindSubsetOfSum(levels, level);
    }


    private boolean canFindSubsetOfSum(ArrayList<Integer> set, int sum) {
        if (sum < 0) return false;
        if (sum == 0) return true;
        if (set.isEmpty()) return false;
        int element = set.get(0);
        if (element < 0) return false;
        set.remove(element);
        if (canFindSubsetOfSum(set, sum)) return true;
        return canFindSubsetOfSum(set, sum - element);
    }
}
