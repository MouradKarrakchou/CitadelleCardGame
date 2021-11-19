package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {
	Controller controller;
	ArrayList<Player> listOfPlayerMocket;
	private final int BonusEnd = 2;

	@BeforeEach
	public void init() {
		controller = new Controller();
		controller.initGame();
	}

	/*
	 * critères d'acception: Contexte: un joueur complète sa ville, return ce joueur
	 * là Contexte: Aucun joueur ne complète sa ville, renvoie Optional.empty()
	 */

	@Test
	public void startRoundPart2FirstWinnerTest() {
		boolean expected = true;
		boolean cityComplete = false;
		boolean result = false;
		int numberOfDistrict = 0;
		Player winner;
		ArrayList<Player> listOfPlayer = new ArrayList<Player>();

		controller.startRoundPart1();
		listOfPlayer = controller.getGame().getListOfPlayer();
		winner = listOfPlayer.get(0);

		while (!cityComplete) {
			winner.chooseDistictCard(new District("testCardDistrict" + numberOfDistrict++, 0));
			cityComplete = winner.play();
		}

		listOfPlayer.set(0, winner);
		result = controller.startRoundPart2(listOfPlayer);

		assertEquals(result, expected);
	}

	@Test
	public void startRoundPart2NoWinnerTest() {
		boolean expected = false;
		boolean result = true;
		ArrayList<Player> listOfPlayer = new ArrayList<Player>();

		controller.startRoundPart1();
		listOfPlayer = controller.getGame().getListOfPlayer();
		result = controller.startRoundPart2(listOfPlayer);

		assertEquals(result, expected);
	}

	@Test
	public void startRoundPart2SecondPlayerGetEndBonusTest() {
		boolean cityComplete = false;
		int numberOfDistrict = 0;
		Player secondPlayerSpy = spy(new Player("testPlayer"));
		ArrayList<Player> listOfPlayer = new ArrayList<Player>();
		Player winner;
		Player secondPlayer;


		controller.getGame().getListOfPlayer().set(1, secondPlayerSpy);	
		
		
		controller.startRoundPart1();
		listOfPlayer = controller.getGame().getListOfPlayer();

		winner = listOfPlayer.get(0);
		while (!cityComplete) {
			winner.chooseDistictCard(new District("testCardDistrict" + numberOfDistrict++, 0));
			cityComplete = winner.play();
		}
		listOfPlayer.set(0, winner);
		
		
		secondPlayer = listOfPlayer.get(1);
		cityComplete = false;
		numberOfDistrict = 0;

		while (!cityComplete) {
			secondPlayer.chooseDistictCard(new District("testCardDistrict" + numberOfDistrict++, 0));
			cityComplete = secondPlayer.play();
		}
		
		listOfPlayer.set(1, secondPlayer);
		controller.startRoundPart2(listOfPlayer);
		
		verify(secondPlayerSpy).updateScore(BonusEnd);

	}

	@Test
	public void runRoundNotLastTurnTest() {
		boolean expected = false;
		boolean result = controller.runRound();
		assertEquals(result, expected);

	}

}
