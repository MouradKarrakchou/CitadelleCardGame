package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.characters_class.Assassin;
import fr.unice.polytech.citadelle.characters_class.Magician;
import fr.unice.polytech.citadelle.characters_class.Thief;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

import java.util.ArrayList;

public class Predict {

    public Predict(){}

    //A changer : il faut une methode qui predit ce que le player a.
    Character predictWhoIsPlayer(Player player, ArrayList<String> listOfUntargetableCharacter){

        ArrayList<String> listOfTargetableCharacter = allCharacters();

        for(String character : listOfUntargetableCharacter) {
            if(listOfTargetableCharacter.contains(character)) listOfTargetableCharacter.remove(character);
        }

        int goldsOfPlayer = player.getGolds();
        ArrayList<District> handOfPlayer = player.getDistrictCards();
        ArrayList<District> cityOfPlayer = player.getCity().getBuiltDistrict();

        //assassin
        //thief
        //magician
        //warlord

        //Is the Thief interesting for this player? It can be if he does not have a lot of golds
        if(goldsOfPlayer <= 3) return new Thief();

        //Is the Magician interesting for this player? It can be if he does not have a lot of district cards
        if(handOfPlayer.size() <= 2) return new Magician();

        //Is the Assassin interesting for this player? It can be if someone is close to finish the game

        //Is the Assassin interesting for this player? It can be if someone is close to finish the game

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
