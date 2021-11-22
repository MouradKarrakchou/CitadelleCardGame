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

public class RoundManager {

	ArrayList<Player> listOfPlayer;
	ArrayList<Bot> listOfBot;
	ArrayList<Character> listOfAllCharacters;
	LinkedHashMap<Character, Bot> hashOfCharacters;
	PrintCitadels printC;
	Game game;

	String currentPhase;
	Bot lastKing;

	private static final int BONUS_FIRST = 4;
	private static final int BONUS_END = 2;
	int roundNumber = 0;

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

	public void runRounds(PhaseManager phaseManager, DeckCharacter deckCharacter, Initialiser initialiser,
			DeckDistrict deckDistrict) {
		while ((currentPhase = phaseManager
				.analyseGame(getTheListOfCity(listOfPlayer))) != PhaseManager.LAST_TURN_PHASE) {

			printC.printNumberRound(roundNumber);

			setupCharacters(deckCharacter, initialiser);
			askEachCharacterToPlay(phaseManager, deckDistrict);

			printC.printBoard(game);
			printC.printLayer();
		}
	}

	public ArrayList<City> getTheListOfCity(ArrayList<Player> listOfPlayer) {
		return listOfPlayer.stream().map(p -> p.getCity()).collect(Collectors.toCollection(ArrayList::new));
	}

	private void setupCharacters(DeckCharacter deckCharacter, Initialiser initialiser) {
		deckCharacter.initialise(listOfAllCharacters);
		if (lastKing != null) {
			chooseACharacterCard(lastKing, initialiser, deckCharacter);
			listOfBot.forEach(bot -> {
				if (bot != lastKing)
					chooseACharacterCard(bot, initialiser, deckCharacter);
			});
		} else {
			listOfBot.forEach(bot -> {
				chooseACharacterCard(bot, initialiser, deckCharacter);
			});
		}

		printC.dropALine();
	}

	private void chooseACharacterCard(Bot bot, Initialiser initialiser, DeckCharacter deckCharacter) {
		Player playerOfBot = bot.getPlayer();
		playerOfBot.chooseCharacterCard(deckCharacter.chooseCharacter());
		initialiser.fillHashOfCharacter(hashOfCharacters, playerOfBot.getCharacter(), bot);
		if (playerOfBot.getCharacter() == listOfAllCharacters.get(Initialiser.KING_INDEX))
			lastKing = bot;
		printC.chooseRole(playerOfBot, playerOfBot.getCharacter());
	}

	private void askEachCharacterToPlay(PhaseManager phaseManager, DeckDistrict deckDistrict) {
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
	}

	private boolean actionsOfTheBot(Character character, Bot bot, boolean aBotCompleteHisCity,
			DeckDistrict deckDistrict) {
		character.spellOfBeginningOfRound(bot.getPlayer(), game);
		aBotCompleteHisCity = bot.play(deckDistrict, currentPhase);
		if (aBotCompleteHisCity) {
			addBonusForPlayers(bot.getPlayer(), aBotCompleteHisCity);
			currentPhase = PhaseManager.LAST_TURN_PHASE;
		}
		return aBotCompleteHisCity;
	}

	public void spellOfCharacters() {
		listOfPlayer.forEach(player -> {
			player.getCharacter().spellOfBeginningOfRound(player, game);
		});
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
