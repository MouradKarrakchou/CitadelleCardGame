package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

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
		{player.updateValue(district.getValue());
		});
	}
	//Maintenant pour tous les joueurs (Avant : Pour 2 joueurs seulement)
	public ArrayList<Player> setWinnerByScore(){
		int max;
		Player rankOfPlayer;
		ArrayList<Player> listOfPlayerSorted = new ArrayList<>();

		listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));

		while (listOfPlayerSorted.size() < 4){
			max = listOfPlayer.get(0).getScore();
			rankOfPlayer = listOfPlayer.get(0);
			for (int i = 1; i < listOfPlayer.size(); i++){
				if (listOfPlayer.get(i).getScore() > max){
					max = listOfPlayer.get(i).getScore();
					rankOfPlayer = listOfPlayer.get(i);
				}
			}
			listOfPlayerSorted.add(rankOfPlayer);
			listOfPlayer.remove(rankOfPlayer);
		}

		for(int i = 0; i < listOfPlayerSorted.size(); i++) listOfPlayerSorted.get(i).setRank(i+1);

		return listOfPlayerSorted;
	}

    public Player getWinnerByRole(){
    	int maxValue = 0;
    	Player winner = null;
    	for(int i = 0 ; i < this.listOfPlayer.size() ;i++) {
    		if(listOfPlayer.get(i).getCharacter().getValue()>maxValue) {
    			maxValue=listOfPlayer.get(i).getCharacter().getValue();
    			winner=listOfPlayer.get(i);
    		}
    	}
    	return winner;
    }
}
