package model.watchers;

import controller.game.DuelMenuController;
import model.CardState;
import model.Enums.Phase;
import model.card.cardinusematerial.CardInUse;
import model.watchers.monsters.CommandKnightHolyWatcher;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Watcher {
    public static HashMap<String, Watcher> allWatchers;
    public static ArrayList<Watcher> stack;
    public WhoToWatch whoToWatch;
    public ArrayList<CardInUse> amWatching;
    public boolean isWatcherActivated = false;
    public CardInUse ownerOfWatcher;
    protected static boolean isInChainMode = false;
    public int speed = 1;

    static {
        allWatchers = new HashMap<>();
        stack = new ArrayList<>();
    }

    public Watcher(CardInUse ownerOfWatcher) {
        this.ownerOfWatcher = ownerOfWatcher;
    }

    public abstract void watch(CardInUse theCard, CardState cardState, DuelMenuController duelMenuController);

    /*
    sees if the watcher can be put on cards.
     */
    public abstract boolean canPutWatcher();

    public abstract void putWatcher(CardInUse cardInUse);

    public void update(Phase newPhase) {
    }

    public void disableWatcher(CardInUse cardInUse) {    //when probably the card is destroyed
        amWatching.remove(cardInUse);
    }

    public void deleteWatcher() {   //when owner of watcher is destroyed or the watcher can only be used once
        for (CardInUse cardInUse : amWatching) {
            cardInUse.watchersOfCardInUse.remove(this);
            amWatching.remove(cardInUse);
        }
    }

    protected static void emptyStack() {
        if (stack.size() > 0) {
            stack = new ArrayList<>();
        }
    }

    /*
    if can add watcher to stack -> true
    else -> false
     */
    protected static boolean addToStack(Watcher watcher) {
        if (stack.size() == 0 || stack.get(stack.size() - 1).speed <= watcher.speed) {
            //TODO ask user
            stack.add(watcher);
            return true;
        }

        return false;
    }

    public boolean handleChain() {
        if (Watcher.addToStack(this)) {
            ownerOfWatcher.watchByState(CardState.ACTIVE_EFFECT);
            emptyStack();
            return true;
        }

        return false;
    }

    public void trapHasDoneItsEffect() {
        isWatcherActivated = true;
        ownerOfWatcher.sendToGraveYard();
    }

    public void addWatcherToCardInUse(CardInUse cardInUse) {
        if (!amWatching.contains(cardInUse)) {
            cardInUse.watchersOfCardInUse.add(this);
            amWatching.add(cardInUse);
        }
    }

    public static Watcher createWatcher(String nameWatcher, CardInUse ownerOfWatcher) {
        switch (nameWatcher) {
            case "CommandKnightHolyWatcher":
                return new CommandKnightHolyWatcher(ownerOfWatcher);
                break;
        }
    }

}
