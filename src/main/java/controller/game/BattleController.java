package controller.game;

import model.Board;
import view.messageviewing.Winner;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

public class BattleController {
    int differenceOfPoints;
    private Board attackerBoard, preyBoard;
    private MonsterCardInUse attacker, preyCard;

    public BattleController(Board attackerBoard, Board preyBoard, MonsterCardInUse attacker, MonsterCardInUse preyCard) {
        this.attackerBoard = attackerBoard;
        this.preyBoard = preyBoard;
        this.attacker = attacker;
        this.preyCard = preyCard;
    }

    private void run() {
        //TODO check to see if can attack : attacker is not in defense mode
        if (((Monster) preyCard.getThisCard()).canReceiveAttack(attackerBoard, preyBoard, attacker, preyCard)) {
            preyCard.changeIsAttacked();
            attacker.changeIsAttacking();

            int attackerAttack = attacker.getAttack();
            int preyPoint = preyCard.appropriatePointAtBattle();
            differenceOfPoints = Math.abs(attackerAttack - preyPoint);
            if (attackerAttack > preyPoint) {
                attackerWins();
            } else if (attackerAttack == preyPoint) {
                noneWins();
            } else {
                preyWins();
            }
        }
    }

    private void attackerWins() {
        if (preyCard.isInAttackMode()) {
            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_AO_WINS, differenceOfPoints, attacker);
        } else {
            Winner winner = preyCard.isFaceUp() ? Winner.AGAINST_DO_WINS : Winner.AGAINST_DH_WINS;
            if (!preyCard.isFaceUp())    preyCard.changePosition();
            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, 0);
            Winner.setWinner(winner, differenceOfPoints, attacker);
        }
    }

    private void preyWins() {
        if (preyCard.isInAttackMode()) {
            ((Monster) attacker.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_AO_LOSE, differenceOfPoints, attacker);
        } else {
            Winner winner = preyCard.isFaceUp() ? Winner.AGAINST_DO_LOSE : Winner.AGAINST_DH_LOSE;
            if (!preyCard.isFaceUp())    preyCard.changePosition();
            attackerBoard.getOwner().decreaseLifePoint(differenceOfPoints);
            Winner.setWinner(winner, differenceOfPoints, attacker);
        }
    }

    private void noneWins() {
        if (preyCard.isInAttackMode()) {
            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, 0);
            ((Monster) attacker.getThisCard()).destroyThis(null, attackerBoard, null, attacker, 0);
            Winner.setWinner(Winner.AGAINST_AO_NONE, differenceOfPoints, attacker);
        } else {
            Winner winner = preyCard.isFaceUp() ? Winner.AGAINST_DO_NONE : Winner.AGAINST_DH_NONE;
            if (!preyCard.isFaceUp())    preyCard.changePosition();
            Winner.setWinner(winner, differenceOfPoints, attacker);
        }
    }
}
