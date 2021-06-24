//TODO load handmade cards

package model.card;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import model.card.monster.Monster;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.PreSpellTrapCard;
import model.card.spelltrap.SpellTrap;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class CardLoader {
    static HashMap<String, ArrayList<String>> cardsWatchers;

    static {
        setCardsWatchers();
    }

    public static void loadCsv() {
        //Monster csv
        //Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price
        try (CSVReader csvReader = new CSVReader(new FileReader("Monster.csv"));) {
            String[] values = null;
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                new PreMonsterCard(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //SpellTrap csv
        //Name, Type, Icon (Property), Description, Status, Price
        try (CSVReader csvReader = new CSVReader(new FileReader("SpellTrap.csv"));) {
            String[] values = null;
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                new PreSpellTrapCard(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCards() {
        HashMap<PreCard, Card> preCardInstances = PreCard.getAllPreCardsInstances();
//        loadHandmadeCards(preCardInstances);

        for (PreCard preCard : preCardInstances.keySet()) {
            if (preCardInstances.get(preCard) == null) {
                if (preCard.getCardType() == CardType.MONSTER)
                    preCardInstances.put(preCard, new Monster(preCard).setUpMonster());
                else
                    preCardInstances.put(preCard, new SpellTrap(preCard).setUpSpellTrap());
            }
        }
    }

    public static void setCardsWatchers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("cards.json")));
            Type type = new TypeToken<HashMap<String, ArrayList<String>>>() {
            }.getType();
            cardsWatchers = new Gson().fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void loadHandmadeCards(HashMap<PreCard, Card> preCardInstances) {
//        //load cards which are made by hand -> command knight, suijin, ...
//        //remember to call setupmonster or spelltrap for them
//        String[] handmadeMonsters = {"Command Knight", "Man-Eater Bug",
//                "Scanner", "SuijinWatcher", "Yomi Ship"};
//
//
//        PreCard preCard = PreCard.findCard("Command Knight");
//        preCardInstances.put(preCard, new CommandKnight(preCard));
//        preCard = PreCard.findCard("Man-Eater Bug");
//        preCardInstances.put(preCard, new ManEaterBug(preCard));
//        //...
//    }
}
