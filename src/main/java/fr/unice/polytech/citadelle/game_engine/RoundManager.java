package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.City;
import fr.unice.polytech.citadelle.game.DeckCharacter;
import fr.unice.polytech.citadelle.game.DeckDistrict;
import fr.unice.polytech.citadelle.game.Game;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * The RoundManager manage the rounds inside a Game
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {

	ArrayList<Player> listOfPlayer;
	ArrayList<Bot> listOfBot;
	ArrayList<Character> listOfAllCharacters;
	ArrayList<Character> listOfCharactersInGame;
	LinkedHashMap<Character, Bot> hashOfCharacters;
	PrintCitadels printC;
	Game game;

	String currentPhase;
	Bot lastKing;

	public static final int BONUS_FIRST = 4;
	public static final int BONUS_END = 2;
	int roundNumber = 0;
	//Ancien
	public RoundManager(ArrayList<Player> listOfPlayer, ArrayList<Bot> listOfBot,
						ArrayList<Character> listOfAllCharacters, LinkedHashMap<Character, Bot> hashOfCharacters,
						PrintCitadels printC, Game game) {
		this.listOfPlayer = listOfPlayer;
		this.listOfBot = listOfBot;
		this.listOfAllCharacters = listOfAllCharacters;
		this.hashOfCharacters = hashOfCharacters;
		this.printC = printC;
		this.game = game;
	}

	public RoundManager(ArrayList<Player> listOfPlayer, ArrayList<Bot> listOfBot,
			ArrayList<Character> listOfAllCharacters,ArrayList<Character> listOfCharactersInGame,LinkedHashMap<Character, Bot> hashOfCharacters,
			PrintCitadels printC, Game game) {
		this.listOfPlayer = listOfPlayer;
		this.listOfBot = listOfBot;
		this.listOfAllCharacters = listOfAllCharacters;
		this.hashOfCharacters = hashOfCharacters;
		this.printC = printC;
		this.game = game;
		this.listOfCharactersInGame=listOfCharactersInGame;
	}

	public void runRounds(PhaseManager phaseManager, DeckCharacter deckCharacter, Initialiser initialiser,
			DeckDistrict deckDistrict) {
		while ((currentPhase = phaseManager
				.analyseGame(getTheListOfCity(listOfPlayer))) != PhaseManager.LAST_TURN_PHASE) {

			printC.printNumberRound(roundNumber);
			game.updateListOfBot();
			listOfCharactersInGame.clear();

			setupCharacters(deckCharacter, initialiser);
			askEachCharacterToPlay(phaseManager, deckDistrict, initialiser);

			printC.printBoard(game);
			printC.printLayer();
		}
	}

	public ArrayList<City> getTheListOfCity(ArrayList<Player> listOfPlayer) {
		return listOfPlayer.stream().map(p -> p.getCity()).collect(Collectors.toCollection(ArrayList::new));
	}

	private void setupCharacters(DeckCharacter deckCharacter, Initialiser initialiser) {
		deckCharacter.initialise(listOfAllCharacters);
		listOfBot.forEach(bot -> chooseACharacterCard(bot, initialiser, deckCharacter));
		printC.dropALine();
	}

	private void chooseACharacterCard(Bot bot, Initialiser initialiser, DeckCharacter deckCharacter) {
		Player playerOfBot = bot.getPlayer();
		playerOfBot.chooseCharacterCard(deckCharacter.chooseCharacter());
		listOfCharactersInGame.add(playerOfBot.getCharacter());
		initialiser.fillHashOfCharacter(hashOfCharacters, playerOfBot.getCharacter(), bot);
		printC.chooseRole(playerOfBot, playerOfBot.getCharacter());
	}

	private void askEachCharacterToPlay(PhaseManager phaseManager, DeckDistrict deckDistrict, Initialiser initialiser) {
		boolean aBotCompleteHisCity = false;
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);
		currentPhase = phaseManager.analyseGame(listOfCity);

		for (Entry<Character, Bot> entry : hashOfCharacters.entrySet()) {
			Character character = entry.getKey();
			Bot bot = entry.getValue();
			if (bot.getPlayer().getName() != "fillPlayer") {
				aBotCompleteHisCity = actionsOfTheBot(character, bot, aBotCompleteHisCity, deckDistrict);
			}
		}
		roundNumber++;
		initialiser.initHashOfCharacter(hashOfCharacters, listOfAllCharacters);
	}

	private boolean actionsOfTheBot(Character character, Bot bot, boolean aBotCompleteHisCity, DeckDistrict deckDistrict)
	{
		aBotCompleteHisCity = bot.play(deckDistrict, currentPhase,game);
		if (aBotCompleteHisCity) {
			addBonusForPlayers(bot.getPlayer(), aBotCompleteHisCity);
			currentPhase = PhaseManager.LAST_TURN_PHASE;
		}
		return aBotCompleteHisCity;
	}

	public boolean addBonusForPlayers(Player player, boolean isLastRound) {
		if (!isLastRound) {
			isLastRound = true;
			printC.printFirstPlayerToComplete(player);
			player.updateScore(BONUS_FIRST);
		} else
			player.updateScore(BONUS_END);
		printC.printPlayerToCompleteCity(player);
		return isLastRound;
	}
}
