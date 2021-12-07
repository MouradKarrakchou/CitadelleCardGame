package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

public class RushBotTest {
	Player player;
	RushBot rusher;
	DeckDistrict deckDistrict;
	Board board;

	@BeforeEach
	public void init() {
		deckDistrict = new DeckDistrict();
		deckDistrict.initialise();
		player = new Player("Player1");
		board = new Board(null,deckDistrict , null);
		rusher = spy(new RushBot(player, board));
	}

	@Test
	public void normalBehaviourNoCardButGoldTest() {
		player = new Player("Player1");
		rusher = spy(new RushBot(player, board));

		player.getDistrictCards().clear();

		player.setGolds(15);

		rusher.normalBehaviour();
		verify(rusher, times(1)).takeCard(any());
	}

	@Test
	public void normalBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		rusher.normalBehaviour();
		verify(rusher, times(1)).takeCard(any());
		verify(rusher, times(0)).takeGold();
	}

	@Test
	public void normalBehaviourTooExpansivesDistrictsTest() {
		int tooExpansiveValue = 4;
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict", tooExpansiveValue, "testColor", "testFamily"));
		rusher.normalBehaviour();
		verify(rusher, times(1)).takeCard(any());
		verify(rusher, times(0)).takeGold();
	}

	@Test
	public void normalBehaviourABuildableDistrictTest() {
		int cheapValue = 3;

		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		player.getCity().getBuiltDistrict().clear();
		player.setGolds(0);

		District newCheapDistrict = new District("testDistrict", cheapValue, "testColor", "testFamily");
		districtsCards.add(newCheapDistrict);

		rusher.normalBehaviour();
		verify(rusher, times(1)).takeGold();
	}

	// ---

	@Test
	public void endGameBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		rusher.endGameBehaviour();
		verify(rusher, times(1)).takeCard(any());
		verify(rusher, times(0)).takeGold();
	}

	@Test
	public void endGameBehaviourCanBuildNewDistrictWithFuturGoldDistrictsTest() {
		int tooExpansiveValueForNow = 3;

		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		player.getCity().getBuiltDistrict().clear();
		rusher.getPlayer().setGolds(1);

		districtsCards.add(new District("testDistrict", tooExpansiveValueForNow, "testColor", "testFamily"));

		rusher.endGameBehaviour();
		verify(rusher, times(1)).takeGold();
	}
	
	@Test
	public void ifPossibleBuildACheapDistrictOneCheapTest() {
		District aCheaperBuildableDistrict = new District("aCheaperBuildableDistrict", 2, "colorTest", "familyTest");
		District aOtherBuildableDistrict = new District("aOtherBuildableDistrict", 5, "colorTest", "familyTest");

		player.getCity().getBuiltDistrict().clear();
		player.getDistrictCards().clear();
		
		player.getDistrictCards().add(aCheaperBuildableDistrict);
		player.getDistrictCards().add(aOtherBuildableDistrict);
		
		assertEquals(player.getCity().getSizeOfCity(), 0);
		rusher.ifPossibleBuildACheapDistrict();
		assertEquals(player.getCity().getSizeOfCity(), 1);
		assertEquals(player.getCity().getBuiltDistrict().get(0), aCheaperBuildableDistrict);
	}
	
	@Test
	public void ifPossibleBuildACheapDistrictTwoCheapTest() {
		District aCheaperBuildableDistrict = new District("aCheaperBuildableDistrict", 1, "colorTest", "familyTest");
		District aOtherCheaperBuildableDistrict = new District("aOtherBuildableDistrict", 2, "colorTest", "familyTest");

		player.getCity().getBuiltDistrict().clear();
		player.getDistrictCards().clear();
		
		player.getDistrictCards().add(aCheaperBuildableDistrict);
		player.getDistrictCards().add(aOtherCheaperBuildableDistrict);
		
		assertEquals(player.getCity().getSizeOfCity(), 0);
		rusher.ifPossibleBuildACheapDistrict();
		assertEquals(player.getCity().getSizeOfCity(), 1);
		assertEquals(player.getCity().getBuiltDistrict().get(0), aCheaperBuildableDistrict);
	}
	
	@Test
	public void ifPossibleBuildACheapDistrictZeroCheapTest() {
		District aNotCheaperBuildableDistrict = new District("aCheaperBuildableDistrict", 10, "colorTest", "familyTest");
		District aOtherNotCheaperBuildableDistrict = new District("aOtherBuildableDistrict", 5, "colorTest", "familyTest");

		player.getCity().getBuiltDistrict().clear();
		player.getDistrictCards().clear();
		
		player.getDistrictCards().add(aNotCheaperBuildableDistrict);
		player.getDistrictCards().add(aOtherNotCheaperBuildableDistrict);
		
		assertEquals(player.getCity().getSizeOfCity(), 0);
		rusher.ifPossibleBuildACheapDistrict();
		assertEquals(player.getCity().getSizeOfCity(), 0);
	}
	
	@Test
	public void ifPossibleBuildACheapDistrict2CheapEqualityTest() {
		District aCheaperBuildableDistrict = new District("aCheaperBuildableDistrict", 2, "colorTest", "familyTest");
		District aOtherCheaperBuildableDistrict = new District("aOtherBuildableDistrict", 2, "colorTest", "familyTest");

		player.getCity().getBuiltDistrict().clear();
		player.getDistrictCards().clear();
		
		player.getDistrictCards().add(aCheaperBuildableDistrict);
		player.getDistrictCards().add(aOtherCheaperBuildableDistrict);
		
		assertEquals(player.getCity().getSizeOfCity(), 0);
		rusher.ifPossibleBuildACheapDistrict();
		assertEquals(player.getCity().getSizeOfCity(), 1);
		assertEquals(player.getCity().getBuiltDistrict().get(0), aCheaperBuildableDistrict);
	}

	
	@Test
	public void chooseBetweenTwoCardsSecondCardChosenTest() {
		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");

		District choosenDistrict = rusher.chooseBetweenTwoCards(aDistrictExpansive, aDistrictCheap);
	
		assertEquals(choosenDistrict, aDistrictCheap);
	}

	@Test
	public void chooseBetweenTwoCardsFirstChosenTest() {
		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");

		District choosenDistrict = rusher.chooseBetweenTwoCards(aDistrictCheap, aDistrictExpansive);

		assertEquals(choosenDistrict, aDistrictCheap);
	}

	@Test
	public void testGetCheaperDistrict(){
		ArrayList<District> districtWeCanBuild=new ArrayList<>();
		districtWeCanBuild.add(new District("expensiveDistrict",2," "," "));
		districtWeCanBuild.add(new District("cheapestDistrict",1," "," "));
		assertEquals("cheapestDistrict",rusher.getCheaperDistrict(districtWeCanBuild).getName());
	}

}
