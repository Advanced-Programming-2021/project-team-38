package model.watchers.monsters;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import controller.game.SummonController;
import exceptions.BeingFull;
import model.CardState;
import model.Enums.Phase;
import model.Enums.ZoneName;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.Monster;
import model.card.monster.MonsterType;
import model.watchers.Watcher;
import model.watchers.Zone;
import view.Menus.DuelMenu;

import java.util.ArrayList;
import java.util.Arrays;

public class TexChangerWatcher extends Watcher {
    MarshmallonHolyWatcher secondaryWatcher;

    public TexChangerWatcher(CardInUse ownerOfWatcher) {
        super(ownerOfWatcher, );
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.IS_ATTACKED) {
            if (secondaryWatcher == null) {
                secondaryWatcher = new MarshmallonHolyWatcher(ownerOfWatcher);
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
        SelectController selectController = new SelectController(new ArrayList<>(Arrays.asList(
                ZoneName.MY_DECK, ZoneName.HAND, ZoneName.MY_GRAVEYARD)), roundController, ownerOfWatcher.getOwnerOfCard());

        selectController.setMonsterTypes(new MonsterType[]{MonsterType.CYBERSE});
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
