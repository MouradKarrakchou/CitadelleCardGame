package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.game_interactor.Executor;

import java.util.ArrayList;

/**
 * Some purple Districts can change do a spell as characters
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class SpellDistrict extends District {
    public SpellDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    /**
     * If the player has built the Library and decide to pick cards,
     * then he does not need to choose one and put back one, he can
     * keep both cards he picked
     * @param player
     * @param deckDistrict
     */
    public void librarySpell(Player player, DeckDistrict deckDistrict) {
        Executor executor = new Executor(player);
        ArrayList<District> pickedCards = executor.pickCards(deckDistrict);
        for (District district : pickedCards) {
            player.getDistrictCards().add(district);
        }
    }
}
