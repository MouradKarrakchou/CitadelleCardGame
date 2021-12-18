package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

/**
 * Some purple Districts can change their own color
 */
public class ColorDistrict extends District {
    public ColorDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

    /**
     * If the player has built the Haunted City, has another purple district,
     * and has 4 or less different colors in his city, the Haunted City changes
     * its own color into the misssing color
     * @param player
     */
    public void hauntedCitySpell(Player player) {
        int numberOfPurpleDistrict = 0;
        String missingColor = null;
        ArrayList<String> colorsInCity = new ArrayList<>();
        ArrayList<String> existingColors = new ArrayList<>();
        existingColors.add("Blue");
        existingColors.add("Yellow");
        existingColors.add("Green");
        existingColors.add("Red");
        existingColors.add("Purple");

        ArrayList<District> builtDistrict = player.getCity().getBuiltDistrict();
        for(District district : builtDistrict) {
            if(!colorsInCity.contains(district.getColor())) colorsInCity.add(district.getColor());
            if(district.getColor().equals("Purple")) numberOfPurpleDistrict++;
        }

        if (numberOfPurpleDistrict >= 2 && colorsInCity.size() < 5) {
            for (String color : existingColors) {
                if (!colorsInCity.contains(color)) missingColor = color;
            }
            String finalMissingColor = missingColor;
            player.getCity().getBuiltDistrict().stream()
                    .filter(district -> district.getName().equals("Haunted City"))
                    .forEach(district -> district.setColor(finalMissingColor));
        }
    }

    /**
     * If the player has built the School of Magic which can change its own color,
     * he can earn one more gold at each turn where he as the King, the Bishop, the Merchant or the Warlord.
     * At the end of the game, this district is considered as purple
     * @param player
     */
    public void schoolOfMagicSpell(Player player) {
        ArrayList<String> possibleRoles = new ArrayList<>();
        possibleRoles.add("King");
        possibleRoles.add("Bishop");
        possibleRoles.add("Merchant");
        possibleRoles.add("Warlord");

        String role = player.getCharacter().getName();

        for(String aRole : possibleRoles)
            if(aRole.equals(role)) player.addOneGold();
    }
}
