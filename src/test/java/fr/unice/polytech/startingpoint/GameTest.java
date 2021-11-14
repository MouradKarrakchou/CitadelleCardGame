package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    Player robot1;
    Player robot2;
    ArrayList<Player> players=new ArrayList<>();
    Game game;
    DeckDistrict deckDistrict;
    DeckCharacter deckCharacter;
    @BeforeEach
    void init() {
        robot1=new Player("robot1");
        robot2=new Player("robot2");
        players.add(robot1);
        players.add(robot2);
        game= new Game(players,deckCharacter,deckDistrict);
    }
    /*
    @Test
    //Test of GameWinner
    void getWinnerTest(){
        robot1.addDistrict(new District("house",1));
        robot2.addDistrict(new District("house",2));
        game.getWinnerByScore();
        assertEquals(robot1.getRank(),2);
        assertEquals(robot2.getRank(),1);
    }*/

}
