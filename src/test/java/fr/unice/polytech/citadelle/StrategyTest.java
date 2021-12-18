package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_engine.Referee;
import fr.unice.polytech.citadelle.game_interactor.Predict;
import fr.unice.polytech.citadelle.game_interactor.Strategy;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;

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

}
