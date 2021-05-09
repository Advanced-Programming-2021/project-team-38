package exeptions;

import view.messageviewing.PrintError;

public class TributeError extends Exception {
    public TributeError() {
        super("there are not enough cards for tribute");
    }
}
