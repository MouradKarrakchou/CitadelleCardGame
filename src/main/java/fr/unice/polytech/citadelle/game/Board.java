package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

/**
 * A Board represents an instance of the Citadel game with all the cards (districts and character) and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Board {
    private ArrayList<Player> listOfPlayer;
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;
    private int roundNumber = 0;
    private ArrayList<Character> listOfCharacter;

    public Board( ArrayList<Player> listOfPlayer,ArrayList<Character>listOfCharacter, DeckDistrict deckDistrict, DeckCharacter deckCharacter) {
        this.listOfPlayer = listOfPlayer;
        this.deckDistrict = deckDistrict;
        this.deckCharacter = deckCharacter;
        this.listOfCharacter=listOfCharacter;
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
}

