package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

import fr.unice.polytech.citadelle.game_interactor.Behaviour;


/**
 * A Game represent a instance of the game Citadelle with all the cards and players that go with.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Board {
    private ArrayList<Player> listOfPlayer;
    private ArrayList<Behaviour> listOfBehaviour;


    public Board( ArrayList<Player> listOfPlayer, ArrayList<Behaviour> listOfBehaviour) {
        this.listOfPlayer = listOfPlayer;
        this.listOfBehaviour = listOfBehaviour;
    }

    public ArrayList<Player> getListOfPlayer(){
        return listOfPlayer;
    }

    public ArrayList<Behaviour> getListOfBehaviour() {
        return listOfBehaviour;
    }}

