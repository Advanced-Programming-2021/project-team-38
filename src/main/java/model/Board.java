package model;

import controller.game.DuelMenuController;
import lombok.Getter;
import lombok.Setter;
import model.Enums.Phase;
import model.card.Card;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.cardinusematerial.SpellTrapCardInUse;
import model.card.monster.Monster;
import model.card.spelltrap.SpellTrap;
import model.watchers.Watcher;
import view.exceptions.InvalidSelection;
import view.exceptions.NoCardFound;

import java.util.ArrayList;

@Getter
@Setter
public class Board {
    private GraveYard graveYard;

    private MonsterCardInUse[] monsterZone;
    private SpellTrapCardInUse[] spellTrapZone;
    private SpellTrapCardInUse fieldCell;   //TODO !!! check updated with new program

    //watchers which are only available in areWatchingMe Cards. -> scanner, changeOfHearts
    private ArrayList<Watcher> freeBuiltInWatchers;
    private int additionalAttack;
    private int additionalDefense;
    private Player owner;
    private Phase myPhase;
    private DuelMenuController controller;

    {
        monsterZone = new MonsterCardInUse[5];
        spellTrapZone = new SpellTrapCardInUse[5];
        graveYard = new GraveYard();
    }

    public Board(Player owner) {
        this.owner = owner;
        newCells();
    }

    //TODO !!! negar please
    //TODO set myPhase at start of game
    public void update() {
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

        for (Watcher builtInWatcher : fieldCell.thisCard.builtInWatchers) {
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
    //the index is between 1 and 5
    public CardInUse getCardInUse(int index, boolean isMonster) throws NoCardFound, InvalidSelection {
        if (index < 1 || index > 5) throw new InvalidSelection();
        CardInUse cardInUse;
        if (isMonster) cardInUse = monsterZone[index - 1];
        else cardInUse = spellTrapZone[index - 1];
        if (cardInUse == null) throw new NoCardFound();
        else return cardInUse;
    }

    public CardInUse getCellOf(Card card) {
        if (card instanceof Monster) {
            for (MonsterCardInUse monsterCardInUse : monsterZone) {
                if (monsterCardInUse.getThisCard().equals(card)) return monsterCardInUse;
            }
        } else if (card instanceof SpellTrap) {
            for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
                if (spellTrapCardInUse.getThisCard().equals((SpellTrap) card)) return spellTrapCardInUse;
            }
        }
        return null;
    }

    public void updateAfterAction() {

    }

    @Override
    public String toString() {
        if (controller.getRoundController().getCurrentPlayer() == this.owner) //todo: fine?
            return myTurnString();
        else
            return rivalTurnString();
    }

    private String myTurnString() {
        StringBuilder myBoard = new StringBuilder();
        String horizontalBoarder = "_".repeat(30);
        myBoard.append(horizontalBoarder).append("\n");

        if (fieldCell.thisCard == null) myBoard.append("E ");
        else myBoard.append("O ");
        myBoard.append("\t".repeat(6));

        myBoard.append(makeTwoBits(graveYard.getNumOfCards())).append("\n");

        myBoard.append("  \t");
        for (MonsterCardInUse monsterCardInUse : monsterZone) {
            myBoard.append(monsterCardInUse.toString()).append("\t");
        }
        myBoard.append("\n  \t");
        for (SpellTrapCardInUse spellTrapCardInUse : spellTrapZone) {
            myBoard.append(spellTrapCardInUse.toString()).append("\t");
        }

        myBoard.append("\n").append("\t".repeat(6)).append(makeTwoBits(owner.getDeck().getNumOfMainCards()));
        myBoard.append(owner.getHand().toString()).append("\n");
        myBoard.append("nick name : ").append(owner.getName()).append("life point : ").append(owner.getLifePoint());
        myBoard.append("\n").append(horizontalBoarder);

        return myBoard.toString();
    }


    private String rivalTurnString() {
        return null;
    }

    private String makeTwoBits(int number) {
        String toReturn = String.valueOf(number);
        if (number < 10) toReturn = "0" + number;
        return toReturn;
    }


    public static void main(String[] args) {
        User user = new User("negar", "123", "negi");
        Player player = new Player(user, null);
        MonsterCardInUse[] monsterZone = player.getBoard().getMonsterZone();
        monsterZone[0].setThisCard(new Monster(PreCard.findCard("Command Knight")));
        monsterZone[2].setThisCard(new Monster(PreCard.findCard("Command Knight")));
        monsterZone[1].setThisCard(new Monster(PreCard.findCard("Command Knight")));
        System.out.println(player.getBoard());
    }
}

