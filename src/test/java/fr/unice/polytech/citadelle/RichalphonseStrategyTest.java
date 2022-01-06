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
		board = new Board(new ArrayList<>(),new ArrayList<>(),deckDistrict , deckCharacter);
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
		assertEquals(res, other);

	}
	
	
}
