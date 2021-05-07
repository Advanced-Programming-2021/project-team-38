package exeptions;

import view.PrintError;

public class UnableToSummonMonster {
    public UnableToSummonMonster() {
        PrintError.print("you can’t summon this card");
    }
}
