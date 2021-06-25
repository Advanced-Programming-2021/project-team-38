package model.watchers;

import controller.game.DuelMenuController;
import controller.game.RoundController;
import lombok.Setter;
import model.CardState;
import model.Enums.Phase;
import model.Player;
import model.card.cardinusematerial.CardInUse;
import model.card.monster.MonsterType;
import model.watchers.monsters.CommandKnightHolyWatcher;
import model.watchers.monsters.CommandKnightWatcher;
import model.watchers.spells.FieldWatcher;
import model.watchers.traps.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Watcher {
    public static HashMap<String, Watcher> allWatchers;
    @Setter
    public static RoundController roundController;  //TODO set At the first of game
    public static ArrayList<Watcher> stack;
    public WhoToWatch whoToWatch;
    public ArrayList<CardInUse> amWatching;
    public boolean isWatcherActivated = false;
    public CardInUse ownerOfWatcher;
    protected static boolean isInChainMode = false;
    public boolean firstOfStack = false;
    public int speed = 1;
    public boolean isDisposable = false;

    static {
        allWatchers = new HashMap<>();
        stack = new ArrayList<>();
    }

    public Watcher(CardInUse ownerOfWatcher, WhoToWatch whoToWatch) {
        this.ownerOfWatcher = ownerOfWatcher;
        this.whoToWatch = whoToWatch;
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
//        if (isDisposable)   dispose();
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
        if (!stack.contains(watcher)) {
            if (stack.size() == 0 || stack.get(stack.size() - 1).speed <= watcher.speed) {
                if (stack.get(stack.size() - 1).ownerOfWatcher.ownerOfCard != watcher.ownerOfWatcher.ownerOfCard)
                    roundController.temporaryTurnChange(watcher.ownerOfWatcher.ownerOfCard);
                if (roundController.wantToActivateCard(watcher.ownerOfWatcher.thisCard.getName())) {
                    stack.add(watcher);
                    return true;
                }
            }
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
        if (cardInUse == null) return;
        if (!amWatching.contains(cardInUse)) {
            cardInUse.watchersOfCardInUse.add(this);
            amWatching.add(cardInUse);
        }
    }

//    public void dispose() {
//        ownerOfWatcher.thisCard.builtInWatchers.remove(this);
//    }

    public static Watcher createWatcher(String nameWatcher, CardInUse ownerOfWatcher) {
        switch (nameWatcher) {
            case "CommandKnightHolyWatcher":
                return new CommandKnightHolyWatcher(ownerOfWatcher, WhoToWatch.MINE);
            case "CommandKnightWatcher":
                return new CommandKnightWatcher(ownerOfWatcher, WhoToWatch.MINE);
            //Field spell
            case "YamiFirst":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.FIEND, MonsterType.SPELLCASTER}, 200, 200, WhoToWatch.ALL);
            case "YamiSec":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.FAIRY}, -200, -200, WhoToWatch.ALL);
            case "Forest":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.INSECT, MonsterType.BEAST_WARRIOR, MonsterType.BEAST}, 200, 200, WhoToWatch.ALL);
            case "Umiiruka":
                return new FieldWatcher(ownerOfWatcher, new MonsterType[]{MonsterType.AQUA}, 500, -400, WhoToWatch.ALL);
            //traps
            case "MagicCylinderWatcher":
                return new MagicCylinderWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "MirrorForceWatcher":
                return new MirrorForceWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "TrapHoleWatcher":
                return new TrapHoleWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "TorrentialTributeWatcher":
                return new TorrentialTributeWatcher(ownerOfWatcher, WhoToWatch.ALL);
            case "TimeSealWatcher":
                return new TimeSealWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
            case "NegateAttackWatcher":
                return new NegateAttackWatcher(ownerOfWatcher, WhoToWatch.RIVALS);
        }

        System.out.println("wrong name");
        return null;
    }

    public static CardInUse[] uniteArrays(CardInUse[] a, CardInUse[] b) {
        HashSet<CardInUse> set = new HashSet<>();
        set.addAll(Arrays.asList(a));
        set.addAll(Arrays.asList(b));

        return (CardInUse[]) set.toArray();
    }

    public CardInUse[] theTargetCells(Zone zoneName) {
        if (whoToWatch == WhoToWatch.ALL) {
            switch (zoneName) {
                case MONSTER:
                    return uniteArrays(roundController.getCurrentPlayer().getBoard().getMonsterZone(),
                            roundController.getRivalBoard().getMonsterZone());
                case SPELL:
                    return uniteArrays(roundController.getCurrentPlayerBoard().getSpellTrapZone(),
                            roundController.getRivalBoard().getSpellTrapZone());
            }
        } else if (whoToWatch == WhoToWatch.MINE) {
            switch (zoneName) {
                case MONSTER:
                    return ownerOfWatcher.getBoard().getMonsterZone();
                case SPELL:
                    return ownerOfWatcher.getBoard().getSpellTrapZone();
            }
        } else if (whoToWatch == WhoToWatch.RIVALS) {
            Player myRival = roundController.getMyRival(ownerOfWatcher.getOwnerOfCard());
            switch (zoneName) {
                case MONSTER:
                    return myRival.getBoard().getMonsterZone();
                case SPELL:
                    return myRival.getBoard().getSpellTrapZone();
            }
        }

        //TODO remove sout
        System.out.println("who to watch is null");
        return null;
    }

}
