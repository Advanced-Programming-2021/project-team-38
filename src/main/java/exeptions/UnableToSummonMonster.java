package exeptions;

import view.messageviewing.PrintError;

public class UnableToSummonMonster {
    public UnableToSummonMonster() {
        PrintError.print("you can’t summon this card");
    }
}
