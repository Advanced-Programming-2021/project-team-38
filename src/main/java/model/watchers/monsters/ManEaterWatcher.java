package model.watchers.monsters;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import model.CardState;
import model.Enums.ZoneName;
import model.card.cardinusematerial.CardInUse;
import model.card.cardinusematerial.MonsterCardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

import java.util.ArrayList;
import java.util.Arrays;

public class ManEaterWatcher extends Watcher {

    public ManEaterWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.FACE_UP) {
            if (handleChain()) {
                selectMonsterCardInUse().destroyThis();
                isWatcherActivated = true;
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

    public MonsterCardInUse selectMonsterCardInUse() {
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.RIVAL_MONSTER_ZONE)), roundController, ownerOfWatcher.getOwnerOfCard());

        return (MonsterCardInUse) selectController.getTheCardInUse();
    }
}
