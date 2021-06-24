package model.watchers.spells;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import controller.game.SummonController;
import view.exceptions.BeingFull;
import model.CardState;
import model.Enums.ZoneName;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.Monster;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import view.Menus.DuelMenu;

import java.util.ArrayList;
import java.util.Arrays;

public class MonsterRebornWatcher extends Watcher {
    public MonsterRebornWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                summonAppropriateMonster();
                isWatcherActivated = true;
            }
        }
    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }

    public void summonAppropriateMonster() {
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.MY_GRAVEYARD, ZoneName.RIVAL_GRAVEYARD)), roundController, ownerOfWatcher.getOwnerOfCard());

        selectController.setCardType(CardType.MONSTER);
        if (ownerOfWatcher.getBoard().getFirstEmptyCardInUse(true) != null) {
            try {
                SummonController.specialSummon((Monster) selectController.getTheCard(),
                        ownerOfWatcher.getOwnerOfCard(), roundController);
            } catch (BeingFull beingFull) {
                DuelMenu.showException(beingFull);
            }
        }
    }
}
