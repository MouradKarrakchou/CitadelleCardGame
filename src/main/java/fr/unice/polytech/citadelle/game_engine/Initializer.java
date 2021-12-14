package fr.unice.polytech.citadelle.game_engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.characters_class.Architect;
import fr.unice.polytech.citadelle.characters_class.Assassin;
import fr.unice.polytech.citadelle.characters_class.Bishop;
import fr.unice.polytech.citadelle.characters_class.King;
import fr.unice.polytech.citadelle.characters_class.Magician;
import fr.unice.polytech.citadelle.characters_class.Merchant;
import fr.unice.polytech.citadelle.characters_class.Thief;
import fr.unice.polytech.citadelle.characters_class.Warlord;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.game_interactor.NormalBot;
import fr.unice.polytech.citadelle.game_interactor.RushBot;

/**
 * The Initializer class initialize all objects needed during the game
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Initializer {
	public static final int ASSASSIN_INDEX = 0;
	public static final int THIEF_INDEX = 1;
	public static final int MAGICIAN_INDEX = 2;
	public static final int KING_INDEX = 3;
	public static final int BISHOP_INDEX = 4;
	public static final int MERCHANT_INDEX = 5;
	public static final int ARCHITECT_INDEX = 6;
	public static final int WARLORD_INDEX = 7;

	public final static int NUMBER_OF_PLAYER = 4;

	/**
	 * Reset a given hash of characters using a given list of characters.
	 * @param hashOfCharacters The hash of characters to reset.
	 * @param listOfAllCharacters The list containing all the characters.
	 */
	public static void resetHashOfCharacter(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters,
											ArrayList<Character> listOfAllCharacters) {
		hashOfCharacters.put(listOfAllCharacters.get(ASSASSIN_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(THIEF_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(MAGICIAN_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(KING_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(BISHOP_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(MERCHANT_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(ARCHITECT_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(WARLORD_INDEX), Optional.empty());

	}

	/**
	 * Create a new list containing all characters.
	 * @return A list containing all the characters.
	 */
	public static ArrayList<Character> createListOfAllCharacter() {
		ArrayList<Character> listOfAllCharacters = new ArrayList<>();
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
		
		return listOfAllCharacters;
	}

	/**
	 * Initialize 4 players with a certain behavior.
	 * @param board The board to add the new player behaviours.
	 * @return The list of behaviours.
	 */
	public static ArrayList<Behaviour> createListOfBehaviour(Board board){
		ArrayList<Behaviour> listOfBehaviour = new ArrayList<>();
		
		for (int i = 1; i < NUMBER_OF_PLAYER; i++) {
			Player newPlayer = new Player("Robot " + i);
			listOfBehaviour.add(new NormalBot(newPlayer, board));
		}
		Player newPlayer = new Player("RobotRusher");
		listOfBehaviour.add(new RushBot(newPlayer, board));
		
		return listOfBehaviour;

	}

	/**
	 * Create a new empty Board.
	 * @return The new empty Board object created.
	 */
	public static Board createBoard(){
		return new Board(new ArrayList<>(),createListOfAllCharacter(), new DeckDistrict(), new DeckCharacter());
	}

	/**
	 * @param hashOfCharacters The empty hash of characters to fill.
	 * @param character The character to add to the hash of characters.
	 * @param Behaviour The associated behaviour.
	 */
	public static void fillHashOfCharacter(HashMap<Character, Optional<Behaviour>> hashOfCharacters, Character character, Behaviour Behaviour) {
		hashOfCharacters.put(character, Optional.of(Behaviour));
	}

	/**
	 * Initialize a given deck of districts.
	 * @param deck - The DeckDistrict to initialize.
	 */
	public static void initDeckDistrict(DeckDistrict deck) {
		deck.initialise();
	}

	/**
	 * Initialize a given deck of characters.
	 * @param deckCharacter The empty deck of characters to fill with characters.
	 * @param listOfCharacter The list of all the characters.
	 */
	public static void initDeckCharacter(DeckCharacter deckCharacter, ArrayList<Character> listOfCharacter) {
			ArrayList<Character> listOfCharacterCards = deckCharacter.getDeckCharacter();
			listOfCharacterCards.clear();
			listOfCharacterCards.add(listOfCharacter.get(Initializer.ASSASSIN_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.THIEF_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.MAGICIAN_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.KING_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.BISHOP_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.MERCHANT_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.ARCHITECT_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initializer.WARLORD_INDEX));
	}

	/**
	 * Method used to give to each player the 4 firsts cards at the beginning of the game.
	 * @param board - The game board allows us to take player and district deck information.
	 */
	public static void initPlayerCards(Board board) {
		ArrayList<Player> players = board.getListOfPlayer();
		DeckDistrict boardDistrictDeck = board.getDeckDistrict();

		for (Player player : players)
			for (int i=0; i<4; i++) {
				District selectedDistrict = boardDistrictDeck.blindPick();
				player.addDistrict(selectedDistrict);
			}
	}
}
