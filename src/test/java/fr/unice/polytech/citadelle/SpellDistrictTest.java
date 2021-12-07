package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellDistrictTest {

    private final DeckDistrict deckDistrict = new DeckDistrict(); //new spyDeckDistrict
    private final Initialiser initialiser = new Initialiser();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(null,deckDistrict , null);
    NormalBot normalBot = new NormalBot(player, board);
    RushBot rushBot = new RushBot(player, board);

    @Test
    void librarySpellNormalBotTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        normalBot.normalBehaviour();
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellNormalBotTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        normalBot.normalBehaviour();
        assertEquals(1, player.getDistrictCards().size());
    }

    @Test
    void librarySpellRushBotNormalBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.normalBehaviour();
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellRushBotNormalBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        rushBot.normalBehaviour();
        assertEquals(1, player.getDistrictCards().size());
    }

    @Test
    void librarySpellRushBotEndGameBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.endGameBehaviour();
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellRushBotEndGameBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        rushBot.endGameBehaviour();
        assertEquals(1, player.getDistrictCards().size());
    }

}
