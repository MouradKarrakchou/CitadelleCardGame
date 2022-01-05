package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatsBotTest {
    @BeforeEach
    void beforeEach() {
        PrintCitadels.activateLevelWarning();
    }

    @Test
    void winrateTest(){
        // Bob has 0 game played and 0 game won
        StatsBot newStatsBot = new StatsBot("bob", 0, 0, -1);
        assertEquals(0, newStatsBot.getWinrate());
    }

    @Test
    void compareTest(){
        StatsBot bobStats = new StatsBot("bob", 1000, 999, -1);
        StatsBot aliceStats = new StatsBot("bob", 1000, 998, -1);
        assertTrue(bobStats.compareTo(aliceStats) < 0);

        bobStats = new StatsBot("bob", 1000, 998, -1);
        aliceStats = new StatsBot("bob", 1000, 999, -1);
        assertTrue(bobStats.compareTo(aliceStats) > 0);

        bobStats = new StatsBot("bob", 1000, 998, -1);
        aliceStats = new StatsBot("bob", 1000, 998, -1);
        assertEquals(0, bobStats.compareTo(aliceStats));
    }
}
