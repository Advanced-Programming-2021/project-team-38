import controller.DeckMenuController;
import controller.ImportAndExportMenu;
import exceptions.AlreadyExistingError;
import exceptions.InvalidName;
import exceptions.InvalidName;
import model.User;
import model.card.CardLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;

public class ImportAndExportTest {

    @BeforeEach
    public void init() {
        CardLoader.loadCsv();
        CardLoader.setCards();
    }

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
                ImportAndExportMenu.exportCard("Suijin");
            }
        };
        Assertions.assertThrows(IOException.class,secondCommand);

    }

    @Test
    @DisplayName("import and export new card")
    public void importAndExportNewCardTest() throws InvalidName, IOException {
        ImportAndExportMenu.importCard("Command Knight");
        String json =ImportAndExportMenu.exportCard("Command Knight");
        Assertions.assertNotNull(json);
        ImportAndExportMenu.deleteCard("Command Knight");

    }

}
