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
import view.exceptions.InvalidTributeAddress;

import java.util.ArrayList;

public class AdvancedRitualArtWatcher extends Watcher {

    public AdvancedRitualArtWatcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        super(ownerOfWatcher, whoToWatch);
    }

    @Override
    public void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController) {
        if (cardState == CardState.ACTIVE_MY_EFFECT) {
            if (handleChain()) {
                this.duelMenuController = duelMenuController;
                this.theCard = theCard;
            }
        }
    }

    @Override
    public void doWhatYouShould() {
        try {
            handleRitualSummon();
        } catch (BeingFull | InvalidTributeAddress ex) {
            DuelMenu.showException(ex);
        }
    }

    private void handleRitualSummon() throws BeingFull, InvalidTributeAddress {
        Monster monster = getMonsterToSummon();
        if (monster == null) return;

        ArrayList<Monster> tributes = getTributes(monster.getLevel());
        if (tributes == null || tributes.isEmpty()) return;

        isWatcherActivated = true;
        Player player = this.ownerOfWatcher.ownerOfCard;
        SummonController.specialSummon(monster, player, player.getBoard().getController().getRoundController(), true);
        sendToGraveYard(tributes);
    }

    private void sendToGraveYard(ArrayList<Monster> tributes) {
        if (tributes == null) return;
        GraveYard graveYard = this.ownerOfWatcher.getBoard().getGraveYard();
        for (Monster tribute : tributes) {
            tribute.beVictim(graveYard);
        }
        roundController.updateBoards();

    }

    private Monster getMonsterToSummon() {
        if (ownerOfWatcher.getBoard().getController().askToEnterSummon()) return null;

        ArrayList<ZoneName> zoneNames = new ArrayList<>();
        zoneNames.add(ZoneName.HAND);
        SelectController selectController = new SelectController(zoneNames, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.getOwnerOfCard());
        selectController.setCardType(CardType.MONSTER);
        selectController.setIfRitual(true);
        Card card = selectController.getTheCard();
        if (card instanceof Monster) return (Monster) card;
        return null; //it may happen when the process is canceled
    }

    private ArrayList<Monster> getTributes(int levelsSum) throws InvalidTributeAddress {
        int numOfTributes;
        //todo: following lines are better to be in view. but never mind
        String numString = DuelMenu.askQuestion("Enter the number of tributes or enter \"cancel\" to cancel!");
        while (true) {
            if (numString.equals("cancel")) return null;
            try {
                numOfTributes = Integer.parseInt(numString);
                break;
            } catch (Exception e) {
                numString = DuelMenu.askQuestion("Invalid number. " +
                        "Enter the number of tributes or enter \"cancel\" to cancel!");
            }
        }

        ArrayList<ZoneName> deckZone = new ArrayList<>();
        ArrayList<Monster> tributes = new ArrayList<>();
        deckZone.add(ZoneName.MY_DECK);
        for (int i = 0; i < numOfTributes; i++) {
            SelectController selectController = new SelectController(deckZone, ownerOfWatcher.getBoard().getController().getRoundController(), ownerOfWatcher.ownerOfCard);
            selectController.setCardType(CardType.MONSTER);
            selectController.setIfNormal(true);
            tributes.add((Monster) selectController.getTheCard());
        }
        if (!areTributesValid(tributes, levelsSum)) {
            throw new InvalidTributeAddress();
        }
        return tributes;
    }

    private boolean areTributesValid(ArrayList<Monster> tributeMonsters, int level) {
        if (tributeMonsters == null) return false;
        ArrayList<Integer> levels = new ArrayList<>();
        for (Monster monster : tributeMonsters) {
            levels.add(monster.getLevel());
        }
        return canFindSubsetOfSum(levels, level);
    }


    private boolean canFindSubsetOfSum(ArrayList<Integer> set, int sum) {
        if (sum < 0) return false;
        if (sum == 0) return true;
        if (set.isEmpty()) return false;
        int element = set.get(0);
        if (element < 0) return false;
        set.remove(element);
        if (canFindSubsetOfSum(set, sum)) return true;
        return canFindSubsetOfSum(set, sum - element);
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
