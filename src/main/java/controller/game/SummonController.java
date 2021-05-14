package controller.game;

import exeptions.AlreadyDoneAction;
import exeptions.NotEnoughTributes;
import model.Cell;
import model.card.PreCard;
import model.card.monster.Monster;
import model.card.monster.MonsterManner;

import java.util.ArrayList;

public class SummonController {
    private Cell cell;
    private PreCard preCard;
    private SummonType summonType;
    private int numOfNormalSummons;
    private GamePlayController controller;

    public SummonController(Cell cell, PreCard preCard, SummonType summonType, GamePlayController controller) {
        this.cell = cell;
        this.summonType = summonType;
        this.preCard = preCard;
        this.numOfNormalSummons = 0;
        this.controller = controller;
    }

    public void run() throws AlreadyDoneAction, NotEnoughTributes {
        Monster monster = (Monster) preCard.newCard(preCard.getName()); //todo : why should the newCard function get a string as input?

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
        } else if (preCard.getLevel() >= 5) tributeSummon(monster);
        else {
            //todo: if( monster.isNormalSummonPossible) or something like that
            monster.setManner(MonsterManner.OFFENSIVE_OCCUPIED);
            cell.setCard(monster);
        }
        numOfNormalSummons++; //todo: is summoning with tribute ( not ritual ) also counted here ?
    }

    private void flip(Monster monster) {

    }

    private void ritual(Monster monster) {

    }


    private void tributeSummon(Monster monster) throws NotEnoughTributes {
        int tributesNeeded = findNumOfTributes(monster);
        if (controller.getCurrentPlayerBoard().getNumOfAvailableTributes() < tributesNeeded) {
            throw new NotEnoughTributes();
        }
        ArrayList<String> tributeAddresses = new ArrayList<>();
        for (int i = 0; i < tributesNeeded; i++) {
            String address = DuelMenuController.askQuestion("Enter the address of a card to tribute:");
//            try{
//                int index = Integer.parseInt(address);
//            }
        }
//        tribute(tributeAddresses);
//        monster.setManner();
    }

    private int findNumOfTributes(Monster monster) {
        if (preCard.getLevel() <= 4) return 0;
        if (preCard.getLevel() <= 6) return 1;
        return 2;
        //todo: some cards need more tributes
    }
}
