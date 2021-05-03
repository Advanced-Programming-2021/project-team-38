package exeptions;

import view.PrintError;

public class NoSelectedCard {
    public NoSelectedCard() {
        PrintError.print("no card is selected");
    }
}
