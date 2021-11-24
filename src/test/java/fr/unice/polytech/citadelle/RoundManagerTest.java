package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.characters_class.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class RoundManagerTest {
	RoundManager roundMan;
	DeckCharacter deckChar;
	DeckDistrict deckDistrict;
	ArrayList<Player> listOfPlayerSpy;
	LinkedHashMap<Character, Optional<Bot>> hashOfCharacters ;
	ArrayList<Character> listOfAllCharacters;
	ArrayList<Bot> listOfBot;
	ArrayList<Player> listOfPlayer;
	Board game;
	PrintCitadels printer;
	

	Initialiser init;
	@BeforeEach
	public void init() {
		hashOfCharacters = new LinkedHashMap<Character, Optional<Bot>>();
		listOfAllCharacters= new ArrayList<Character>();
		listOfBot= spy(new ArrayList<Bot>());
		listOfPlayer= new ArrayList<Player>();
		listOfPlayerSpy = spy(listOfPlayer);

		init = new Initialiser();
		deckChar = new DeckCharacter();
		deckDistrict = new DeckDistrict();
		
		game = new Board(listOfPlayer, listOfBot);
		printer = new PrintCitadels();

		
		init.initAll(hashOfCharacters, listOfAllCharacters, listOfBot, listOfPlayerSpy);
		deckChar.initialise(listOfAllCharacters);
		deckDistrict.initializer();

		roundMan = spy(new RoundManager(listOfPlayerSpy, listOfBot, listOfAllCharacters, hashOfCharacters, printer, game));
	
	}
	
	@Test
	public void runRoundsOnceTest() {
		PhaseManager phaseMan=Mockito.mock(PhaseManager.class);
		when(phaseMan.analyseGame(Mockito.any())).thenReturn(PhaseManager.LAST_TURN_PHASE);
		roundMan.runRounds(phaseMan,init);
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
	
	@Test
	public void actionsOfTheBotTest() {
		Character c = new Character("testCharacter", 0);
		Bot bot = spy(new Bot(new Player("testPlayer")));
		bot.getPlayer().setRole(c);
		boolean aBotCompleteHisCity = false;
		roundMan.actionsOfTheBot(c, bot, aBotCompleteHisCity, deckDistrict);
		verify(bot, times(1)).play(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	
	@Test
	public void setupCharactersTest() {
		ArrayList<Character> listOfCharactersInGame = new ArrayList<Character>();
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));

		
		RoundManager roundManTwo = spy(new RoundManager(listOfPlayerSpy, listOfBot, listOfAllCharacters, hashOfCharacters, printer, game));
		roundManTwo.setupCharacters(deckChar, init);
		verify(roundManTwo, times(4)).chooseACharacterCard(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void askEachCharacterToPlayTest() {
		PhaseManager phase = new PhaseManager();
		ArrayList<Character> listOfCharactersInGame = new ArrayList<Character>();
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		listOfCharactersInGame.add(new Character("testCharacter", 0));
		
		Character c = new Character("testCharacter", 0);
		Bot bot = spy(new Bot(new Player("testPlayer")));
		bot.getPlayer().setRole(c);
		
		LinkedHashMap<Character, Optional<Bot>> hashOfCharactersTwo = new LinkedHashMap<Character, Optional<Bot>>();
		hashOfCharactersTwo.put(c, Optional.of(bot));
		int numberOfUniqueCharacter = 1;


		
		RoundManager roundManTwo = spy(new RoundManager(listOfPlayerSpy, listOfBot, listOfAllCharacters, hashOfCharactersTwo, printer, game));
		
		roundManTwo.askEachCharacterToPlay(phase, deckDistrict, init);
		verify(roundManTwo, times(numberOfUniqueCharacter)).actionsOfTheBot(Mockito.any(), Mockito.any(), Mockito.anyBoolean(),Mockito.any());
	}
	@Test
	public void orderTurnByKing(){
		//creation of Bot
		Bot botArchitecte = new Bot(new Player("architectePlayer"));
		Bot botBishop =new Bot(new Player("bishopPlayer"));
		Bot botMagician = new Bot(new Player("magicianPlayer"));
		Bot botKing=new Bot(new Player("kingPlayer"));

		//creation of the list of bot
		listOfBot.clear();
		listOfBot.add(botArchitecte);
		listOfBot.add(botBishop);
		listOfBot.add(botMagician);
		listOfBot.add(botKing);
		//creation of the characters in game
		Architect architect = new Architect();
		Bishop bishop = new Bishop();
		Magician magician = new Magician();
		King king = new King();

		//we set the character of our bot
		botKing.getPlayer().setRole(king);

		//creation of the hashOfCharacter
		hashOfCharacters.put(architect, Optional.of(botArchitecte));
		hashOfCharacters.put(bishop, Optional.of(botBishop));
		hashOfCharacters.put(magician, Optional.of(botMagician));
		hashOfCharacters.put(king,Optional.of(botKing));

		//Verify that he finds the right spot for the king
		botKing.setBotIsKing(true);
		assertEquals(3,roundMan.findKing(listOfBot));

		//Verify that they are well ordered
		ArrayList<Bot> botOrdered=roundMan.orderListOfPlayer(listOfBot,3);
		assertEquals(botKing,botOrdered.get(0));
		assertEquals(botArchitecte,botOrdered.get(1));
		assertEquals(botBishop,botOrdered.get(2));
		assertEquals(botMagician,botOrdered.get(3));

	}
}
