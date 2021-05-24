import controller.ImportAndExportMenu;
import exceptions.InvalidName;
import exceptions.InvalidName;
import model.card.CardLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

public class ImportAndExportTest {
//    @BeforeAll
//    //egara kon csv ro
    @Test
    @DisplayName("import wrong card name")
    public void importCardTest() {
        Executable firstCommands = new Executable() {
            @Override
            public void execute() throws Throwable {
                ImportAndExportMenu.importCard("command knight");
            }
        };
        Assertions.assertThrows(InvalidName.class, firstCommands);

        Executable secondCommand = new Executable() {
            @Override
            public void execute() throws Throwable {
                ImportAndExportMenu.importCard("Command Knight");
                ImportAndExportMenu.exportCard("Sujin");
            }
        };
        Assertions.assertThrows(IOException.class,secondCommand);

    }

    @Test
    @DisplayName("import and export new card")
    public void importAndExportNewCardTest() throws InvalidName, IOException {
        CardLoader.loadCsv();
        CardLoader.setCards();
        ImportAndExportMenu.importCard("Command Knight");
        String json =ImportAndExportMenu.exportCard("Sujin");
        Assertions.assertEquals("jjk",json);
    }

}
