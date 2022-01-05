package fr.unice.polytech.citadelle.game_interactor.game_behaviour;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_interactor.game_strategy.RichalphonseStrategy;
import fr.unice.polytech.citadelle.game_interactor.game_strategy.Situation;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Richalphonse extends Behaviour {
    RichalphonseStrategy richStrat;
    Situation currentBestSituation;
    

    public Richalphonse(Player player, Board board) {
        super(player, board);
        richStrat=new RichalphonseStrategy(board,player);
    }

    /**
     * Execute the spell of the Library which is keeping both card
     * picked instead of choosing one and putting back the other
     * @param spellDistrict
     * @param deckDistrict
     */
    public void executeSpell(ArrayList<SpellDistrict> spellDistrict, DeckDistrict deckDistrict) {
        spellDistrict.get(0).librarySpell(player, deckDistrict);
    }

    /**
     * Build expensive districts
     */
    @Override
    public void normalBehaviour() {
        DeckDistrict deckDistrict = board.getDeckDistrict();
        int goldOfPlayer = player.getGolds();
        if (goldOfPlayer == 0 || cityMan.districtWeHaveEnoughMoneyToBuild(goldOfPlayer + 2).size() > 0 || board.getDeckDistrict().getSize() < 2) {
            takeGold();
        }
        else {
            ArrayList<SpellDistrict> spellDistrict = new ArrayList<>();
            for (District district : player.getCity().getBuiltDistrict()) {
                if (district.getName().equals("Library")) spellDistrict.add((SpellDistrict) district);
            }
            if (spellDistrict.size() != 0) executeSpell(spellDistrict, deckDistrict);
            else {
                District choosenDistrictCard = pickCardsInDeck();
                takeCard(choosenDistrictCard);
            }
        }
        ifPossibleBuildADistrict();
    }

    @Override
    public void endGameBehaviour() {
        normalBehaviour();
    }

    @Override
    public void lastTurnBehaviour() {
        normalBehaviour();
    }

    /**
     * Choose between 2 cards
     * @param firstDistrict
     * @param secondDistrict
     * @return the chosen card
     */
    @Override
    public District chooseBetweenTwoCards(District firstDistrict, District secondDistrict) {
        DeckDistrict deckDistrict = board.getDeckDistrict();
        ArrayList<District> pickedCards = new ArrayList<>();
        pickedCards.add(firstDistrict);
        pickedCards.add(secondDistrict);
        District chosenCard = selectTheHigherDistrict(pickedCards);
        if(chosenCard.equals(firstDistrict))
            executor.putCardBackInDeck(deckDistrict, secondDistrict);
        else
            executor.putCardBackInDeck(deckDistrict, firstDistrict);
        return chosenCard;
    }
    /**
     * Select a character to use spell according to the player current character.
     * @param hashOfCharacters The associated hash of character & bot.
     * @return The character to use spell on.
     */
    @Override
    public Character selectCharacterForSpell(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        if (currentBestSituation.getTargetCharacter().isEmpty())
            return super.selectCharacterForSpell(hashOfCharacters);
        return(currentBestSituation.getTargetCharacter().get());
    }

    /**
     * According to the Warlord strategy, will try to choose the suitable Player to use Warlord spell.
     * @return The player to destroy a district.
     */
    @Override
    public Player selectPlayerForWarlord() {
        if (currentBestSituation.getTargetPlayer().isEmpty())
            return strategy.choosePlayerForWarlordRandom();
        return (currentBestSituation.getTargetPlayer().get());
    }
    /**
     * According to the Magician strategy, will try to choose the suitable Player to use Magician spell.
     * @return The Player to swap card with.
     */
    public Player choosePlayerForMagicianSpell() {
        if (currentBestSituation.getTargetPlayer().isEmpty())
            return strategy.choosePlayerForMagicianRandom();
        return currentBestSituation.getTargetPlayer().get();
    }

    /**
     * return the position of the Cards that he wants to swap
     * @return Empty -> The magician choose to swap its cards with another player.
     * 			Not empty -> The Array of card to swap with the deck of district of the board.
     */
    @Override
    public ArrayList<District> chooseMagicianAction() {
        return new ArrayList<>();
    }
    /**
     * Choose the best character according to the bot strategy.
     * @param bot The bot that control the player.
     * @return The preferred Character.
     */
    @Override
    public Character chooseCharacterWithStrategy(Behaviour bot) {
    	Situation situationWeAreIn=richStrat.getBestSituation(board.getDeckCharacter().getDeckCharacter());
        this.currentBestSituation=situationWeAreIn;
        if (situationWeAreIn.getCharacterToChoose().isEmpty())
            return board.getDeckCharacter().getDeckCharacter().remove(0);
        return situationWeAreIn.getCharacterToChoose().get();
    }


}
