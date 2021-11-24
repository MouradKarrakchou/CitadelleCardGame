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


    public Board( ArrayList<Player> listOfPlayer, ArrayList<Bot> listOfBot) {
        this.listOfPlayer = listOfPlayer;
        this.listOfBot = listOfBot;
    }

    public ArrayList<Player> getListOfPlayer(){
        return listOfPlayer;
    }

    public ArrayList<Bot> getListOfBot() {
        return listOfBot;
    }}

