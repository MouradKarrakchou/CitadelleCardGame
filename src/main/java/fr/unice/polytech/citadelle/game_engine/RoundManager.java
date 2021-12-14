package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;


/**
 * The RoundManager manage the rounds inside a Game
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {
	private ArrayList<Behaviour> listOfBehaviour;
	private final ArrayList<Character> listOfAllCharacters;
	private final LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	private final Board board;

	private final PrintCitadels printC;
	private final Referee referee;

	private String currentPhase;

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
		this.printC = new PrintCitadels();
		this.referee = new Referee(board);
	}

	/**
	 * Run a round while the game is not finished.
	 * @param phaseManager Class used to analyze the Game to deduct a particular phase.
	 * @return The game leaderBoard.
	 */
	public ArrayList<Behaviour> runRounds(PhaseManager phaseManager) {
		ArrayList<Behaviour> leaderBoard = new ArrayList<>();

		while ((currentPhase = phaseManager.analyseGame(getTheListOfCity(getListOfPlayers())))
				!= PhaseManager.LAST_TURN_PHASE) {

			printC.printNumberRound(board.getRoundNumber());
			if (board.getRoundNumber() > 0)
				updateListOfBehaviour();

			setupCharacters();
			leaderBoard = askEachCharacterToPlay(phaseManager);

			printC.printBoard(board);
			printC.printLayer();
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
		printC.printRolePhase();
		DeckCharacter deckCharacter = board.getDeckCharacter();
		Initializer.initDeckCharacter(deckCharacter, listOfAllCharacters);
		listOfBehaviour.forEach(bot -> chooseACharacterCard(bot, deckCharacter));
		printC.dropALine();
	}

	/**
	 * Player will choose a character card according to its associated behaviour and the round of the game.
	 * @param bot The given bot to take a character card.
	 * @param deckCharacter The current deck of character to choose a character card from.
	 */
	public void chooseACharacterCard(Behaviour bot, DeckCharacter deckCharacter) {
		Player playerOfBehaviour = bot.getPlayer();

		if (board.getRoundNumber() == 0)
			playerOfBehaviour.chooseCharacterCard(deckCharacter.chooseRandomCharacter());
		else
			playerOfBehaviour.chooseCharacterCard(chooseCharacter(bot, deckCharacter));

		Initializer.fillHashOfCharacter(hashOfCharacters, playerOfBehaviour.getCharacter(), bot);
		printC.chooseRole(playerOfBehaviour, playerOfBehaviour.getCharacter());
	}


	public Character chooseCharacter(Behaviour bot, DeckCharacter deckCharacter) {
		int counter;

		//Choice of Assassin
		ArrayList<Character> listOfAssassin = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Assassin"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;
		for(Player player : board.getListOfPlayer()) {
			if(player.getCity().getBuiltDistrict().size() >= 6 && listOfAssassin.size() != 0) {
				for(Character character : deckCharacter.getDeckCharacter()) {
					if(character.getName().equals("Assassin")) return deckCharacter.getDeckCharacter().remove(counter);
					counter++;
				}
			}
		}

		//Choice of Architect
		ArrayList<Character> listOfArchitect = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Architect"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;

		if(bot.getPlayer().getDistrictCardsSize() >= 3 && listOfArchitect.size() != 0) {
			for(Character character : deckCharacter.getDeckCharacter()) {
				if(character.getName().equals("Architect")) return deckCharacter.getDeckCharacter().remove(counter);
				counter++;
			}
		}

		//Choice of Magician
		ArrayList<Character> listOfMagician = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Magician"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;
		for(Player player : board.getListOfPlayer()) {
			if(bot.getPlayer().getDistrictCardsSize() < player.getDistrictCardsSize() && listOfMagician.size() != 0) {
				for(Character character : deckCharacter.getDeckCharacter()) {
					if(character.getName().equals("Magician")) return deckCharacter.getDeckCharacter().remove(counter);
					counter++;
				}
			}
		}

		//Choice of Thief !!!VERIFIE TOUS LES PLAYERS MEMES CELUI QUI VEUT CHOISIR SONT PERSO (PAS LOGIQUE : EX CELUI QUI A DES GOLDS)!!!
		ArrayList<Character> listOfThief = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Thief"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;
		for(Player player : board.getListOfPlayer()) {
			if(player.getGolds() >= 5 && listOfThief.size() != 0) {
				for(Character character : deckCharacter.getDeckCharacter()) {
					if(character.getName().equals("Thief")) return deckCharacter.getDeckCharacter().remove(counter);
					counter++;
				}
			}
		}

		//Choice of King or Merchant (if they are both equality worth, King is chosen)
		counter = 0;
		String nameOfCharacterChosen = listOfAllCharacters.get(isThereAFamily(bot)).getName();
		for (Character character : deckCharacter.getDeckCharacter()) {
			if (character.getName().equals(nameOfCharacterChosen))
				return deckCharacter.getDeckCharacter().remove(counter);
			counter++;
		}

		//Choice of Bishop
		ArrayList<Character> listOfBishop = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Bishop"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;

		if(bot.getPlayer().getCity().getBuiltDistrict().size() >= 6 && listOfBishop.size() != 0) {
			for(Character character : deckCharacter.getDeckCharacter()) {
				if(character.getName().equals("Bishop")) return deckCharacter.getDeckCharacter().remove(counter);
				counter++;
			}
		}

		//Choice of Warlord (Last one because his spell has not been implemented yet)
		ArrayList<Character> listOfWarlord = deckCharacter.getDeckCharacter().stream()
				.filter(character -> character.getName().equals("Warlord"))
				.collect(Collectors.toCollection(ArrayList::new));
		counter = 0;

		for(Player player : board.getListOfPlayer()) {
			if(player.getCity().getBuiltDistrict().size() >= 6 && listOfWarlord.size() != 0) {
				for(Character character : deckCharacter.getDeckCharacter()) {
					if(character.getName().equals("Architect")) return deckCharacter.getDeckCharacter().remove(counter);
					counter++;
				}
			}
		}

		return deckCharacter.getDeckCharacter().remove(0);
	}

	/**
	 * Check if the player of the given bot has a family in its city.
	 * @param bot The given bot to check.
	 * @return the integer index value of the family owned by the bot. Return a random family if bot don't own any family.
	 */
	public int isThereAFamily(Behaviour bot) {
		Random random = new Random();
		Player playerOfBehaviour = bot.getPlayer();
		ArrayList<District> districtsInACity;
		ArrayList<String> nameOfFamilies = new ArrayList<>();

		nameOfFamilies.add("Nobility");
		nameOfFamilies.add("Trade and Handicrafts");

		for (String familyName : nameOfFamilies) {
			districtsInACity = playerOfBehaviour.getCity().getBuiltDistrict();

			ArrayList<District> districtFilter = districtsInACity.stream()
					.filter(district -> district.getNameOfFamily().equals(familyName))
					.collect(Collectors.toCollection(ArrayList::new));

			if (familyName.equals("Nobility") && districtFilter.size() == 3)
				return Initializer.KING_INDEX;
			else if (familyName.equals("Trade and Handicrafts") && districtFilter.size() >= 3)
				return Initializer.MERCHANT_INDEX;
		}
		return random.nextInt(board.getDeckCharacter().getSize());
	}

	/**
	 * According to the Citadels rule, RoundManager will ask a character to play.
	 * If a player own a called character, associated behaviour will play for the player.
	 * @param phaseManager Class used to analyze the Game to deduct a particular phase.
	 * @return The game leaderBoard (modified if a player complete its city).
	 */
	public ArrayList<Behaviour> askEachCharacterToPlay(PhaseManager phaseManager) {
		ArrayList<Behaviour> leaderBoard = new ArrayList<>();
		ArrayList<Player> listOfPlayer = getListOfPlayers();
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);
		currentPhase = phaseManager.analyseGame(listOfCity);

		for (Entry<Character, Optional<Behaviour>> entry : hashOfCharacters.entrySet()) {
			Optional<Behaviour> optionalBehaviour = entry.getValue();
			if (optionalBehaviour.isPresent()) {
				Behaviour currentBehaviour = optionalBehaviour.get();
				printC.dropALine();
				printC.playerStartTurn(entry.getKey(), currentBehaviour.getPlayer());
				actionOfBehaviour(currentBehaviour);
				cityVerification(currentBehaviour, leaderBoard);
				printC.dropALine();
			}
		}
		
		Initializer.resetHashOfCharacter(hashOfCharacters, listOfAllCharacters);
		board.incrementRoundNumber();
		return leaderBoard;
	}

	/**
	 * Return the character action according the player behaviour and if the player is not kill.
	 * @param currentBehaviour The behaviour associated to the player.
	 */
	public void actionOfBehaviour(Behaviour currentBehaviour) {
		if (!currentBehaviour.getPlayer().getCharacter().isCharacterIsAlive()){
			printC.botIsDead(currentBehaviour.getPlayer());
		}
		else
		currentBehaviour.play(currentPhase, hashOfCharacters);
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
				printC.printFirstPlayerToComplete(currentBehaviour.getPlayer());
			else 
				printC.printPlayerToCompleteCity(currentBehaviour.getPlayer());
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
		currentPhase = PhaseManager.LAST_TURN_PHASE;
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