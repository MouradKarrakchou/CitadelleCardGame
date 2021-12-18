package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

/**
 *A character is a class that gives the player special abilities.
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Character {
    private String name;
    private int value;
    private  boolean characterIsAlive=true;

    public Character(String name, int value) {
    	this.name = name;    	
    	this.value = value;    	
    }


    /**
     * The method return the number of gold generated from his family district.
     * @param bot
     * @param familyOfTheDistrict
     * @return the number of golds earned
     */
    public int collectGold(Behaviour bot, String familyOfTheDistrict){
        int goldEarned = bot.getPlayer().getCity().getBuiltDistrict().stream()
                .filter(district -> district.getNameOfFamily().equals(familyOfTheDistrict))
                .map(district ->1)
                .reduce(0, Integer::sum);
        bot.getPlayer().setGolds(bot.getPlayer().getGolds()+goldEarned);
        return(goldEarned);
    }

    /**
     * The spell of the Character
     * @param bot
     * @param hashOfCharacters
     */
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){}

    /**
     * print the spell of District family golds
     * @param bot
     * @param nameOfTheCharacter
     * @param nameOfTheFamilyDistrict
     */
    protected void spellOfTurnDistrictFamily(Behaviour bot,String nameOfTheCharacter,String nameOfTheFamilyDistrict) {
        int moneyEarned = collectGold(bot,nameOfTheFamilyDistrict);
        if (moneyEarned>0)
            PrintCitadels.printCharacterEarnedMoney(moneyEarned,nameOfTheCharacter,nameOfTheFamilyDistrict);
        else
            PrintCitadels.printCharacterEarnedNoMoney(nameOfTheCharacter,nameOfTheFamilyDistrict);
    }

    public  void setCharacterIsAlive(boolean characterIsAlive) {
        this.characterIsAlive = characterIsAlive;
    }

    public boolean getCharacterisAlive() {
        return characterIsAlive;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
    	if(!(obj instanceof Character))
		  return false;

		Character other = (Character) obj;
		return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
