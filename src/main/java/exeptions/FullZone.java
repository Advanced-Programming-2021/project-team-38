package exeptions;

import view.PrintError;

public class FullZone {
    public FullZone(Boolean isMonsterZone) {
        if (isMonsterZone) {
            PrintError.print("monster card zone is full");
        } else {
            PrintError.print("spell card zone is full");
        }
    }
}
