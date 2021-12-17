package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game.purple_districts.DragonGate;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Predict;
import fr.unice.polytech.citadelle.game_interactor.Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StrategyTestWarlord {
    private District green01;
    private District green03;
    private District green06;

    private District harbor;
    private District church;
    private District dragonGate;

    private Board gameBoard;
    private Predict predict;

    private Player bob;
    private Player alice;
    private Player peter;


    @BeforeEach
    public void setup(){
        green01 = new District("greenDistrict", 1, "Green", "empty");
        green03 = new District("greenDistrict", 3, "Green", "empty");
        green06 = new District("greenDistrict", 6, "Green", "empty");

        harbor = new District("Harbor",4,"Green","Trade and Handicrafts");
        church = new District("Church",2,"Blue","Religion");
        dragonGate = new DragonGate("Dragon Gate", 6,"Purple","Prestige");


        gameBoard = Initializer.createBoard(Initializer.createListOfAllCharacter());
        predict = new Predict(gameBoard);
        bob = new Player("Bob");
        alice = new Player("Alice");
        peter = new Player("Peter");
    }

    @Test
    public void choosePlayerForWarlordAdvancedTest(){
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(bob);
        listOfPlayers.add(alice);
        listOfPlayers.add(peter);

        alice.buildDistrict(green01); // Predicted score of bob will be 1.
        bob.buildDistrict(green03); // Predicted score of bob will be 3.
        peter.buildDistrict(green06); // Predicted score of bob will be 6.

        gameBoard.getListOfPlayer().addAll(listOfPlayers);
        Strategy bobStrategy = new Strategy(8, gameBoard, bob, predict);

        // Bob is 2nd on the predicted leaderboard.
        // Bob will try to destroy a peter's district, first on the predicted leaderboard.
        Player selectPlayerForWarlord = bobStrategy.choosePlayerForWarlordAdvanced();
        assertEquals(peter, selectPlayerForWarlord);
    }

    @Test
    public void choosePlayerForWarlordFirstOnLeaderboardTest(){
        ArrayList<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.add(bob);
        listOfPlayers.add(alice);
        listOfPlayers.add(peter);

        alice.buildDistrict(green01); // Predicted score of bob will be 1.
        bob.buildDistrict(green03); // Predicted score of bob will be 3.
        peter.buildDistrict(green06); // Predicted score of bob will be 6.

        gameBoard.getListOfPlayer().addAll(listOfPlayers);
        Strategy bobStrategy = new Strategy(8, gameBoard, peter, predict);

        // peter is first on the predicted leaderboard.
        // peter will try to destroy a bob's district, second on the predicted leaderboard.
        Player selectPlayerForWarlord = bobStrategy.choosePlayerForWarlordAdvanced();
        assertEquals(bob, selectPlayerForWarlord);
    }

//    @Test
//    public void chooseDistrictToDestroyTest(){
//        bob.buildDistrict(harbor);
//        bob.buildDistrict(church);
//        bob.buildDistrict(dragonGate);
//        Strategy aliceStrategy = new Strategy(8, gameBoard, alice, predict);
//
//        alice.setGolds(4);
//        assertNull(aliceStrategy.chooseDistrictToDestroy(bob));
//
//        alice.setGolds(5);
//        assertEquals(dragonGate, aliceStrategy.chooseDistrictToDestroy(bob));
//
//        alice.setGolds(6);
//        assertEquals(dragonGate, aliceStrategy.chooseDistrictToDestroy(bob));
//    }
}
