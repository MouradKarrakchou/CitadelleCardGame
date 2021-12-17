package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;

/**
 * A Board represents an instance of the Citadel game with all the cards (districts and character) and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
/**
 * @author leolb
 *
 */
public class Board {
    private int roundNumber = 0;
    private ArrayList<Player> listOfPlayer;
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;
    private ArrayList<Character> listOfCharacter;
	private final LinkedHashMap<Player, Optional<Character>> hashOfViewCharacters = new LinkedHashMap<>();


    public Board( ArrayList<Player> listOfPlayer,ArrayList<Character>listOfCharacter, DeckDistrict deckDistrict, DeckCharacter deckCharacter) {
        this.listOfPlayer = listOfPlayer;
        this.deckDistrict = deckDistrict;
        this.deckCharacter = deckCharacter;
        this.listOfCharacter=listOfCharacter;
        Initializer.initTheHashOfViewCharacters(hashOfViewCharacters, listOfPlayer);
    }

    public Board() {}

	public ArrayList<Player> getListOfPlayer(){
        return listOfPlayer;
    }

	public DeckDistrict getDeckDistrict() {
		return deckDistrict;
	}

	public DeckCharacter getDeckCharacter() {
		return deckCharacter;
	}

    public void setListOfPlayer(ArrayList<Player> listOfPlayer) {
		this.listOfPlayer = listOfPlayer;
	}
	
    public void incrementRoundNumber() {
        roundNumber++;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public ArrayList<Character> getListOfCharacter(){
        return listOfCharacter;
    }
    
    
    /**
     * Update the LinkedHashMap of Character/Behaviour when a Behaviour Reveals his Character
     * @param character
     * @param behaviour
     */
    public void updateViewCharacter(Player player, Character character) {
    	hashOfViewCharacters.put(player, Optional.of(character));
    }
}

