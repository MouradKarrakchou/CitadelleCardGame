package fr.unice.polytech.citadelle.game_character;

import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * The magician have 2 options:
 * Exchange his entire hand of cards (not the cards in your city) with the hand of another player
 * (this applies even if you have no cards in your hand, in which case you simply take the other player's cards).
 * Place any number of cards from your hand facedown at the bottom of the District Deck,
 * and then draw an equal number of cards from the top of the District Deck.
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Magician extends Character {
    public Magician(){
        super("Magician", 3);
    }

    /**
     * swap cards between the two bots
     * @param bot1
     * @param bot2
     */
    public void swapCardsWithBot(Behaviour bot1,Behaviour bot2) {
        ArrayList<District> deckDistrict1=bot1.getPlayer().getDistrictCards();
        ArrayList<District> deckDistrict2=bot2.getPlayer().getDistrictCards();
        ArrayList<District> deckDistrict1copy = new ArrayList<>(deckDistrict1);
        ArrayList<District> deckDistrict2copy = new ArrayList<>(deckDistrict2);
        deckDistrict1.clear();
        deckDistrict2.clear();
        deckDistrict1.addAll(deckDistrict2copy);
        deckDistrict2.addAll(deckDistrict1copy);
    }

    /**
     * put back the cards that he don't want at the end of the deck and remove them from his hand
     * @param bot
     * @param cardsToBeSwaped
     * @return the deleted districts
     */
    public ArrayList<District> swapCardsWithDeck(Behaviour bot, ArrayList<District> cardsToBeSwaped){
        ArrayList<District> districtDeleted=new ArrayList<>();
        for (District district : cardsToBeSwaped) {
            ArrayList<District> districtCards = bot.getPlayer().getDistrictCards();
            districtDeleted.add(district);
            districtCards.remove(district);
            bot.putDistrictBackInDeck(district);
        }
        return districtDeleted;
    }

    /**
     * Execute the spell
     * @param bot
     * @param hashOfCharacters
     */
    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        ArrayList<District> magicianAction=bot.chooseMagicianAction();
        //If the magicianAction is null it means that the bot decided to not do his spell
        if (magicianAction == null) return;
        //If the magicianAction is empty then we have to exchange with another character
        else if (magicianAction.size() == 0){
            Character characterToSwapWith=bot.selectCharacterForSpell(hashOfCharacters);
            Optional<Behaviour> botForSwap=hashOfCharacters.get(characterToSwapWith);
            if (botForSwap.isPresent()){
                swapCardsWithBot(bot,botForSwap.get());
                PrintCitadels.printMagicianSpellSwapHands(characterToSwapWith);}
            else
                PrintCitadels.printMagicianSpellFailed(characterToSwapWith);}
        //If the magicianAction isn't empty it's an array of Districts to be swapped
        else
            PrintCitadels.printMagicianSpellSwapCards(swapCardsWithDeck(bot,magicianAction));
            for(int k=0;k<magicianAction.size();k++)
                bot.takeCard(bot.pickCard());
    }
}
