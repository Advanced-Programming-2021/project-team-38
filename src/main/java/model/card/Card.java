package model.card;

import lombok.Getter;
import model.GraveYard;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = preCardInGeneral.getName();
    }

    public void putBuiltInWatchers(CardInUse cardInUse) {
        for (Watcher watcher : builtInWatchers) {
            if (watcher.canPutWatcher()) {
                watcher.putWatcher(cardInUse);
            }
        }
    }

    public void theCardIsBeingDeleted() {
        for (Watcher builtInWatcher : builtInWatchers) {
            builtInWatcher.deleteWatcher();
        }
    }

    public void cardIsBeingSetInCell(CardInUse cardInUse) {
        for (String nameOfWatcher : CardLoader.cardsWatchers.get(name)) {
            builtInWatchers.add(Watcher.createWatcher(nameOfWatcher, cardInUse));
        }

        putBuiltInWatchers(cardInUse);
    }

    public void beVictim(GraveYard graveYard) {
        graveYard.addCard(this);
    }

    @Override
    public String toString() {
        return preCardInGeneral.toString();
    }
}
