//completed: setOccupiedCard, receive attack
//canActiveEffect -> setOccupiedCard

package model.card.monster.mosterswitheffect;

import controller.game.BattleController;
import model.Board;
import model.Enums.Phase;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;


public class ScannerMonster extends Monster {
    private MonsterCardInUse occupiedCard;

    public ScannerMonster(PreCard preCard) {
        super(preCard);
        setName("Scanner");
    }

    @Override
    public void activeEffect(Board playerBoard, Board rivalBoard, MonsterCardInUse monsterCardInUse, CardInUse rivalCard) {
        //select a card and then call setOccupiedCard
    }

    @Override
    public boolean canActiveEffect(Board rivalBoard, Board myBoard, MonsterCardInUse rivalCard, MonsterCardInUse thisCard) {
        //check to see we are in phase draw phase or draw rival phase
        if (myBoard.getMyPhase() == Phase.DRAW)
//            setOccupiedCard(); //TODO complete -> choose a card of graveyard
            return super.canActiveEffect(rivalBoard, myBoard, rivalCard, thisCard);
        //todo: I'm negar! I just returned something before the bug is fixed
        return false;
    }

    @Override
    public void receiveAttack(BattleController battleController) {
        if (battleController.isPreyCardInAttackMode())
            battleController.setPreyPoint(occupiedCard.getAttack());
        else
            battleController.setPreyPoint(occupiedCard.getDefense());
    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        return super.canReceiveAttack(attackerBoard, myBoard, attacker, occupiedCard);
    }

    private void setOccupiedCard(PreMonsterCard rivalPreMonsterCard) {
        MonsterCardInUse monsterCardInUse = new MonsterCardInUse();
        monsterCardInUse.setACardInThisCell(rivalPreMonsterCard);
        occupiedCard = monsterCardInUse;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {    //shouldn't be used actually
        return new ScannerMonster(preCardInGeneral);
    }
}
