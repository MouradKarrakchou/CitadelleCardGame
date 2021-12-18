package fr.unice.polytech.citadelle.game;

/**
 * Some purple Districts which act like a bonus
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class BonusDistrict extends District {
    public BonusDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    /**
     * Add 2 golds to the player who has built a DragonGate or a University
     * A total of 2 + 2 = 4 golds if he has built them both
     * @param player
     */
    public void bonusDistrict(Player player) {
        player.updateScore(2);
    }

}
