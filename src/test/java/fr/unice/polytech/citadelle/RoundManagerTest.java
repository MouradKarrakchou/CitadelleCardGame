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
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class RoundManagerTest {
	RoundManager roundMan;
	DeckDistrict deckDistrict;
	ArrayList<Player> listOfPlayerSpy;
	Board game;
	PrintCitadels printer;
	Referee referee;
	
	LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter;
	ArrayList<Character> listOfAllCharacter;
	ArrayList<Behaviour> listOfAllBehaviour;
	Board board;
	

	Initialiser init;
	@BeforeEach
	public void init() {

		init = new Initialiser();
		deckDistrict = new DeckDistrict();
		printer = new PrintCitadels();

		
		deckDistrict.initialise();

		hashOfCharacter = new LinkedHashMap<Character, Optional<Behaviour>>();
		listOfAllCharacter = init.createListOfAllCharacter();
		listOfAllBehaviour = init.createListOfBehaviour(board);
		board = init.createBoard(listOfAllCharacter);
		init.initDeckCharacter(board.getDeckCharacter(), listOfAllCharacter);
		hashOfCharacter = init.resetHashOfCharacter(hashOfCharacter, listOfAllCharacter);
		roundMan = spy(new RoundManager(listOfAllCharacter, listOfAllBehaviour,hashOfCharacter, board));
		referee = new Referee(board);
		
		init.initDeckCharacter(roundMan.getBoard().getDeckCharacter(), listOfAllCharacter);
		init.initDeckDistrict(roundMan.getBoard().getDeckDistrict());
		
		board.setListOfPlayer(roundMan.getListOfPlayers());

		roundMan = spy(new RoundManager(listOfAllCharacter, listOfAllBehaviour, hashOfCharacter, board));
		//listOfPlayerSpy = spy(roundMan.getListOfPlayers());

	
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
	public void actionsOfTheBehaviourTest() {
		Character c = new Character("testCharacter", 0);
		Behaviour bot = spy(new Behaviour(new Player("testPlayer"), board));
		bot.getPlayer().setRole(c);
		roundMan.actionOfBehaviour(bot, deckDistrict);
		verify(bot, times(1)).play(Mockito.any(), Mockito.any());
	}
	
	
	@Test
	public void setupCharactersTest() {
		roundMan.setupCharacters(init);
		verify(roundMan, times(Initialiser.NUMBER_OF_PLAYER)).chooseACharacterCard(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void askEachCharacterToPlayTest() {
		PhaseManager phaseMan = new PhaseManager();
		LinkedHashMap<Character, Optional<Behaviour>> hashCharacter;
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

		Optional<Behaviour> behaviour1 = Optional.of(new Behaviour(p1, board));
		Optional<Behaviour> behaviour2 = Optional.of(new Behaviour(p2, board));
		Optional<Behaviour> behaviour3 = Optional.of(new Behaviour(p3, board));

		hashCharacter.put(assasin, behaviour1);	
		hashCharacter.put(thief, behaviour2);		
		hashCharacter.put(architect, behaviour3);		
		
		roundMan.askEachCharacterToPlay(phaseMan, roundMan.getBoard().getDeckDistrict(), init);
		verify(roundMan, times(3)).actionOfBehaviour(Mockito.any(), Mockito.any());
	
	}
	
	@Test
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
		//creation of the characters in game
		Architect architect = new Architect();
		Bishop bishop = new Bishop();
		Magician magician = new Magician();
		King king = new King();

		//we set the character of our bot
		botKing.getPlayer().setRole(king);

		//creation of the hashOfCharacter
		hashOfCharacter.put(architect, Optional.of(botArchitecte));
		hashOfCharacter.put(bishop, Optional.of(botBishop));
		hashOfCharacter.put(magician, Optional.of(botMagician));
		hashOfCharacter.put(king,Optional.of(botKing));

		//Verify that he finds the right spot for the king
		botKing.setBehaviourIsKing(true);
		assertEquals(3,roundMan.findKing(listOfAllBehaviour));

		//Verify that they are well ordered
		ArrayList<Behaviour> botOrdered=roundMan.orderListOfPlayer(listOfAllBehaviour,3);
		assertEquals(botKing,botOrdered.get(0));
		assertEquals(botArchitecte,botOrdered.get(1));
		assertEquals(botBishop,botOrdered.get(2));
		assertEquals(botMagician,botOrdered.get(3));
	}
	
	@Test
	public void updateLeaderboardTest() {
		Behaviour aBehaviour = new Behaviour(new Player("testPlayer"), board);
		ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();
		assertEquals(leaderBoard.size(), 0);
		roundMan.updateLeaderboard(aBehaviour, leaderBoard);
		assertEquals(leaderBoard.size(), 1);
		assertEquals(leaderBoard.get(0), aBehaviour);

	}

	@Test
	public void cityVerificationNoCompleteCityTest() {
		Behaviour aBehaviour = new Behaviour(new Player("testPlayer"), board);
		aBehaviour.getPlayer().getCity().getBuiltDistrict().clear();

		ArrayList<Behaviour> leaderboard = new 	ArrayList<Behaviour>();
		assertEquals(leaderboard.size(), 0);
		roundMan.cityVerification(aBehaviour, leaderboard );
		assertEquals(leaderboard.size(), 0);
	}

	@Test
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

	@Test
	public void chooseCharacterTest() {

	}

	@Test
	public void isThereAFamilyTest(){
		Player bob = new Player("bob");
		Behaviour bobBehaviour = new Behaviour(bob, board);
		bob.buildDistrict(new District("Nobility district 01", 3, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Nobility district 02", 1, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Nobility district 03", 6, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Other district 01", 1, "notImportant", "otherFamily"));
		bob.buildDistrict(new District("Other district 01", 2, "notImportant", "otherFamily"));
		bob.buildDistrict(new District("Other district 01", 4, "notImportant", "otherFamily"));

		assertEquals(6, bob.getCity().getSizeOfCity());
		assertEquals(Initialiser.KING_INDEX, roundMan.isThereAFamily(bobBehaviour));

	}

}
