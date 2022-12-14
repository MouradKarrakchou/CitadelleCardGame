package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;


/**
 * The controller coordinates all classes in the project
 *
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 *
 */
public class Controller {
	private RoundManager roundManager;
	private Referee referee;

	/**
	 * Controller constructor, setting-up the printer class, the Initializer class and the PhaseManager class
	 *
	 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
	 */
	public Controller() {}

	/**
	 * Method called once to initialize the game.
	 * Initialize :
	 * - The deck of characters
	 * - The deck of districts
	 * - The game referee
	 * - The game board
	 * @param numberOfRichalphonse Number of Richalphonse bot
	 * @param numberOfInvestor Number of Investor bot
	 * @param numberOfRusher Number of Rusher bot
	 * @param numberOfStrategator Number of Strategator bot
	 */
	public void initGame(int numberOfRichalphonse, int numberOfInvestor, int numberOfRusher, int numberOfStrategator) {
		ArrayList<Behaviour> listOfAllBehaviour;
		LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter = new LinkedHashMap<>();
		ArrayList<Character> listOfAllCharacter = Initializer.createListOfAllCharacter();

		int nbPlayer = numberOfInvestor + numberOfRichalphonse + numberOfRusher + numberOfStrategator;
		Board board = Initializer.createBoard(listOfAllCharacter, nbPlayer);
		referee = new Referee(board);
		listOfAllBehaviour = Initializer.createListOfBehaviour(board, numberOfRichalphonse, numberOfInvestor, numberOfRusher, numberOfStrategator);

		Initializer.initDeckCharacter(board.getDeckCharacter(), listOfAllCharacter);
		Initializer.resetHashOfCharacter(hashOfCharacter, listOfAllCharacter);

		roundManager = new RoundManager(listOfAllCharacter, listOfAllBehaviour,hashOfCharacter, board);
		
		Initializer.initDeckCharacter(roundManager.getBoard().getDeckCharacter(), listOfAllCharacter);
		Initializer.initDeckDistrict(roundManager.getBoard().getDeckDistrict());

		board.setListOfPlayer(roundManager.getListOfPlayers());
		board.setListOfPlayerOrdered((ArrayList<Player>) roundManager.getListOfPlayers().clone());


		Initializer.initPlayerCards(roundManager.getBoard());
	}

	/**
	 * Method called once to start the game after game initialization.
	 */
	public ArrayList<Player> runGame() {
		ArrayList<Behaviour> leaderBoard;
		PrintCitadels.startCitadelles();
		leaderBoard = roundManager.runRounds();
		return end(leaderBoard);
	}

	/**
	 * Method called once when the game is finished. Allows the referee to calculate the game result.
	 */
	public ArrayList<Player> end(ArrayList<Behaviour> leaderBoard) {
		referee.addBonusForPlayers(leaderBoard);
		referee.getWinner();
		PrintCitadels.printRanking(roundManager.getBoard().getListOfPlayer());
		return roundManager.getBoard().getListOfPlayer();
	}

}
