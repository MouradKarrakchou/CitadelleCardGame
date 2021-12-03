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
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {

	DeckDistrict deckDistrict;
	DeckCharacter deckCharacter;
	ArrayList<Behaviour> listOfBehaviour;
	ArrayList<Character> listOfAllCharacters;
	LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters;
	PrintCitadels printC;
	Board board;



	String currentPhase;

	public static final int BONUS_FIRST = 4;
	public static final int BONUS_END = 2;
	int roundNumber = 0;

	public RoundManager(ArrayList<Player> listOfPlayer, ArrayList<Behaviour> listOfBehaviour,
			ArrayList<Character> listOfAllCharacters,LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters,
			PrintCitadels printC, Board board) {
		this.listOfBehaviour = listOfBehaviour;
		this.listOfAllCharacters = listOfAllCharacters;
		this.hashOfCharacters = hashOfCharacters;
		this.printC = printC;
		this.board = board;
		this.deckCharacter = new DeckCharacter();
		this.deckDistrict = new DeckDistrict();

	}

	public void runRounds(PhaseManager phaseManager, Initialiser initialiser) {
		
		while ((currentPhase = phaseManager
				.analyseGame(getTheListOfCity(getListOfPlayers()))) != PhaseManager.LAST_TURN_PHASE) {

			printC.printNumberRound(roundNumber);
			updateListOfBehaviour();

			setupCharacters(deckCharacter, initialiser);
			askEachCharacterToPlay(phaseManager, deckDistrict, initialiser);

			printC.printBoard(board);
			printC.printLayer();
			reviveAll();
		}
	}

	public ArrayList<City> getTheListOfCity(ArrayList<Player> listOfPlayer) {
		return listOfPlayer.stream().map(p -> p.getCity()).collect(Collectors.toCollection(ArrayList::new));
	}

	public void setupCharacters(DeckCharacter deckCharacter, Initialiser initialiser) {
		deckCharacter.initialise(listOfAllCharacters);
		listOfBehaviour.forEach(bot -> chooseACharacterCard(bot, initialiser, deckCharacter));
		printC.dropALine();
	}

	public void chooseACharacterCard(Behaviour bot, Initialiser initialiser, DeckCharacter deckCharacter) {
		Player playerOfBehaviour = bot.getPlayer();
		if (roundNumber == 0) playerOfBehaviour.chooseCharacterCard(deckCharacter.chooseRandomCharacter());
		else playerOfBehaviour.chooseCharacterCard(chooseCharacter(bot, deckCharacter));
		initialiser.fillHashOfCharacter(hashOfCharacters, playerOfBehaviour.getCharacter(), bot);
		printC.chooseRole(playerOfBehaviour, playerOfBehaviour.getCharacter());
	}


	//  !!! NE PAS EFFACER CES COMMENTAIREs !!!
	//assassin : s'il y a un joueur proche de la fin (préférable de tuer l'architecte)
	//voleur : s'il y a un joueur avec beaucoup de golds
	//roi : si 3 quartiers noble construits
	//marchand : si 3 quartier commerce construits
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

	int isThereAFamily(Behaviour bot) {
		Random random = new Random();
		Player playerOfBehaviour = bot.getPlayer();
		ArrayList<District> districtsInACity;
		ArrayList<String> nameOfFamilies = new ArrayList<>();

		nameOfFamilies.add("Nobility");
		nameOfFamilies.add("Trade and Handicrafts");

		for (String familyName : nameOfFamilies) {
			districtsInACity = playerOfBehaviour.getCity().getBuiltDistrict();
			districtsInACity.stream().filter(district -> district.getNameOfFamily().equals(familyName));
			if (familyName.equals("Nobility") && districtsInACity.size() == 3) return Initialiser.KING_INDEX;
			else if (familyName.equals("Trade and Handicrafts") && districtsInACity.size() >= 3)
			return Initialiser.MERCHANT_INDEX;
		}

		return random.nextInt(deckCharacter.getDeckCharacter().size());
	}


	public void askEachCharacterToPlay(PhaseManager phaseManager, DeckDistrict deckDistrict, Initialiser initialiser) {
		boolean aBehaviourCompleteHisCity = false;
		ArrayList<Player> listOfPlayer = getListOfPlayers();
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);
		currentPhase = phaseManager.analyseGame(listOfCity);

		for (Entry<Character, Optional<Behaviour>> entry : hashOfCharacters.entrySet()) {
			Character character = entry.getKey();
			Optional<Behaviour> bot = entry.getValue();
			if (bot.isPresent()) {
				aBehaviourCompleteHisCity = actionsOfTheBehaviour(character, bot.get(), aBehaviourCompleteHisCity, deckDistrict);
			}
		}
		roundNumber++;
		initialiser.initHashOfCharacter(hashOfCharacters, listOfAllCharacters);
	}

	public boolean actionsOfTheBehaviour(Character character, Behaviour bot, boolean aBehaviourCompleteHisCity, DeckDistrict deckDistrict){
		aBehaviourCompleteHisCity = bot.play(deckDistrict, currentPhase,hashOfCharacters);
		if (aBehaviourCompleteHisCity) {
			//addBonusForPlayers(bot.getPlayer(), aBehaviourCompleteHisCity);
			currentPhase = PhaseManager.LAST_TURN_PHASE;
		}
		return aBehaviourCompleteHisCity;
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

	public void updateListOfBehaviour() {
			int indexOfKing=findKing(listOfBehaviour);
			if (indexOfKing!=-1){
				ArrayList<Behaviour> listOfBehaviourCopy=listOfBehaviour;
				listOfBehaviour=new ArrayList<>();
				listOfBehaviour.addAll(orderListOfPlayer(listOfBehaviourCopy,indexOfKing));}
	}
	public int findKing(ArrayList<Behaviour> listOfBehaviour){
		for (int k=0;k<listOfBehaviour.size();k++){
			if (listOfBehaviour.get(k).getBotIsKing()) {
				listOfBehaviour.get(k).setBotIsKing(false);
				return(k);}}
		return(-1);
	}
	public ArrayList<Behaviour> orderListOfPlayer(ArrayList<Behaviour> listOfBehaviour, int positionOfKingHolder){
		int positionToChange=positionOfKingHolder;
		int sizeListOfPlayer=listOfBehaviour.size();
		ArrayList<Behaviour> listOfBehaviourNextRound=new ArrayList<>();
		for (int i=0;i<sizeListOfPlayer;i++){
			if (positionToChange>=sizeListOfPlayer) positionToChange=0;
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
		return (ArrayList<Player>) listOfBehaviour.stream().
				map(bot -> bot.getPlayer()).
				collect(Collectors.toCollection(ArrayList::new));
	}
}
