package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.Bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class BotTest {
	private Player player01;
	private Bot botOfPlayer01;
	DeckDistrict deckDistrict;

	@BeforeEach
	public void init() {
		player01 = new Player("Player01");
		botOfPlayer01 = spy(new Bot(player01));
		deckDistrict = new DeckDistrict();
		deckDistrict.initializer();
	}

	@Test
	void selectTheHigherDistrictSelectDifferentValues() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		// In this test, player has picked a district of value 5 and a district of value
		// 2
		District districtOfValue5 = new District("DistrictOfValue5", 5, "r", "r");
		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue5);
		pickedDistricts.add(districtOfValue2);

		District selectedDistrict = botOfPlayer01.selectTheHigherDistrict(mockedDeckDistrict, pickedDistricts);

		assertEquals(districtOfValue5, selectedDistrict);

		verify(mockedDeckDistrict, times(1)).addDistrict(any());
		verifyNoMoreInteractions(mockedDeckDistrict);
	}

	@Test
	void selectTheHigherDistrictSelectSameValues() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");
		District otherDistrictOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue2);
		pickedDistricts.add(otherDistrictOfValue2);

		District selectedDistrict = botOfPlayer01.selectTheHigherDistrict(mockedDeckDistrict, pickedDistricts);

		assertEquals(districtOfValue2, selectedDistrict);

		verify(mockedDeckDistrict, times(1)).addDistrict(any());
		verifyNoMoreInteractions(mockedDeckDistrict);
	}

	/**
	 * Selected cards are different Player's city is empty Player's hand is empty
	 */
	@Test
	void districtCardIsSelectedHandEmptyCityEmptySelectedDistrictDifferent() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		District districtOfValue1 = new District("DistrictOfValue1", 1, "r", "r");
		District districtOfValue7 = new District("DistrictOfValue7", 7, "r", "r");

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue1);
		pickedDistricts.add(districtOfValue7);

		assertTrue(botOfPlayer01.districtCardIsSelected(mockedDeckDistrict, pickedDistricts));
		assertTrue(player01.getDistrictCards().contains(districtOfValue7));
		verify(mockedDeckDistrict, times(1)).addDistrict(any());
		verifyNoMoreInteractions(mockedDeckDistrict);
	}

	/**
	 * Selected cards are different Player's city is empty Player's hand already has
	 * the higher card of the selected cards
	 */
	@Test
	void districtCardIsSelectedHandAlreadyHaveItCityEmptySelectedDistrictDifferent() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		District districtOfValue4 = new District("DistrictOfValue4", 4, "r", "r");
		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		// Player already have a district in its hand
		player01.addDistrict(new District("DistrictOfValue4", 4, "r", "r"));

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue4);
		pickedDistricts.add(districtOfValue2);

		assertTrue(botOfPlayer01.districtCardIsSelected(mockedDeckDistrict, pickedDistricts));
		assertTrue(player01.getDistrictCards().contains(districtOfValue2));

		verify(mockedDeckDistrict, times(1)).addDistrict(any());

		assertEquals(2, player01.getDistrictCardsSize());
		assertEquals(0, player01.getCity().getSizeOfCity());
	}

	/**
	 * Selected cards are different Player's city already has the lower card of the
	 * selected cards Player's hand already has the higher card of the selected
	 * cards
	 */
	@Test
	void districtCardIsSelectedHandAlreadyHaveItCityAlreadyHaveItSelectedDistrictDifferent() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		District districtOfValue4 = new District("DistrictOfValue4", 4, "r", "r");
		District districtOfValue7 = new District("DistrictOfValue7", 7, "r", "r");

		player01.addDistrict(new District("DistrictOfValue7", 7, "r", "r"));
		player01.getCity().buildDistrict(new District("DistrictOfValue4", 4, "r", "r"));

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue4);
		pickedDistricts.add(districtOfValue7);

		assertFalse(botOfPlayer01.districtCardIsSelected(mockedDeckDistrict, pickedDistricts));
		assertFalse(player01.getDistrictCards().contains(districtOfValue4));

		verify(mockedDeckDistrict, times(2)).addDistrict(any());

		assertEquals(1, player01.getDistrictCardsSize());
		assertEquals(1, player01.getCity().getSizeOfCity());
	}

	/**
	 * Selected cards are different Player's city already has the lower card of the
	 * selected cards Player's hand is empty
	 */
	@Test
	void districtCardIsSelectedCityAlreadyHaveItSelectedDistrictDifferent() {
		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);

		District districtOfValue8 = new District("DistrictOfValue8", 8, "r", "r");
		District districtOfValue7 = new District("DistrictOfValue7", 7, "r", "r");

		player01.getCity().buildDistrict(new District("DistrictOfValue8", 8, "r", "r"));

		ArrayList<District> pickedDistricts = new ArrayList<>();
		pickedDistricts.add(districtOfValue8);
		pickedDistricts.add(districtOfValue7);

		assertTrue(botOfPlayer01.districtCardIsSelected(mockedDeckDistrict, pickedDistricts));
		assertTrue(player01.getDistrictCards().contains(districtOfValue7));
	}

	/**
	 * Testing if bot take a card or take two golds Player's hand is empty Player's
	 * city is empty
	 */
	@Test
	void takeCardTest() {
		District districtOfValue4 = new District("DistrictOfValue4", 4, "r", "r");
		District districtOfValue3 = new District("DistrictOfValue3", 3, "r", "r");

		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);
		when(mockedDeckDistrict.chooseDistrict()).thenReturn(districtOfValue4).thenReturn(districtOfValue3);

		assertEquals(0, player01.getDistrictCardsSize());
		botOfPlayer01.takeCard(mockedDeckDistrict);
		assertTrue(player01.getDistrictCards().contains(districtOfValue4));
		assertEquals(1, player01.getDistrictCardsSize());
	}

	/**
	 * Selected cards are different Player's city is empty Player's hand already has
	 * the higher card of the selected cards
	 */
	@Test
	void takeCardHasCardInHand() {
		District districtOfValue6 = new District("DistrictOfValue6", 6, "r", "r");
		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);
		when(mockedDeckDistrict.chooseDistrict()).thenReturn(districtOfValue6).thenReturn(districtOfValue2);

		player01.addDistrict(districtOfValue6);

		botOfPlayer01.takeCard(mockedDeckDistrict);
		assertTrue(player01.getDistrictCards().contains(districtOfValue2));
		assertTrue(player01.getDistrictCards().contains(districtOfValue6));
		assertEquals(2, player01.getDistrictCardsSize());
	}

	/**
	 * Selected cards are different Player's city already has the lower card of the
	 * selected cards Player's hand already has the higher card of the selected
	 * cards
	 */
	@Test
	void takeCardHasCardInHandHasCardInCity() {
		District districtOfValue6 = new District("DistrictOfValue6", 6, "r", "r");
		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);
		when(mockedDeckDistrict.chooseDistrict()).thenReturn(districtOfValue6).thenReturn(districtOfValue2);

		player01.addDistrict(districtOfValue6);
		player01.getCity().buildDistrict(districtOfValue2);

		botOfPlayer01.takeCard(mockedDeckDistrict);
		assertFalse(player01.getDistrictCards().contains(districtOfValue2));
		assertTrue(player01.getDistrictCards().contains(districtOfValue6));
		assertEquals(1, player01.getDistrictCardsSize());
		assertEquals(4, player01.getGolds());
	}

	/**
	 */
	@Test
	void normalBehaviourCanPlaceADistrictWith2MoreGold() {
		int goldOfPlayerWithNewGold = player01.getGolds()+2;

		ArrayList<District> districtsCards = player01.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict", goldOfPlayerWithNewGold, "testColor", "testFamily"));
		botOfPlayer01.normalBehaviour(deckDistrict);
		verify(botOfPlayer01, times(0)).takeCard(any());
		verify(botOfPlayer01, times(1)).takeGold();
	}
	
	/**
	 */
	@Test
	void normalBehaviourCantPlaceADistrictWith2MoreGold() {
		int tooExpansiveValue = player01.getGolds()+3;
		ArrayList<District> districtsCards = player01.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict",tooExpansiveValue , "testColor", "testFamily"));
		botOfPlayer01.normalBehaviour(deckDistrict);
		verify(botOfPlayer01, times(1)).takeCard(any());
		verify(botOfPlayer01, times(0)).takeGold();
	}
	
	/**
	 */
	@Test
	void normalBehaviourNoGold() {
		player01.setGolds(0);
		botOfPlayer01.normalBehaviour(deckDistrict);
		verify(botOfPlayer01, times(0)).takeCard(any());
		verify(botOfPlayer01, times(1)).takeGold();
	}


	/*
	 * int cheapValue = 3; ArrayList<District> districtsCards =
	 * player1.getDistrictCards(); districtsCards.clear(); districtsCards.add(new
	 * District("testDistrict", cheapValue, "testColor", "testFamily"));
	 * rushBot.normalBehaviour(deckDistrict); verify(rushBot,
	 * times(0)).takeCard(any()); verify(rushBot, times(1)).takeGold();
	 */

	/**
	 * Selected cards are different Player's hand is empty.
	 */
	@Test
	void normalBehaviourNoDistrictInHand() {
		District districtOfValue6 = new District("DistrictOfValue6", 6, "r", "r");
		District districtOfValue2 = new District("DistrictOfValue2", 2, "r", "r");

		DeckDistrict mockedDeckDistrict = mock(DeckDistrict.class);
		when(mockedDeckDistrict.chooseDistrict()).thenReturn(districtOfValue6).thenReturn(districtOfValue2);

		assertEquals(2, player01.getGolds());
		botOfPlayer01.normalBehaviour(mockedDeckDistrict);
		verify(mockedDeckDistrict, times(2)).chooseDistrict();
		verify(mockedDeckDistrict, times(1)).addDistrict(any());
		assertEquals(2, player01.getGolds());
	}

	// ------------------

}
