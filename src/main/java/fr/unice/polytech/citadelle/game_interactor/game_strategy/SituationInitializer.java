package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import com.sun.jdi.ArrayReference;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game_character.Character;

import java.util.ArrayList;
import java.util.Optional;

public class SituationInitializer {
    public static ArrayList<Situation> generateAllSituations(Board board){
        ArrayList<Situation> AllSituations=new ArrayList<>();
        ArrayList<Character> characterAvailable;
        Character characterToChoose;
        //Creating default situation (if no other situation is available)
        //AllSituations.add(new Situation(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),0));
        //Situation where I pick a Player the Assassin to kill the player that would want to win a lot of money
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        //AllSituations.add(new Situation(Optional.empty(),Optional.empty(),Optional.of(characterAvailable) ,Optional.of(characterToChoose),1));

        return(AllSituations);
    }
}
