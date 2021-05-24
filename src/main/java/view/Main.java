package view;

import model.card.CardLoader;

public class Main {
    public static void main(String[] args) {
        CardLoader.loadCsv();
        Print.print("Welcome to Yo Gi Oh!");
        Menu.run();
    }
}
