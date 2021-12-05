package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;


/**
 * The controller coordinates all classes in the project
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 *
 */
public class Controller {

	private PrintCitadels printC;
	private Initialiser initialiser;
	private RoundManager roundManager;
	private PhaseManager phaseManager;
	private Referee referee;

	
	public Controller() {
		printC = new PrintCitadels();
		initialiser = new Initialiser();
		phaseManager = new PhaseManager();			
	}
	public void initGame() {
		LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter = new LinkedHashMap<Character, Optional<Behaviour>>();
		ArrayList<Character> listOfAllCharacter = initialiser.createListOfAllCharacter();
		ArrayList<Behaviour> listOfAllBehaviour = initialiser.createListOfBehaviour();
		Board board = initialiser.createBoard(listOfAllCharacter);
		initialiser.initDeckCharacter(board.getDeckCharacter(), listOfAllCharacter);
		hashOfCharacter = initialiser.resetHashOfCharacter(hashOfCharacter, listOfAllCharacter);
		roundManager = new RoundManager(listOfAllCharacter, listOfAllBehaviour,hashOfCharacter, board);
		referee = new Referee(board);
		
		initialiser.initDeckCharacter(roundManager.getBoard().getDeckCharacter(), listOfAllCharacter);
		initialiser.initDeckDistrict(roundManager.getBoard().getDeckDistrict());
		
		board.setListOfPlayer(roundManager.getListOfPlayers());

	}

	public void init() {
		LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter = roundManager.getHashOfCharacters();
		ArrayList<Behaviour> listOfBehaviour = roundManager.getListOfBehaviour();
		ArrayList<Player> listOfPlayer = roundManager.getBoard().getListOfPlayer();
		ArrayList<Character> listOfAllCharacters = roundManager.getListOfAllCharacters();

		initialiser.initDeckCharacter(roundManager.getBoard().getDeckCharacter(), listOfAllCharacters);
		initialiser.initDeckDistrict(roundManager.getBoard().getDeckDistrict());
	}

	public void runGame() {
		ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();
		leaderBoard = roundManager.runRounds(phaseManager, initialiser);
		end(leaderBoard);
	}

	public void end(ArrayList<Behaviour> leaderBoard) {
		referee.addBonusForPlayers(leaderBoard);
		referee.getWinner();
		printC.printRanking(roundManager.getBoard().getListOfPlayer());
	}

}
