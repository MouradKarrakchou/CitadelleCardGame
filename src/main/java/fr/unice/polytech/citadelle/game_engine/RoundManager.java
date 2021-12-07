package fr.unice.polytech.citadelle.game_engine;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * The RoundManager manage the rounds inside a Game
 * 
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {

	private ArrayList<Behaviour> listOfBehaviour;
	private ArrayList<Character> listOfAllCharacters;
	private LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	private Board board;

	private PrintCitadels printC;
	private Initialiser initialiser;
	private Referee referee;

	private String currentPhase;
	private int roundNumber = 0;

	public RoundManager(ArrayList<Character> listOfAllCharacter, ArrayList<Behaviour> listOfAllBehaviour,
			LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacter, Board board) {
		this.hashOfCharacters = hashOfCharacter;
		this.listOfAllCharacters = listOfAllCharacter;
		this.listOfBehaviour = listOfAllBehaviour;
		this.board = board;
		this.printC = new PrintCitadels();
		this.initialiser = new Initialiser();
		this.referee = new Referee(board);
	}

	public ArrayList<Behaviour> runRounds(PhaseManager phaseManager, Initialiser initialiser) {
		ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();

		while ((currentPhase = phaseManager
				.analyseGame(getTheListOfCity(getListOfPlayers()))) != PhaseManager.LAST_TURN_PHASE) {

			printC.printNumberRound(roundNumber);
			updateListOfBehaviour();

			setupCharacters(initialiser);
			leaderBoard = askEachCharacterToPlay(phaseManager, board.getDeckDistrict(), initialiser);

			printC.printBoard(board);
			printC.printLayer();
			reviveAll();
		}
		return leaderBoard;
	}

	public ArrayList<City> getTheListOfCity(ArrayList<Player> listOfPlayer) {
		return listOfPlayer.stream().map(p -> p.getCity()).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Initialise the deck of character then for each behaviour, choose a
	 * characterCard
	 * 
	 * @param initialiser
	 */
	public void setupCharacters(Initialiser initialiser) {
		DeckCharacter deckCharacter = board.getDeckCharacter();
		initialiser.initDeckCharacter(deckCharacter, listOfAllCharacters);
		listOfBehaviour.forEach(bot -> chooseACharacterCard(bot, initialiser, deckCharacter));
		printC.dropALine();
	}

	public void chooseACharacterCard(Behaviour bot, Initialiser initialiser, DeckCharacter deckCharacter) {
		Player playerOfBehaviour = bot.getPlayer();
		if (roundNumber == 0)
			playerOfBehaviour.chooseCharacterCard(deckCharacter.chooseRandomCharacter());
		else
			playerOfBehaviour.chooseCharacterCard(chooseCharacter(bot, deckCharacter));
		initialiser.fillHashOfCharacter(hashOfCharacters, playerOfBehaviour.getCharacter(), bot);
		printC.chooseRole(playerOfBehaviour, playerOfBehaviour.getCharacter());
	}

	// !!! NE PAS EFFACER CES COMMENTAIREs !!!
	// assassin : s'il y a un joueur proche de la fin (préférable de tuer
	// l'architecte)
	// voleur : s'il y a un joueur avec beaucoup de golds
	// roi : si 3 quartiers noble construits
	// marchand : si 3 quartier commerce construits
	public Character chooseCharacter(Behaviour bot, DeckCharacter deckCharacter) {
		int counter = 0;
		String nameOfCharacterChosen = listOfAllCharacters.get(isThereAFamily(bot)).getName();
		for (Character character : deckCharacter.getDeckCharacter()) {
			if (character.getName().equals(nameOfCharacterChosen))
				return deckCharacter.getDeckCharacter().remove(counter);
			counter++;
		}
		return deckCharacter.getDeckCharacter().remove(0);
	}

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
				return Initialiser.KING_INDEX;
			else if (familyName.equals("Trade and Handicrafts") && districtFilter.size() >= 3)
				return Initialiser.MERCHANT_INDEX;
		}

		return random.nextInt(board.getDeckCharacter().getSize());
	}

	public ArrayList<Behaviour> askEachCharacterToPlay(PhaseManager phaseManager, DeckDistrict deckDistrict,
			Initialiser initialiser) {
		ArrayList<Behaviour> leaderBoard = new ArrayList<Behaviour>();
		ArrayList<Player> listOfPlayer = getListOfPlayers();
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);
		currentPhase = phaseManager.analyseGame(listOfCity);

		for (Entry<Character, Optional<Behaviour>> entry : hashOfCharacters.entrySet()) {
			Optional<Behaviour> optionalBehaviour = entry.getValue();
			if (optionalBehaviour.isPresent()) {
				Behaviour currentBehaviour = optionalBehaviour.get();
				actionOfBehaviour(currentBehaviour, deckDistrict);
				cityVerification(currentBehaviour, leaderBoard);		
			}
		}
		
		initialiser.resetHashOfCharacter(hashOfCharacters, listOfAllCharacters);
		roundNumber++;
		return leaderBoard;
	}

	public void actionOfBehaviour(Behaviour currentBehaviour, DeckDistrict deckDistrict) {
		
		if (currentBehaviour.getPlayer().getCharacter().isCharacterIsAlive())
			currentBehaviour.play(currentPhase, hashOfCharacters);
		else
			printC.botIsDead(currentBehaviour.getPlayer());
	}

	private void cityVerification(Behaviour currentBehaviour, ArrayList<Behaviour> leaderBoard) {
		boolean aPlayerCompleteCity = referee.CityIsComplete(currentBehaviour.getPlayer());
		if (aPlayerCompleteCity) {
			if(leaderBoard.size() ==0)
				printC.printFirstPlayerToComplete(currentBehaviour.getPlayer());
			else 
				printC.printPlayerToCompleteCity(currentBehaviour.getPlayer());
			updateLeaderboard(currentBehaviour, leaderBoard);
		}
	}

	public  ArrayList<Behaviour> updateLeaderboard(Behaviour currentBehaviour, ArrayList<Behaviour> leaderBoard) {
		leaderBoard.add(currentBehaviour);
		currentPhase = PhaseManager.LAST_TURN_PHASE;
		return leaderBoard;
	}

	public void updateListOfBehaviour() {
		int indexOfKing = findKing(listOfBehaviour);
		if (indexOfKing != -1) {
			ArrayList<Behaviour> listOfBehaviourCopy = listOfBehaviour;
			listOfBehaviour = new ArrayList<>();
			listOfBehaviour.addAll(orderListOfPlayer(listOfBehaviourCopy, indexOfKing));
		}
	}

	public int findKing(ArrayList<Behaviour> listOfBehaviour) {
		for (int k = 0; k < listOfBehaviour.size(); k++) {
			if (listOfBehaviour.get(k).getBehaviourIsKing()) {
				listOfBehaviour.get(k).setBehaviourIsKing(false);
				return (k);
			}
		}
		return (-1);
	}

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

	public int getRoundNumber() {
		return roundNumber;
	}

	public void reviveAll() {
		listOfBehaviour.forEach(bot -> bot.getPlayer().getCharacter().setCharacterIsAlive(true));
	}

	public ArrayList<Player> getListOfPlayers() {
		return (ArrayList<Player>) listOfBehaviour.stream().map(bot -> bot.getPlayer())
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
