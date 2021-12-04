package fr.unice.polytech.citadelle.game.purple_districts;

import fr.unice.polytech.citadelle.game.ColorJoker;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;

public class SchoolOfMagic  extends District implements ColorJoker {

    public SchoolOfMagic(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    @Override
    public void spell(Player player) {
        //nothing to fill here
    }

    @Override
    public void joker(Player player) {
        //fill here
    }
}
