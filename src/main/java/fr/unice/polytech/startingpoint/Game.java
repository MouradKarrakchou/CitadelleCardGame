package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    DeckCharacter deckCharacter;
    //------------------new
    ArrayList<Player> listOfPlayer;

    
    public Game(ArrayList<Player> listOfPlayer, DeckCharacter deckCharacter) {
    	this.listOfPlayer = listOfPlayer;
    	this.deckCharacter = deckCharacter;
	}

	void chooseCharacter(Player player){
        Random random=new Random();
        player.setRole(deckCharacter.deckCharacter.remove(random.nextInt(deckCharacter.getSize())));
    }

    public Player getWinnerV2(){
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
    
    public void startRoundV2(){
    	this.listOfPlayer.forEach(player -> this.chooseCharacter(player));
    }
}
