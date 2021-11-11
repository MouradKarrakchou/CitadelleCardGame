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
        game=new Game(new Player("robot1"),new Player("robot2"));
        printC=new PrintCitadels();
    }
    void lauchGame(){
        game.startRoundV2();
        printC.chooseRole(game.getRobot1());
        printC.chooseRole(game.getRobot2());
        printC.win(game.getWinner());
    }
    
    //---------new
    void lauchGameV2(){
        game.startRoundV2();
        printC.printStartInformation(listOfPlayer);
        printC.win(game.getWinnerV2());
    }
    
    public void initGame() {
        listOfPlayer = new ArrayList<Player>();  
        deckCharacter = new DeckCharacter();
    	for(int i =0; i < nbrJoueur ; i++)
    		listOfPlayer.add(new Player("robot"+i));
    	this.game = new Game(listOfPlayer, deckCharacter); // créer un jeu avec tout les éléments nécessaires
    }
    //---------new


}
