package fr.unice.polytech.citadelle.game;

import java.util.ArrayList;

public class ColorDistrict extends District {
    public ColorDistrict(String name, int value, String color, String nameOfFamily) {
        super(name, value, color, nameOfFamily);
    }

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

    public void schoolOfMagicSpell(Player player) {
        ArrayList<String> possibleRoles = new ArrayList<>();
        possibleRoles.add("King");
        possibleRoles.add("Bishop");
        possibleRoles.add("Merchant");
        possibleRoles.add("Warlord");

        Character role = player.getCharacter();

        for(String aRole : possibleRoles)
            if(aRole.equals(role)) player.addOneGold();
    }
}
