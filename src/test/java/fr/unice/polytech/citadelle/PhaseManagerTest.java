package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.PhaseManager;

public class PhaseManagerTest {
	Player player;
	Board board;
	PhaseManager phaseManager;
	
	@BeforeEach
	public void init() {
	    board = new Board(new ArrayList<Player>(), new ArrayList<Character>(), new DeckDistrict(), new DeckCharacter());
		player = new Player("testPlayer");
		phaseManager = new PhaseManager(player, board);
	}
	
	@RepeatedTest(1)
	@Test
	public void analyseGameLastTurnTest() {
		Player playerEndCity = new Player("playerEndCity");
		ArrayList<Player> listOfPlayer = board.getListOfPlayer();
		listOfPlayer.clear();
		listOfPlayer.add(playerEndCity);
		listOfPlayer.add(player);
		
		City city = playerEndCity.getCity();
		city.getBuiltDistrict().clear();
		for(int i = 0 ; i < 8 ; i++)
			city.buildDistrict(new District("testName", i, "testColor", "testFamily"));

		assertEquals(PhaseManager.LAST_TURN_PHASE,phaseManager.analyseGame());	
	}
	
	@RepeatedTest(1)
	@Test
	public void analyseGameEndGameTest() {
		City city = player.getCity();
		city.getBuiltDistrict().clear();
		for(int i = 0 ; i < 6 ; i++)
			city.buildDistrict(new District("testName", i, "testColor", "testFamily"));
		assertEquals(PhaseManager.END_GAME_PHASE,phaseManager.analyseGame());	

	}
	
	@RepeatedTest(1)
	@Test
	public void analyseGameMidGameTest() {
		City city = player.getCity();
		city.getBuiltDistrict().clear();
		for(int i = 0 ; i < 5 ; i++)
			city.buildDistrict(new District("testName", i, "testColor", "testFamily"));
		assertEquals(PhaseManager.MID_GAME_PHASE,phaseManager.analyseGame());	
	}
	
}
