package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.game_strategy.RichalphonseStrategy;

public class RichalphonseStrategyTest {
	RichalphonseStrategy richalphStrat;
	Board board;
	Player player;

	@BeforeEach
	public void init() {
		player = new Player("player");
		DeckDistrict deckDistrict = new DeckDistrict();
		DeckCharacter deckCharacter = new DeckCharacter(4);
		Initializer.initDeckDistrict(deckDistrict);
		Initializer.initDeckCharacter(deckCharacter, Initializer.createListOfAllCharacter());
		board = new Board(new ArrayList<>(), new ArrayList<>(), deckDistrict, deckCharacter);
		richalphStrat = new RichalphonseStrategy(board, player);
	}

	@Test
	public void getTargetPlayerHasMostCardTest() {
		Player other = new Player("Other");
		other.getDistrictCards().add(new District("test", 0, "test", "test"));
		other.getDistrictCards().add(new District("test", 1, "test", "test"));
		other.getDistrictCards().add(new District("test", 2, "test", "test"));
		other.getDistrictCards().add(new District("test", 3, "test", "test"));
		other.getDistrictCards().add(new District("test", 4, "test", "test"));
		other.getDistrictCards().add(new District("test", 5, "test", "test"));
		other.getDistrictCards().add(new District("test", 6, "test", "test"));
		other.getDistrictCards().add(new District("test", 7, "test", "test"));
		other.getDistrictCards().add(new District("test", 8, "test", "test"));
		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		Player res = richalphStrat.getTargetPlayerHasMostCard();
		assertEquals(other, res);

	}

	// aPlayerIsCloseToWinForSixAndSeven()

	@Test
	public void aPlayerIsCloseToWinForSixAndSevenTrueTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));

		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		boolean res = richalphStrat.aPlayerIsCloseToWinForSixAndSeven();
		assertEquals(true, res);

	}

	@Test
	public void aPlayerIsCloseToWinForSixAndSevenFalseTest() {
		Player other = new Player("Other");
		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);
		boolean res = richalphStrat.aPlayerIsCloseToWinForSixAndSeven();
		assertEquals(false, res);
	}

	@Test
	public void getPlayerCloseToWinBySixOrMoreTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));

		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		Player res = richalphStrat.getPlayerCloseToWinBySixOrMore();
		assertEquals(other, res);

	}

	// getPositionOfPotentialWinner
	@Test
	public void getPositionOfPotentialWinnerBeforeTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));

		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		int res = richalphStrat.getPositionOfPotentialWinner();
		assertEquals(-1, res);
	}

	@Test
	public void getPositionOfPotentialWinnerAfterTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));

		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(player);
		board.getListOfPlayerOrdered().add(other);

		int res = richalphStrat.getPositionOfPotentialWinner();
		assertEquals(1, res);
	}

	@Test
	public void getPositionOfPotentialWinnerDoesntExistrTest() {
		Player other = new Player("Other");
		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		int res = richalphStrat.getPositionOfPotentialWinner();
		assertEquals(0, res);
	}

	// doRichardHasMoreOf6Golds
	@Test
	public void doRichardHasMoreOf6GoldsTrueTest() {
		player.setGolds(7);
		boolean res = richalphStrat.doRichardHasMoreOf6Golds();
		assertEquals(true, res);
	}

	// doRichardHasMoreOf6Golds
	@Test
	public void doRichardHasMoreOf6GoldsFalseTest() {
		player.setGolds(2);
		boolean res = richalphStrat.doRichardHasMoreOf6Golds();
		assertEquals(false, res);
	}

	// doRichardDontPlayFirst
	@Test
	public void doRichardDontPlayFirstTrueTest() {
		Player other = new Player("Other");
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);
		boolean res = richalphStrat.doRichardDontPlayFirst();
		assertEquals(true, res);
	}

	// doRichardDontPlayFirst
	@Test
	public void doRichardDontPlayFirstFalseTest() {
		Player other = new Player("Other");
		board.getListOfPlayerOrdered().add(player);
		board.getListOfPlayerOrdered().add(other);
		boolean res = richalphStrat.doRichardDontPlayFirst();
		assertEquals(false, res);
	}

	// isNumberOfPlayerLess5
	@Test
	public void isNumberOfPlayerLess5TrueTest() {
		Player other = new Player("Other");
		board.getListOfPlayerOrdered().add(player);
		board.getListOfPlayerOrdered().add(other);
		boolean res = richalphStrat.isNumberOfPlayerLess5();
		assertEquals(true, res);
	}

	// isNumberOfPlayerLess5
	@Test
	public void isNumberOfPlayerLess5FalseTest() {
		for (int i = 0; i < 7; i++) {
			board.getListOfPlayerOrdered().add(new Player("" + i));
		}
		Player other = new Player("Other");
		boolean res = richalphStrat.isNumberOfPlayerLess5();
		assertEquals(false, res);
	}

	// isRoundNumberLess5

	@Test
	public void isRoundNumberLess5TrueTest() {
		Player other = new Player("Other");
		boolean res = richalphStrat.isRoundNumberLess5();
		assertEquals(true, res);
	}

	// isNumberOfPlayerLess5
	@Test
	public void isRoundNumberLess5FalseTest() {
		for (int i = 0; i < 7; i++) {
			board.incrementRoundNumber();
		}
		boolean res = richalphStrat.isRoundNumberLess5();
		assertEquals(false, res);
	}
	
	//getPlayerCloseToWin
	@Test
	public void getPlayerCloseToWinTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));
		other.getCity().buildDistrict(new District("test", 6, "test", "test"));


		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		Player res = richalphStrat.getPlayerCloseToWin();
		assertEquals(other, res);

	}
	
	//getDistanceBetweenRichardAndAPlayerCloseToWin
	@Test
	public void getDistanceBetweenRichardAndAPlayerCloseToWinTest() {
		Player other = new Player("Other");
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 1, "test", "test"));
		other.getCity().buildDistrict(new District("test", 2, "test", "test"));
		other.getCity().buildDistrict(new District("test", 3, "test", "test"));
		other.getCity().buildDistrict(new District("test", 4, "test", "test"));
		other.getCity().buildDistrict(new District("test", 5, "test", "test"));
		other.getCity().buildDistrict(new District("test", 6, "test", "test"));


		player.getDistrictCards().clear();
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		int res = richalphStrat.getDistanceBetweenRichardAndAPlayerCloseToWin();
		assertEquals(-1, res);
	}
	
	//getFirstPlayerToPlay
	@Test
	public void getFirstPlayerToPlayTest() {
		Player other = new Player("Other");
		board.getListOfPlayerOrdered().add(other);
		board.getListOfPlayerOrdered().add(player);

		Player res = richalphStrat.getFirstPlayerToPlay();
		assertEquals(other, res);
	}
	

}
