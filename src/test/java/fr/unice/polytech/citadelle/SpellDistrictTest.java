package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellDistrictTest {

    private ArrayList<Player> listOfPlayer = new ArrayList<>();
    private DeckDistrict deckDistrict = new DeckDistrict(); //new spyDeckDistrict
    private Initialiser initialiser = new Initialiser();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    NormalBot normalBot = new NormalBot(player);
    RushBot rushBot = new RushBot(player);

    @Test
    void librarySpellNormalBotTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        normalBot.normalBehaviour(deckDistrict);
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellNormalBotTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        normalBot.normalBehaviour(deckDistrict);
        assertEquals(1, player.getDistrictCards().size());
    }

    @Test
    void librarySpellRushBotNormalBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.normalBehaviour(deckDistrict);
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellRushBotNormalBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        rushBot.normalBehaviour(deckDistrict);
        assertEquals(1, player.getDistrictCards().size());
    }

    @Test
    void librarySpellRushBotEndGameBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.endGameBehaviour(deckDistrict);
        assertEquals(2, player.getDistrictCards().size());
    }

    @Test
    void noLibrarySpellRushBotEndGameBehaviorTest() {
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        rushBot.endGameBehaviour(deckDistrict);
        assertEquals(1, player.getDistrictCards().size());
    }

}
