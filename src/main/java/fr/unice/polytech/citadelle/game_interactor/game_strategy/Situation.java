package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;


public class Situation implements Comparable<Situation>{
	private int rang;
	private int nbrDistrictBuild;
	private ArrayList<Character> listOfCharacterPickable;
	private ArrayList<Character> listOfCharacterNotHere;

	private Character characterToChoose;
	private Character targetCharacter;
	private Player targetPlayer;
	private int priority;
	

	public Situation(int rang, int nbrDistrictBuild, ArrayList<Character> listOfCharacterPickable, Character characterToChoose, int priority) {
		this.rang = rang;
		this.nbrDistrictBuild = nbrDistrictBuild;
		this.listOfCharacterPickable = listOfCharacterPickable;
		this.characterToChoose = characterToChoose;
		this.priority = priority;
	}


	public Optional<Integer> getRang() {
		return Optional.of(rang);
	}


	public Optional<Integer> getNbrDistrictBuild() {
		return Optional.of(nbrDistrictBuild);
	}


	public Optional<ArrayList<Character>> getListOfCharacterPickable() {
		return Optional.of(listOfCharacterPickable);
	}


	public Optional<Character> getCharacterToChoose() {
		return Optional.of(characterToChoose);
	}


	public Optional<Character> getTargetCharacter() {
		return Optional.of(targetCharacter);
	}


	public Optional<Player> getTargetPlayer() {
		return Optional.of(targetPlayer);
	}


	@Override
	public int compareTo(Situation other) {
        return this.priority - other.priority;

	}
	
	
	
}
