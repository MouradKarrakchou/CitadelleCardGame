package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    ArrayList<Player> players = new ArrayList<>();
    Player robot1;
    Player robot2;
    Player robot3;
    Player robot4;
    DeckCharacter deckCharacter;
    DeckDistrict deckDistrict;
    Game game;

    @BeforeEach
    void init() {
        robot1 = new Player("robot1");
        robot2 = new Player("robot2");
        robot3 = new Player("robot3");
        robot4 = new Player("robot4");

        players.add(robot1);
        players.add(robot2);
        players.add(robot3);
        players.add(robot4);

        game = new Game(players, deckCharacter, deckDistrict);
    }

    @Test
    //Test of GameWinner
    void getWinner4One(){
        robot1.addDistrict(new District("house",1));
        robot2.addDistrict(new District("house",1));
        robot3.addDistrict(new District("house",1));
        robot4.addDistrict(new District("house",1));

        robot1.setRole(new Character("villager", 1));
        robot2.setRole(new Character("villager", 5));
        robot3.setRole(new Character("villager", 2));
        robot4.setRole(new Character("villager", 7));

        game.getWinner();
        assertEquals(robot1.getRank(),4);
        assertEquals(robot2.getRank(),2);
        assertEquals(robot3.getRank(),3);
        assertEquals(robot4.getRank(),1);
    }

    @Test
    void getWinner3OneVs1Two(){
        robot1.addDistrict(new District("house",1));
        robot2.addDistrict(new District("house",1));
        robot3.addDistrict(new District("house",2));
        robot4.addDistrict(new District("house",1));

        robot1.setRole(new Character("villager", 5));
        robot2.setRole(new Character("villager", 7));
        robot3.setRole(new Character("villager", 2));
        robot4.setRole(new Character("villager", 1));

        game.getWinner();
        assertEquals(robot1.getRank(),3);
        assertEquals(robot2.getRank(),2);
        assertEquals(robot3.getRank(),1);
        assertEquals(robot4.getRank(),4);
    }

    @Test
    void getWinner2OneVs2Two(){
        robot1.addDistrict(new District("house",1));
        robot2.addDistrict(new District("house",2));
        robot3.addDistrict(new District("house",2));
        robot4.addDistrict(new District("house",1));

        robot1.setRole(new Character("villager", 5));
        robot2.setRole(new Character("villager", 7));
        robot3.setRole(new Character("villager", 2));
        robot4.setRole(new Character("villager", 1));

        game.getWinner();
        assertEquals(robot1.getRank(),3);
        assertEquals(robot2.getRank(),1);
        assertEquals(robot3.getRank(),2);
        assertEquals(robot4.getRank(),4);
    }

    @Test
    void getWinner1OneVs3Two(){
        robot1.addDistrict(new District("house",1));
        robot2.addDistrict(new District("house",2));
        robot3.addDistrict(new District("house",2));
        robot4.addDistrict(new District("house",2));

        robot1.setRole(new Character("villager", 5));
        robot2.setRole(new Character("villager", 7));
        robot3.setRole(new Character("villager", 2));
        robot4.setRole(new Character("villager", 1));

        game.getWinner();
        assertEquals(robot1.getRank(),4);
        assertEquals(robot2.getRank(),1);
        assertEquals(robot3.getRank(),2);
        assertEquals(robot4.getRank(),3);
    }

    @Test
    void getWinner4Two(){
        robot1.addDistrict(new District("house",2));
        robot2.addDistrict(new District("house",2));
        robot3.addDistrict(new District("house",2));
        robot4.addDistrict(new District("house",2));

        robot1.setRole(new Character("villager", 4));
        robot2.setRole(new Character("villager", 2));
        robot3.setRole(new Character("villager", 3));
        robot4.setRole(new Character("villager", 6));

        game.getWinner();
        assertEquals(robot1.getRank(),2);
        assertEquals(robot2.getRank(),4);
        assertEquals(robot3.getRank(),3);
        assertEquals(robot4.getRank(),1);
    }

}
