package controller;

import com.google.gson.Gson;
import model.User;
import model.card.CardLoader;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static void loadThings() {
        CardLoader.loadCsv();
        loadUsers();
    }

    private static void loadUsers() {

    }

    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("../../../users.json");
            fileWriter.write(new Gson().toJson(User.getAllUsers()));
            fileWriter.close();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
