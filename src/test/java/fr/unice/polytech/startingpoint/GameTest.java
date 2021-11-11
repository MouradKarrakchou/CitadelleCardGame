package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    Player robot1;
    Player robot2;
    Game game;
    @BeforeEach
    void init() {
        robot1=new Player("robot1");
        robot2=new Player("robot2");
        game= new Game(robot1,robot2);
    }
    @Test
    //Test of GameWinner
    void getWinnerTest(){
        robot1.setRole(new Character("villageois",5));
        robot2.setRole(new Character("villageois",7));
        assertEquals(robot2,game.getWinner());
    }

}
