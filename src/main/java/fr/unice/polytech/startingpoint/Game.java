package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    Player robot1;
    Player robot2;
    Deck deck;

    public Game(Player robot1,Player robot2){
        this.robot1=robot1;
        this.robot2=robot2;
        deck=new Deck();
        deck.initialise();
    }
    void chooseCharacter(Player player){
        Random random=new Random();
        player.setRole(deck.deckCharacter.remove(random.nextInt(deck.getSize())));
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
    public void startRound(){
        chooseCharacter(robot1);
        chooseCharacter(robot2);
    }
    public Player getRobot1() {
        return robot1;
    }
    public Player getRobot2() {
        return robot2;
    }
}
