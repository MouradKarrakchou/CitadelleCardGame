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

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class RoundManagerTest {
	RoundManager roundMan;
	DeckCharacter deckChar;
	DeckDistrict deckDistrict;
	ArrayList<Player> listOfPlayerSpy;
	LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters ;
	ArrayList<Character> listOfAllCharacters;
	ArrayList<Behaviour> listOfBehaviour;
	ArrayList<Player> listOfPlayer;
	Board game;
	PrintCitadels printer;
	

	Initialiser init;
	@BeforeEach
	public void init() {
		hashOfCharacters = new LinkedHashMap<Character, Optional<Behaviour>>();
		listOfAllCharacters= new ArrayList<Character>();
		listOfBehaviour= spy(new ArrayList<Behaviour>());
		listOfPlayer= new ArrayList<Player>();
		listOfPlayerSpy = spy(listOfPlayer);

		init = new Initialiser();
		deckChar = new DeckCharacter(listOfAllCharacters);
		deckDistrict = new DeckDistrict();
		
		game = new Board();
		printer = new PrintCitadels();

		
		init.initAll(hashOfCharacters, listOfAllCharacters, listOfPlayerSpy, listOfBehaviour);
		deckChar.initialise(listOfAllCharacters);
		deckDistrict.initialise();

		roundMan = spy(new RoundManager());
	
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
	public void actionsOfTheBehaviourTest() {
		Character c = new Character("testCharacter", 0);
		Behaviour bot = spy(new Behaviour(new Player("testPlayer")));
		bot.getPlayer().setRole(c);
		boolean aBehaviourCompleteHisCity = false;
		roundMan.actionsOfTheBehaviour(c, bot, aBehaviourCompleteHisCity, deckDistrict);
		verify(bot, times(1)).play(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	
	@Test
	public void setupCharactersTest() {
		RoundManager roundMan = spy(new RoundManager());
		Initialiser initialiser = new Initialiser();
		initialiser.initAll(roundMan.getHashOfCharacters(), roundMan.getListOfAllCharacters(), roundMan.getBoard().getListOfPlayer(), roundMan.getListOfBehaviour());
		initialiser.initDeckCharacter(roundMan.getBoard().getDeckCharacter(), listOfAllCharacters);
		initialiser.initDeckDistrict(roundMan.getBoard().getDeckDistrict());
		
		roundMan.setupCharacters(initialiser);
		verify(roundMan, times(Initialiser.NUMBER_OF_PLAYER)).chooseACharacterCard(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void askEachCharacterToPlayTest() {
		RoundManager roundMan = spy(new RoundManager());
		Initialiser initialiser = new Initialiser();
		PhaseManager phaseMan = new PhaseManager();
		LinkedHashMap<Character, Optional<Behaviour>> hashCharacter;
		
		initialiser.initAll(roundMan.getHashOfCharacters(), roundMan.getListOfAllCharacters(), roundMan.getBoard().getListOfPlayer(), roundMan.getListOfBehaviour());
		initialiser.initDeckCharacter(roundMan.getBoard().getDeckCharacter(), listOfAllCharacters);
		initialiser.initDeckDistrict(roundMan.getBoard().getDeckDistrict());
		hashCharacter = roundMan.getHashOfCharacters();
		
		Character assasin = roundMan.getListOfAllCharacters().get(Initialiser.ASSASIN_INDEX);
		Character thief = roundMan.getListOfAllCharacters().get(Initialiser.THIEF_INDEX);
		Character architect = roundMan.getListOfAllCharacters().get(Initialiser.ARCHITECT_INDEX);
		
		Player p1 = new Player("test1");
		Player p2 = new Player("test2");
		Player p3 = new Player("test3");
		
		p1.setRole(architect);
		p2.setRole(architect);
		p3.setRole(architect);

		Optional<Behaviour> behaviour1 = Optional.of(new Behaviour(p1));
		Optional<Behaviour> behaviour2 = Optional.of(new Behaviour(p2));
		Optional<Behaviour> behaviour3 = Optional.of(new Behaviour(p3));

		hashCharacter.put(assasin, behaviour1);	
		hashCharacter.put(thief, behaviour2);		
		hashCharacter.put(architect, behaviour3);		
		
		roundMan.askEachCharacterToPlay(phaseMan, roundMan.getBoard().getDeckDistrict(), initialiser);
		verify(roundMan, times(3)).actionsOfTheBehaviour(Mockito.any(), Mockito.any(), Mockito.anyBoolean(), Mockito.any());
	
	}
	@Test
	public void orderTurnByKing(){
		//creation of Behaviour
		Behaviour botArchitecte = new Behaviour(new Player("architectePlayer"));
		Behaviour botBishop =new Behaviour(new Player("bishopPlayer"));
		Behaviour botMagician = new Behaviour(new Player("magicianPlayer"));
		Behaviour botKing=new Behaviour(new Player("kingPlayer"));

		//creation of the list of bot
		listOfBehaviour.clear();
		listOfBehaviour.add(botArchitecte);
		listOfBehaviour.add(botBishop);
		listOfBehaviour.add(botMagician);
		listOfBehaviour.add(botKing);
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
		botKing.setBehaviourIsKing(true);
		assertEquals(3,roundMan.findKing(listOfBehaviour));

		//Verify that they are well ordered
		ArrayList<Behaviour> botOrdered=roundMan.orderListOfPlayer(listOfBehaviour,3);
		assertEquals(botKing,botOrdered.get(0));
		assertEquals(botArchitecte,botOrdered.get(1));
		assertEquals(botBishop,botOrdered.get(2));
		assertEquals(botMagician,botOrdered.get(3));

	}
}
