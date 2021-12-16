package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.characters_class.Assassin;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

import java.util.ArrayList;

public class Predict {
    public Predict(){
    }
    Character predictWhoIsPlayer(Player player, ArrayList<String> listOfUntargetableCharacter){
        //A changer : il faut une methode qui predit ce que le player a.
        ArrayList<String> listOfTargetableCharacter = allCharacters();

        for(String character : listOfUntargetableCharacter) {

        }

        int goldsOfPlayer = player.getGolds();
        ArrayList<District> handOfPlayer = player.getDistrictCards();
        ArrayList<District> cityOfPlayer = player.getCity().getBuiltDistrict();


        return (new Assassin());
    }

    ArrayList<String> allCharacters() {
        ArrayList<String> listOfTargetableCharacter = new ArrayList<>();
        listOfTargetableCharacter.add("Architect");
        listOfTargetableCharacter.add("Assassin");
        listOfTargetableCharacter.add("Bishop");
        listOfTargetableCharacter.add("King");
        listOfTargetableCharacter.add("Magician");
        listOfTargetableCharacter.add("Merchant");
        listOfTargetableCharacter.add("Thief");
        listOfTargetableCharacter.add("Warlord");

        return listOfTargetableCharacter;
    }
}
