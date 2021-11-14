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
    DeckCharacter deckCharacter;
    ArrayList<Player> listOfPlayer;
	DeckDistrict deckDistrict;

    
    public Game(ArrayList<Player> listOfPlayer, DeckCharacter deckCharacter,DeckDistrict deckDistrict) {
    	this.listOfPlayer = listOfPlayer;
    	this.deckCharacter = deckCharacter;
		this.deckDistrict= deckDistrict;
	}
	public void calculateScoreOfPlayer(Player player){
		player.getDistrictCards().forEach(district ->
		{player.updateScore(district.getValue());
		});
	}

	public void getWinner(){
		int max;
		Player playerToRank;
		listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));
		Collections.sort(listOfPlayer);
		Collections.reverse(listOfPlayer);
		for(int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i+1);
	}
}
