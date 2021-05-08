package exeptions;

import view.messageviewing.PrintError;

public class WrongPhaseForAction {
    public WrongPhaseForAction() {
        PrintError.print("action not allowed in this phase");
    }
}
