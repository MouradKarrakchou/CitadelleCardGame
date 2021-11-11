package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    Player robot1;
    Player robot2;
    DeckCharacter deckCharacter;
    //------------------new
    ArrayList<Player> listOfPlayer;

    public Game(Player robot1,Player robot2){
        this.robot1=robot1;
        this.robot2=robot2;
        deckCharacter=new DeckCharacter();
        //deckCharacter.initialise(); //car maintenant le deck s'initialise quand on le construit
    }
    //------------------new

    
    public Game(ArrayList<Player> listOfPlayer, DeckCharacter deckCharacter) {
    	this.listOfPlayer = listOfPlayer;
    	this.deckCharacter = deckCharacter;
	}

	void chooseCharacter(Player player){
        Random random=new Random();
        player.setRole(deckCharacter.deckCharacter.remove(random.nextInt(deckCharacter.getSize())));
    }
    
    public Player getWinner(){
        int valuePlayer1=robot1.getCharacter().getValue();
        int valuePlayer2=robot2.getCharacter().getValue();
        if (valuePlayer1>valuePlayer2){
            return(robot1);
        }
        else
            return (robot2);
    }
    
    //------------------new

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
    //------------------new

    public void startRound(){
        chooseCharacter(robot1);
        chooseCharacter(robot2);
    }
    
    public void startRoundV2(){
    	this.listOfPlayer.forEach(player -> this.chooseCharacter(player));
    }
    public Player getRobot1() {
        return robot1;
    }
    public Player getRobot2() {
        return robot2;
    }
}
