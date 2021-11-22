package fr.unice.polytech.citadelle;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Initialiser;

public class InitialiserTest {
	Initialiser init;
	LinkedHashMap<Character, Bot> hashOfCharacters;
	ArrayList<Character> listOfAllCharacters;
	ArrayList<Bot> listOfBot; 
	ArrayList<Player> listOfPlayer;

	
	@BeforeEach
	public void init() {
		init = new Initialiser();
		hashOfCharacters= new LinkedHashMap<Character, Bot>();
		listOfAllCharacters= new ArrayList<Character>();
		listOfBot = new ArrayList<Bot>();
		listOfPlayer = new ArrayList<Player>();


	}
	
	@Test
	public void initHashOfCharacterTest() {
		listOfAllCharacters.add(new Character("test0", 0));
		listOfAllCharacters.add(new Character("test1", 1));
		listOfAllCharacters.add(new Character("test2", 2));
		listOfAllCharacters.add(new Character("test3", 3));
		listOfAllCharacters.add(new Character("test4", 4));
		listOfAllCharacters.add(new Character("test5", 5));
		listOfAllCharacters.add(new Character("test6", 6));
		listOfAllCharacters.add(new Character("test7", 7));

		
		LinkedHashMap<Character, Bot> hashOfCharactersSpy = spy(hashOfCharacters);
		init.initHashOfCharacter(hashOfCharactersSpy, listOfAllCharacters);
		verify(hashOfCharactersSpy, times(8)).put(Mockito.any(Character.class), Mockito.any(Bot.class));
	}
	
	@Test
	public void initListOfAllCharacterTest() {
		ArrayList<Character> listOfAllCharactersSpy = spy(listOfAllCharacters);
		init.initListOfAllCharacter(listOfAllCharactersSpy);
		verify(listOfAllCharactersSpy, times(8)).add(Mockito.any(Character.class));
	}
	
	@Test
	public void initListOfBotTest() {
		ArrayList<Bot> listOfBotSpy = spy(listOfBot);
		ArrayList<Player> listOfPlayerSpy = spy(listOfPlayer);
		
		init.initListOfBot(listOfBotSpy, listOfPlayerSpy);
		verify(listOfBotSpy, times(Initialiser.NUMBER_OF_PLAYER)).add(Mockito.any(Bot.class));
		verify(listOfPlayerSpy, times(Initialiser.NUMBER_OF_PLAYER)).add(Mockito.any(Player.class));

	}
	
	@Test
	public void fillHashOfCharacterTest() {
		LinkedHashMap<Character, Bot> hashOfCharactersSpy = spy(hashOfCharacters);
		init.fillHashOfCharacter(hashOfCharactersSpy, new Character("testCharacter", 0), new Bot(new Player("testPlayer")));
		verify(hashOfCharactersSpy, times(1)).put(Mockito.any(Character.class), Mockito.any(Bot.class));
	}
}
