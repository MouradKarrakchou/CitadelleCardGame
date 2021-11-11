package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    Player robot1;
    Player robot2;
    ArrayList<Character> deckCharacter;

    public Game(Player robot1,Player robot2){
        this.robot1=robot1;
        this.robot2=robot2;
    }
    private void createDeck(){
        deckCharacter=new ArrayList<>();
        for (int k=1;k<9;k++){
            deckCharacter.add(new Character("villager",k));
        }
    }
    void chooseCharacter(Player player){
        Random random=new Random();
        player.setRole(deckCharacter.remove(random.nextInt(deckCharacter.size())));
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
        createDeck();
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
