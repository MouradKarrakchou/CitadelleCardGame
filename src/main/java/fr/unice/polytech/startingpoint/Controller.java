package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    Game game;
    PrintCitadels printC;
    
    //---------new
    int nbrJoueur = 2;
    ArrayList<Player> listOfPlayer;  
    DeckCharacter deckCharacter;
    //---------new

    
    Controller(){
        listOfPlayer=new ArrayList<>();
        listOfPlayer.add(new Player("robot1"));
        listOfPlayer.add(new Player("robot2"));
        deckCharacter=new DeckCharacter();
        game=new Game(listOfPlayer,deckCharacter);
        printC=new PrintCitadels();
    }
    
    //---------new
    void lauchGameV2(){
        game.startRoundV2();
        printC.printStartInformation(listOfPlayer);
        printC.win(game.getWinnerV2());
    }
    
    public void initGame() {
        listOfPlayer = new ArrayList<>();
        deckCharacter = new DeckCharacter();
    	for(int i =0; i < nbrJoueur ; i++)
    		listOfPlayer.add(new Player("robot"+i));
    	this.game = new Game(listOfPlayer, deckCharacter); // créer un jeu avec tout les éléments nécessaires
    }
    //---------new


}
