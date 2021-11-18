package fr.unice.polytech.startingpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ControllerTest {
	Controller controller;
	ArrayList<Player> listOfPlayerMocket;

	@BeforeEach
	public void init() {
		controller = new Controller();
		controller.initGame();
		listOfPlayerMocket = new ArrayList<Player>();
	}

	/*
	 * critères d'acception: Contexte: un joueur complète sa ville, return ce joueur
	 * là Contexte: Aucun joueur ne complète sa ville, renvoie Optional.empty()
	 */
	@Test
	public void startRoundPart2NormalUtilisationTest() {
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

}
