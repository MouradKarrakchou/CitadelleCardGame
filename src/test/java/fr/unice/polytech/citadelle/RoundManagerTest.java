package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.purple_districts.Graveyard;
import fr.unice.polytech.citadelle.game.purple_districts.Observatory;
import fr.unice.polytech.citadelle.game.purple_districts.Smithy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.characters_class.*;
import fr.unice.polytech.citadelle.game.Character;

import org.mockito.Mockito;

import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_engine.PhaseManager;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;

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

		init = new Initializer();
		deckDistrict = new DeckDistrict();

		
		deckDistrict.initialise();

		hashOfCharacter = new LinkedHashMap<Character, Optional<Behaviour>>();
		listOfAllCharacter = Initializer.createListOfAllCharacter();
		listOfAllBehaviour = Initializer.createListOfBehaviour(board);
		board = Initializer.createBoard();
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
	
	@Test
	public void runRoundsOnceTest() {
		PhaseManager phaseMan=Mockito.mock(PhaseManager.class);
		when(phaseMan.analyseGame(Mockito.any())).thenReturn(PhaseManager.LAST_TURN_PHASE);
		roundMan.runRounds(phaseMan);
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
		roundMan.actionOfBehaviour(bot);
		verify(bot, times(1)).play(Mockito.any(), Mockito.any());
	}
	
	
	@Test
	public void setupCharactersTest() {
		roundMan.setupCharacters();
		verify(roundMan, times(Initializer.NUMBER_OF_PLAYER)).chooseACharacterCard(Mockito.any(), Mockito.any());
	}
	
	@Test
	public void askEachCharacterToPlayTest() {
		PhaseManager phaseMan = new PhaseManager();
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
		
		roundMan.askEachCharacterToPlay(phaseMan);
		verify(roundMan, times(3)).actionOfBehaviour(Mockito.any());
	
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
	public void chooseCharacterAssassinTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		board.setListOfPlayer(listOfPlayers);

		player2.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player2.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player2.buildDistrict(new District("Palace",5,"Yellow","Nobility"));
		player2.buildDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));
		player2.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
		player2.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));

		Character assassin = new Character("Assassin", Initializer.ASSASSIN_INDEX);

		assertEquals(assassin, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterArchitectTest() {
		Player player1 = new Player("Player1");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);

		player1.addDistrict(new District("Castle",4,"Yellow","Nobility"));
		player1.addDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player1.addDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));

		player1.setGolds(6);

		Character architect = new Character("Architect", Initializer.ARCHITECT_INDEX);

		assertEquals(architect, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterMagicianTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		board.setListOfPlayer(listOfPlayers);

		player2.addDistrict(new District("Castle",4,"Yellow","Nobility"));
		player2.addDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player2.addDistrict(new District("Palace",5,"Yellow","Nobility"));
		player2.addDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));
		player2.addDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));

		Character magician = new Character("Magician", Initializer.MAGICIAN_INDEX);

		assertEquals(magician, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterThiefTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		board.setListOfPlayer(listOfPlayers);

		player2.setGolds(12);

		Character thief = new Character("Thief", Initializer.THIEF_INDEX);

		assertEquals(thief, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterKingTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player.buildDistrict(new District("Palace",5,"Yellow","Nobility"));

		Character king = new Character("King", Initializer.KING_INDEX);

		assertEquals(king, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterMerchantTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Trading Post", 2, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Harbor", 4, "Green", "Trade and Handicrafts"));

		Character merchant = new Character("Merchant", Initializer.MERCHANT_INDEX);

		assertEquals(merchant, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterKingVsMerchantTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Trading Post", 2, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Harbor", 4, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player.buildDistrict(new District("Palace",5,"Yellow","Nobility"));

		Character king = new Character("King", Initializer.KING_INDEX);

		assertEquals(king, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterBishopTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		board.getListOfPlayer().clear();
		board.getListOfPlayer().add(player);

		player.buildDistrict(new District("Trading Post", 2, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
		player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player.buildDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));

		Character bishop = new Character("Bishop", Initializer.BISHOP_INDEX);

		assertEquals(bishop, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	public void chooseCharacterWarlordTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		board.setListOfPlayer(listOfPlayers);

		player2.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player2.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player2.buildDistrict(new District("Palace",5,"Yellow","Nobility"));
		player2.buildDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));
		player2.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
		player2.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));

		//Remove the Assassin because he does the same thing
		deckCharacter.getDeckCharacter().remove(0);

		Character warlord = new Character("Warlord", Initializer.WARLORD_INDEX);

		assertEquals(warlord, roundMan.chooseCharacter(aBehaviour, deckCharacter));
	}

	@Test
	//Test the last return of the chooseCharacter method from RoundManager
	//which return the first character (Assassin) by default
	void chooseCharacterDefaultTest() {
		Player player1 = new Player("Player1");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacter);

		Character assassin = new Character("Assassin", Initializer.ASSASSIN_INDEX);
		assertEquals(assassin, roundMan.chooseCharacter(aBehaviour, deckCharacter));
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
	public void isThereAFamilyTestNobility(){
		Player bob = new Player("bob");
		Behaviour bobBehaviour = new Behaviour(bob, board);
		bob.buildDistrict(new District("Nobility district 01", 3, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Nobility district 02", 1, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Nobility district 03", 6, "notImportant", "Nobility"));
		bob.buildDistrict(new District("Other district 01", 1, "notImportant", "otherFamily"));
		bob.buildDistrict(new District("Other district 02", 2, "notImportant", "otherFamily"));
		bob.buildDistrict(new District("Other district 03", 4, "notImportant", "otherFamily"));

		assertEquals(6, bob.getCity().getSizeOfCity());
		assertEquals(Initializer.KING_INDEX, roundMan.isThereAFamily(bobBehaviour));
	}

	@Test
	public void isThereAFamilyTestTradeAndHandicrafts(){
		Player alice = new Player("alice");
		Behaviour aliceBehaviour = new Behaviour(alice, board);
		alice.buildDistrict(new District("Trade and Handicrafts district 01", 3, "notImportant", "Trade and Handicrafts"));
		alice.buildDistrict(new District("Trade and Handicrafts district 02", 1, "notImportant", "Trade and Handicrafts"));
		alice.buildDistrict(new District("Trade and Handicrafts district 03", 6, "notImportant", "Trade and Handicrafts"));
		alice.buildDistrict(new District("Nobility district 01", 1, "notImportant", "Nobility"));
		alice.buildDistrict(new District("Nobility district 02", 2, "notImportant", "Nobility"));
		alice.buildDistrict(new District("Other district 01", 4, "notImportant", "otherFamily"));

		assertEquals(6, alice.getCity().getSizeOfCity());
		assertEquals(Initializer.MERCHANT_INDEX, roundMan.isThereAFamily(aliceBehaviour));
	}

	@Test
	public void isThereAFamilyTestNothing(){

		Player fred = new Player("fred");
		Behaviour fredBehaviour = new Behaviour(fred, board);
		fred.buildDistrict(new District("Trade and Handicrafts district 01", 3, "notImportant", "Trade and Handicrafts"));
		fred.buildDistrict(new District("Trade and Handicrafts district 02", 1, "notImportant", "Trade and Handicrafts"));
		fred.buildDistrict(new District("Other district 01", 4, "notImportant", "otherFamily"));
		fred.buildDistrict(new District("Nobility district 01", 1, "notImportant", "Nobility"));
		fred.buildDistrict(new District("Nobility district 02", 2, "notImportant", "Nobility"));
		fred.buildDistrict(new District("Other district 02", 2, "notImportant", "otherFamily"));

		assertEquals(6, fred.getCity().getSizeOfCity());
		assertTrue(roundMan.isThereAFamily(fredBehaviour) == -1);
	}

	@Test
	public void testFindKing(){
		listOfAllBehaviour.get(0).getPlayer().setRole(new Magician());
		listOfAllBehaviour.get(1).getPlayer().setRole(new Bishop());
		listOfAllBehaviour.get(2).getPlayer().setRole(new King());
		listOfAllBehaviour.get(3).getPlayer().setRole(new Assassin());
		roundMan.findKing(listOfAllBehaviour);

		assertEquals(2,roundMan.findKing(listOfAllBehaviour));
	}

	@Test
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

	@Test
	public void reviveAllTest() {
		ArrayList<Behaviour> listOfAllBehaviour=new ArrayList<>();
		Player player = new Player("testPlayer");
		player.setRole(new Character("testCharacter", 0));
		Behaviour behaviour  = new Behaviour(player, board);
		listOfAllBehaviour.add(behaviour);
		player.getCharacter().setCharacterIsAlive(false);
		RoundManager roundManager = new RoundManager(listOfAllCharacter, listOfAllBehaviour, hashOfCharacter, board);
		assertFalse(player.getCharacter().isCharacterIsAlive());
		roundManager.reviveAll();
		assertTrue(player.getCharacter().isCharacterIsAlive());
	}

}
