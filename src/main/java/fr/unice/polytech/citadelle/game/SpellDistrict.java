package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.game_interactor.Executor;

import java.util.ArrayList;

public class SpellDistrict extends District {
    public SpellDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    public void librarySpell(Player player, DeckDistrict deckDistrict) {
        Executor executor = new Executor(player);
        ArrayList<District> pickedCards = executor.pickCards(deckDistrict);
        for (District district : pickedCards) {
            player.getDistrictCards().add(district);
        }
    }
}
