package model.watchers.spells;

import controller.game.DuelMenuController;
import model.CardState;
import model.card.Card;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;

import java.util.ArrayList;

public class AdvancedRitualArtWatcher extends Watcher {

    public AdvancedRitualArtWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
        whoToWatch = WhoToWatch.MINE;
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                //TODO
                handleRitualSummon();
                isWatcherActivated = true;
            }
        }
    }

    private void handleRitualSummon() {
        //get tributes
        //check if tributes are valid
        //send to grave yard
        //summon
//        ArrayList<ZoneName> zoneNames = new ArrayList<>();
//        zoneNames.add(ZoneName.HAND);
//        SelectController selectController = new SelectController(zoneNames, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.ownerOfCard);
//

    }

    private ArrayList<Card> getTributes() {

    }

    @Override
    public boolean canPutWatcher() {
        return !isWatcherActivated;
    }

    @Override
    public void putWatcher(CardInUse cardInUse) {
        addWatcherToCardInUse(cardInUse);
    }
}
