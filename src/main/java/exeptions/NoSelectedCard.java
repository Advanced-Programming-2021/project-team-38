package exeptions;

import view.PrintError;

public class NoSelectedCard extends Exception {
    public NoSelectedCard() {
        super("no card is selected");
    }
}
