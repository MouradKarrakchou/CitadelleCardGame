package fr.unice.polytech.citadelle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

public class RushBotTest {
	Player player1;
	RushBot rushBot;
	DeckDistrict deckDistrict;
	
	@BeforeEach
	public void init() {
		 deckDistrict = new DeckDistrict();
		 deckDistrict.initialise();
	}

	
	@Test
	public void normalBehaviourNoCardButGoldTest() {
		Player player = new Player("Player1");
		RushBot rusher = spy(new RushBot(player));
		
		player.getDistrictCards().clear();
		
		player.setGolds(15);
		
		rusher.normalBehaviour(deckDistrict);
		verify(rusher, times(1)).takeCard(any(), any());
	}
	
	/*
	@Test
	public void normalBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player1.getDistrictCards();
		districtsCards.clear();
		rushBot.normalBehaviour(deckDistrict);
        verify(rushBot, times(1)).takeCard(any(), deckDistrict);
        verify(rushBot, times(0)).takeGold();
	}
	
	@Test
	public void normalBehaviourTooExpansivesDistrictsTest() {
		int tooExpansiveValue = 4;
		ArrayList<District> districtsCards = player1.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict", tooExpansiveValue, "testColor", "testFamily"));
		rushBot.normalBehaviour(deckDistrict);
        verify(rushBot, times(1)).takeCard(any(), deckDistrict);
        verify(rushBot, times(0)).takeGold();
	}
	
	@Test
	public void normalBehaviourABuildableDistrictTest() {
		int cheapValue = 3;
		ArrayList<District> districtsCards = player1.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict", cheapValue, "testColor", "testFamily"));
		rushBot.normalBehaviour(deckDistrict);
        verify(rushBot, times(0)).takeCard(any(), deckDistrict);
        verify(rushBot, times(1)).takeGold();
	}
	
	//---
	
	@Test
	public void endGameBehaviourNoCardTest() {
		ArrayList<District> districtsCards = player1.getDistrictCards();
		districtsCards.clear();
		rushBot.endGameBehaviour(deckDistrict);
        verify(rushBot, times(1)).takeCard(any(), deckDistrict);
        verify(rushBot, times(0)).takeGold();
	}
	
	@Test
	public void endGameBehaviourCanBuildNewDistrictWithFuturGoldDistrictsTest() {
		int tooExpansiveValue = 3;
		ArrayList<District> districtsCards = player1.getDistrictCards();
		districtsCards.clear();
		districtsCards.add(new District("testDistrict", tooExpansiveValue, "testColor", "testFamily"));
		rushBot.getPlayer().setGolds(1);
		rushBot.endGameBehaviour(deckDistrict);
        verify(rushBot, times(1)).takeGold();
	}*/
	
}
