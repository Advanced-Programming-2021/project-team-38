package controller.game;


import exeptions.AlreadyDoneAction;
import exeptions.NotEnoughTributes;
import model.Board;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import view.messageviewing.Print;

import java.util.ArrayList;

public class SummonController {
    private final PreMonsterCard preMonster;
    private final SummonType summonType;
    private int numOfNormalSummons;
    private final GamePlayController controller;
    private MonsterCardInUse monsterCardInUse;
    private Board board;

    public SummonController(MonsterCardInUse monsterCardInUse, PreMonsterCard preMonster, SummonType summonType, GamePlayController controller) {
        this.summonType = summonType;
        this.preMonster = preMonster;
        this.numOfNormalSummons = 0;
        this.controller = controller;
        this.monsterCardInUse = monsterCardInUse;
        this.board = controller.getCurrentPlayerBoard();
    }

    public void run() throws AlreadyDoneAction, NotEnoughTributes {
        Monster monster = (Monster) preMonster.newCard();

        switch (summonType) {
            case FLIP:
                flip(monster);
                break;
            case NORMAL:
                normal(monster);
                break;
        }
    }


    private void normal(Monster monster) throws AlreadyDoneAction, NotEnoughTributes {
        if (numOfNormalSummons != 0) {
            throw new AlreadyDoneAction("summoned");
        } else if (preMonster.getLevel() >= 5) tributeSummon(monster);
        else {
            //todo: if( monster.isNormalSummonPossible) or something like that
            monsterCardInUse.setAttackPosition(true);
            monsterCardInUse.setFaceUp(true);
            monsterCardInUse.setThisCard(monster);
        }
        numOfNormalSummons++; //todo: is summoning with tribute ( not ritual ) also counted here ?
    }

    private void flip(Monster monster) {

    }

    private void ritual(Monster monster) {

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
                tributeIndexes.add(index);
            } catch (Exception e) {
                Print.print("Invalid Address");
                i--;
            }
        }
        tribute(tributeIndexes);
        monsterCardInUse = (MonsterCardInUse) board.getFirstEmptyCardInUse(true);
        monsterCardInUse.setAttackPosition(true);
        monsterCardInUse.setFaceUp(true);
        monsterCardInUse.setThisCard(monster);

    }

    private int findNumOfTributes(Monster monster) {
        if (preMonster.getLevel() <= 4) return 0;
        if (preMonster.getLevel() <= 6) return 1;
        return 2;
        //todo: some cards need more tributes
    }

    private void tribute(ArrayList<Integer> tributeIndexes) {
        for (Integer index : tributeIndexes) {
            Monster tributeMonster = (Monster) board.getMonsterZone()[index].getThisCard();
            tributeMonster.sendToGraveYard();
        }
    }
}
