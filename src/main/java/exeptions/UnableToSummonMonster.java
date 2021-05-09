package exeptions;

import view.messageviewing.PrintError;

public class UnableToSummonMonster extends Exception {
    public UnableToSummonMonster() {
        super("you can’t summon this card");
    }
}
