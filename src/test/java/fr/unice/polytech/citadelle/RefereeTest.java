package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;


public class RefereeTest {
    private final int bonusForFiveDistrict = 3;

    Player bob;
    Referee referee;
    Board mockedBoard;

    District green01;
    District green03;
    District green06;
    District yellow02;
    District blue03;
    District purple04;
    District red05;

    @BeforeEach
    public void init() {
        bob = new Player("bob");

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(bob);
        Board board = mock(Board.class);
        when(board.getListOfPlayer()).thenReturn(playerList);
        referee = new Referee(board);

        green01 = new District("greenDistrict", 1, "Green", "empty");
        green03 = new District("greenDistrict", 6, "Green", "empty");
        green06 = new District("greenDistrict", 6, "Green", "empty");

        yellow02 = new District("greenDistrict", 2, "Yellow", "empty");
        blue03 = new District("greenDistrict", 3, "Blue", "empty");
        purple04 = new District("greenDistrict", 4, "Purple", "empty");
        red05 = new District("greenDistrict", 5, "Red", "empty");

    }

    @RepeatedTest(100)
    //@Test
    void cityScoreCalculate01(){
        bob.buildDistrict(green01); // District value is 1
        bob.buildDistrict(green06); // District value is 6
        bob.buildDistrict(purple04); // District value is 4
        bob.buildDistrict(red05); // District value is 5

        referee.updatePlayerWithCityScore(bob);

        assertEquals((1 + 6 + 4 + 5), bob.getScore());
    }

    @RepeatedTest(100)
    //@Test
    void cityScoreCalculate02(){
        bob.buildDistrict(green01); // District value is 1
        bob.buildDistrict(green06); // District value is 6
        bob.buildDistrict(yellow02); // District value is 2
        bob.buildDistrict(blue03); // District value is 3
        bob.buildDistrict(purple04); // District value is 4
        bob.buildDistrict(red05); // District value is 5

        referee.updatePlayerWithCityScore(bob);

        assertEquals((1 + 6 + 2 + 3 + 4 + 5 + bonusForFiveDistrict),
                bob.getScore());
    }

    @RepeatedTest(100)
    //@Test
    void playerHasOnlyFiveDifferentDistrictColor(){
        bob.buildDistrict(green01);
        bob.buildDistrict(yellow02);
        bob.buildDistrict(blue03);
        bob.buildDistrict(purple04);
        bob.buildDistrict(red05);

        assertTrue(referee.hasFiveDistrictColors(bob));
    }

    @RepeatedTest(100)
    //@Test
    void playerHasOnlyFourDifferentDistrictColor(){
        bob.buildDistrict(green01);
        bob.buildDistrict(green03);
        bob.buildDistrict(green06);

        bob.buildDistrict(yellow02);
        bob.buildDistrict(blue03);
        bob.buildDistrict(red05);

        assertFalse(referee.hasFiveDistrictColors(bob));
    }
    
    @RepeatedTest(100)
    //@Test
	public void addBonusForPlayersTest() {
    	Board board = new Board();

    	ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();
		Player player1 = new Player("player1Test");
		Player player2 = new Player("player2Test");
		Player player3= new Player("player3Test");
		
		Behaviour behaviour1 = new Behaviour(player1, board);
		Behaviour behaviour2 = new Behaviour(player2, board);
		Behaviour behaviour3= new Behaviour(player3, board);
		
		leaderBoard.add(behaviour1);
		leaderBoard.add(behaviour2);
		leaderBoard.add(behaviour3);

		referee.addBonusForPlayers(leaderBoard);
		assertEquals(player1.getScore(), Referee.BONUS_FIRST);
		assertEquals(player2.getScore(), Referee.BONUS_END);
		assertEquals(player2.getScore(), Referee.BONUS_END);
	}

}
