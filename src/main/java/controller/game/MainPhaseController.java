package controller.game;


import exceptions.*;
import model.Player;
import model.card.Card;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.CardIcon;
import model.card.spelltrap.SpellTrap;
import view.messageviewing.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class MainPhaseController {
    private final Player player;
    private final RoundController controller;


    private final ArrayList<CardInUse> summonedInThisPhase; //used in handling the flip summon. can be used for some effects of cards

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

        monsterCardInUse.setFaceUp(true);
        monsterCardInUse.setAttacking(true);
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
        if (!summonedInThisPhase.isEmpty()) throw new AlreadyDoneAction("summoned/set");//todo: fine?( in tests )

        monsterCardInUse.setACardInCell(selectedCard);
        monsterCardInUse.setFaceUp(false);
        monsterCardInUse.setInAttackMode(false);
        new SuccessfulAction("", "set");
    }

    private void setSpellTrap(SpellTrap selectedCard) throws BeingFull {
        SpellTrapCardInUse spellTrapCardInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
        if (spellTrapCardInUse == null) throw new BeingFull("spell card zone");

//        SpellTrap spellTrap = (SpellTrap) selectedCard.newCard();
        spellTrapCardInUse.setThisCard(selectedCard);
        spellTrapCardInUse.setFaceUp(false);
        new SuccessfulAction("", "set");
        //todo: the spell or trap card in use should be face down. but will it be needed?
    }

    public void activateEffect(boolean ofCurrentPlayer)
            throws NoSelectedCard, ActivateEffectNotSpell, CantDoActionWithCard, BeingFull, SpellPreparation, AlreadyActivatedEffect, NoCardFound, InvalidTributeAddress, NotAppropriateCard, CloneNotSupportedException, InvalidSelection, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {

        Card selectedCard = getSelectedCard();
        if (!selectedCard.getPreCardInGeneral().getCardType().equals(CardType.SPELL))
            throw new ActivateEffectNotSpell();
        if (ofCurrentPlayer && player.getHand().doesContainCard(selectedCard)) {
            activateEffectFromHand((SpellTrap) selectedCard);
        } else activateEffectFromBoard(ofCurrentPlayer);
    }

    private void activateEffectFromBoard(boolean ofCurrentPlayer)
            throws CantDoActionWithCard, NoSelectedCard, ActivateEffectNotSpell, AlreadyActivatedEffect, SpellPreparation, CloneNotSupportedException, BeingFull, NotAppropriateCard, InvalidSelection, InvalidTributeAddress, NoCardFound, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction {
        Card selectedCard = getSelectedCard();
        if (!(selectedCard instanceof SpellTrap)) throw new ActivateEffectNotSpell();
//        SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) cardInUse;
        SpellTrap spellCard = (SpellTrap) selectedCard;

        if (spellCard.isActivated()) throw new AlreadyActivatedEffect(); //todo: check with hasti

        Player activator, activatorRival;
        if (ofCurrentPlayer) {
            activator = controller.getCurrentPlayer();
            activatorRival = controller.getRival();
        } else {
            activator = controller.getRival();
            activatorRival = controller.getCurrentPlayer();
        }

//        if (!spellCard.areEffectPreparationsDone(activator, activatorRival, spellInUse, controller)) {
//            if (spellCard.getName().equals("Advanced Ritual Art")) throw new InvalidRitualPreparations();
//            throw new SpellPreparation();
//        }
//
//        spellCard.activateEffect(activator, activatorRival, spellInUse, controller);
//        if (spellCard.isShouldDieAfterActivated())
//            this.controller.sendToGraveYard(spellInUse); //todo: check with hasti!

//            Print.print("spell activated"); //todo: check if needed
    }

    //the input is either a filed spell or a spell that we should first set and then acivate
    private void activateEffectFromHand(SpellTrap spell) throws BeingFull, SpellPreparation, NotAppropriateCard, NoSelectedCard, InvalidTributeAddress, NoCardFound, InvalidSelection, CloneNotSupportedException, InvalidRitualPreparations, PreparationsNotChecked, NotEnoughTributes, AlreadyDoneAction, CantDoActionWithCard {
        if (spell == null) return;
        if (spell.getMyPreCard().getIcon().equals(CardIcon.FIELD)) {
            //it is a field spell inside hand and we want to send it to the field zone
            CardInUse fieldCell = player.getBoard().getFieldCard();
            if (fieldCell != null) {
                fieldCell.sendToGraveYard();
            }
            player.getBoard().getFieldCard().setACardInCell(spell);
        } else {
            //the spell is in hand and we should send it to the board and activate it
            SpellTrapCardInUse spellInUse = (SpellTrapCardInUse) player.getBoard().getFirstEmptyCardInUse(false);
            if (spellInUse == null) throw new BeingFull("spell card zone");

//            if (!spell.areEffectPreparationsDone(controller.getCurrentPlayer(), controller.getRival(), spellInUse, controller)) {
//                if (spellCard.getName().equals("Advanced Ritual Art")) throw new InvalidRitualPreparations();
//                throw new SpellPreparation();
//            }
            spellInUse.setACardInCell(spell);
//            spellCard.activateEffect(controller.getCurrentPlayer(), controller.getRival(), spellInUse, controller); //todo: check with hasti
            Print.print("spell activated"); //todo: check if needed
        }
    }


    //returns true if the ritual summon is cancelled
    public boolean handleRitualSummon() throws BeingFull, CantDoActionWithCard {
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
