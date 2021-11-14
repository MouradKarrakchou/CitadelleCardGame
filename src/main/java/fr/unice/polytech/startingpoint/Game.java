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
	ArrayList<Player> listOfPlayerSorted = new ArrayList<>();
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
	

	
	public ArrayList<Player> sortByScoreAndCharacter_v666(ArrayList<Player> list){
		Collections.sort(list, Comparator.comparing(Player::getScore).reversed()
	            .thenComparing(Player::getCharacterValue));
		return list;
	}
	 	
	
	
	//Maintenant pour tous les joueurs (Avant : Pour 2 joueurs seulement)
	public ArrayList<Player> getWinnerByScore(){
		int max;
		Player playerToRank;

		listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));

		while (listOfPlayerSorted.size() < 4){
			max = listOfPlayer.get(0).getScore();
			playerToRank = listOfPlayer.get(0);
			for (int i = 1; i < listOfPlayer.size(); i++){
				if (listOfPlayer.get(i).getScore() > max){
					max = listOfPlayer.get(i).getScore();
					playerToRank = listOfPlayer.get(i);
				}
			}
			listOfPlayerSorted.add(playerToRank);
			listOfPlayer.remove(playerToRank);
		}

		for(int i = 0; i < listOfPlayerSorted.size(); i++) listOfPlayerSorted.get(i).setRank(i+1);

		return listOfPlayerSorted;
	}

	public ArrayList<Player> getWinnerByRole(ArrayList<Player> playersToCompare){
		int size = playersToCompare.size();
		ArrayList<Player> listOfPlayerSorted = new ArrayList<>();

		while (listOfPlayerSorted.size() < size) {
			int max = playersToCompare.get(0).getCharacter().getValue();
			Player playerWithHighestRole = playersToCompare.get(0);
			for (int i = 1; i < playersToCompare.size(); i++) {
				if (playersToCompare.get(i).getCharacter().getValue() > max) {
					max = playersToCompare.get(i).getCharacter().getValue();
					playerWithHighestRole = playersToCompare.get(i);
				}
			}
			listOfPlayerSorted.add(playerWithHighestRole);
			playersToCompare.remove(playerWithHighestRole);
		}

		for(int i = 0; i < listOfPlayerSorted.size(); i++) listOfPlayerSorted.get(i).setRank(i+1);

		return listOfPlayerSorted;
	}



	public ArrayList<Player> equality(ArrayList<Player> listOfPlayerSorted){
		int compteur = 0;
		int firstValue;

		ArrayList<Player> playersRanked = new ArrayList<>();
		ArrayList<Player> playersToCompare = new ArrayList<>();
		ArrayList<Player> playersCompared;

		//Pour 4 joueurs triés
		while (playersRanked.size() < 4) {
			firstValue = listOfPlayerSorted.get(compteur).getScore();
			playersToCompare.add(listOfPlayerSorted.get(0));

			while (listOfPlayerSorted.size() > 1 && compteur < listOfPlayerSorted.size()-1 && listOfPlayerSorted.get(compteur + 1).getScore() == firstValue) {
				playersToCompare.add(listOfPlayerSorted.get(compteur + 1));
				compteur++;
			}

			playersCompared = getWinnerByRole(playersToCompare);
			playersRanked.addAll(playersCompared);
			listOfPlayerSorted.removeAll(playersCompared);
			playersToCompare.removeAll(playersCompared);
			compteur = 0;
		}

		for(int i = 0; i < playersRanked.size(); i++) playersRanked.get(i).setRank(i+1);

		return playersRanked;
	}
}
