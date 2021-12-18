package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * The RoundManager manage the rounds inside a Game
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {
	private ArrayList<Behaviour> listOfBehaviour;
	private final ArrayList<Character> listOfAllCharacters;
	private final LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	private final Board board;
	private final Referee referee;

	/**
	 * Instantiate a RoundManager object.
	 * @param listOfAllCharacter The list filled with all the characters.
	 * @param listOfAllBehaviour The list filled with all the behaviours.
	 * @param hashOfCharacter The hash list associating a character with his behaviour.
	 * @param board The game board.
	 */
	public RoundManager(ArrayList<Character> listOfAllCharacter, ArrayList<Behaviour> listOfAllBehaviour,
			LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter, Board board) {
		this.hashOfCharacters = hashOfCharacter;
		this.listOfAllCharacters = listOfAllCharacter;
		this.listOfBehaviour = listOfAllBehaviour;
		this.board = board;
		this.referee = new Referee(board);
	}

	/**
	 * Run a round while the game is not finished.
	 * @return The game leaderBoard.
	 */
	public ArrayList<Behaviour> runRounds() {
		ArrayList<Behaviour> leaderBoard = new ArrayList<>();

		while (leaderBoard.size() == 0) {

			PrintCitadels.printNumberRound(board.getRoundNumber());
			if (board.getRoundNumber() > 0)
				updateListOfBehaviour();

			setupCharacters();
			leaderBoard = askEachCharacterToPlay();

			PrintCitadels.printBoard(board);
			PrintCitadels.printLayer();
			reviveAll();
		}
		return leaderBoard;
	}

	/**
	 * Get the city of each player.
	 * @param listOfPlayer The list of all players.
	 * @return The list of each player's city.
	 */
	public ArrayList<City> getTheListOfCity(ArrayList<Player> listOfPlayer) {
		return listOfPlayer.stream()
				.map(Player::getCity)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Initialise the deck of character then for each behaviour, choose a characterCard.
	 */
	public void setupCharacters() {
		DeckCharacter deckCharacter = board.getDeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacters);

		deckCharacter.deckStartRound(); // Method called to burn a certain number of cards.

		PrintCitadels.printRolePhase();
		listOfBehaviour.forEach(bot -> chooseACharacterCard(bot, deckCharacter));
		PrintCitadels.dropALine();
	}

	/**
	 * Player will choose a character card according to its associated behaviour and the round of the game.
	 * @param bot The given bot to take a character card.
	 * @param deckCharacter The current deck of character to choose a character card from.
	 */
	public void chooseACharacterCard(Behaviour bot, DeckCharacter deckCharacter) {
		Player playerOfBehaviour = bot.getPlayer();

		deckCharacter.checkAndUpdateDeckForLastPlayer();

		if (board.getRoundNumber() == 0)
			playerOfBehaviour.chooseCharacterCard(deckCharacter.chooseRandomCharacter());
		else
			playerOfBehaviour.chooseCharacterCard(bot.chooseCharacterWithStrategy(bot));

		Initializer.fillHashOfCharacter(hashOfCharacters, playerOfBehaviour.getCharacter(), bot);
		PrintCitadels.chooseRole(playerOfBehaviour, playerOfBehaviour.getCharacter());
	}

	/**
	 * According to the Citadels rule, RoundManager will ask a character to play.
	 * If a player own a called character, associated behaviour will play for the player.
	 * @return The game leaderBoard (modified if a player complete its city).
	 */
	public ArrayList<Behaviour> askEachCharacterToPlay() {
		ArrayList<Behaviour> leaderBoard = new ArrayList<>();
		ArrayList<Player> listOfPlayer = getListOfPlayers();
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);

		for (Entry<Character, Optional<Behaviour>> entry : hashOfCharacters.entrySet()) {
			Optional<Behaviour> optionalBehaviour = entry.getValue();
			if (optionalBehaviour.isPresent()) {
				Behaviour currentBehaviour = optionalBehaviour.get();
				PrintCitadels.dropALine();
				PrintCitadels.playerStartTurn(entry.getKey(), currentBehaviour.getPlayer());
				actionOfBehaviour(currentBehaviour);
				cityVerification(currentBehaviour, leaderBoard);
				PrintCitadels.dropALine();
			}
		}
		
		Initializer.resetHashOfCharacter(hashOfCharacters, listOfAllCharacters);
		Initializer.initTheHashOfViewCharacters(board.gethashOfViewCharacters(), listOfPlayer);
		board.resetListOfPlayerWhoPlayed();
		board.incrementRoundNumber();
		return leaderBoard;
	}

	/**
	 * Return the character action according the player behaviour and if the player is not kill.
	 * @param currentBehaviour The behaviour associated to the player.
	 */
	public void actionOfBehaviour(Behaviour currentBehaviour) {
		if (!currentBehaviour.getPlayer().getCharacter().getCharacterisAlive()){
			PrintCitadels.botIsDead(currentBehaviour.getPlayer());
		}
		else {
			Character CharacterOfTheBehaviour = currentBehaviour.play(hashOfCharacters);
			board.revealCharacter(currentBehaviour.getPlayer(), CharacterOfTheBehaviour);
		}
	}

	/**
	 * Check if a player's city is completed.
	 * @param currentBehaviour The given player behaviour to process.
	 * @param leaderBoard Updating the leaderboard if the player complete its city.
	 */
	public void cityVerification(Behaviour currentBehaviour, ArrayList<Behaviour> leaderBoard) {
		boolean aPlayerCompleteCity = referee.CityIsComplete(currentBehaviour.getPlayer());
		if (aPlayerCompleteCity) {
			if(leaderBoard.size() ==0)
				PrintCitadels.printFirstPlayerToComplete(currentBehaviour.getPlayer());
			else
				PrintCitadels.printPlayerToCompleteCity(currentBehaviour.getPlayer());
			updateLeaderboard(currentBehaviour, leaderBoard);
		}
	}

	/**
	 * If the city is completed updateLeaderboard will be called.
	 * updateLeaderboard will add the player that trigger the event (by completing its city) to the leaderboard.
	 * currentPhase will be also changed to the LAST_TURN_PHASE.
	 * @param currentBehaviour The associated player behaviour who have completed its city.
	 * @param leaderBoard The game leaderboard.
	 */
	public void updateLeaderboard(Behaviour currentBehaviour, ArrayList<Behaviour> leaderBoard) {
		leaderBoard.add(currentBehaviour);
	}

	/**
	 * Updating the list of behaviour to let the player's behaviour having the king card to play the first.
	 */
	public void updateListOfBehaviour() {
		int indexOfKing = findKing(listOfBehaviour);
		if (indexOfKing != -1) {
			ArrayList<Behaviour> listOfBehaviourCopy = listOfBehaviour;
			listOfBehaviour = new ArrayList<>();
			listOfBehaviour.addAll(orderListOfPlayer(listOfBehaviourCopy, indexOfKing));
		}
	}

	/**
	 * Check which player has the king character.
	 * @param listOfBehaviour The list of the behaviours.
	 * @return The index on the player list of the player having the king character. -1 if no one has the king card.
	 */
	public int findKing(ArrayList<Behaviour> listOfBehaviour) {
		for (int k = 0; k < listOfBehaviour.size(); k++) {
			if (listOfBehaviour.get(k).getBehaviourIsKing()) {
				return (k);
			}
		}
		return (-1);
	}

	/**
	 * Order properly the player's behaviour order according to their character card (eh. king should be playing the first)
	 * @param listOfBehaviour The list of player's behaviour.
	 * @param positionOfKingHolder The position of the king on the player's behaviour list.
	 * @return The ordered player's behaviour list.
	 */
	public ArrayList<Behaviour> orderListOfPlayer(ArrayList<Behaviour> listOfBehaviour, int positionOfKingHolder) {
		int positionToChange = positionOfKingHolder;
		int sizeListOfPlayer = listOfBehaviour.size();
		ArrayList<Behaviour> listOfBehaviourNextRound = new ArrayList<>();

		for (int i = 0; i < sizeListOfPlayer; i++) {
			if (positionToChange >= sizeListOfPlayer)
				positionToChange = 0;
			listOfBehaviourNextRound.add(listOfBehaviour.get(positionToChange));
			positionToChange++;
		}

		return (listOfBehaviourNextRound);
	}

	/**
	 * Revive all killed players.
	 */
	public void reviveAll() {
		listOfBehaviour.forEach(bot -> bot.getPlayer().getCharacter().setCharacterIsAlive(true));
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfBehaviour.stream().map(Behaviour::getPlayer)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public ArrayList<Behaviour> getListOfBehaviour() {
		return listOfBehaviour;
	}

	public ArrayList<Character> getListOfAllCharacters() {
		return listOfAllCharacters;
	}

	public LinkedHashMap<Character, Optional<Behaviour>> getHashOfCharacters() {
		return hashOfCharacters;
	}

	public Board getBoard() {
		return board;
	}
}