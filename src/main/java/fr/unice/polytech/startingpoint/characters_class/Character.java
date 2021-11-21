package fr.unice.polytech.startingpoint.characters_class;

import fr.unice.polytech.startingpoint.Game;
import fr.unice.polytech.startingpoint.Player;
import fr.unice.polytech.startingpoint.PrintCitadels;

public abstract class Character {
    String name;
    int value;

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void spellOfBeginningOfRound(Player player, Game game){}

    public void printOfBeginningOfTurn(Player player, PrintCitadels printC){}
}
