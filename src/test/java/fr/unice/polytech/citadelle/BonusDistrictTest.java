package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.purple_districts.DragonGate;
import fr.unice.polytech.citadelle.game.purple_districts.University;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_interactor.Executor;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BonusDistrictTest {
    private ArrayList<Player> listOfPlayer = new ArrayList<>();
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(listOfPlayer,new ArrayList<>(), deckDistrict, deckCharacter);
    Referee referee = new Referee(board);

    @RepeatedTest(1)
    @Test
    void dragonGateBonusTest() {
        executor.buildDistrict(new DragonGate("Dragon Gate", 6,"Purple","Prestige"));
        referee.updatePlayerWithCityScore(player);
        assertEquals(8, player.getScore());
    }

    @RepeatedTest(1)
    @Test
    void universityBonusTest() {
        executor.buildDistrict(new University("University", 6,"Purple","Prestige"));
        referee.updatePlayerWithCityScore(player);
        assertEquals(8, player.getScore());
    }

    @RepeatedTest(1)
    @Test
    void bothBonusTest() {
        executor.buildDistrict(new DragonGate("Dragon Gate", 6,"Purple","Prestige"));
        executor.buildDistrict(new University("University", 6,"Purple","Prestige"));
        referee.updatePlayerWithCityScore(player);
        assertEquals(16, player.getScore());
    }
}
