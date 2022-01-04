package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import com.sun.jdi.ArrayReference;
import fr.unice.polytech.citadelle.game.Board;

import java.util.ArrayList;

public class SituationInitializer {
    public static ArrayList<Situation> generateAllSituations(Board board){
        ArrayList<Situation> AllSituations=new ArrayList<>();
        ArrayList<Character> characterAvailable= new ArrayList<>();
        //Creating default situation (if no other situation is available)
        AllSituations.add(new Situation(null,null,null,null,null));

        return(AllSituations);
    }
}
