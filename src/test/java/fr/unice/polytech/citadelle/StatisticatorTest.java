package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StatisticatorTest {
    Statisticator myStatisticator;

    @BeforeEach
    public void init() {
        PrintCitadels.activateLevelWarning();
        myStatisticator = new Statisticator();
    }

    @Test
    void getBotStatsTest(){
        Optional<StatsBot> myStat = myStatisticator.getBotStats("bob");
        assertFalse(myStat.isPresent());
    }

    @Test
    void updateTest(){
        ArrayList<Player> leaderboard = new ArrayList<>();

        Player mokedPlayer = mock(Player.class);
        when(mokedPlayer.getName()).thenReturn("bob");

        leaderboard.add(mokedPlayer);
        myStatisticator.updateBotStats(leaderboard);

        Optional<StatsBot> statsBot = myStatisticator.getBotStats("bob");
        assertTrue(statsBot.isPresent());
        assertEquals("bob", statsBot.get().getName());
        assertEquals(100.00, statsBot.get().getWinrate());
    }
}
