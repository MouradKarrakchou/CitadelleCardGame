package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class Situation {
	private int rang;
	private int nbrDistrictBuild;
	private ArrayList<Character> listOfCharacterPickable;
	private ArrayList<Character> listOfCharacterNotHere;

	private Character characterToChoose;
	private Character targetCharacter;
	private Player targetPlayer;
	

	public Situation(int rang, int nbrDistrictBuild, ArrayList<Character> listOfCharacterPickable, Character characterToChoose) {
		this.rang = rang;
		this.nbrDistrictBuild = nbrDistrictBuild;
		this.listOfCharacterPickable = listOfCharacterPickable;
		this.characterToChoose = characterToChoose;
	}


	public int getRang() {
		return rang;
	}


	public int getnbrDistrictBuild() {
		return nbrDistrictBuild;
	}


	public ArrayList<Character> getListOfCharacterPickable() {
		return listOfCharacterPickable;
	}


	public Character getCharacterToChoose() {
		return characterToChoose;
	}


	public Character getTargetCharacter() {
		return targetCharacter;
	}


	public Player getTargetPlayer() {
		return targetPlayer;
	}
	
	
}
