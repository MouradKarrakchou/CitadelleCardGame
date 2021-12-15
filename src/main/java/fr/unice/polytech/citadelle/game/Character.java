package fr.unice.polytech.citadelle.game;

import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 *A character is a class that gives the player special abilities.
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Character {
    String name;
    int value;
    boolean characterIsAlive=true;

    public Character(String name, int value) {
    	this.name = name;    	
    	this.value = value;    	
    }
    
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int collectGold(Behaviour bot, String familyOfTheDistrict){
        int goldEarned=bot.getPlayer().getCity().getBuiltDistrict().stream()
                .filter(district -> district.getNameOfFamily().equals(familyOfTheDistrict))
                .map(district ->1)
                .reduce(0, (total, count) -> total + count);
        bot.getPlayer().setGolds(bot.getPlayer().getGolds()+goldEarned);
        return(goldEarned);
    }

    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){}

    public  void setCharacterIsAlive(boolean characterIsAlive) {
        this.characterIsAlive=characterIsAlive;
    }

    public boolean isCharacterIsAlive() {
        return characterIsAlive;
    }

    @Override
    public boolean equals(Object obj) {
        Character other = (Character) obj;
        if(name.equals(other.getName())) return true;
        return  false;
    }

    @Override
    public String toString() {
        return name;
    }


    protected void spellOfTurnDistrictFamily(Behaviour bot,String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
        int moneyEarned=collectGold(bot,nameOfTheFamilyDistrict);
        if (moneyEarned>0)
            PrintCitadels.printCharacterEarnedMoney(moneyEarned,nameOfTheCharacter,nameOfTheFamilyDistrict);
        else
            PrintCitadels.printCharacterEarnedNoMoney(nameOfTheCharacter,nameOfTheFamilyDistrict);
    }
}
