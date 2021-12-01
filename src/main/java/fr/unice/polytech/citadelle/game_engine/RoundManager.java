package fr.unice.polytech.citadelle.game_engine;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * The RoundManager manage the rounds inside a Game
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class RoundManager {

	DeckDistrict deckDistrict;
	DeckCharacter deckCharacter;
	ArrayList<Bot> listOfBot;
	ArrayList<Character> listOfAllCharacters;
	LinkedHashMap<Character, Optional<Bot>> hashOfCharacters;
	PrintCitadels printC;
	Board board;



	String currentPhase;

	public static final int BONUS_FIRST = 4;
	public static final int BONUS_END = 2;
	int roundNumber = 0;

	public RoundManager(ArrayList<Player> listOfPlayer, ArrayList<Bot> listOfBot,
			ArrayList<Character> listOfAllCharacters,LinkedHashMap<Character, Optional<Bot>> hashOfCharacters,
			PrintCitadels printC, Board board) {
		this.listOfBot = listOfBot;
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
			updateListOfBot();

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
		listOfBot.forEach(bot -> chooseACharacterCard(bot, initialiser, deckCharacter));
		printC.dropALine();
	}

	public void chooseACharacterCard(Bot bot, Initialiser initialiser, DeckCharacter deckCharacter) {
		Player playerOfBot = bot.getPlayer();
		if (roundNumber == 0) playerOfBot.chooseCharacterCard(deckCharacter.chooseRandomCharacter());
		else playerOfBot.chooseCharacterCard(chooseCharacter(bot, deckCharacter));
		initialiser.fillHashOfCharacter(hashOfCharacters, playerOfBot.getCharacter(), bot);
		printC.chooseRole(playerOfBot, playerOfBot.getCharacter());
	}


	//  !!! NE PAS EFFACER CES COMMENTAIREs !!!
	//assassin : s'il y a un joueur proche de la fin (préférable de tuer l'architecte)
	//voleur : s'il y a un joueur avec beaucoup de golds
	//roi : si 3 quartiers noble construits
	//marchand : si 3 quartier commerce construits
	public Character chooseCharacter(Bot bot, DeckCharacter deckCharacter) {
		Random random = new Random();
		Player playerOfBot = bot.getPlayer();
		ArrayList<District> districtsInACity = playerOfBot.getCity().getBuiltDistrict();

		districtsInACity.stream().filter(district -> district.getNameOfFamily().equals("Nobility")).count();
		if (districtsInACity.size() >= 3) return deckCharacter.getDeckCharacter().remove(Initialiser.KING_INDEX);

		return deckCharacter.getDeckCharacter().remove(random.nextInt(deckCharacter.getDeckCharacter().size()));
	}


	public void askEachCharacterToPlay(PhaseManager phaseManager, DeckDistrict deckDistrict, Initialiser initialiser) {
		boolean aBotCompleteHisCity = false;
		ArrayList<Player> listOfPlayer = getListOfPlayers();
		ArrayList<City> listOfCity = getTheListOfCity(listOfPlayer);
		currentPhase = phaseManager.analyseGame(listOfCity);

		for (Entry<Character, Optional<Bot>> entry : hashOfCharacters.entrySet()) {
			Character character = entry.getKey();
			Optional<Bot> bot = entry.getValue();
			if (bot.isPresent()) {
				aBotCompleteHisCity = actionsOfTheBot(character, bot.get(), aBotCompleteHisCity, deckDistrict);
			}
		}
		roundNumber++;
		initialiser.initHashOfCharacter(hashOfCharacters, listOfAllCharacters);
	}

	public boolean actionsOfTheBot(Character character, Bot bot, boolean aBotCompleteHisCity, DeckDistrict deckDistrict){
		aBotCompleteHisCity = bot.play(deckDistrict, currentPhase,hashOfCharacters);
		if (aBotCompleteHisCity) {
			//addBonusForPlayers(bot.getPlayer(), aBotCompleteHisCity);
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

	public void updateListOfBot() {
			int indexOfKing=findKing(listOfBot);
			if (indexOfKing!=-1){
				ArrayList<Bot> listOfBotCopy=listOfBot;
				listOfBot=new ArrayList<>();
				listOfBot.addAll(orderListOfPlayer(listOfBotCopy,indexOfKing));}
	}
	public int findKing(ArrayList<Bot> listOfBot){
		for (int k=0;k<listOfBot.size();k++){
			if (listOfBot.get(k).getBotIsKing()) {
				listOfBot.get(k).setBotIsKing(false);
				return(k);}}
		return(-1);
	}
	public ArrayList<Bot> orderListOfPlayer(ArrayList<Bot> listOfBot, int positionOfKingHolder){
		int positionToChange=positionOfKingHolder;
		int sizeListOfPlayer=listOfBot.size();
		ArrayList<Bot> listOfBotNextRound=new ArrayList<>();
		for (int i=0;i<sizeListOfPlayer;i++){
			if (positionToChange>=sizeListOfPlayer) positionToChange=0;
			listOfBotNextRound.add(listOfBot.get(positionToChange));
			positionToChange++;
		}
		return (listOfBotNextRound);
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public void reviveAll() {
		listOfBot.forEach(bot -> bot.getPlayer().getCharacter().setCharacterIsAlive(true));	
	}
	
	public ArrayList<Player> getListOfPlayers() {
		return (ArrayList<Player>) listOfBot.stream().
				map(bot -> bot.getPlayer()).
				collect(Collectors.toCollection(ArrayList::new));
	}
}
