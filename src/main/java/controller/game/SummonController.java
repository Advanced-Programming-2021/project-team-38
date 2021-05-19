package controller.game;


import exceptions.AlreadyDoneAction;
import exceptions.InvalidTributeAddress;
import exceptions.NotEnoughTributes;
import model.Board;
import model.Enums.ZoneName;
import model.Player;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import view.messageviewing.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class SummonController {
    private final PreMonsterCard preMonster;
    private int numOfNormalSummons;
    private final GamePlayController controller;
    private MonsterCardInUse monsterCardInUse;
    private Board board;
    ArrayList<CardInUse> summonedCards; //it is generated in the main phase calling this class


    public SummonController(MonsterCardInUse monsterCardInUse, PreMonsterCard preMonster, GamePlayController controller, ArrayList<CardInUse> summonedCards) {
        this.preMonster = preMonster;
        this.numOfNormalSummons = 0;
        this.controller = controller;
        this.monsterCardInUse = monsterCardInUse;
        this.board = controller.getCurrentPlayerBoard();
        this.summonedCards = summonedCards;
    }

    public void run() throws AlreadyDoneAction, NotEnoughTributes {
        Monster monster = (Monster) preMonster.newCard();
        //todo: if( monster.isNormalSummonPossible) or something like that:
        normal(monster);
    }


    private void normal(Monster monster) throws AlreadyDoneAction, NotEnoughTributes {
        if (numOfNormalSummons != 0) {
            throw new AlreadyDoneAction("summoned/set");
        } else if (preMonster.getLevel() >= 5) tributeSummon(monster);
        else {
            putMonsterInUse(monster);
        }
        numOfNormalSummons++; //todo: is summoning with tribute ( not ritual ) also counted here ?
    }

    private void ritual(Monster monster) {
        //todo:
        //check if the monster is ritual
        //getting the necessary card to tribute
        //handling the tribute
        //summoning finally
    }


    private void tributeSummon(Monster monster) throws NotEnoughTributes {
        int tributesNeeded = findNumOfTributes(monster);
        if (board.getNumOfAvailableTributes() < tributesNeeded) {
            throw new NotEnoughTributes();
        }
        ArrayList<Integer> tributeIndexes = new ArrayList<>();
        for (int i = 0; i < tributesNeeded; i++) {
            String address = DuelMenuController.askQuestion("Enter the index of a card to tribute:");
            try {
                int index = Integer.parseInt(address);
                tribute(index);
            } catch (InvalidTributeAddress invalidAddress) {
                if (address.equals("cancel")) return;
                Print.print(invalidAddress.getMessage());
                i--;
            }
        }
        monsterCardInUse = (MonsterCardInUse) board.getFirstEmptyCardInUse(true);
        putMonsterInUse(monster);

    }


    private void putMonsterInUse(Monster monster) {
        monsterCardInUse.setInAttackMode(true);
        monsterCardInUse.setFaceUp(true);
        monsterCardInUse.setThisCard(monster);
        this.summonedCards.add(monsterCardInUse);
        new SuccessfulAction("", "summoned");
    }

    private int findNumOfTributes(Monster monster) {
        if (preMonster.getLevel() <= 4) return 0;
        if (preMonster.getLevel() <= 6) return 1;
        return 2;
        //todo: some cards need more tributes
    }

    private void tribute(int tributeIndex) throws InvalidTributeAddress {
        if (tributeIndex < 1 || tributeIndex > 5) throw new InvalidTributeAddress();
        Monster tributeMonster = (Monster) board.getMonsterZone()[tributeIndex].getThisCard();
        if (tributeMonster == null) throw new InvalidTributeAddress();
        tributeMonster.sendToGraveYard();
    }

    public static void specialSummonPreCard(GamePlayController gamePlay,
                                            PreCard preCard, Player player, ZoneName zoneName) throws CloneNotSupportedException {
        Board playerBoard = player.getBoard();
        switch (zoneName) {
            case MONSTER:
                for (CardInUse cell : playerBoard.getMonsterZone()) {
                    if (cell.isCellEmpty()) {
                        cell.setACardInThisCell(preCard);
                        break;
                    }
                }
                break;
            case SPELL:
                for (CardInUse cell : playerBoard.getSpellTrapZone()) {
                    if (cell.isCellEmpty()) {
                        cell.setACardInThisCell(preCard);
                        break;
                    }
                }
                break;
                //TODO continue
        }
    }
}
