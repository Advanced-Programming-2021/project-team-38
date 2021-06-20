package controller.game;


import exceptions.*;
import model.Enums.Phase;
import model.card.Card;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;

import java.util.ArrayList;

public class BattlePhaseController {

    public RoundController gamePlay;
    public BattleController battleController;

    public ArrayList<CardInUse> attackedInThisTurn;

    {
        attackedInThisTurn = new ArrayList<>();
    }

    public BattlePhaseController(RoundController gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void battleAnnounced(int cellOfCard) throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, NoCardToAttack, CardAttackedBeforeExeption {
        MonsterCardInUse preyCard;
        MonsterCardInUse attacker;
        CardInUse cardInUse = gamePlay.getSelectedCardInUse();
        if (cardInUse == null || cardInUse.isCellEmpty())
            throw new NoSelectedCard();
        else if (!(cardInUse instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (((MonsterCardInUse) cardInUse).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if ((preyCard = gamePlay.getActionsOnRival().getRivalMonsterCell(cellOfCard)).isCellEmpty())
            throw new NoCardToAttack();
        else if (attackedInThisTurn.contains(attacker = (MonsterCardInUse) cardInUse))
            throw new CardAttackedBeforeExeption();
        else {
            battleController = new BattleController(gamePlay.getCurrentPlayerBoard(),
                    gamePlay.getRivalBoard(), attacker, preyCard);
            attackedInThisTurn.add(attacker);
        }
    }

    public void attackToLifePoint() throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, CardAttackedBeforeExeption, CantAttackDirectlyException {
        MonsterCardInUse attacker;
        CardInUse cardInUse = gamePlay.getSelectedCardInUse();
        if (cardInUse == null || cardInUse.isCellEmpty())
            throw new NoSelectedCard();
        else if (!(cardInUse instanceof MonsterCardInUse))
            throw new CardCantAttack();
        else if (((MonsterCardInUse) cardInUse).isInAttackMode())
            throw new CardCantAttack();
        else if (gamePlay.getCurrentPhase() != Phase.BATTLE)
            throw new WrongPhaseForAction();
        else if ((attacker = (MonsterCardInUse) cardInUse).hasBeenAttacker())
            throw new CardAttackedBeforeExeption();
        else if (!canAttackDirectly())
            throw new CantAttackDirectlyException();
        else
            gamePlay.getRival().decreaseLifePoint(attacker.getAttack());
    }

    private boolean canAttackDirectly() {   //complete
        return gamePlay.getRivalBoard().getFirstEmptyCardInUse(true) == null;
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
