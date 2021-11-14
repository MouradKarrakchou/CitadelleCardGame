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
	// Pour 2 joueurs seulement
	public void setWinnerByScore(){
		listOfPlayer.forEach(player->calculateScoreOfPlayer(player));
		if (listOfPlayer.get(0).getScore()>listOfPlayer.get(1).getScore()){
			listOfPlayer.get(0).setRank(1);
			listOfPlayer.get(1).setRank(2);
		}
		else{
			listOfPlayer.get(1).setRank(1);
			listOfPlayer.get(0).setRank(2);
		}
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
