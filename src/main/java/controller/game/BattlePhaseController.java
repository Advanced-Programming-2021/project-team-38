package controller.game;


import exceptions.*;
import model.Enums.Phase;
import model.card.*;
import model.card.cardinusematerial.MonsterCardInUse;

import java.util.ArrayList;

class BattlePhaseController {

    private Card selectedCard;
    private ArrayList<Card> chain;
    private GamePlayController gamePlay;

    public BattlePhaseController(GamePlayController gamePlay) {
        this.gamePlay = gamePlay;
    }

    private void battleAnnounced(int cellOfCard) throws NoSelectedCard, CardCantAttack, WrongPhaseForAction, NoCardToAttack, CardAttackedBeforeExeption {
        MonsterCardInUse preyCard;
        MonsterCardInUse attacker;
        if (gamePlay.getSelectedCardInUse().isCellEmpty())
            throw new NoSelectedCard();
        else if (!(gamePlay.getSelectedCardInUse() instanceof MonsterCardInUse))
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

    public String run() {
        return null;
    }

    public void setChain(ArrayList<Card> chain) {
        this.chain = chain;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ArrayList<Card> getChain() {
        return chain;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }


    private void controlDamage(Card attackReceiver) {

    }

    private String declareEnd() {
        return null;
    }

    public void attackToLifePoint() {

    }

    private boolean isPossibleToAttack() {
        return true;
    }

    public boolean canAddCardToChain(Card card) {
        return true;
    }

    public void addToChain(Card card) {
        chain.add(card);
    }


}
