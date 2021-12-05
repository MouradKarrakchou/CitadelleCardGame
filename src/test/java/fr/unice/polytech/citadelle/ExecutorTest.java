package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.Executor;

public class ExecutorTest {
	Player player;
	Executor executor;
	DeckDistrict deckDistrict;

	@BeforeEach
	public void init() {
		deckDistrict = new DeckDistrict();
		deckDistrict.initialise();
		player = new Player("Player");
		executor = new Executor(player);
	}

	@Test
	public void takeCardTest() {
		District aDistrict = new District("aDistrict", 2, "colorTest", "familyTest");
		player.getDistrictCards().clear();

		assertEquals(player.getDistrictCards().size(), 0);
		executor.takeCard(aDistrict, deckDistrict);
		assertEquals(player.getDistrictCards().size(), 1);
		assertEquals(player.getDistrictCards().get(0), aDistrict);
	}
	
	@Test
	public void buildDistrictTest() {
		District aDistrict = new District("aDistrict", 2, "colorTest", "familyTest");
		player.getCity().getBuiltDistrict().clear();


		assertEquals(player.getCity().getBuiltDistrict().size(), 0);
		executor.buildDistrict(aDistrict);
		assertEquals(player.getCity().getBuiltDistrict().size(), 1);
		assertEquals(player.getCity().getBuiltDistrict().get(0), aDistrict);
	}
	
	@Test
	public void takeGoldTest() {
		player.setGolds(0);
		
		assertEquals(player.getGolds(), 0);
		executor.takeGold();
		assertEquals(player.getGolds(), 2);
	}
	
	@Test
	public void putCardBackInDeckTest() {
		District aDistrict = new District("aDistrict", 2, "colorTest", "familyTest");
		int sizeBefore = deckDistrict.getSize();
		
		executor.putCardBackInDeck(deckDistrict, aDistrict);
		assertEquals(deckDistrict.getSize(), sizeBefore+1);
	}
	
	@Test
	public void pickCardsTest() {
		ArrayList<District> pickedCards = new ArrayList<District>();
		District aDistrict = new District("aDistrict", 2, "colorTest", "familyTest");
		District aOtherDistrict = new District("aOtherDistrict", 2, "colorTest", "familyTest");
		pickedCards.add(aDistrict);
		pickedCards.add(aDistrict);

		
		DeckDistrict mockDeckDistrict = spy(new DeckDistrict());
		mockDeckDistrict.initialise();
		when(mockDeckDistrict.pickBlindDistrict()).thenReturn(aDistrict);
		assertEquals(executor.pickCards(mockDeckDistrict), pickedCards);
	}
}
