package model;

import controller.game.DuelMenuController;
import exceptions.InvalidSelection;
import exceptions.NoCardFound;
import lombok.Getter;
import lombok.Setter;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.watchers.Watcher;

import java.util.ArrayList;

@Getter
@Setter
public class Board {
    GraveYard graveYard;

    private MonsterCardInUse[] monsterZone;
    private SpellTrapCardInUse[] spellTrapZone;
    private SpellTrapCardInUse fieldCard;   //TODO !!! check updated with new program

    //watchers which are only available in areWatchingMe Cards. -> scanner, changeOfHearts
    private ArrayList<Watcher> freeBuiltInWatchers;
    private int additionalAttack;
    private int additionalDefense;
    private Player owner;
    private Phase myPhase;
    private Deck deckInUse;
    private DuelMenuController controller;

    {
        this.monsterZone = new MonsterCardInUse[5];
        this.spellTrapZone = new SpellTrapCardInUse[5];
        newCells();
        graveYard = new GraveYard();
    }

    //TODO !!! negar please
    //TODO set myPhase at start of game
    public void update(){
        myPhase = myPhase.goToNextPhase();

        for (Watcher freeWatcher : freeBuiltInWatchers) {
            freeWatcher.update(myPhase);
        }

        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            for (Watcher builtInWatcher : monsterCardInUse.thisCard.builtInWatchers) {
                builtInWatcher.update(myPhase);
            }
        }

        for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
            for (Watcher builtInWatcher : spellTrapCardInUse.thisCard.builtInWatchers) {
                builtInWatcher.update(myPhase);
            }
        }

        for (Watcher builtInWatcher : fieldCard.thisCard.builtInWatchers) {
            builtInWatcher.update(myPhase);
        }
    }


    public GraveYard getGraveYard() {
        return graveYard;
    }

    private void newCells() {
        for (int i = 0; i < 5; i++) {
            monsterZone[i] = new MonsterCardInUse(this);
            spellTrapZone[i] = new SpellTrapCardInUse(this);
        }
    }

    public void addToAllMonsterCellsAttack(int amount) { //amount can be negative too
        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            monsterCardInUse.addToAttack(amount);
        }
    }

    public boolean isMonsterZoneFull() {
        return getFirstEmptyCardInUse(true) == null;
    }

    public boolean isSpellTrapZoneFull() {
        return getFirstEmptyCardInUse(false) == null;
    }

    public void addToAdditionalAttack(int amount) {     //maybe not useful
        additionalAttack += amount;
    }

    public CardInUse getFirstEmptyCardInUse(boolean isMonster) {
        CardInUse[] zone;
        if (isMonster) zone = this.monsterZone;
        else zone = this.spellTrapZone;
        for (CardInUse cardInUse : zone) {
            if (cardInUse.isCellEmpty())
                return cardInUse;
        }
        return null;
    }

    public int getNumOfAvailableTributes() {
        int counter = 0;
        for (MonsterCardInUse monsterCardInUse : this.monsterZone) {
            if (monsterCardInUse.getThisCard() != null) counter++;
        }
        return counter;
    }

    /* used for selecting cards*/
    public CardInUse getCardInUse(int index, boolean isMonster) throws NoCardFound, InvalidSelection {
        if (index < 1 || index > 5) throw new InvalidSelection();
        CardInUse cardInUse;
        if (isMonster) cardInUse = monsterZone[index - 1];
        else cardInUse = spellTrapZone[index - 1];
        if (cardInUse == null) throw new NoCardFound();
        else return cardInUse;
    }
}
