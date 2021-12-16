package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.characters_class.*;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

import java.util.ArrayList;

public class Predict {
    Board board;

    public Predict(){}

    //A changer : il faut une methode qui predit ce que le player a.
    Character predictWhoIsPlayer(Player player, ArrayList<String> listOfUntargetableCharacter){
        ArrayList<Player> listOfPlayers = board.getListOfPlayer();

        ArrayList<String> listOfTargetableCharacter = allCharacters();
        for(String character : listOfUntargetableCharacter) {
            if(listOfTargetableCharacter.contains(character)) listOfTargetableCharacter.remove(character);
        }

        int goldsOfPlayer = player.getGolds();
        ArrayList<District> handOfPlayer = player.getDistrictCards();
        ArrayList<District> cityOfPlayer = player.getCity().getBuiltDistrict();

        //Is the Architect interesting for this player? It can be if he does not have a lot of golds
        if(handOfPlayer.size() >= 3 && goldsOfPlayer >= 6 && listOfTargetableCharacter.contains("Architect"))
            return new Architect();

        //Is the Bishop interesting for this player? It can be if he has 6 or more districts built in his city
        if(cityOfPlayer.size() >= 6 && listOfTargetableCharacter.contains("Bishop"))
            return new Bishop();

        //Is the King interesting for this player? It can be if he has 6 or more districts built in his city
        int counter = 0;
        for(District district : cityOfPlayer) {
            if(district.getNameOfFamily().equals("Nobility")) counter++;
        }
        if(counter == 3 && listOfTargetableCharacter.contains("Bishop"))
            return new Bishop();

        //Is the Thief interesting for this player? It can be if he does not have a lot of golds
        if(goldsOfPlayer <= 3 && listOfTargetableCharacter.contains("Thief"))
            return new Thief();

        //Is the Magician interesting for this player? It can be if he does not have a lot of district cards
        if(handOfPlayer.size() <= 2 && listOfTargetableCharacter.contains("Magician"))
            return new Magician();

        //Is the Warlord interesting for this player? It can be if someone is close to finish the game (has 7 districts)
        for(Player otherPlayer : listOfPlayers) {
            if(otherPlayer.getCity().getBuiltDistrict().size() == 7 && listOfTargetableCharacter.contains("Warlord"))
                return new Warlord();
        }

        return new Assassin();
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
