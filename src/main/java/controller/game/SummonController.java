/* summon controller gets a preCard, a board, and a cell to put the card in. It checks the summon type, handles the tribute and stuff! */

package controller.game;


import exceptions.*;
import model.Board;
import model.Player;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterCardType;
import model.card.monster.MonsterManner;
import model.card.monster.PreMonsterCard;
import view.messageviewing.Print;
import view.messageviewing.SuccessfulAction;

import java.util.ArrayList;

public class SummonController {
    private final Monster monster;
    private int numOfNormalSummons;
    private final RoundController controller;
    private MonsterCardInUse monsterCardInUse;
    private final Board board;
    ArrayList<CardInUse> summonedCards; //it is generated in the main phase calling this class

    public SummonController(MonsterCardInUse monsterCardInUse, Monster monster, RoundController controller, ArrayList<CardInUse> summonedCards) {
        this.monster = monster;
        this.numOfNormalSummons = 0;
        this.controller = controller;
        this.monsterCardInUse = monsterCardInUse;
        this.board = controller.getCurrentPlayerBoard();
        this.summonedCards = summonedCards;
    }

    public void normal() throws AlreadyDoneAction, NotEnoughTributes {
        if (numOfNormalSummons != 0) throw new AlreadyDoneAction("summoned");
        //todo: sometimes the monster's level shouldn't be checked(special effects)
        if (monster.getLevel() >= 5) tributeSummon(monster);
        else putMonsterInUse(monster, false, this.monsterCardInUse, this.summonedCards);
        numOfNormalSummons++;
    }

    private void tributeSummon(Monster monster) throws NotEnoughTributes {
        int tributesNeeded = findNumOfTributes(monster);
        if (board.getNumOfAvailableTributes() < tributesNeeded) {
            throw new NotEnoughTributes();
        }
        for (int i = 0; i < tributesNeeded; i++) {
            String address = DuelMenuController.askQuestion("Enter the index of a card to tribute:");
            try {
                payTributeFromBoard(address);
            } catch (InvalidTributeAddress invalidAddress) {
                if (address.equals("cancel")) return;
                Print.print(invalidAddress.getMessage());
                i--;
            }
        }
        monsterCardInUse = (MonsterCardInUse) board.getFirstEmptyCardInUse(true);
        putMonsterInUse(monster, false, this.monsterCardInUse, this.summonedCards);
    }

    public void ritualSummon(ArrayList<MonsterCardInUse> tributes) throws CantDoActionWithCard {
        PreMonsterCard preMonster = (PreMonsterCard) monster.getPreCardInGeneral();
        if (!preMonster.getType().equals(MonsterCardType.RITUAL)) throw new CantDoActionWithCard("ritual summon");
        for (MonsterCardInUse tribute : tributes) {
            controller.sendToGraveYard(tribute);
        }
        MonsterManner monsterManner = controller.getDuelMenuController().getRitualManner();
        putMonsterInUse(monster, true, this.monsterCardInUse, summonedCards);
        switch (monsterManner) { //todo: it should be a function inside the monster itself( because watchers watch this!)
            case DEFENSIVE_OCCUPIED:
                monsterCardInUse.setFaceUp(true);
                monsterCardInUse.setInAttackMode(false);
                break;
            case OFFENSIVE_OCCUPIED:
                monsterCardInUse.setFaceUp(true);
                monsterCardInUse.setInAttackMode(true);
                break;
        }
    }

    private static void putMonsterInUse(Monster monster, boolean isSpecial, MonsterCardInUse monsterCardInUse, ArrayList<CardInUse> summonedCards) {
        if (monsterCardInUse == null || summonedCards == null) return;
        monsterCardInUse.setInAttackMode(true);
        monsterCardInUse.setFaceUp(true);
        monsterCardInUse.setThisCard(monster);
        if (!isSpecial) summonedCards.add(monsterCardInUse);
        new SuccessfulAction("", "summoned");
        //todo : after that, all of the watchers in the world should be called! because some of them may start wathcing us;
    }

    private int findNumOfTributes(Monster monster) {
        return monster.getNumOfNeededTributes();
    }

    private void payTributeFromBoard(String address) throws InvalidTributeAddress {
        int tributeIndex;
        try {
            tributeIndex = Integer.parseInt(address);
        } catch (Exception e) {
            throw new InvalidTributeAddress();
        }
        if (tributeIndex < 1 || tributeIndex > 5) throw new InvalidTributeAddress();
        Monster tributeMonster = (Monster) board.getMonsterZone()[tributeIndex - 1].getThisCard();
        if (tributeMonster == null) throw new InvalidTributeAddress();
        controller.sendToGraveYard(board.getMonsterZone()[tributeIndex - 1]);
    }


    //todo: its not good because it is static , (kollan it should be changed)
    public static void specialSummon(Monster monster, Player player) throws BeingFull {
        if (player == null || monster == null) return;
        Board playerBoard = player.getBoard();
        MonsterCardInUse monsterCardInUse = (MonsterCardInUse) playerBoard.getFirstEmptyCardInUse(true);
        if (monsterCardInUse == null) throw new BeingFull("monster card zone");
        putMonsterInUse(monster, true, monsterCardInUse, null);
    }
}

