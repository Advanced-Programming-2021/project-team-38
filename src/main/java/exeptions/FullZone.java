package exeptions;

import view.messageviewing.PrintError;

public class FullZone extends Exception{
    public FullZone(Boolean isMonsterZone) {
        if (isMonsterZone) {
            PrintError.print("monster card zone is full");
        } else {
            PrintError.print("spell card zone is full");
        }
    }
}
