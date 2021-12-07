package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class SpellDistrictTest {

    private final DeckDistrict deckDistrict = new DeckDistrict(); //new spyDeckDistrict
    private final Initialiser initialiser = new Initialiser();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(null,deckDistrict , null);
    NormalBot normalBot = spy(new NormalBot(player, board));
    RushBot rushBot = spy(new RushBot(player, board));

    @Test
    void librarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        initialiser.initDeckDistrict(deckDistrict);
        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        normalBot.normalBehaviour();
        verify(normalBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }

    @Test
    void noLibrarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        normalBot.normalBehaviour();
        verify(normalBot, times(0)).executeSpell(spellDistricts, deckDistrict);
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
        player.setGolds(1);
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
        System.out.println(player.getGolds());
        player.getDistrictCards().clear();
        initialiser.initDeckDistrict(deckDistrict);

        rushBot.endGameBehaviour();
        System.out.println(player.getGolds());
        assertEquals(1, player.getDistrictCards().size());
    }



}
