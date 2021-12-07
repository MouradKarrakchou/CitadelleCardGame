package fr.unice.polytech.citadelle.characters_class;

import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game_interactor.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Magician extends Character {
    public Magician(){
        super("Magician", 3);
    }
    public void swapCardsWithBot(Behaviour bot1,Behaviour bot2) {
        ArrayList<District> deckDistrict1=bot1.getPlayer().getDistrictCards();
        ArrayList<District> deckDistrict2=bot2.getPlayer().getDistrictCards();
        ArrayList<District> deckDistrict1copy=new ArrayList<>();
        ArrayList<District> deckDistrict2copy=new ArrayList<>();
        deckDistrict1copy.addAll(deckDistrict1);
        deckDistrict2copy.addAll(deckDistrict2);
        deckDistrict1.clear();
        deckDistrict2.clear();
        deckDistrict1.addAll(deckDistrict2copy);
        deckDistrict2.addAll(deckDistrict1copy);
    }
    public ArrayList<District> swapCardsWithDeck(Behaviour bot, ArrayList<Integer> positionOfCardsToBeSwaped){
        ArrayList<District> districtDeleted=new ArrayList<>();
        for(int k=0;k<positionOfCardsToBeSwaped.size();k++){
            ArrayList<District> districtCards=bot.getPlayer().getDistrictCards();
            districtDeleted.add(districtCards.get(positionOfCardsToBeSwaped.get(k)));
            bot.getPlayer().getDistrictCards().remove(positionOfCardsToBeSwaped.get(k).intValue());
        }
        return districtDeleted;
    }
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters, PrintCitadels printC){
        ArrayList<Integer> magicianAction=bot.chooseMagicianAction();
        //If the magicianAction is empty then we have to exchange with another character
        if (magicianAction.size()==0){
            Character characterToSwapWith=bot.chooseCharacterForMagician(hashOfCharacters);
            Optional<Behaviour> botForSwap=hashOfCharacters.get(characterToSwapWith);
            if (botForSwap.isPresent()){
                swapCardsWithBot(bot,botForSwap.get());
                printC.printMagicianSpellSwapHands(characterToSwapWith);}
            else
                printC.printMagicianSpellFailed(characterToSwapWith);}
        //If the magicianAction isn't empty it's an array of Integer wish are the position of the Cards to be Swapped
        else
            printC.printMagicianSpellSwapCards(swapCardsWithDeck(bot,magicianAction));
            for(int k=0;k<magicianAction.size();k++)
                bot.takeCard(bot.pickCard());
    }
}
