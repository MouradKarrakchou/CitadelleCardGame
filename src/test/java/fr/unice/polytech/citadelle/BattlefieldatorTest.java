package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattlefieldatorTest {
    String path;
    CsvManager csvManager;
    ArrayList<Player> leaderboard;
    Battlefieldator battlefield;

    @BeforeEach
    void init() throws Exception {
        PrintCitadels.activateLevelWarning();
        path = "csvTests\\results.csv";
        leaderboard = new ArrayList<>();

        // Reset the csv file
        csvManager = new CsvManager(path);
        csvManager.createFile();
    }

    @Test
    void getBestBotNameTest() throws Exception {
        Player mockedBob = mock(Player.class);
        when(mockedBob.getName()).thenReturn("RobotBob 1");
        when(mockedBob.getRank()).thenReturn(1);

        Player mockedAlice = mock(Player.class);
        when(mockedAlice.getName()).thenReturn("RobotAlice 1");
        when(mockedAlice.getRank()).thenReturn(2);

        leaderboard.add(mockedBob);
        leaderboard.add(mockedAlice);

        csvManager = new CsvManager(leaderboard, path);
        csvManager.append();
        battlefield = new Battlefieldator(csvManager);

        assertEquals("RobotBob", battlefield.getBestBotName());
    }
}
