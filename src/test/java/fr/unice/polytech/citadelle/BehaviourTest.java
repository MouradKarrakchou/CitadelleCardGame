package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;

public class BehaviourTest {
	Player playerTest;
	Behaviour bea;
	DeckDistrict deckDistrict;

	@BeforeEach
	public void init() {
		playerTest = new Player("playerTest");
		bea = new Behaviour(playerTest);
		deckDistrict = new DeckDistrict();
		deckDistrict.initialise();
	}

	@Test
	public void selectTheHigherDistrictTest() {
		ArrayList<District> pickedDistricts = new ArrayList<District>();

		District highterDistrict = new District("HighterDistrictTest", 10, "colorTest", "familyTest");
		District cheaperDistrict = new District("cheaperDistrictTest", 5, "colorTest", "familyTest");

		pickedDistricts.add(highterDistrict);
		pickedDistricts.add(cheaperDistrict);
		District choosenDistrict = bea.selectTheHigherDistrict(deckDistrict, pickedDistricts);
		assertEquals(choosenDistrict, highterDistrict);
	}

	@Test
	public void ifPossibleBuildADistrictCouldBuildTest() {
		District aDistrict = new District("districtTest", 2, "colorTest", "familyTest");

		playerTest.setGolds(10);
		playerTest.getDistrictCards().clear();
		playerTest.addDistrict(aDistrict);

		assertEquals(playerTest.getCity().getSizeOfCity(), 0);
		bea.ifPossibleBuildADistrict();
		assertEquals(playerTest.getCity().getSizeOfCity(), 1);
		assertEquals(playerTest.getCity().getBuiltDistrict().get(0), aDistrict);
	}
	
	
	@Test
	public void pick2CardsIntoTheDeckTwoCardTest() {
		Player playerOfMockTest = new Player("playerOfMockTest");
		Behaviour spyBea = spy(new Behaviour(playerOfMockTest));
		
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		District aDistrict = new District("aDistrict", 10, "colorTest", "familyTest");
		District aOtherDistrict = new District("aOtherDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(aDistrict);
		pickedDistrictCards.add(aOtherDistrict);

		Mockito.doReturn(pickedDistrictCards).when(spyBea).chooseToKeepOrNotPickedCards(Mockito.any(), Mockito.any());
		Mockito.doReturn(aDistrict).when(spyBea).chooseBetweenTwoCards(Mockito.any(), Mockito.any(),Mockito.any());

		
		assertEquals(spyBea.pick2CardsIntoTheDeck(deckDistrict), Optional.of(aDistrict));
	}
	
	@Test
	public void pick2CardsIntoTheDeckOneCardTest() {
		Player playerOfMockTest = new Player("playerOfMockTest");
		Behaviour spyBea = spy(new Behaviour(playerOfMockTest));
		
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		District aDistrict = new District("aDistrict", 10, "colorTest", "familyTest");

		pickedDistrictCards.add(aDistrict);

		Mockito.doReturn(pickedDistrictCards).when(spyBea).chooseToKeepOrNotPickedCards(Mockito.any(), Mockito.any());
		
		assertEquals(spyBea.pick2CardsIntoTheDeck(deckDistrict), Optional.of(aDistrict));
	}
	
	@Test
	public void pick2CardsIntoTheDeckZeroCardTest() {
		Player playerOfMockTest = new Player("playerOfMockTest");
		Behaviour spyBea = spy(new Behaviour(playerOfMockTest));
		
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();

		Mockito.doReturn(pickedDistrictCards).when(spyBea).chooseToKeepOrNotPickedCards(Mockito.any(), Mockito.any());
		
		assertEquals(spyBea.pick2CardsIntoTheDeck(deckDistrict), Optional.empty());
	}


	@Test
	public void chooseToKeepOrNotPickedCardskeepAllTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District aDistrict = new District("aDistrict", 10, "colorTest", "familyTest");
		District aOtherDistrict = new District("aOtherDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(aDistrict);
		pickedDistrictCards.add(aOtherDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 0);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards, deckDistrict);

		assertEquals(keepedDistrictCards.size(), 2);
	}

	@Test
	public void chooseToKeepOrNotPickedCardsKeepFirstBecause2ndInHandCardTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District futurKeepDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(futurKeepDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getDistrictCards().add(futurBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 1);
		assertEquals(playerTest.getCity().getSizeOfCity(), 0);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards, deckDistrict);

		assertEquals(keepedDistrictCards.size(), 1);
		assertEquals(keepedDistrictCards.get(0), futurKeepDistrict);

	}

	@Test
	public void chooseToKeepOrNotPickedCardsKeepFirstBecause2ndInCityCardTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District futurKeepDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(futurKeepDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getCity().getBuiltDistrict().add(futurBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 1);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards, deckDistrict);

		assertEquals(keepedDistrictCards.size(), 1);
		assertEquals(keepedDistrictCards.get(0), futurKeepDistrict);

	}

	@Test
	public void chooseToKeepOrNotPickedCardsNoKeepTest() {
		ArrayList<District> pickedDistrictCards = new ArrayList<District>();
		ArrayList<District> keepedDistrictCards = new ArrayList<District>();

		District otherFuturBurnDistrict = new District("futurKeepDistrict", 10, "colorTest", "familyTest");
		District futurBurnDistrict = new District("futurBurnDistrict", 5, "colorTest", "familyTest");

		pickedDistrictCards.add(otherFuturBurnDistrict);
		pickedDistrictCards.add(futurBurnDistrict);

		playerTest.getDistrictCards().clear();
		playerTest.getCity().getBuiltDistrict().clear();

		playerTest.getCity().getBuiltDistrict().add(futurBurnDistrict);
		playerTest.getCity().getBuiltDistrict().add(otherFuturBurnDistrict);

		assertEquals(playerTest.getDistrictCards().size(), 0);
		assertEquals(playerTest.getCity().getSizeOfCity(), 2);

		keepedDistrictCards = bea.chooseToKeepOrNotPickedCards(pickedDistrictCards, deckDistrict);

		assertEquals(keepedDistrictCards.size(), 0);

	} 
	
	@Test
	public void takeGoldTest() {
		playerTest.setGolds(0);
		assertEquals(playerTest.getGolds(), 0);
		bea.takeGold();
		assertEquals(playerTest.getGolds(), 2);
	}
	
	@Test
	public void takeCardTest() {
		District choosenCard = deckDistrict.getDeckDistrict().get(0);
		playerTest.getDistrictCards().clear();;
		assertEquals(playerTest.getDistrictCards().size(), 0);
		bea.takeCard(choosenCard, deckDistrict);
		assertEquals(playerTest.getDistrictCards().size(), 1);
		assertEquals(playerTest.getDistrictCards().get(0), choosenCard);
	}
}
