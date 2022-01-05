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
    void createFile() throws Exception {
        csvManager.createFile();
        for(int i = 0; i < 12; i++) {
            CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
            CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\createFile.csv"), ',', '"', 0);
            assertEquals(readFileTest.readAll().get(0)[i], readCreatedFile.readAll().get(0)[i]);
        }
    }
}
