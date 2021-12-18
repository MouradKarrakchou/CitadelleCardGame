package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Investor;

public class InvestorTest {
	Player player;
	Investor investor;
	DeckDistrict deckDistrict;
	Board board;

	@BeforeEach
	public void init() {
		deckDistrict  = new DeckDistrict();
		deckDistrict.initialise();
		player = new Player("Player");
    	board = new Board(null,new ArrayList<>(),deckDistrict , null);
		investor = spy(new Investor(player, board));
	}

	@RepeatedTest(1)
	//@Test
	public void normalBehaviourNoCardButGoldTest() {
		player = new Player("Player1");
		investor = spy(new Investor(player, board));

		player.getDistrictCards().clear();

		player.setGolds(15);

		investor.normalBehaviour();
		verify(investor, times(1)).takeCard(any());
		verify(investor, times(0)).takeGold();
	}

	@RepeatedTest(1)
	//@Test
	public void normalBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		investor.normalBehaviour();
		verify(investor, times(1)).takeCard(any());
		verify(investor, times(0)).takeGold();
	}

	@RepeatedTest(1)
	//@Test
	public void normalBehaviourCanBuildTworMoreGoldTest() {
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		player.setGolds(2);

		District aDistrict = new District("aDistrict", 4, "testColor", "testFamily");
		districtsCards.add(aDistrict);

		investor.normalBehaviour();
		verify(investor, times(0)).takeCard(any());
		verify(investor, times(1)).takeGold();
	}

	@RepeatedTest(1)
	//@Test
	public void normalBehaviourABuildableDistrictTest() {
		int cheapValue = 3;

		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		player.getCity().getBuiltDistrict().clear();
		player.setGolds(0);

		District newCheapDistrict = new District("testDistrict", cheapValue, "testColor", "testFamily");
		districtsCards.add(newCheapDistrict);

		investor.normalBehaviour();
		verify(investor, times(1)).takeGold();
	}


	@RepeatedTest(1)
	//@Test
	public void endGameBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		investor.endGameBehaviour();
		verify(investor, times(1)).takeCard(any());
		verify(investor, times(0)).takeGold();
	}

	@RepeatedTest(1)
	//@Test
	public void endGameBehaviourCanBuildNewDistrictWithFuturGoldDistrictsTest() {
		int tooExpansiveValueForNow = 3;

		ArrayList<District> districtsCards = player.getDistrictCards();
		districtsCards.clear();
		player.getCity().getBuiltDistrict().clear();
		investor.getPlayer().setGolds(1);

		districtsCards.add(new District("testDistrict", tooExpansiveValueForNow, "testColor", "testFamily"));

		investor.endGameBehaviour();
		verify(investor, times(1)).takeGold();
	}

	@RepeatedTest(1)
	//@Test
	public void chooseBetweenTwoCardsTest() {
		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");

		
		District choosenDistrict = investor.chooseBetweenTwoCards(aDistrictExpansive, aDistrictCheap);
	
		assertEquals(choosenDistrict, aDistrictExpansive);
	}
	
	@RepeatedTest(1)
	//@Test
	public void pickCardsInDeckTwoCardsTest() {
		ArrayList<District> districtsCards = new ArrayList<District>();
		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");
		districtsCards.add(aDistrictCheap);
		districtsCards.add(aDistrictExpansive);		

		when(investor.pick2CardsIntoTheDeck()).thenReturn(districtsCards);		
		when(investor.pick2CardsIntoTheDeck()).thenReturn(districtsCards);
		when(investor.chooseToKeepOrNotPickedCards(districtsCards)).thenReturn(districtsCards);

		District choosenDistrict = investor.pickCardsInDeck();
		assertEquals(choosenDistrict, aDistrictExpansive);
	}
	
	@RepeatedTest(1)
	//@Test
	public void pickCardsInDeckOneCardsTest() {
		ArrayList<District> districtsCards = new ArrayList<District>();
		ArrayList<District> afterPickDistrictsCards = new ArrayList<District>();

		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");
		districtsCards.add(aDistrictCheap);
		districtsCards.add(aDistrictExpansive);	
		
		afterPickDistrictsCards.add(aDistrictCheap);
		

		when(investor.pick2CardsIntoTheDeck()).thenReturn(districtsCards);		
		when(investor.chooseToKeepOrNotPickedCards(districtsCards)).thenReturn(afterPickDistrictsCards);

		District choosenDistrict = investor.pickCardsInDeck();
		assertEquals(choosenDistrict, aDistrictCheap);
	}
	
	@RepeatedTest(1)
	//@Test
	public void pickCardsInDeckZeroCardsTest() {
		ArrayList<District> districtsCards = new ArrayList<District>();
		ArrayList<District> afterPickDistrictsCards = new ArrayList<District>();

		District aDistrictExpansive = new District("aDistrictExpansive", 5, "testColor", "testFamily");
		District aDistrictCheap = new District("aDistrictCheap", 1, "testColor", "testFamily");
		districtsCards.add(aDistrictCheap);
		districtsCards.add(aDistrictExpansive);			

		when(investor.pick2CardsIntoTheDeck()).thenReturn(districtsCards);		
		when(investor.chooseToKeepOrNotPickedCards(districtsCards)).thenReturn(afterPickDistrictsCards);

		District choosenDistrict = investor.pickCardsInDeck();
		assertEquals(choosenDistrict, aDistrictExpansive);
	}
}
