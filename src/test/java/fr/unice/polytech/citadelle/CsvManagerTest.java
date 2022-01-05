package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Arrays;
import au.com.bytecode.opencsv.CSVReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvManagerTest {

    @Test
    //On one game (overwrite)
    void saveRank() throws Exception {
        ArrayList<Player> leaderboard = new ArrayList<>();
        leaderboard.add(new Player("Player2"));
        leaderboard.add(new Player("Player4"));
        leaderboard.add(new Player("Player1"));
        leaderboard.add(new Player("Player3"));

        String [] realLeaderboard = {
                "[BotType, 1, 2, 3, 4, 5, 6, 7]",
                "[Player2, 1, 0, 0, 0, 0, 0, 0]",
                "[Player4, 0, 1, 0, 0, 0, 0, 0]",
                "[Player1, 0, 0, 1, 0, 0, 0, 0]",
                "[Player3, 0, 0, 0, 1, 0, 0, 0]"
        };

        CsvManager csvManager = new CsvManager(leaderboard);
        //csvManager.write();

        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\save\\results.csv"), ',' , '"' , 0);
        int counter = 0;
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                assertEquals(realLeaderboard[counter],Arrays.toString(nextLine));
                counter++;
            }
        }

    }
}
