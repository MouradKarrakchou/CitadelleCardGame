package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;


public class Situation implements Comparable<Situation>{
	private int priority;
	private Optional<Integer> rang;
	private Optional<Integer> nbrDistrictBuild;
	private Optional<ArrayList<Character>> listOfCharacterPickable;
	private Optional<ArrayList<Character>> listOfCharacterNotHere;
	 
	

	private Optional<Character> characterToChoose;
	private Character targetCharacter;
	private Player targetPlayer;
	

	public Situation(Optional<Integer> rang, Optional<Integer> nbrDistrictBuild, Optional<ArrayList<Character>> listOfCharacterPickable, Optional<Character> characterToChoose, int priority) {
		this.priority = priority;
		this.rang = rang;
		this.nbrDistrictBuild = nbrDistrictBuild;
		this.listOfCharacterPickable = listOfCharacterPickable;
		this.characterToChoose = characterToChoose;


	}


	public Optional<Integer> getRang() {
		return rang;
	}


	public Optional<Integer> getNbrDistrictBuild() {
		return nbrDistrictBuild;
	}


	public Optional<ArrayList<Character>> getListOfCharacterPickable() {
		return listOfCharacterPickable;
	}


	public Optional<Character> getCharacterToChoose() {
		return characterToChoose;
	}


	public Optional<Character> getTargetCharacter() {
		if (targetCharacter==null) return(Optional.empty());
		return Optional.of(targetCharacter);
	}


	public Optional<Player> getTargetPlayer() {
		if (targetPlayer==null) return(Optional.empty());
		return Optional.of(targetPlayer);
	}
	
	public Optional<ArrayList<Character>> getListOfCharacterNotHere() {
		return listOfCharacterNotHere;
	}


	@Override
	public int compareTo(Situation other) {
        return other.priority-this.priority;

	}


	
	
	
	
}
