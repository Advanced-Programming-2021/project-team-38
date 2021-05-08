package exeptions;

import view.messageviewing.PrintError;

public class NoSelectedCard {
    public NoSelectedCard() {
        PrintError.print("no card is selected");
    }
}
