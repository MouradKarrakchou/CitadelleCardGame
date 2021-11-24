package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.bot.Bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * A Game represent a instance of the game Citadelle with all the cards and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Game {
    private ArrayList<Player> listOfPlayer;
    private DeckCharacter deckCharacter;
    private DeckDistrict deckDistrict;
    private ArrayList<Player> listOfPlayerOfNextRound;
    private ArrayList<Bot> listOfBot;
    private ArrayList<Bot> listOfBotOfNextRound;
    private ArrayList<Character> listOfCharacters;
    private LinkedHashMap<Character, Bot> hashOfCharacters;


    public Game(LinkedHashMap<Character, Bot> hashOfCharacters,ArrayList<Player> listOfPlayer,ArrayList<Bot> listOfBot,ArrayList<Character> listOfAllCharacters, DeckCharacter deckCharacter, DeckDistrict deckDistrict) {
        this.listOfPlayer = listOfPlayer;
        this.listOfBot = listOfBot;
        this.deckCharacter = deckCharacter;
        this.deckDistrict = deckDistrict;
        this.listOfBotOfNextRound= new ArrayList<>();
        this.listOfCharacters=listOfAllCharacters;
        this.hashOfCharacters=hashOfCharacters;
    }

    public void calculateScoreOfPlayer(Player player) {
        player.getDistrictCards().forEach(district ->
        {
            player.updateScore(district.getValue());
        });
    }

    public void getWinner() {
        listOfPlayer.forEach(player -> calculateScoreOfPlayer(player));
        Collections.sort(listOfPlayer);
        Collections.reverse(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) listOfPlayer.get(i).setRank(i + 1);
    }

    public void updateListOfBot() {
        if (listOfBotOfNextRound.size()!=0)
        {listOfBot.clear();
            listOfBot.addAll(listOfBotOfNextRound);}
    }

    public ArrayList<Bot> getListOfBotOfNextRound() {
        return listOfBotOfNextRound;
    }

    public ArrayList<Player> getListOfPlayer(){
        return listOfPlayer;
    }

    public ArrayList<Bot> getListOfBot() {
        return listOfBot;
    }

    public ArrayList<Character> getListOfCharacters() {
        return listOfCharacters;
    }

    public LinkedHashMap<Character, Bot> getHashOfCharacters() {
        return hashOfCharacters;
    }}
