package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
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
	private LinkedHashMap<Character, Bot> hashOfCharacters;
	private ArrayList<Bot> listOfBot;
	private Game game;

	
	public Controller() {
		listOfPlayer = new ArrayList<>();
		listOfAllCharacters = new ArrayList<>();
		listOfBot = new ArrayList<>();
		hashOfCharacters = new LinkedHashMap<>();
		printC = new PrintCitadels();
		initialiser = new Initialiser();
		phaseManager = new PhaseManager();
		game = new Game(hashOfCharacters,listOfPlayer,listOfBot,listOfAllCharacters);
		roundManager = new RoundManager(listOfPlayer, listOfBot, listOfAllCharacters,hashOfCharacters, printC, game);
	}

	public void initGame() {
		initialiser.initAll(hashOfCharacters, listOfAllCharacters, listOfBot, listOfPlayer);
	}

	public void runGame() {
		roundManager.runRounds(phaseManager, initialiser);
		end();
	}

	public void end() {
		game.getWinner();
		printC.printRanking(listOfPlayer);
	}

	public Game getGame() {
		return game;
	}

}
