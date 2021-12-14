package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class SpellDistrictTest {

    private final DeckDistrict deckDistrict = new DeckDistrict();
    private final Initializer initializer = new Initializer();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(null,new ArrayList<>(),deckDistrict , null);
    NormalBot normalBot = spy(new NormalBot(player, board));
    RushBot rushBot = spy(new RushBot(player, board));

    @BeforeEach
    void beforeEach() {
        initializer.initDeckDistrict(deckDistrict);
    }

    @Test
    void librarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        normalBot.normalBehaviour();
        verify(normalBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }
    
    @Test
    void noLibrarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        normalBot.normalBehaviour();
        verify(normalBot, times(0)).executeSpell(spellDistricts, deckDistrict);
    }
    
    @Test
    void librarySpellRushBotNormalBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.normalBehaviour();
        verify(rushBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }

    @Test
    void noLibrarySpellRushBotNormalBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        rushBot.normalBehaviour();
        verify(rushBot, times(0)).executeSpell(spellDistricts, deckDistrict);
    }

    @Test
    void librarySpellRushBotEndGameBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.endGameBehaviour();
        verify(rushBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }

    @Test
    void noLibrarySpellRushBotEndGameBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        rushBot.endGameBehaviour();
        verify(rushBot, times(0)).executeSpell(spellDistricts, deckDistrict);
    }

}
