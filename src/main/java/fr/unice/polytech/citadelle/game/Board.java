package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;

/**
 * A Board represents an instance of the Citadel game with all the cards (districts and character) and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
/**
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 *
 */
public class Board {
    private int roundNumber = 0;
    private ArrayList<Player> listOfPlayer;
    private ArrayList<Player> listOfPlayerWhoPlayed=new ArrayList<>();
    private DeckDistrict deckDistrict;
    private DeckCharacter deckCharacter;
    private ArrayList<Character> listOfCharacter;
	private final LinkedHashMap<Player, Optional<Character>> hashOfViewCharacters = new LinkedHashMap<>();


    public Board( ArrayList<Player> listOfPlayer,ArrayList<Character>listOfCharacter, DeckDistrict deckDistrict, DeckCharacter deckCharacter) {
        this.listOfPlayer = listOfPlayer;
        this.deckDistrict = deckDistrict;
        this.deckCharacter = deckCharacter;
        this.listOfCharacter=listOfCharacter;
    }

    public Board() {}

    public void putDistrictBackInDeck(District district) {
        deckDistrict.addDistrictAtTheEnd(district);
    }

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
        Initializer.initTheHashOfViewCharacters(hashOfViewCharacters, listOfPlayer);

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

    public ArrayList<Player> getListOfPlayerWhoPlayed() {
        return listOfPlayerWhoPlayed;
    }

    public LinkedHashMap<Player, Optional<Character>> gethashOfViewCharacters(){
        return hashOfViewCharacters;
    }

    public void resetListOfPlayerWhoPlayed(){
        this.listOfPlayerWhoPlayed.clear();
    }
    
    
    /**
     * Update the LinkedHashMap of Character/Behaviour when a Behaviour Reveals his Character
     * @param character
     * @param player
     */
    public void revealCharacter(Player player, Character character) {
    	hashOfViewCharacters.put(player, Optional.of(character));
        listOfPlayerWhoPlayed.add(player);
    }
    
    /**
     * @return the list of player who has already played, compute with the hashOfViewCharacters
     */
    public ArrayList<Character> getListOfPlayerWhoHasAlreadyPlayed(){
    	ArrayList<Character> listOfPlayerWhoHasAlreadyPlayed = new ArrayList<>();
    	for (Entry<Player, Optional<Character>> entry : hashOfViewCharacters.entrySet()) {
			Optional<Character> optionalBehaviour = entry.getValue();
			if(optionalBehaviour.isPresent())
				listOfPlayerWhoHasAlreadyPlayed.add(optionalBehaviour.get());
    	}
    	return listOfPlayerWhoHasAlreadyPlayed;
    }
    
    public ArrayList<String> getListOfPlayerWhoHasAlreadyPlayedStringVersion(){
    	return getListOfPlayerWhoHasAlreadyPlayed().stream().
    												map(character -> character.getName())
    												.collect(Collectors.toCollection(ArrayList::new));

    }

	public ArrayList<City> getListOfCity() {
		return listOfPlayer.stream().
				map(player -> player.getCity()).
				collect(Collectors.toCollection(ArrayList::new));
	}


    public Character findDeadCharacter() {
        for (int k=0;k<listOfCharacter.size();k++){
            if (!listOfCharacter.get(k).getCharacterisAlive()) return listOfCharacter.get(k);
        }
        return null;
    }
}

