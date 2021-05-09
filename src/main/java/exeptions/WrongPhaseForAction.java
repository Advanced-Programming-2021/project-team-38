package exeptions;

import view.PrintError;

public class WrongPhaseForAction extends Exception {
    public WrongPhaseForAction() {
        super("action not allowed in this phase");
    }
}
