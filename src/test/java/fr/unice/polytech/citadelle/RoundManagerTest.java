package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;

import fr.unice.polytech.citadelle.output.PrintCitadels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;

public class RoundManagerTest {
	RoundManager roundMan;
	DeckDistrict deckDistrict;
	ArrayList<Player> listOfPlayerSpy;
	Board game;
	Referee referee;

	LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter;
	ArrayList<Character> listOfAllCharacter;
	ArrayList<Behaviour> listOfAllBehaviour;
	Board board;


	Initializer init;
	@BeforeEach
	public void init() {
		PrintCitadels.activateLevelWarning();

		init = new Initializer();
		deckDistrict = new DeckDistrict();


		deckDistrict.initialise();

		hashOfCharacter = new LinkedHashMap<Character, Optional<Behaviour>>();
		listOfAllCharacter = Initializer.createListOfAllCharacter();
		board = Initializer.createBoard(listOfAllCharacter);
		listOfAllBehaviour = Initializer.createListOfBehaviour(board);
		Initializer.initDeckCharacter(board.getDeckCharacter(), listOfAllCharacter);
		Initializer.resetHashOfCharacter(hashOfCharacter, listOfAllCharacter);
		roundMan = spy(new RoundManager(listOfAllCharacter, listOfAllBehaviour,hashOfCharacter, board));
		referee = new Referee(board);

		Initializer.initDeckCharacter(roundMan.getBoard().getDeckCharacter(), listOfAllCharacter);
		Initializer.initDeckDistrict(roundMan.getBoard().getDeckDistrict());

		board.setListOfPlayer(roundMan.getListOfPlayers());

		roundMan = spy(new RoundManager(listOfAllCharacter, listOfAllBehaviour, hashOfCharacter, board));
		//listOfPlayerSpy = spy(roundMan.getListOfPlayers());


	}

	@RepeatedTest(1)
	//@Test
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

	@RepeatedTest(1)
	//@Test
	public void actionsOfTheBehaviourTest() {
		Character c = new Character("testCharacter", 0);
		Behaviour bot = spy(new Behaviour(new Player("testPlayer"), board));
		bot.getPlayer().setRole(c);
		roundMan.actionOfBehaviour(bot);
		verify(bot, times(1)).play(Mockito.any());
	}

	@RepeatedTest(1)
	//@Test
	public void setupCharactersTest() {
		roundMan.setupCharacters();
		verify(roundMan, times(Initializer.NUMBER_OF_PLAYER)).chooseACharacterCard(Mockito.any(), Mockito.any());
	}

	@RepeatedTest(1)
	//@Test
	public void askEachCharacterToPlayTest() {
		LinkedHashMap<Character, Optional<Behaviour>> hashCharacter;
		hashCharacter = roundMan.getHashOfCharacters();

		Character assassin = roundMan.getListOfAllCharacters().get(Initializer.ASSASSIN_INDEX);
		Character thief = roundMan.getListOfAllCharacters().get(Initializer.THIEF_INDEX);
		Character architect = roundMan.getListOfAllCharacters().get(Initializer.ARCHITECT_INDEX);

		Player p1 = new Player("test1");
		Player p2 = new Player("test2");
		Player p3 = new Player("test3");

		p1.setRole(architect);
		p2.setRole(architect);
		p3.setRole(architect);

		Optional<Behaviour> behaviour1 = Optional.of(new Behaviour(p1, board));
		Optional<Behaviour> behaviour2 = Optional.of(new Behaviour(p2, board));
		Optional<Behaviour> behaviour3 = Optional.of(new Behaviour(p3, board));

		hashCharacter.put(assassin, behaviour1);
		hashCharacter.put(thief, behaviour2);
		hashCharacter.put(architect, behaviour3);

		roundMan.askEachCharacterToPlay();
		verify(roundMan, times(3)).actionOfBehaviour(Mockito.any());

	}

