package fr.unice.polytech.citadelle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
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
	public void getListOfRichardCharacterNotPickableTest() {
		/*ArrayList<Character> deckCharacterList = board.getDeckCharacter().getDeckCharacter();
		ArrayList<Character> notPickable = new ArrayList<>();

		int indexOfKing = 3;
		Character king = deckCharacterList.get(indexOfKing);
		notPickable.add(king);
		deckCharacterList.remove(board.findCharacter("king"));
		ArrayList<Character> res = richalphStrat.();
		assertEquals(notPickable.size(), res.size());
		assertEquals(notPickable.get(0), res.get(0));*/

	}
	
	
}
