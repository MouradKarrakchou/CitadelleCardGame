package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import au.com.bytecode.opencsv.CSVReader;

import java.util.ArrayList;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class CsvManagerTest {

    ArrayList<Player> aLeaderboard = new ArrayList<>();
    ArrayList<Player> anotherLeaderboard = new ArrayList<>();
    ArrayList<Player> anotherOneLeaderboard = new ArrayList<>();
    CsvManager csvManager = new CsvManager(aLeaderboard);

    @BeforeEach
    void initialization() {
        String path = "save\\results.csv";
        File csvFile = new File(path);
        csvFile.delete();

        aLeaderboard.add(new Player("P1"));
        aLeaderboard.add(new Player("P2"));
        aLeaderboard.add(new Player("P3"));
        aLeaderboard.add(new Player("P4"));
        aLeaderboard.add(new Player("P5"));

        anotherLeaderboard.add(new Player("P4"));
        anotherLeaderboard.add(new Player("P1"));
        anotherLeaderboard.add(new Player("P5"));
        anotherLeaderboard.add(new Player("P3"));
        anotherLeaderboard.add(new Player("P2"));

        anotherOneLeaderboard.add(new Player("P1"));
        anotherOneLeaderboard.add(new Player("P2"));
        anotherOneLeaderboard.add(new Player("P3"));
        anotherOneLeaderboard.add(new Player("P4"));
        anotherOneLeaderboard.add(new Player("P5"));
        anotherOneLeaderboard.add(new Player("P6"));
    }

    @Test
    void createFileTest() throws Exception {
        csvManager.createFile();
        for(int i = 0; i < 12; i++) {
            CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
            CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\createFile.csv"), ',', '"', 0);
            assertEquals(readFileTest.readAll().get(0)[i], readCreatedFile.readAll().get(0)[i]);
        }
    }

    @Test
    void appendTest() throws Exception {
        int score = 10;
        for(Player player : aLeaderboard) player.updateScore(score--);
        csvManager.createFile();
        csvManager.append();

        for(int j = 1; j < 6; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\append.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void updateRankAllTest() throws Exception {
        int score = 10;
        for(Player player : aLeaderboard) player.updateScore(score--);
        csvManager.createFile();
        csvManager.append();

        aLeaderboard = anotherLeaderboard;
        CsvManager csvManager = new CsvManager(aLeaderboard);
        csvManager.write(csvManager.updateRankAll());

        for(int j = 1; j < 6; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\updateRankAll.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void averageTest() throws Exception {
        int score = 10;
        for(Player player : aLeaderboard) player.updateScore(score--);
        csvManager.createFile();
        csvManager.append();

        aLeaderboard = anotherLeaderboard;
        score = 10;
        for(Player player : anotherLeaderboard) player.updateScore(score--);
        CsvManager csvManager = new CsvManager(aLeaderboard);
        csvManager.write(csvManager.updateRankAll());

        csvManager.write(csvManager.winrate(csvManager.read()));
        csvManager.write(csvManager.average(csvManager.read()));

        for(int j = 1; j < 6; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\average.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void updateWinrateAverageTest() throws Exception {
        int score = 10;
        for(Player player : aLeaderboard) player.updateScore(score--);
        csvManager.createFile();
        csvManager.append();

        aLeaderboard = anotherLeaderboard;
        score = 10;
        for(Player player : anotherLeaderboard) player.updateScore(score--);
        CsvManager csvManager = new CsvManager(aLeaderboard);
        csvManager.write(csvManager.updateRankAll());

        csvManager.updateWinrateAverage(csvManager.read());

        for(int j = 1; j < 6; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\average.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void newChallengerTest() throws Exception {
        int score = 10;
        for(Player player : aLeaderboard) player.updateScore(score--);
        csvManager.createFile();
        csvManager.append();

        aLeaderboard = anotherOneLeaderboard;
        CsvManager csvManager = new CsvManager(aLeaderboard);
        csvManager.newChallenger();

        for(int j = 1; j < 7; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\newChallenger.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void emptyFileTrueTest() throws Exception {
        csvManager.createFile();
        assertTrue(csvManager.emptyFile());
    }

    @Test
    void emptyFileFalseTest() throws Exception {
        csvManager.createFile();
        csvManager.append();
        assertFalse(csvManager.emptyFile());
    }

    @Test
    void existingFileTrueTest() throws Exception {
        csvManager.createFile();
        csvManager.existingFile();
        for(int i = 0; i < 12; i++) {
            CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
            CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\createFile.csv"), ',', '"', 0);
            assertEquals(readFileTest.readAll().get(0)[i], readCreatedFile.readAll().get(0)[i]);
        }
    }

    @Test
    void existingFileFalseTest() throws Exception {
        csvManager.existingFile();
        for(int i = 0; i < 12; i++) {
            CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
            CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\createFile.csv"), ',', '"', 0);
            assertEquals(readFileTest.readAll().get(0)[i], readCreatedFile.readAll().get(0)[i]);
        }
    }

    @Test
    void saveFileEmptyTest() throws Exception {
        csvManager.saveFile();
        for(int j = 1; j < 6; j++) {
            for (int i = 0; i < 12; i++) {
                CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
                CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\saveFileEmpty.csv"), ',', '"', 0);
                assertEquals(readFileTest.readAll().get(j)[i], readCreatedFile.readAll().get(j)[i]);
            }
        }
    }

    @Test
    void saveFileNotEmptyTest() throws Exception {
        csvManager.createFile();
        csvManager.append();
        csvManager.saveFile();
        for(int i = 0; i < 12; i++) {
            CSVReader readCreatedFile = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);
            CSVReader readFileTest = new CSVReader(new FileReader("csvTests\\saveFileNotEmpty.csv"), ',', '"', 0);
            assertEquals(readFileTest.readAll().get(0)[i], readCreatedFile.readAll().get(0)[i]);
        }
    }

    @Test
    void clean() {
        String path = "save\\results.csv";
        File csvFile = new File(path);
        csvFile.delete();
    }
}
