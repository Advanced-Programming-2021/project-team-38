package controller.game;


import exceptions.*;
import model.Enums.Phase;
import model.card.Card;
import model.card.cardinusematerial.MonsterCardInUse;

import java.util.ArrayList;

class BattlePhaseController {

    private RoundController gamePlay;

    public BattlePhaseController(RoundController gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void battleAnnounced(int cellOfCard) throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, NoCardToAttack, CardAttackedBeforeExeption {
        MonsterCardInUse preyCard;
        MonsterCardInUse attacker;
        if (gamePlay.getSelectedCardInUse().isCellEmpty())
            throw new NoSelectedCard();
        else if (!(gamePlay.getSelectedCardInUse() instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (((MonsterCardInUse)gamePlay.getSelectedCardInUse()).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if ((preyCard = gamePlay.getActionsOnRival().getRivalMonsterCell(cellOfCard)).isCellEmpty())
            throw new NoCardToAttack();
        else if ((attacker = (MonsterCardInUse) gamePlay.getSelectedCardInUse()).hasBeenAttacker())
            throw new CardAttackedBeforeExeption();
        else
            new BattleController(gamePlay.getCurrentPlayerBoard(),
                    gamePlay.getRivalBoard(), attacker, preyCard);
    }

    public void attackToLifePoint() throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, CardAttackedBeforeExeption, CantAttackDirectlyException {
        MonsterCardInUse attacker;
        if (gamePlay.getSelectedCardInUse().isCellEmpty())
            throw new NoSelectedCard();
        else if (!(gamePlay.getSelectedCardInUse() instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (((MonsterCardInUse)gamePlay.getSelectedCardInUse()).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if ((attacker = (MonsterCardInUse) gamePlay.getSelectedCardInUse()).hasBeenAttacker())
            throw new CardAttackedBeforeExeption();
        else if (!canAttackDirectly())
            throw new CantAttackDirectlyException();
        else
            gamePlay.getRival().decreaseLifePoint(attacker.getAttack());
    }

    private boolean canAttackDirectly() {   //complete
        return gamePlay.getRivalBoard().getFirstEmptyCardInUse(true) == null;
    }

    public String run() {
        return null;
    }

    public void setChain(ArrayList<Card> chain) {

    }

    public void setSelectedCard(Card selectedCard) {

    }

    public ArrayList<Card> getChain() {
        return null;
    }

    public Card getSelectedCard() {
        return null;
    }


    private void controlDamage(Card attackReceiver) {

    }

    private String declareEnd() {
        return null;
    }



    private boolean isPossibleToAttack() {
        return true;
    }

    public boolean canAddCardToChain(Card card) {
        return true;
    }

    public void addToChain(Card card) {
    }


}
