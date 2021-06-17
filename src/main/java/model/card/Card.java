package model.card;

import lombok.Getter;
import model.card.cardinusematerial.CardInUse;
import model.watchers.Watcher;

import java.util.ArrayList;

@Getter
public abstract class Card {
    public String name;
    public Card instance;
    public PreCard preCardInGeneral;
    public boolean shouldDieAfterActivated = false;
    public ArrayList<Watcher> builtInWatchers;

    public Card(PreCard preCard) {
        preCardInGeneral = preCard;
    }

    public Card getInstance() { //TODO to be complete
        return null;
    }

    public void setInstance(Card instance) {
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void putBuiltInWatchers(CardInUse cardInUse) {
        for (Watcher watcher : builtInWatchers) {
            if (watcher.canPutWatcher()) {
                watcher.putWatcher(cardInUse);
            }
        }
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException; //todo: check with hasti

    @Override
    public String toString() {
        return name;
    }
    //todo: What should be here?
}