	@RepeatedTest(1)
	//@Test
	public void orderTurnByKing(){
		//creation of Behaviour
		Behaviour botArchitecte = new Behaviour(new Player("architectePlayer"), board);
		Behaviour botBishop =new Behaviour(new Player("bishopPlayer"), board);
		Behaviour botMagician = new Behaviour(new Player("magicianPlayer"), board);
		Behaviour botKing=new Behaviour(new Player("kingPlayer"), board);
		//creation of the list of bot
		listOfAllBehaviour.clear();

		listOfAllBehaviour.add(botArchitecte);
		listOfAllBehaviour.add(botBishop);
		listOfAllBehaviour.add(botMagician);
		listOfAllBehaviour.add(botKing);

		listOfAllBehaviour.get(0).getPlayer().setRole(new Architect());
		listOfAllBehaviour.get(1).getPlayer().setRole(new Bishop());
		listOfAllBehaviour.get(2).getPlayer().setRole(new Magician());
		listOfAllBehaviour.get(3).getPlayer().setRole(new King());


		//Verify that he finds the right spot for the king
		assertEquals(3,roundMan.findKing(listOfAllBehaviour));

		//Verify that they are well ordered
		ArrayList<Behaviour> botOrdered=roundMan.orderListOfPlayer(listOfAllBehaviour,3);
		assertEquals(botKing,botOrdered.get(0));
		assertEquals(botArchitecte,botOrdered.get(1));
		assertEquals(botBishop,botOrdered.get(2));
		assertEquals(botMagician,botOrdered.get(3));
	}

	@RepeatedTest(1)
	//@Test
	public void updateLeaderboardTest() {
		Behaviour aBehaviour = new Behaviour(new Player("testPlayer"), board);
		ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();
		assertEquals(leaderBoard.size(), 0);
		roundMan.updateLeaderboard(aBehaviour, leaderBoard);
		assertEquals(leaderBoard.size(), 1);
		assertEquals(leaderBoard.get(0), aBehaviour);

	}

	@RepeatedTest(1)
	//@Test
	public void cityVerificationNoCompleteCityTest() {
		Behaviour aBehaviour = new Behaviour(new Player("testPlayer"), board);
		aBehaviour.getPlayer().getCity().getBuiltDistrict().clear();

		ArrayList<Behaviour> leaderboard = new 	ArrayList<Behaviour>();
		assertEquals(leaderboard.size(), 0);
		roundMan.cityVerification(aBehaviour, leaderboard );
		assertEquals(leaderboard.size(), 0);
	}

	@RepeatedTest(1)
	//@Test
	public void cityVerificationHeCompleteCityTest() {
		ArrayList<Behaviour> leaderboard = new 	ArrayList<>();
		ArrayList<District> city = new 	ArrayList<>();
		Behaviour aBehaviour = new Behaviour(new Player("testPlayer"), board);
		aBehaviour.getPlayer().getCity().getBuiltDistrict().clear();
		for(int i = 0 ; i < 8 ; i ++) {
			aBehaviour.getPlayer().getCity().buildDistrict(new District("aDistrict"+i, i, "testColor", "testFamily"));
		}
		assertEquals(leaderboard.size(), 0);
		roundMan.cityVerification(aBehaviour, leaderboard );
		assertEquals(leaderboard.size(), 1);
	}

	@RepeatedTest(1)
	//@Test
	public void testFindKing(){
		listOfAllBehaviour.get(0).getPlayer().setRole(new Magician());
		listOfAllBehaviour.get(1).getPlayer().setRole(new Bishop());
		listOfAllBehaviour.get(2).getPlayer().setRole(new King());
		listOfAllBehaviour.get(3).getPlayer().setRole(new Assassin());
		roundMan.findKing(listOfAllBehaviour);

		assertEquals(2,roundMan.findKing(listOfAllBehaviour));
	}

