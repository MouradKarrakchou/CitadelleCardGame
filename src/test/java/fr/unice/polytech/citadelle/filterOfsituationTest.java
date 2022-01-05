package fr.unice.polytech.citadelle;

import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Investor;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Richalphonse;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Rusher;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Strategator;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class filterOfsituationTest {
	Player player;
	DeckDistrict deckDistrict;
	Board board;
	Richalphonse richalphonse;
	Rusher rusher;
	Investor investor;
	Strategator strategator;


	@BeforeEach
	public void init() {
		PrintCitadels.activateLevelWarning();

		deckDistrict  = new DeckDistrict();
		deckDistrict.initialise();
		player = new Player("Player");
    	board = new Board(null,new ArrayList<>(),deckDistrict , null);
    	//----create 4 bots
		richalphonse = spy(new Richalphonse( new Player("richalphonse"), board));
		rusher = spy(new Rusher(new Player("rusher"), board));
		investor = spy(new Investor(new Player("investor"), board));
		strategator = spy(new Strategator(new Player("strategator"), board));
		//-----------------
		ArrayList<Player> listOfPlayer = board.getListOfPlayer();
		listOfPlayer.add(richalphonse.getPlayer());
	}
	
	@Test
	public void filterByListOfCharacterPickableTest() {
		
	}
	
	
}
