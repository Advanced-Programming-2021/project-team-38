package controller;

import com.google.gson.Gson;
import exceptions.InvalidCardName;
import model.card.CardType;
import model.card.PreCard;
import model.card.monster.PreMonsterCard;
import model.card.spelltrap.PreSpellTrapCard;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;

public class ImportAndExportMenu {


    public void importCard(String cardName) throws InvalidCardName, IOException {

        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidCardName();

        if (preCard.getCardType() == CardType.MONSTER) {

            PreMonsterCard preMonsterCard = (PreMonsterCard) preCard;
            try {
                FileWriter fileWriter = new FileWriter("../../resources/cards/monster/" + cardName + ".json");
                fileWriter.write(new Gson().toJson(preMonsterCard));
                fileWriter.close();
            } catch (IOException e) {
                throw e;
            }

        } else {
            PreSpellTrapCard preSpellTrapCard = (PreSpellTrapCard) preCard;
            try {
                FileWriter fileWriter = new FileWriter("../../resources/cards/spell-trap/" + cardName + ".json");
                fileWriter.write(new Gson().toJson(preSpellTrapCard));
                fileWriter.close();
            } catch (IOException e) {
                throw e;
            }
        }

    }

    public String exportCard(String cardName) throws InvalidCardName, IOException {

        PreCard preCard = PreCard.findCard(cardName);
        if (preCard == null) throw new InvalidCardName();

        if (preCard.getCardType() == CardType.MONSTER) {
            try {
                String json = new String(Files.readAllBytes(
                        Paths.get("../../resources/cards/monster/" + cardName + ".json")));
                PreMonsterCard preMonsterCard = new Gson().fromJson(json, PreMonsterCard.class);
                return preMonsterCard.toString();
            } catch (IOException e) {
                throw e;
            }

        } else {

            try {
                String json = new String(Files.readAllBytes(
                        Paths.get("../../resources/cards/spell-trap/" + cardName + ".json")));
                PreSpellTrapCard preSpellTrapCard = new Gson().fromJson(json, PreSpellTrapCard.class);
                return preSpellTrapCard.toString();
            } catch (IOException e) {
                throw e;
            }

        }

    }

    public void importUser(Matcher matcher) {


    }

    public void exportUser(Matcher matcher) {

    }

//    public Matcher getMatcher(String info) {
//        return null;
//    }
//
//    public String processFile(File file) {
//        return null;
//    }
}