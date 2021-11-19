package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ControllerTest {
	Controller controller;
	ArrayList<Player> listOfPlayerMocket;
	Player p1;
    private final int BonusEnd = 2;


	@BeforeEach
	public void init() {
		controller = new Controller();
		controller.initGame();
		listOfPlayerMocket = new ArrayList<Player>();
		p1 = spy(new Player("testPlayer"));
	}

	/*
	 * critères d'acception: Contexte: un joueur complète sa ville, return ce joueur
	 * là Contexte: Aucun joueur ne complète sa ville, renvoie Optional.empty()
	 */
	@Test
	public void startRoundPart2FirstWinnerTest() {
		boolean expected = true;
		controller.startRoundPart1();
		ArrayList<Player> listOfPlayer = controller.getGame().getListOfPlayer();
		Player winner = listOfPlayer.get(0);
		boolean cityComplete = false;
		while(!cityComplete) {
			winner.chooseDistictCard(new District("testCardDistrict", 0));
			cityComplete = winner.buildDistrict(0);
		}
		listOfPlayer.set(0, winner);
		boolean result = controller.startRoundPart2(listOfPlayer);
		assertEquals(result, expected);
	}

	
	@Test
	public void startRoundPart2NoWinnerTest() {
		boolean expected = false;
		controller.startRoundPart1();
		ArrayList<Player> listOfPlayer = controller.getGame().getListOfPlayer();
		boolean result = controller.startRoundPart2(listOfPlayer);
		assertEquals(result, expected);
	}
	
	@Test
	public void startRoundPart2SecondPlayerGetEndBonusTest() {
		boolean expected = true;
		controller.getGame().getListOfPlayer().set(1, p1);
		controller.startRoundPart1();
		ArrayList<Player> listOfPlayer = controller.getGame().getListOfPlayer();
		
		Player winner = listOfPlayer.get(0);
		boolean cityComplete = false;
		while(!cityComplete) {
			winner.chooseDistictCard(new District("testCardDistrict", 0));
			cityComplete = winner.buildDistrict(0);
		}
		listOfPlayer.set(0, winner);
		
		
		Player secondPlayer = listOfPlayer.get(1);
		cityComplete = false;
		while(!cityComplete) {
			secondPlayer.chooseDistictCard(new District("testCardDistrict", 0));
			cityComplete = secondPlayer.buildDistrict(0);
		}
		
		listOfPlayer.set(1	, secondPlayer);
		boolean result = controller.startRoundPart2(listOfPlayer);
		verify(p1).updateScore(BonusEnd);
		
	}
	
	@Test
	public void runRoundNotLastTurnTest() {
		boolean expected = false;
		boolean result = controller.runRound();
		assertEquals(result, expected);

	}
	
	@Test
	public void runRoundLastTurnTest() {
		Controller spyController = spy(controller);
		boolean expected = true;
		ArrayList<Player> listPlayer = spyController.getGame().getListOfPlayer();
	    Mockito.doReturn(true).when(spyController).startRoundPart2(listPlayer);
		boolean result = spyController.runRound();
		assertEquals(result, expected);

	}
}
