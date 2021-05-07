package exeptions;

import view.PrintError;

public class TributeError {
    public TributeError() {
        PrintError.print("there are not enough cards for tribute");
    }
}
