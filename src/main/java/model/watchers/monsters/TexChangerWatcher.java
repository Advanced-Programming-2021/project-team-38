package model.watchers.monsters;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import controller.game.SummonController;
import model.CardState;
import model.Enums.Phase;
import model.Enums.ZoneName;
import model.card.Card;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterType;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import view.Menus.DuelMenu;
import view.exceptions.BeingFull;

import java.util.ArrayList;
import java.util.Arrays;

public class TexChangerWatcher extends Watcher {
    MarshmallonHolyWatcher secondaryWatcher;

    public TexChangerWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }


    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (secondaryWatcher == null) {
                secondaryWatcher = new MarshmallonHolyWatcher(ownerOfWatcher, WhoToWatch.MINE);
                secondaryWatcher.watch(theCard, cardState, duelMenuController);
                summonAppropriateMonsterCard();
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return true;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    @Override
    public void update(Phase newPhase) {
        if (newPhase == Phase.END_RIVAL) {
            secondaryWatcher = null;
        }
    }

    public void summonAppropriateMonsterCard() {
        roundController.temporaryTurnChange(ownerOfWatcher.ownerOfCard);
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.MY_DECK, ZoneName.HAND, ZoneName.MY_GRAVEYARD)), roundController, ownerOfWatcher.getOwnerOfCard());

        selectController.setMonsterTypes(new MonsterType[]{MonsterType.CYBERSE});
        selectController.setCardType(CardType.MONSTER);
        if (ownerOfWatcher.getBoard().getFirstEmptyCardInUse(true) != null) {
            try {
                Card selected = selectController.getTheCard();
                if (selected != null) {
                    SummonController.specialSummon((Monster) selected,
                            ownerOfWatcher.getOwnerOfCard(), roundController, false);
                    ownerOfWatcher.getBoard().getGraveYard().removeCard(selected);
                }
            } catch (BeingFull beingFull) {
                DuelMenu.showException(beingFull);
            }
        }
    }
}