	@RepeatedTest(1)
	//@Test
	public void testUpdateListOfBehaviour(){
		ArrayList<Behaviour> listOfAllBehaviourCopy = new ArrayList<>();


		//creation of Behaviour
		Behaviour botArchitect = new Behaviour(new Player("architectPlayer"), board);
		Behaviour botBishop =new Behaviour(new Player("bishopPlayer"), board);
		Behaviour botKing=new Behaviour(new Player("kingPlayer"), board);
		Behaviour botMagician = new Behaviour(new Player("magicianPlayer"), board);
		//creation of the list of bot

		listOfAllBehaviour.clear();

		listOfAllBehaviour.add(botArchitect);
		listOfAllBehaviour.add(botBishop);
		listOfAllBehaviour.add(botKing);
		listOfAllBehaviour.add(botMagician);

		listOfAllBehaviour.get(0).getPlayer().setRole(new Architect());
		listOfAllBehaviour.get(1).getPlayer().setRole(new Bishop());
		listOfAllBehaviour.get(2).getPlayer().setRole(new King());
		listOfAllBehaviour.get(3).getPlayer().setRole(new Magician());


		listOfAllBehaviourCopy.addAll(listOfAllBehaviour);

		roundMan=new RoundManager(listOfAllCharacter,listOfAllBehaviour,hashOfCharacter,board);
		roundMan.updateListOfBehaviour();


		assertEquals(roundMan.getListOfBehaviour().get(2),listOfAllBehaviour.get(0));
		assertEquals(roundMan.getListOfBehaviour().get(3),listOfAllBehaviour.get(1));
		assertEquals(roundMan.getListOfBehaviour().get(0),listOfAllBehaviour.get(2));
		assertEquals(roundMan.getListOfBehaviour().get(1),listOfAllBehaviour.get(3));
	}

	@RepeatedTest(1)
	//@Test
	public void reviveAllTest() {
		ArrayList<Behaviour> listOfAllBehaviour=new ArrayList<>();
		Player player = new Player("testPlayer");
		player.setRole(new Character("testCharacter", 0));
		Behaviour behaviour  = new Behaviour(player, board);
		listOfAllBehaviour.add(behaviour);
		player.getCharacter().setCharacterIsAlive(false);
		RoundManager roundManager = new RoundManager(listOfAllCharacter, listOfAllBehaviour, hashOfCharacter, board);
		assertFalse(player.getCharacter().getCharacterisAlive());
		roundManager.reviveAll();
		assertTrue(player.getCharacter().getCharacterisAlive());
	}

	@RepeatedTest(1)
	//@Test
	public void checkIfUpdateViewCharacterInBoardWorkTest() {
		ArrayList<Player> listOfPlayerForHash =  new ArrayList<>();
		Character testCharacter = new Character("testCharacter", 0);
		Player player = new Player("testPlayer");
		player.setRole(testCharacter);
		Behaviour bot = spy(new Behaviour(player, board));

		listOfPlayerForHash.add(player);
		board.setListOfPlayer(listOfPlayerForHash);
		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayerForHash);
		assertEquals(Optional.empty(), board.gethashOfViewCharacters().get(player));
		roundMan.actionOfBehaviour(bot);
		assertEquals(Optional.of(testCharacter), board.gethashOfViewCharacters().get(player));
	}

	@RepeatedTest(1)
	//@Test
	public void getListOfPlayerWhoHasAlreadyPlayedTest() {
		ArrayList<Player> listOfPlayerForHash =  new ArrayList<>();
		Character testCharacter = new Character("testCharacter", 0);
		Player player = new Player("testPlayer");
		player.setRole(testCharacter);
		Behaviour bot = spy(new Behaviour(player, board));

		listOfPlayerForHash.add(player);
		board.setListOfPlayer(listOfPlayerForHash);
		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayerForHash);
		assertEquals(0, board.getListOfPlayerWhoHasAlreadyPlayed().size());
		roundMan.actionOfBehaviour(bot);
		assertEquals(testCharacter, board.getListOfPlayerWhoHasAlreadyPlayed().get(0));

	}

}
