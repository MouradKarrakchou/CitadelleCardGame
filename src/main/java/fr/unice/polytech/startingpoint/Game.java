package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    private ArrayList<Player> listOfPlayer;
    private DeckCharacter deckCharacter;
    private DeckDistrict deckDistrict;
    private ArrayList<Player> listOfPlayerOfNextRound;


    public Game(ArrayList<Player> listOfPlayer, DeckCharacter deckCharacter, DeckDistrict deckDistrict) {
        this.listOfPlayer = listOfPlayer;
        this.deckCharacter = deckCharacter;
        this.deckDistrict = deckDistrict;
        this.listOfPlayerOfNextRound= new ArrayList<>();
    }

    public void calculateScoreOfPlayer(Player player) {
        player.getDistrictCards().forEach(district ->
        {
            player.updateScore(district.getValue());
        });
    }

    public void getWinner() {
        listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i + 1);
    }

    public ArrayList<Player> getListOfPlayerOfNextRound() {
        return listOfPlayerOfNextRound;
    }

    public ArrayList<Player> getListOfPlayer(){
    	return listOfPlayer;
    }

    public void updateListOfPlayer() {
        if (listOfPlayerOfNextRound.size()!=0)
        {listOfPlayer.clear();
        listOfPlayer.addAll(listOfPlayerOfNextRound);}
    }
}
