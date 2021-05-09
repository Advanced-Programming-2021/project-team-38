package exeptions;

import view.PrintError;

public class BeingFull extends Exception{
    public BeingFull(String name) {
        super(String.format("%s is full", name));
    }
}
