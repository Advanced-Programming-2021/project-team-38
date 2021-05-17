//completed: setOccupiedCard

package model.card.monster.mosterswitheffect;

import model.Board;
import model.card.PreCard;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.card.monster.Monster;


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

    private boolean canActiveEffect(Board playerBoard, Board rivalBoard, CardInUse cardInUse) {
        //check to see we are in phase draw phase or draw rival phase
        return false;
    }

    @Override
    protected void receiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        // use occupied card instead of this card
        super.receiveAttack(attackerBoard, myBoard, attacker, occupiedCard);
    }

    @Override
    public boolean canReceiveAttack(Board attackerBoard, Board myBoard, MonsterCardInUse attacker, MonsterCardInUse thisCard) {
        return super.canReceiveAttack(attackerBoard, myBoard, attacker, thisCard);
    }

    private void setOccupiedCard(MonsterCardInUse rivalSelectedCard) {
        MonsterCardInUse monsterCardInUse = new MonsterCardInUse();
        monsterCardInUse.setACardInThisCell(rivalSelectedCard.getThisCard().getPreCardInGeneral());
        occupiedCard = monsterCardInUse;
    }

    @Override
    public Monster getCard() {
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {    //shouldn't be used actually
        return new ScannerMonster(preCardInGeneral);
    }
}
