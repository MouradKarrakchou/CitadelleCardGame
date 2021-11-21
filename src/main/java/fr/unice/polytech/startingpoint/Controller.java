package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.unice.polytech.startingpoint.characters_class.Architect;
import fr.unice.polytech.startingpoint.characters_class.Assassin;
import fr.unice.polytech.startingpoint.characters_class.Bishop;
import fr.unice.polytech.startingpoint.characters_class.Character;
import fr.unice.polytech.startingpoint.characters_class.King;
import fr.unice.polytech.startingpoint.characters_class.Magician;
import fr.unice.polytech.startingpoint.characters_class.Merchant;
import fr.unice.polytech.startingpoint.characters_class.Thief;
import fr.unice.polytech.startingpoint.characters_class.Warlord;


public class Controller {
	public final static int NUMBER_OF_PLAYER = 4;
	private static final int BONUS_FIRST = 4;
	private static final int BONUS_END = 2;
	private Game game;
	private PrintCitadels printC;
	private PhaseManager phaseManager;
	private ArrayList<Player> listOfPlayer;
	private DeckCharacter deckCharacter;
	private DeckDistrict deckDistrict;
	private int roundNumber = 1;
	
	//------
	private ArrayList<Character> listOfAllCharacters;
	//private ArrayList<Character> listOfCharactersOfTheGame;

	private LinkedHashMap<Character, Bot> hashOfCharacters;
	private ArrayList<Bot> listOfBot;
	private String currentPhase = "none";
	public static final int ASSASIN_INDEX = 0;
	public static final int THIEF_INDEX = 1;
	public static final int MAGICIAN_INDEX = 2;
	public static final int KING_INDEX = 3;
	public static final int BISHOP_INDEX = 4;
	public static final int MERCHANT_INDEX = 5;
	public static final int ARCHITECT_INDEX = 6;
	public static final int WARLORD_INDEX = 7;


	//------


	public Controller() {
		listOfPlayer = new ArrayList<>();
		listOfAllCharacters = new ArrayList<>();
		//listOfCharactersOfTheGame = new ArrayList<>();
		listOfBot = new ArrayList<>();
		hashOfCharacters = new LinkedHashMap<>();
		deckCharacter = new DeckCharacter();
		deckDistrict = new DeckDistrict();

		game = new Game(listOfPlayer, deckCharacter, deckDistrict);
		printC = new PrintCitadels();
		phaseManager = new PhaseManager();
	}
	
	public void initGame() {
		initListOfAllCharacter();
		deckCharacter.initialise(listOfAllCharacters);;
		initHashOfCharacter();
		for (int i = 1; i <= NUMBER_OF_PLAYER; i++)
			listOfPlayer.add(new Player("robot" + i));
		game = new Game(listOfPlayer, deckCharacter, deckDistrict); // créer un jeu avec tout les éléments nécessaires
	}



	public void runGame() {
		boolean res = false;
		while (!res) {
			printC.printNumberRound(roundNumber);
			printC.printBoard(game);
			res = runRound();
			printC.printLayer();
		}
		end();
	}

	public boolean runRound() {
		if (roundNumber != 1)
			game.updateListOfPlayer();
		startRoundPart1();
		return (startRoundPart2(game.getListOfPlayer()));
	}

	public void end() {
		game.getWinner();
		printC.printRanking(listOfPlayer);
	}

	

	public void startRoundPart1() {
		deckCharacter.initialise(listOfAllCharacters);
		listOfPlayer.forEach(player -> {
			player.chooseCharacterCard(deckCharacter.chooseCharacter());
			printC.chooseRole(player, player.getCharacter());
		});
		printC.dropALine();
	}

	public boolean startRoundPart2(ArrayList<Player> listOfPlayer) {
		boolean isLastRound = false;
		ArrayList<City> listOfCity = getTheListOfCity();
		String currentPhase = phaseManager.analyseGame(listOfCity);
		spellOfCharacters();
		
		
		for (Player player : listOfPlayer) {

			 // Instantiates a bot that will make decisions for the player.
			Bot bot = new Bot(player);
			bot.botStartRoundPart2(deckDistrict, currentPhase);

			boolean res = bot.play();
			if (res) {
				isLastRound = addBonusForPlayers(player, res);
				currentPhase = PhaseManager.LAST_TURN_PHASE;
			}
		}
		printC.dropALine();
		roundNumber++;
		return isLastRound;
	}

	public void spellOfCharacters() {
		listOfPlayer.forEach(player -> {
			player.getCharacter().spellOfBeginningOfRound(player, game);
		});
	}

