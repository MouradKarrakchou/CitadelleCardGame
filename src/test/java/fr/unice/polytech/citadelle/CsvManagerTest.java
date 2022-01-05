package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Arrays;
import au.com.bytecode.opencsv.CSVReader;

import java.util.ArrayList;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvManagerTest {

    ArrayList<Player> aLeaderboard = new ArrayList<>();
    CsvManager csvManager = new CsvManager(aLeaderboard);

    @BeforeEach
    void initialization() {
        aLeaderboard.add(new Player("P1"));
        aLeaderboard.add(new Player("P2"));
        aLeaderboard.add(new Player("P3"));
        aLeaderboard.add(new Player("P4"));
        aLeaderboard.add(new Player("P5"));
    }

    @AfterEach
    void clean() {
        String path = "save\\results.csv";
        File csvFile = new File(path);
        csvFile.delete();
    }

    @Test
    //On one game (overwrite)
    void createFile() throws Exception {

        String [] fields = {"BotType", "1", "2", "3", "4", "5", "6", "7", "Win-rate", "Games played", "Total score", "Average score"};
        csvManager.createFile();
        CSVReader reader = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);

        String a = fields[1];
        String b = reader.readAll().get(0)[1];
        assertEquals(a, b);

        a = fields[1];
        b = reader.readAll().get(0)[1];
        assertEquals(a, b);

//        for(int i = 0; i < 12; i++) {
//            assertEquals(fields[i], reader.readAll().get(0));
//        }

        /*
        ArrayList<Player> leaderboard = new ArrayList<>();
        leaderboard.add(new Player("Player2"));
        leaderboard.add(new Player("Player4"));
        leaderboard.add(new Player("Player1"));
        leaderboard.add(new Player("Player3"));

        String [] realLeaderboard = {
                "[BotType, 1, 2, 3, 4, 5, 6, 7, Win-rate, Games played]",
                "[Player2, 1, 0, 0, 0, 0, 0, 0, 100.0, 1]",
                "[Player4, 0, 1, 0, 0, 0, 0, 0, 0.0, 1]",
                "[Player1, 0, 0, 1, 0, 0, 0, 0, 0.0, 1]",
                "[Player3, 0, 0, 0, 1, 0, 0, 0, 0.0, 1]"
        };

        CsvManager csvManager = new CsvManager(leaderboard);
        csvManager.append();

        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\save\\results.csv"), ',' , '"' , 0);
        int counter = 0;
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                assertEquals(realLeaderboard[counter],Arrays.toString(nextLine));
                counter++;
            }
        }
*/
    }
}
