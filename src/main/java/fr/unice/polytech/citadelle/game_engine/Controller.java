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
	private ArrayList<Player> listOfPlayer;

	private ArrayList<Character> listOfAllCharacters;
	private LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	private ArrayList<Behaviour> listOfBehaviour;
	private Referee referee;

	
	public Controller() {
		listOfPlayer = new ArrayList<>();
		listOfAllCharacters = new ArrayList<>();
		listOfBehaviour = new ArrayList<>();
		hashOfCharacters = new LinkedHashMap<>();
		printC = new PrintCitadels();
		initialiser = new Initialiser();
		phaseManager = new PhaseManager();
		Board board = new Board(listOfPlayer,listOfBehaviour);
		roundManager = new RoundManager(listOfPlayer, listOfBehaviour, listOfAllCharacters,hashOfCharacters, printC, board);
		referee=new Referee(listOfPlayer);
	}

	public void initGame() {
		initialiser.initAll(hashOfCharacters, listOfAllCharacters, listOfBehaviour, listOfPlayer);
	}

	public void runGame() {
		roundManager.runRounds(phaseManager, initialiser);
		end();
	}

	public void end() {
		referee.getWinner();
		printC.printRanking(listOfPlayer);
	}

}