	public ArrayList<City> getTheListOfCity() {
		return listOfPlayer.stream().map(p -> p.getCity()).collect(Collectors.toCollection(ArrayList::new));
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

	public Game getGame() {
		return game;
	}

	
	//-----------------
	public void initGameV2() {
		initListOfAllCharacter();
		//initListOfCharacterOfTheGame();
		initHashOfCharacter();
		for (int i = 1; i <= NUMBER_OF_PLAYER; i++) {
			Player newPlayer = new Player("robot" + i);
			listOfBot.add(new Bot(newPlayer));
			listOfPlayer.add(newPlayer);
		}
		game = new Game(listOfPlayer, deckCharacter, deckDistrict); // créer un jeu avec tout les éléments nécessaires
	}

	
	/*private void initListOfCharacterOfTheGame() {
	        Random random = new Random();
	        for(int i = 0 ; i < NUMBER_OF_PLAYER ; i ++) {
	        	Character randomCharacter = listOfAllCharacters.get(random.nextInt(listOfAllCharacters.size()));
	        	listOfCharactersOfTheGame.add(randomCharacter);
	        }
		
	}*/
	
	private void initListOfAllCharacter() {
		Assassin theAssassin = new Assassin();
		Thief theThief = new Thief();
		Magician theMagician = new Magician();
		King theKing = new King();
		Bishop theBishop = new Bishop();
		Merchant theMerchant = new Merchant();
		Architect theArchitect = new Architect();
		Warlord theWarlord = new Warlord();
		
		listOfAllCharacters.add(theAssassin);
		listOfAllCharacters.add(theThief);
		listOfAllCharacters.add(theMagician);
		listOfAllCharacters.add(theKing);
		listOfAllCharacters.add(theBishop);
		listOfAllCharacters.add(theMerchant);
		listOfAllCharacters.add(theArchitect);
		listOfAllCharacters.add(theWarlord);


	}

	
	private void initHashOfCharacter() {
		Bot fillBot = new Bot(new Player("fillPlayer"));
		
		hashOfCharacters.put(listOfAllCharacters.get(ASSASIN_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(THIEF_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(MAGICIAN_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(KING_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(BISHOP_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(MERCHANT_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(ARCHITECT_INDEX), fillBot);
		hashOfCharacters.put(listOfAllCharacters.get(WARLORD_INDEX), fillBot);
	}

	private void fillHashOfCharacter(Character character, Bot bot) {
		hashOfCharacters.put(character, bot);
	}

	public void runGameV2() {
		while ((currentPhase= phaseManager.analyseGame(getTheListOfCity())) != PhaseManager.LAST_TURN_PHASE) {
			printC.printNumberRound(roundNumber);
			
			setupCharacters();
			askEachCharacterToPlay();
			
			printC.printBoard(game);
			printC.printLayer();
		}
		end();
	}
	
	
	private void setupCharacters() {
		deckCharacter.initialise(listOfAllCharacters);
		listOfBot.forEach(bot -> {
			Player playerOfBot = bot.getPlayer();
			playerOfBot.chooseCharacterCard(deckCharacter.chooseCharacter());
			fillHashOfCharacter(playerOfBot.getCharacter(), bot);
			printC.chooseRole(playerOfBot, playerOfBot.getCharacter());
		});
		printC.dropALine();
	}
	
	private void askEachCharacterToPlay() {
		boolean aBotCompleteHisCity = false ; 
		ArrayList<City> listOfCity = getTheListOfCity();
		currentPhase = phaseManager.analyseGame(listOfCity);
		
		for(Entry<Character, Bot> entry : hashOfCharacters.entrySet()) {
		    Character character = entry.getKey();
		    Bot bot = entry.getValue();
		    if(bot.getPlayer().getName() != "fillPlayer") {
		    	aBotCompleteHisCity = actionsOfTheBot(character, bot, aBotCompleteHisCity);
		    }   
		}
		roundNumber++;
	}
	
	private boolean actionsOfTheBot(Character character, Bot bot, boolean aBotCompleteHisCity){
		character.spellOfBeginningOfRound(bot.getPlayer(), game);
	    aBotCompleteHisCity = bot.play(deckDistrict, currentPhase);
	    if(aBotCompleteHisCity) {
	    	addBonusForPlayers(bot.getPlayer(), aBotCompleteHisCity);
	    	currentPhase = PhaseManager.LAST_TURN_PHASE;
	    }
	    return aBotCompleteHisCity;
	}
	
	
}
	//-----------------

