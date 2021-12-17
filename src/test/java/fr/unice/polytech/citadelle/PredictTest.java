package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.characters_class.Thief;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Predict;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PredictTest {

    Board board = new Board();
    Predict predict = new Predict(board);

    ArrayList<Character> allCharacters = board.getListOfCharacter();

    @Test
    void targetableCharactersForPredictWhoIsPlayerTest() {
        ArrayList<Character> untargetableCharacter = new ArrayList<>();
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.BISHOP_INDEX));
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.THIEF_INDEX));

        ArrayList<Character> listToReturn = allCharacters;
        listToReturn.remove(Initializer.BISHOP_INDEX);
        listToReturn.remove(Initializer.THIEF_INDEX);

        assertEquals(listToReturn, predict.targetableCharactersForPredictWhoIsPlayer(untargetableCharacter));
    }

}
