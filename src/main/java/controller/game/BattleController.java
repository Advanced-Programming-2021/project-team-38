package controller.game;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.CardState;
import view.Print;
import view.messageviewing.Winner;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;

@Getter
@Setter
public class BattleController {
    int differenceOfPoints;
    private Board attackerBoard, preyBoard;
    private MonsterCardInUse attacker, preyCard;
    private boolean isPreyCardInAttackMode;
    private int attackerAttack, preyPoint;
    public boolean canBattleHappen = true;

    public BattleController(Board attackerBoard, Board preyBoard, MonsterCardInUse attacker, MonsterCardInUse preyCard) {
        this.attackerBoard = attackerBoard;
        this.preyBoard = preyBoard;
        this.attacker = attacker;
        this.preyCard = preyCard;
        attackerAttack = attacker.getAttack();
        preyPoint = preyCard.appropriatePointAtBattle();
        attacker.watchByState(CardState.WANT_TO_ATTACK);
        preyCard.watchByState(CardState.IS_ATTACKED);
        isPreyCardInAttackMode = preyCard.isInAttackMode();
        if (canBattleHappen) {
            preyCard.isCellEmpty(); //TODO
            run();
        }
        else System.out.println("this battle can't happen");
    }

    private void run() {
//        preyCard.changeIsAttacked();
//        attacker.changeIsAttacking();

        if (!isPreyCardInAttackMode) preyCard.changePosition();

//        ((Monster) preyCard.getThisCard()).receiveAttack(this);
        differenceOfPoints = Math.abs(attackerAttack - preyPoint);
        if (!isPreyCardInAttackMode) {
            Print.print(String.format("opponent’s monster card was %s",
                    preyCard.getThisCard().getName()));
        }
        if (attackerAttack > preyPoint) {
            attackerWins();
        } else if (attackerAttack == preyPoint) {
            noneWins();
        } else {
            preyWins();
        }
    }

    private void attackerWins() {
        if (isPreyCardInAttackMode) {
            preyCard.destroyThis();
            preyBoard.getOwner().decreaseLifePoint(differenceOfPoints);
//            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_A_WINS, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_WINS;
            preyCard.destroyThis();
//            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, 0);
            Winner.setWinner(winner, differenceOfPoints);
        }
    }

    private void preyWins() {
        if (isPreyCardInAttackMode) {
            attacker.destroyThis();
            attackerBoard.getOwner().decreaseLifePoint(differenceOfPoints);
//            ((Monster) attacker.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, differenceOfPoints);
            Winner.setWinner(Winner.AGAINST_A_LOSE, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_LOSE;
            attackerBoard.getOwner().decreaseLifePoint(differenceOfPoints);
            Winner.setWinner(winner, differenceOfPoints);
        }
    }

    private void noneWins() {
        if (isPreyCardInAttackMode) {
            attacker.destroyThis();
            preyCard.destroyThis();
//            ((Monster) preyCard.getThisCard()).destroyThis(attackerBoard, preyBoard, attacker, preyCard, 0);
//            ((Monster) attacker.getThisCard()).destroyThis(null, attackerBoard, null, attacker, 0);
            Winner.setWinner(Winner.AGAINST_A_NONE, differenceOfPoints);
        } else {
            Winner winner = Winner.AGAINST_D_NONE;
            Winner.setWinner(winner, differenceOfPoints);
        }
    }
}
