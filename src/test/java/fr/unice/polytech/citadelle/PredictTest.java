package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.purple_districts.Graveyard;
import fr.unice.polytech.citadelle.game.purple_districts.Observatory;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Predict;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PredictTest {

    Board board = new Board(new ArrayList<>(), Initializer.createListOfAllCharacter(), new DeckDistrict(), new DeckCharacter());
    Predict predict = new Predict(board);
    ArrayList<Character> allCharacters = board.getListOfCharacter();

    @RepeatedTest(100)
    @Test
    void targetableCharactersForPredictWhoIsPlayerTest() {

        ArrayList<Character> untargetableCharacter = new ArrayList<>();
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.BISHOP_INDEX));
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.THIEF_INDEX));

        ArrayList<Character> listToReturn = new ArrayList<>();
        for(Character character : allCharacters) {
            if(!character.getName().equals("Bishop") && !character.getName().equals("Thief"))
                listToReturn.add(character);
        }

        assertEquals(listToReturn, predict.targetableCharactersForPredictWhoIsPlayer(untargetableCharacter));
    }

    @RepeatedTest(100)
    @Test
    void allCharactersTest() {
        assertEquals(allCharacters, predict.allCharacters());
    }

    @RepeatedTest(100)
    @Test
    void canBeArchitectTest() {
        Player player = new Player("Player");
        player.addDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.addDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.addDistrict(new District("Battlefield",3,"Red","Soldiery"));

        player.setGolds(6);

        assertEquals(true, predict.canBeArchitect(player, new ArrayList<>()));
    }

    @RepeatedTest(100)
    @Test
    void Test() {

    }



}
