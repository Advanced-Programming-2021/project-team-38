package exeptions;

import view.messageviewing.PrintError;

public class NoSelectedCard extends Exception {
    public NoSelectedCard() {
        super("no card is selected");
    }
}
