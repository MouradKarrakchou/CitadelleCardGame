package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class RoundManagertTest {
	RoundManager roundMan;
	DeckCharacter deckChar;
	DeckDistrict deckDistrict;
	ArrayList<Player> listOfPlayerSpy;
	LinkedHashMap<Character, Bot> hashOfCharacters ;
	ArrayList<Character> listOfAllCharacters;
	ArrayList<Bot> listOfBot;
	ArrayList<Player> listOfPlayer;
	Game game;
	PrintCitadels printer;
	

	Initialiser init;
	@BeforeEach
	public void init() {
		hashOfCharacters = new LinkedHashMap<Character, Bot>();
		listOfAllCharacters= new ArrayList<Character>();
		listOfBot= new ArrayList<Bot>();
		listOfPlayer= new ArrayList<Player>();
		listOfPlayerSpy = spy(listOfPlayer);

		init = new Initialiser();
		deckChar = new DeckCharacter();
		deckDistrict = new DeckDistrict();
		
		game = new Game(listOfPlayer, deckChar, deckDistrict);
		printer = new PrintCitadels();

		
		init.initAll(hashOfCharacters, listOfAllCharacters, listOfBot, listOfPlayerSpy);
		deckChar.initialise(listOfAllCharacters);
		deckDistrict.initializer();

		roundMan = new RoundManager(listOfPlayerSpy, listOfBot, listOfAllCharacters, hashOfCharacters, printer, game);
	
	}
	
	@Test
	public void runRoundsOnceTest() {
		PhaseManager phaseMan=Mockito.mock(PhaseManager.class);
		when(phaseMan.analyseGame(Mockito.any())).thenReturn(PhaseManager.LAST_TURN_PHASE);
		roundMan.runRounds(phaseMan, deckChar, init, deckDistrict);
		verify(phaseMan, times(1)).analyseGame(Mockito.any());
	}
	
	@Test
	public void getTheListOfCityTest() {
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		Player p3 = new Player("p3");
		ArrayList<Player> listOfPlayer = new ArrayList<Player>();
		ArrayList<City> listOfCity= new ArrayList<City>();

		listOfPlayer.add(p1);
		listOfPlayer.add(p2);
		listOfPlayer.add(p3);
		
		listOfCity.add(p1.getCity());
		listOfCity.add(p2.getCity());
		listOfCity.add(p3.getCity());
		
		assertEquals(roundMan.getTheListOfCity(listOfPlayer), listOfCity);
	}
	
	@Test
	public void spellOfCharactersTest() {
		Player p1 = spy(new Player("p1"));
		Player p2 = spy(new Player("p2"));
		Player p3 = spy(new Player("p3"));
		ArrayList<Player> aListOfPlayer = new ArrayList<Player>();

		p1.setRole(new Character("testCharacter", 0));
		p2.setRole(new Character("testCharacter", 0));
		p3.setRole(new Character("testCharacter", 0));

		aListOfPlayer.add(p1);
		aListOfPlayer.add(p2);
		aListOfPlayer.add(p3);
		
		ArrayList<Player> aListOfPlayerSpy = spy(aListOfPlayer);
		
		roundMan = new RoundManager(aListOfPlayerSpy, listOfBot, listOfAllCharacters, hashOfCharacters, printer, game);
		
		verify(p1, times(1)).getCharacter();
		verify(p2, times(1)).getCharacter();
		verify(p3, times(1)).getCharacter();

	}
	@Test
	public void addBonusForPlayersFirstTest() {
		Player player = new Player("test");
		assertEquals(player.getScore(), 0);
		roundMan.addBonusForPlayers(player, false);
		assertEquals(player.getScore(), RoundManager.BONUS_FIRST);
	}
	
	public void addBonusForPlayersNotFirstTest() {
		Player player = new Player("test");
		assertEquals(player.getScore(), 0);
		roundMan.addBonusForPlayers(player, true);
		assertEquals(player.getScore(), RoundManager.BONUS_END);
	}
}
