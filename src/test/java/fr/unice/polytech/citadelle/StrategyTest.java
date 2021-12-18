package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.purple_districts.Graveyard;
import fr.unice.polytech.citadelle.game.purple_districts.Observatory;
import fr.unice.polytech.citadelle.game.purple_districts.Smithy;
import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_engine.RoundManager;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.game_interactor.game_strategy.Predict;
import fr.unice.polytech.citadelle.game_interactor.game_strategy.Strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class StrategyTest {

	private final int bonusForFiveDistrict = 3;

	Board board;
	District green01;
	District green03;
	District green06;
	District yellow02;
	District blue03;
	District purple04;
	District red05;
	private LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	Behaviour botArchitecte;
	Behaviour botBishop;
	Behaviour botMagician;
	Behaviour botAssassin;
	Behaviour botKing;
	Behaviour botThief;
	Behaviour botMerchant;
	Behaviour botWarlord;
	Architect architect;
	Bishop bishop;
	Magician magician;
	Assassin assassin;
	King king;
	Thief thief;
	Merchant merchant;
	Warlord warlord;

	DeckDistrict deckDistrict;
	private Strategy strategy;

	@BeforeEach
	public void init() {
		hashOfCharacters = new LinkedHashMap<>();
		deckDistrict = new DeckDistrict();
		board = new Board(new ArrayList<Player>(), new ArrayList<Character>(), deckDistrict, new DeckCharacter());
		board.getDeckDistrict().initialise();

		// creation of Behaviour
		botArchitecte = new Behaviour(new Player("architectePlayer"), board);
		botBishop = new Behaviour(new Player("bishopPlayer"), board);
		botMagician = spy(new Behaviour(new Player("magicianPlayer"), board));
		botAssassin = new Behaviour(new Player("assassinPlayer"), board);
		botKing = new Behaviour(new Player("kingPlayer"), board);
		botThief = spy(new Behaviour(new Player("thiefPlayer"), board));
		botMerchant = new Behaviour(new Player("merchantPlayer"), board);
		botWarlord = new Behaviour(new Player("warlordPlayer"), board);

		// creation of the characters in game
		architect = new Architect();
		bishop = new Bishop();
		magician = new Magician();
		assassin = new Assassin();
		king = new King();
		thief = new Thief();
		merchant = new Merchant();
		warlord = new Warlord();

		// we set the character of our bot
		botKing.getPlayer().setRole(king);
		botMerchant.getPlayer().setRole(merchant);
		botMagician.getPlayer().setRole(magician);
		botArchitecte.getPlayer().setRole(architect);
		botWarlord.getPlayer().setRole(warlord);
		botThief.getPlayer().setRole(thief);
		botAssassin.getPlayer().setRole(assassin);
		botBishop.getPlayer().setRole(bishop);

		// Adding the character in the Board
		board.getListOfCharacter().add(assassin);
		board.getListOfCharacter().add(thief);
		board.getListOfCharacter().add(magician);
		board.getListOfCharacter().add(king);
		board.getListOfCharacter().add(bishop);
		board.getListOfCharacter().add(merchant);
		board.getListOfCharacter().add(architect);
		board.getListOfCharacter().add(warlord);

		// Adding the players in the Board
		board.getListOfPlayer().add(botAssassin.getPlayer());
		board.getListOfPlayer().add(botThief.getPlayer());
		board.getListOfPlayer().add(botMagician.getPlayer());
		board.getListOfPlayer().add(botKing.getPlayer());
		board.getListOfPlayer().add(botBishop.getPlayer());
		board.getListOfPlayer().add(botMerchant.getPlayer());
		board.getListOfPlayer().add(botArchitecte.getPlayer());
		board.getListOfPlayer().add(botWarlord.getPlayer());
		// creation of the hashOfCharacter
		hashOfCharacters.put(assassin, Optional.of(botAssassin));
		hashOfCharacters.put(thief, Optional.of(botThief));
		hashOfCharacters.put(magician, Optional.of(botMagician));
		hashOfCharacters.put(king, Optional.of(botKing));
		hashOfCharacters.put(bishop, Optional.of(botBishop));
		hashOfCharacters.put(merchant, Optional.of(botMerchant));
		hashOfCharacters.put(architect, Optional.of(botArchitecte));
		hashOfCharacters.put(warlord, Optional.of(botWarlord));

		green01 = new District("greenDistrict", 1, "Green", "empty");
		green03 = new District("greenDistrict", 6, "Green", "empty");
		green06 = new District("greenDistrict", 6, "Green", "empty");

		yellow02 = new District("greenDistrict", 2, "Yellow", "empty");
		blue03 = new District("greenDistrict", 3, "Blue", "empty");
		purple04 = new District("greenDistrict", 4, "Purple", "empty");
		red05 = new District("greenDistrict", 5, "Red", "empty");

		strategy = new Strategy(8, board, botMagician.getPlayer());

	}

	@RepeatedTest(20)
	void chooseCharacterForThiefRandom() {

		Strategy strategy = new Strategy(8, null, botThief.getPlayer());
		Character characterChoosen = strategy.chooseCharacterForThiefRandom(hashOfCharacters);
		assertNotEquals("Assassin", characterChoosen);
		assertNotEquals("Thief", characterChoosen);
		assertEquals(true, characterChoosen.isCharacterIsAlive());
	}

	@RepeatedTest(20)
	void chooseCharacterForAssassinRandom() {
		Strategy strategy = new Strategy(8, null, botAssassin.getPlayer());
		Character characterChoosen = strategy.chooseCharacterForAssassinRandom(hashOfCharacters);
		assertNotEquals("Assassin", characterChoosen);
	}

	@RepeatedTest(20)
	void chooseCharacterForMagicianRandom() {
		Strategy strategy = new Strategy(8, null, botMagician.getPlayer());
		Character characterChoosen = strategy.chooseCharacterForMagicianRandom(hashOfCharacters);
		assertNotEquals("Magician", characterChoosen);
	}

	@Test
	void playerHasOnlyFiveDifferentDistrictColor() {
		botMagician.buildDistrict(green01);
		botMagician.buildDistrict(yellow02);
		botMagician.buildDistrict(blue03);
		botMagician.buildDistrict(purple04);
		botMagician.buildDistrict(red05);

		assertTrue(strategy.hasFiveDistrictColors(botMagician.getPlayer()));
	}

	@Test
	void playerHasOnlyFourDifferentDistrictColor() {
		botMagician.buildDistrict(green01);
		botMagician.buildDistrict(green03);
		botMagician.buildDistrict(green06);
		botMagician.buildDistrict(yellow02);
		botMagician.buildDistrict(blue03);
		botMagician.buildDistrict(red05);

		assertFalse(strategy.hasFiveDistrictColors(botMagician.getPlayer()));
	}

	@Test
	void cityScoreCalculate01() {
		botMagician.buildDistrict(green01); // District value is 1
		botMagician.buildDistrict(green06); // District value is 6
		botMagician.buildDistrict(purple04); // District value is 4
		botMagician.buildDistrict(red05); // District value is 5

		assertEquals((1 + 6 + 4 + 5), strategy.playerPredictScore(botMagician.getPlayer()));
	}

	@Test
	void cityScoreCalculate02() {
		botMagician.buildDistrict(green01); // District value is 1
		botMagician.buildDistrict(green06); // District value is 6
		botMagician.buildDistrict(yellow02); // District value is 2
		botMagician.buildDistrict(blue03); // District value is 3
		botMagician.buildDistrict(purple04); // District value is 4
		botMagician.buildDistrict(red05); // District value is 5

		assertEquals((1 + 6 + 2 + 3 + 4 + 5 + bonusForFiveDistrict),
				strategy.playerPredictScore(botMagician.getPlayer()));
	}

	@Test
	void testchooseCharacterForAssassinAdvancedEgality() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		// This is the bot that is going to use the strategy
		botAssassin.buildDistrict(yellow02); // District value is 2
		botAssassin.buildDistrict(red05); // District value is 5
		// he has the same score
		botMagician.buildDistrict(green01); // District value is 1
		botMagician.buildDistrict(green06); // District value is 6
		// he has 2 points different
		botBishop.buildDistrict(yellow02); // District value is 2
		botBishop.buildDistrict(blue03); // District value is 3

		assertEquals(botMagician.getPlayer(), strategy.findThePlayerWithClosestScoreAssassin());
	}

	@Test
	void testchooseCharacterForAssassinAdvancedWhenBothGotLessPoints() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		// This is the bot that is going to use the strategy
		botAssassin.buildDistrict(green01); // District value is 1
		botAssassin.buildDistrict(green06); // District value is 6

		// he has 2 points different
		botBishop.buildDistrict(yellow02); // District value is 2
		botBishop.buildDistrict(blue03); // District value is 3

		// he has 2 points different
		botMagician.buildDistrict(yellow02); // District value is 2

		strategy.findThePlayerWithClosestScoreAssassin();
		assertEquals(botBishop.getPlayer(), strategy.findThePlayerWithClosestScoreAssassin());
	}

	@Test
	void testchooseCharacterForAssassinAdvancedWhenBothGotMorePoints() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		// This is the bot that is going to use the strategy
		botAssassin.buildDistrict(green01); // District value is 1
		botAssassin.buildDistrict(green06); // District value is 6

		// he has 2 points different
		botBishop.buildDistrict(green06); // District value is 6
		botBishop.buildDistrict(blue03); // District value is 3

		// he has 5 points different
		botMagician.buildDistrict(green06); // District value is 6
		botMagician.buildDistrict(blue03); // District value is 3
		botMagician.buildDistrict(green03); // District value is 3

		strategy.findThePlayerWithClosestScoreAssassin();
		assertEquals(botBishop.getPlayer(), strategy.findThePlayerWithClosestScoreAssassin());
	}

	@Test
	void testchooseCharacterForAssassinAdvancedWhenOneGotMoreAndOtherGotLess1() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		// This is the bot that is going to use the strategy
		botAssassin.buildDistrict(green01); // District value is 1
		botAssassin.buildDistrict(green06); // District value is 6

		// he has 1 points different
		botBishop.buildDistrict(green06); // District value is 6

		// he has 5 points different
		botMagician.buildDistrict(green06); // District value is 6
		botMagician.buildDistrict(blue03); // District value is 3
		botMagician.buildDistrict(green03); // District value is 3

		strategy.findThePlayerWithClosestScoreAssassin();
		assertEquals(botBishop.getPlayer(), strategy.findThePlayerWithClosestScoreAssassin());
	}

	@Test
	void testchooseCharacterForAssassinAdvancedWhenOneGotMoreAndOtherGotLess2() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		// This is the bot that is going to use the strategy
		botAssassin.buildDistrict(green06); // District value is 6
		botAssassin.buildDistrict(blue03); // District value is 3
		botAssassin.buildDistrict(green03); // District value is 3

		// he has 1 points different
		botBishop.buildDistrict(green06); // District value is 6

		// he has 5 points different
		botMagician.buildDistrict(green01); // District value is 1
		botMagician.buildDistrict(green06); // District value is 6
		botMagician.buildDistrict(blue03); // District value is 3
		botMagician.buildDistrict(green03); // District value is 3

		strategy.findThePlayerWithClosestScoreAssassin();
		assertEquals(botMagician.getPlayer(), strategy.findThePlayerWithClosestScoreAssassin());
	}

	@Test
	void chooseCharacterForAssassinAdvancedWhenTargetHasAlreadyRevealCharacter() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());

		ArrayList<Player> listOfPlayerForHash = new ArrayList<>();

		listOfPlayerForHash.add(botArchitecte.getPlayer());
		listOfPlayerForHash.add(botAssassin.getPlayer());

		board.setListOfPlayer(listOfPlayerForHash);
		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayerForHash);
		board.revealCharacter(botArchitecte.getPlayer(), architect);

		Character CharacterOfTheTarget = strategy.chooseCharacterForAssassinAdvanced();
		assertEquals(architect, CharacterOfTheTarget);
	}

	@Test
	void chooseCharacterForAssassinAdvancedWhenTargetWhenCharacterNotReveal() {
		strategy = spy(new Strategy(8, board, botAssassin.getPlayer()));
		ArrayList<Player> listOfPlayerForHash = new ArrayList<>();
		listOfPlayerForHash.add(botArchitecte.getPlayer());
		listOfPlayerForHash.add(botAssassin.getPlayer());
		board.setListOfPlayer(listOfPlayerForHash);
		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayerForHash);	
		
		strategy.chooseCharacterForAssassinAdvanced();
		verify(strategy, times(1)).getAPrediction(any(), any());

	}

	@RepeatedTest(100)
	void getCharacterOfPlayerTest() {
		strategy = new Strategy(8, board, botAssassin.getPlayer());
		ArrayList<Player> listOfPlayers = board.getListOfPlayer();
		listOfPlayers.clear();

		
		Player player1 = new Player("player1");
		Character testCharacter1 = new Character("testCharacter1", 0);
		player1.setRole(testCharacter1);
		
		Player player2 = new Player("player2");
		Character testCharacter2 = new Character("testCharacter2", 0);
		player2.setRole(testCharacter2);

		
		Player player3 = new Player("player3");
		Character testCharacter3 = new Character("testCharacter3", 0);
		player3.setRole(testCharacter3);
		
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		listOfPlayers.add(player3);

		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayers);
		
		Player botPlayer = botAssassin.getPlayer();
		botPlayer= player1;
		board.revealCharacter(player3, testCharacter3);
		board.revealCharacter(player2, testCharacter2);


		Optional<Character> predictCharacter = strategy.getCharacterOfPlayer(player3);
		assertTrue(predictCharacter.isPresent());
		assertEquals(testCharacter3, predictCharacter.get());
	}
	@Test
	void testchooseCharacterWithMostGolds() {
		board.getListOfPlayer().clear();
		Player player1=new Player("p1");
		player1.setRole(new Thief());
		Player player2=new Player("p2");
		player2.setRole(new Magician());
		Player player3=new Player("p3");
		player3.setRole(new Merchant());

		board.getListOfPlayer().add(player1);
		board.getListOfPlayer().add(player2);
		board.getListOfPlayer().add(player3);
		strategy = new Strategy(8, board, player1);
		player1.setGolds(3);
		player2.setGolds(4);
		player3.setGolds(5);
		strategy.findThePlayerWithMostGolds();
		assertEquals(player3, strategy.findThePlayerWithMostGolds());
	}
	@Test
	void testchooseCharacterWithMostGolds2() {
		//We now Try when the thief has the most golds
		board.getListOfPlayer().clear();
		Player player1=new Player("p1");
		player1.setRole(new Thief());
		Player player2=new Player("p2");
		player2.setRole(new Magician());
		Player player3=new Player("p3");
		player3.setRole(new Merchant());

		board.getListOfPlayer().add(player1);
		board.getListOfPlayer().add(player2);
		board.getListOfPlayer().add(player3);
		strategy = new Strategy(8, board, player1);
		player1.setGolds(5);
		player2.setGolds(4);
		player3.setGolds(3);
		strategy.findThePlayerWithMostGolds();
		assertEquals(player2, strategy.findThePlayerWithMostGolds());
	}

	@Test
	void testchooseCharacterWithMostGolds3() {
		//We now Try when the assassin has the most golds
		board.getListOfPlayer().clear();
		Player player1=new Player("p1");
		player1.setRole(new Thief());
		Player player2=new Player("p2");
		player2.setRole(new Assassin());
		Player player3=new Player("p3");
		player3.setRole(new Merchant());

		board.getListOfPlayerWhoPlayed().add(player2);


		board.getListOfPlayer().add(player1);
		board.getListOfPlayer().add(player2);
		board.getListOfPlayer().add(player3);
		strategy = new Strategy(8, board, player1);
		player1.setGolds(5);
		player2.setGolds(7);
		player3.setGolds(3);
		strategy.findThePlayerWithMostGolds();
		assertEquals(player3, strategy.findThePlayerWithMostGolds());
	}

	@Test
	public void chooseCharacterAssassinTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
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

		assertEquals(assassin, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterArchitectTest() {
		Player player1 = new Player("Player1");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());

		player1.addDistrict(new District("Castle",4,"Yellow","Nobility"));
		player1.addDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player1.addDistrict(new Smithy("Smithy", 5,"Purple","Prestige"));

		player1.setGolds(6);

		Character architect = new Character("Architect", Initializer.ARCHITECT_INDEX);

		assertEquals(architect, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterMagicianTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
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

		assertEquals(magician, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterThiefTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
		ArrayList<Player> listOfPlayers = new ArrayList<>();
		listOfPlayers.add(player1);
		listOfPlayers.add(player2);
		board.setListOfPlayer(listOfPlayers);

		player2.setGolds(12);

		Character thief = new Character("Thief", Initializer.THIEF_INDEX);

		assertEquals(thief, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterKingTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player.buildDistrict(new District("Palace",5,"Yellow","Nobility"));

		Character king = new Character("King", Initializer.KING_INDEX);

		assertEquals(king, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterMerchantTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Trading Post", 2, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Harbor", 4, "Green", "Trade and Handicrafts"));

		Character merchant = new Character("Merchant", Initializer.MERCHANT_INDEX);

		assertEquals(merchant, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterKingVsMerchantTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
		Player player = new Player("Player");
		Behaviour aBehaviour = new Behaviour(player, board);

		player.buildDistrict(new District("Trading Post", 2, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Harbor", 4, "Green", "Trade and Handicrafts"));
		player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
		player.buildDistrict(new District("Manor", 3,"Yellow","Nobility"));
		player.buildDistrict(new District("Palace",5,"Yellow","Nobility"));

		Character king = new Character("King", Initializer.KING_INDEX);

		assertEquals(king, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterBishopTest() {
		DeckCharacter deckCharacter = new DeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
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

		assertEquals(bishop, strategy.chooseCharacter(aBehaviour));
	}

	@Test
	public void chooseCharacterWarlordTest() {
		Player player1 = new Player("Player1");
		Player player2 = new Player("Player2");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());
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
		player2.buildDistrict(new District("Battlefield",3,"Red","Soldiery"));

		//Remove the Assassin because he does the same thing
		deckCharacter.getDeckCharacter().remove(0);

		Character warlord = new Character("Warlord", Initializer.WARLORD_INDEX);

		assertEquals(warlord, strategy.chooseCharacter(aBehaviour));
	}

	@Test
		//Test the last return of the chooseCharacter method from RoundManager
		//which return the first character (Assassin) by default
	void chooseCharacterDefaultTest() {
		Player player1 = new Player("Player1");
		DeckCharacter deckCharacter = new DeckCharacter();
		Behaviour aBehaviour = new Behaviour(player1, board);
		Initializer.initDeckCharacter(deckCharacter, board.getListOfCharacter());

		Character assassin = new Character("Assassin", Initializer.ASSASSIN_INDEX);
		assertEquals(assassin, strategy.chooseCharacter(aBehaviour));
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
		assertEquals(Initializer.KING_INDEX, strategy.isThereAFamily(bobBehaviour));
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
		assertEquals(Initializer.MERCHANT_INDEX, strategy.isThereAFamily(aliceBehaviour));
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
		assertTrue(strategy.isThereAFamily(fredBehaviour) == -1);
	}

	@Test
	void testChooseSpellForMagicianWhenTargetOtherPlayer(){
		botMagician.getPlayer().getDistrictCards().add(new District(null,1,null,null));
		botArchitecte.getPlayer().getDistrictCards().add(new District(null,1,null,null));
		botArchitecte.getPlayer().getDistrictCards().add(new District(null,1,null,null));


		strategy=new Strategy(8,board,botMagician.getPlayer());

		assertEquals(botArchitecte.getPlayer(),strategy.findThePlayerWithMostCards());
		assertEquals(0,strategy.chooseMagicianActionAdvanced().size());
		assertEquals(true,strategy.isThereAPlayerWithTwoTimesHisDistricts());

		assertEquals(null,strategy.cardToBeSwapped());
	}
	@Test
	void testChooseSpellForMagicianWhenTargetDeck(){
		District districtDoble =new District("districtDoble",1,"Purple","Prestige");
		botMagician.buildDistrict(new District("districtDoble",1,"Purple","Prestige"));
		botMagician.getPlayer().getDistrictCards().add(districtDoble);
		botMagician.getPlayer().getDistrictCards().add(new District("districtOther",1,"Purple","Prestige"));

		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));


		strategy=new Strategy(8,board,botMagician.getPlayer());

		assertEquals(botArchitecte.getPlayer(),strategy.findThePlayerWithMostCards());

		assertEquals(districtDoble,strategy.chooseMagicianActionAdvanced().get(0));

		assertEquals(false,strategy.isThereAPlayerWithTwoTimesHisDistricts());

		assertEquals(districtDoble,strategy.cardToBeSwapped().get(0));
	}
	@Test
	void testChooseSpellForMagicianWhenTargetDeck2(){
		District districtDoble =new District("districtDoble",1,"Purple","Prestige");
		District districtOtherDoble =new District("otherDistrictDoble",1,"Purple","Prestige");
		botMagician.buildDistrict(new District("districtDoble",1,"Purple","Prestige"));
		botMagician.buildDistrict(new District("otherDistrictDoble",1,"Purple","Prestige"));
		botMagician.getPlayer().getDistrictCards().add(districtDoble);
		botMagician.getPlayer().getDistrictCards().add(districtOtherDoble);
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));


		strategy=new Strategy(8,board,botMagician.getPlayer());

		assertEquals(botArchitecte.getPlayer(),strategy.findThePlayerWithMostCards());

		assertEquals(districtDoble,strategy.chooseMagicianActionAdvanced().get(0));
		assertEquals(districtOtherDoble,strategy.chooseMagicianActionAdvanced().get(1));

		assertEquals(false,strategy.isThereAPlayerWithTwoTimesHisDistricts());

		assertEquals(districtDoble,strategy.cardToBeSwapped().get(0));
	}
	@Test
	void testChooseTargetOfSpellMagician(){
		botMagician.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botArchitecte.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botWarlord.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));
		botWarlord.getPlayer().getDistrictCards().add(new District("randomDistrict",1,"Purple","Prestige"));


		strategy=new Strategy(8,board,botMagician.getPlayer());

		assertEquals(botArchitecte.getPlayer(),strategy.findThePlayerWithMostCards());
		assertEquals(true,strategy.isThereAPlayerWithTwoTimesHisDistricts());

	}
}
