package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellDistrictTest {

    private ArrayList<Player> listOfPlayer = new ArrayList<>();
    private DeckDistrict deckDistrict = new DeckDistrict();
    private Initialiser initialiser = new Initialiser();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    NormalBot normalBot = new NormalBot(player);

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

}
