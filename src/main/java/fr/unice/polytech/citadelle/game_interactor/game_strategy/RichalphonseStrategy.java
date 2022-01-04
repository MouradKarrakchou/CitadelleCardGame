package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.function.Function;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;




public class RichalphonseStrategy {
    Board board;
    Player currentPlayer;
    Character targetCharacter;
    LinkedHashSet<MyInterface> hashOfStrategies = new LinkedHashSet<>();

    public RichalphonseStrategy(Board board, Player player) {
        this.board = board;
        this.currentPlayer = player;
    }

    public LinkedHashMap<String,MyInterface> chooseCharacterWithStrategy() {
        
    	LinkedHashMap<String,MyInterface> hashmap = createStrategiesHashmap();
    	
    	return hashmap;
    }
    
    private LinkedHashMap<String, MyInterface> createStrategiesHashmap(){
    	LinkedHashMap<String, MyInterface> hashSet = new LinkedHashMap<>();
    	MyInterface myFunction = (character, name) ->{ 
    		name= character.getName();
    		return character; 
		};
    	hashSet.put("helloWorld", myFunction);
    	return hashSet;
    }
    
}
