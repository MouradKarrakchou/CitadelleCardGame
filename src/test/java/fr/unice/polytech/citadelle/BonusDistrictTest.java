package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_interactor.Executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BonusDistrictTest {
    private ArrayList<Player> listOfPlayer = new ArrayList<>();
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;

    Player player = new Player("Player");
    Executor executor = new Executor(player);
    Board board = new Board(listOfPlayer, deckDistrict, deckCharacter);
    Referee referee = new Referee(board);



    @BeforeEach
    void initialiser() {
        executor.buildDistrict(new District("Dragon Gate", 6,"Purple","Prestige"));
        referee.updatePlayerWithCityScore(player);
    }

    @Test
    void dragonGateSpell() {
        assertEquals(8, player.getScore());
    }
}
