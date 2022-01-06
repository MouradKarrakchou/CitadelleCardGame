package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.SpellDistrict;
import fr.unice.polytech.citadelle.game.purple_districts.Library;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Executor;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Investor;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Rusher;

import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class SpellDistrictTest {

    private final DeckDistrict deckDistrict = new DeckDistrict();

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(new ArrayList<>(),new ArrayList<>(),deckDistrict , null);
    Investor investor = spy(new Investor(player, board));
    Rusher rushBot = spy(new Rusher(player, board));

    @BeforeEach
    void beforeEach() {
        PrintCitadels.activateLevelWarning();
        Initializer.initDeckDistrict(deckDistrict);
    }

    @RepeatedTest(1)
    //@Test
    void librarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        investor.normalBehaviour();
        verify(investor, times(1)).executeSpell(spellDistricts, deckDistrict);
    }
    
    @RepeatedTest(1)
    //@Test
    void noLibrarySpellNormalBotTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        investor.normalBehaviour();
        verify(investor, times(0)).executeSpell(spellDistricts, deckDistrict);
    }
    
    @RepeatedTest(1)
    //@Test
    void librarySpellRushBotNormalBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.normalBehaviour();
        verify(rushBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }

    @RepeatedTest(1)
    //@Test
    void noLibrarySpellRushBotNormalBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        rushBot.normalBehaviour();
        verify(rushBot, times(0)).executeSpell(spellDistricts, deckDistrict);
    }

    @RepeatedTest(1)
    //@Test
    void librarySpellRushBotEndGameBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        executor.buildDistrict(new Library("Library", 6,"Purple","Prestige"));

        rushBot.endGameBehaviour();
        verify(rushBot, times(1)).executeSpell(spellDistricts, deckDistrict);
    }

    @RepeatedTest(1)
    //@Test
    void noLibrarySpellRushBotEndGameBehaviorTest() {
        ArrayList<SpellDistrict> spellDistricts = new ArrayList<>();
        spellDistricts.add(new SpellDistrict("Library", 6, "Purple", "Prestige"));

        rushBot.endGameBehaviour();
        verify(rushBot, times(0)).executeSpell(spellDistricts, deckDistrict);
    }

}
