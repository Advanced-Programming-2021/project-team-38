//TODO load handmade cards

package model.card;

import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.PreSpellTrapCard;
import model.card.spelltrap.SpellTrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CardLoader {

    public static void loadCsv() {
        //Monster csv
        File file = new File("Monster.csv");
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        try {
            Scanner dataIn = new Scanner(file);
            dataIn.nextLine();
            while (dataIn.hasNextLine()) {
                new PreMonsterCard(dataIn.nextLine().split(","));
            }
            dataIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //SpellTrap csv
        file = new File("SpellTrap.csv");
        //Name, Type, Icon (Property), Description, Status, Price
        try {
            Scanner dataIn = new Scanner(file);
            dataIn.nextLine();
            while (dataIn.hasNextLine()) {
                new PreSpellTrapCard(dataIn.nextLine().
                        replaceAll(",", " , ").
                        replaceAll("\\s{2,}", " ").split(" , "));
            }
            dataIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setCards() {
        HashMap<PreCard, Card> preCardInstances = PreCard.getAllPreCardsInstances();
        loadHandmadeCards(preCardInstances);

        for (PreCard preCard : preCardInstances.keySet()) {
            if (preCardInstances.get(preCard) == null) {
                if (preCard.getCardType() == CardType.MONSTER)
                    preCardInstances.put(preCard, new Monster(preCard).setUpMonster());
                else
                    preCardInstances.put(preCard, new SpellTrap(preCard).setUpSpellTrap());
            }
        }
    }

    private static void loadHandmadeCards(HashMap<PreCard, Card> preCardInstances) {
        //load cards which are made by hand -> command knight, suijin, ...
        //remember to call setupmonster or spelltrap for them
    }
}
