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
 * The Initialiser initialize all objects needed for a game
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Initialiser {
	
	//créer un enum
	public static final int ASSASIN_INDEX = 0;
	public static final int THIEF_INDEX = 1;
	public static final int MAGICIAN_INDEX = 2;
	public static final int KING_INDEX = 3;
	public static final int BISHOP_INDEX = 4;
	public static final int MERCHANT_INDEX = 5;
	public static final int ARCHITECT_INDEX = 6;
	public static final int WARLORD_INDEX = 7;
	public final static int NUMBER_OF_PLAYER = 4;

	
	public Initialiser() {}

	public LinkedHashMap<Character, Optional<Behaviour>> resetHashOfCharacter(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters,
			ArrayList<Character> listOfAllCharacters) {
		hashOfCharacters.put(listOfAllCharacters.get(ASSASIN_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(THIEF_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(MAGICIAN_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(KING_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(BISHOP_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(MERCHANT_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(ARCHITECT_INDEX), Optional.empty());
		hashOfCharacters.put(listOfAllCharacters.get(WARLORD_INDEX), Optional.empty());
		
		return hashOfCharacters;
	}
	
	public ArrayList<Character> createListOfAllCharacter() {
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
	
	public ArrayList<Behaviour> createListOfBehaviour(Board board){
		ArrayList<Behaviour> listOfBehaviour = new ArrayList<Behaviour>();
		
		for (int i = 1; i < NUMBER_OF_PLAYER; i++) {
			Player newPlayer = new Player("Robot " + i);
			listOfBehaviour.add(new NormalBot(newPlayer, board));
		}
		Player newPlayer = new Player("RobotRusher");
		listOfBehaviour.add(new RushBot(newPlayer, board));
		
		return listOfBehaviour;

	}
	
	public Board createBoard(ArrayList<Character> listOfCharacter){
		Board board = new Board(new ArrayList<Player>(), new DeckDistrict(), new DeckCharacter());
		return board;
	}
	
	public void fillHashOfCharacter(HashMap<Character, Optional<Behaviour>> hashOfCharacters, Character character, Behaviour Behaviour) {
		hashOfCharacters.put(character, Optional.of(Behaviour));
	}
	
	
	public DeckDistrict initDeckDistrict(DeckDistrict deck) {
		deck.initialise();
		return deck;
	}
	
	public DeckCharacter initDeckCharacter(DeckCharacter deckCharacter, ArrayList<Character> listOfCharacter) {
			ArrayList<Character> listOfCharacterCards = deckCharacter.getDeckCharacter();
			listOfCharacterCards.clear();
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.ASSASIN_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.THIEF_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.MAGICIAN_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.KING_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.BISHOP_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.MERCHANT_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.ARCHITECT_INDEX));
			listOfCharacterCards.add(listOfCharacter.get(Initialiser.WARLORD_INDEX));
			return deckCharacter;
	}

	/**
	 * Method used to give to each player the 4 firsts cards at the beginning of the game.
	 * @param board - The game board allows us to take player and district deck information.
	 */
	public void initPlayerCards(Board board) {
		ArrayList<Player> players = board.getListOfPlayer();
		DeckDistrict boardDistrictDeck = board.getDeckDistrict();

		for (Player player : players)
			for (int i=0; i<4; i++) {
				District selectedDistrict = boardDistrictDeck.blindPick();
				player.addDistrict(selectedDistrict);
			}
	}
}
