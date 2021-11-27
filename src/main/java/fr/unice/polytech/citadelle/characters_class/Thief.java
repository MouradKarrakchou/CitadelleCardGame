package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.bot.Bot;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.LinkedHashMap;
import java.util.Optional;

public class Thief extends Character {
    public Thief(){
        super("Thief", 2);
    }
    public void spellOfTurn(Bot bot, LinkedHashMap<Character, Optional<Bot>> hashOfCharacters, PrintCitadels printC){
        int goldStolen=0;
        Character characterToSteal= bot.selectCharacterForSpell(hashOfCharacters);
        if (hashOfCharacters.get(characterToSteal).isPresent())
        {goldStolen=hashOfCharacters.get(characterToSteal).get().getPlayer().stealGoldOfThePlayer(bot.getPlayer());
            printC.stealCharacter(characterToSteal,goldStolen);}
        else printC.failedToStealCharacter(characterToSteal);
    }
}