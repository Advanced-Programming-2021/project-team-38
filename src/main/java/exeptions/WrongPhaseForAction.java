package exeptions;

import view.messageviewing.PrintError;

public class WrongPhaseForAction extends Exception {
    public WrongPhaseForAction() {
        super("action not allowed in this phase");
    }
}
