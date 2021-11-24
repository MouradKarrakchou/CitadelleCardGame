package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.bot.Bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * A Game represent a instance of the game Citadelle with all the cards and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Board {
    private ArrayList<Player> listOfPlayer;
    private ArrayList<Bot> listOfBot;
    private ArrayList<Bot> listOfBotOfNextRound;
    private LinkedHashMap<Character, Bot> hashOfCharacters;


    public Board(LinkedHashMap<Character, Bot> hashOfCharacters, ArrayList<Player> listOfPlayer, ArrayList<Bot> listOfBot, ArrayList<Character> listOfAllCharacters) {
        this.listOfPlayer = listOfPlayer;
        this.listOfBot = listOfBot;
        this.listOfBotOfNextRound= new ArrayList<>();
        this.hashOfCharacters=hashOfCharacters;
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

    public LinkedHashMap<Character, Bot> getHashOfCharacters() {
        return hashOfCharacters;
    }}
