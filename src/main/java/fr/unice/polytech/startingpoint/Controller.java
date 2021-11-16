package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private int nbrJoueur = 4;
    private Game game;
    private PrintCitadels printC;
    private ArrayList<Player> listOfPlayer;
    private DeckCharacter deckCharacter;
    private DeckDistrict deckDistrict;

    public Controller(){
        listOfPlayer = new ArrayList<>();

        deckCharacter = new DeckCharacter();
        deckDistrict = new DeckDistrict();

        game = new Game(listOfPlayer, deckCharacter, deckDistrict);
        printC = new PrintCitadels();
    }

    void lauchGame(){
        initGame();
        startRoundPart1();
        System.out.println("");
        startRoundPart2();
        System.out.println("");
        game.getWinner();
        printC.printRanking(listOfPlayer);
    }

    public void initGame() {
        listOfPlayer = new ArrayList<>();
        deckCharacter = new DeckCharacter();
        deckDistrict = new DeckDistrict();
        for(int i = 1; i <= nbrJoueur; i++)
            listOfPlayer.add(new Player("robot" + i));
        game = new Game(listOfPlayer, deckCharacter, deckDistrict); // créer un jeu avec tout les éléments nécessaires
    }

    public void startRoundPart1(){
        listOfPlayer.forEach(player ->
        {
            deckCharacter.chooseCharacter(player);
            printC.chooseRole(player, player.getCharacter());
        });
    }

    public void startRoundPart2(){
        listOfPlayer.forEach(player ->
        {
            deckDistrict.chooseDistrict(player);
            //car pour l'instant un seul district
            printC.chooseDistrict(player, player.getDistrictCards().get(0));
            player.buildDistrict(0);
        });
    }
}