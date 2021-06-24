package model.watchers.spells;

import controller.game.DuelMenuController;
import controller.game.SelectController;
import controller.game.SummonController;
import model.CardState;
import model.Enums.ZoneName;
import model.GraveYard;
import model.Player;
import model.card.Card;
import model.card.CardType;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.Monster;
import model.watchers.Watcher;
import model.watchers.WhoToWatch;
import view.Menus.DuelMenu;
import view.exceptions.BeingFull;

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
                try {
                    handleRitualSummon();
                    isWatcherActivated = true;
                } catch (BeingFull beingFull) {
                    DuelMenu.showException(beingFull);
                }
            }
        }
    }

    private void handleRitualSummon() throws BeingFull {
        Monster monster = getMonsterToSummon();
        ArrayList<Card> tributes = getTributes();
        Player player = this.ownerOfWatcher.ownerOfCard;
        SummonController.specialSummon(monster, player, player.getBoard().getController().getRoundController());
        sendToGraveYard(tributes);
    }

    private void sendToGraveYard(ArrayList<Card> tributes) {
        if (tributes == null) return;
        GraveYard graveYard = this.ownerOfWatcher.getBoard().getGraveYard();
        for (Card tribute : tributes) {
            tribute.beVictim(graveYard);
        }
    }

    private Monster getMonsterToSummon() {
        ArrayList<ZoneName> zoneNames = new ArrayList<>();
        zoneNames.add(ZoneName.HAND);
        SelectController selectController = new SelectController(zoneNames, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.getOwnerOfCard());
        selectController.setCardType(CardType.MONSTER);
        selectController.setIfRitual(true);
        Card card = selectController.getTheCard();
        if (card instanceof Monster) return (Monster) card;
        return null; //it won't happen
    }

    private ArrayList<Card> getTributes() {
        //todo

        //check if tributes are valid
        return null;
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
