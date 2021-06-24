package view.Menus;

import controller.ImportExportController;
import view.exceptions.InvalidName;
import view.messageviewing.Print;

import java.io.IOException;

public class ImportExportMenu {
    public static void checkMenuCommands(String command, boolean isImport) throws InvalidName {
        ImportExportController importExportController = new ImportExportController();
        if (isImport) {
            try {
                importExportController.importCard(command);
            } catch (IOException e) {
                Print.print("File error happened");
            }
        } else {
            try {
                Print.print(importExportController.exportCard(command));
            } catch (IOException e) {
                Print.print("File error happened");
            }
        }
    }
}
