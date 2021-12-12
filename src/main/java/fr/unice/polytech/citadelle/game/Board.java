package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

/**
 * A Game represent a instance of the game Citadelle with all the cards and players that go with.
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Board {
    private ArrayList<Player> listOfPlayer;
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;
    private int roundNumber = 0;


    public Board( ArrayList<Player> listOfPlayer, DeckDistrict deckDistrict, DeckCharacter deckCharacter) {
        this.listOfPlayer = listOfPlayer;
        this.deckDistrict = deckDistrict;
        this.deckCharacter = deckCharacter;
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
}

