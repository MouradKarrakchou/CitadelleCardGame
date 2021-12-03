package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.Character;
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
		roundManager = new RoundManager();
		referee = new Referee(roundManager.getBoard());
	}

	public void initGame() {
		LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter = roundManager.getHashOfCharacters();
		ArrayList<Behaviour> listOfBehaviour = roundManager.getListOfBehaviour();
		ArrayList<Player> listOfPlayer = roundManager.getBoard().getListOfPlayer();
		ArrayList<Character> listOfAllCharacters = roundManager.getListOfAllCharacters();

		initialiser.initAll(hashOfCharacter, listOfAllCharacters, listOfPlayer, listOfBehaviour);
		initialiser.initDeckCharacter(roundManager.getBoard().getDeckCharacter(), listOfAllCharacters);
		initialiser.initDeckDistrict(roundManager.getBoard().getDeckDistrict());
	}

	public void runGame() {
		roundManager.runRounds(phaseManager, initialiser);
		end();
	}

	public void end() {
		referee.getWinner();
		printC.printRanking(roundManager.getBoard().getListOfPlayer());
	}

}
