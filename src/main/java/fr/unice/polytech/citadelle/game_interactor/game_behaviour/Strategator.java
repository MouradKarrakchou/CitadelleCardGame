package fr.unice.polytech.citadelle.game_interactor.game_behaviour;

import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

/**
 * Investor act a bit like the Investor but,
 * he is also smart on the way to choose his own character,
 * on how he will do his spell and try to find the
 * best target by predicting who has what
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class Strategator extends Behaviour{

    public Strategator(Player player, Board board) {
        super(player, board);
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
        PrintCitadels.printPhase("Endgame", player);
        normalBehaviour();
    }

    @Override
    public void lastTurnBehaviour() {
        PrintCitadels.printPhase("LAST TURN", player);
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

    @Override
    public Player selectPlayerForWarlord() {
        return(strategy.choosePlayerForWarlordAdvanced());
    }

    @Override
    public Player choosePlayerForMagicianSpell() {
        return (strategy.choosePlayerForMagicianAdvanced());
    }

    @Override
    public Character chooseCharacterForAssassin(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
        return (strategy.chooseCharacterForAssassinAdvanced());
    }

    @Override
    public Character chooseCharacterForThief(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
        return (strategy.chooseCharacterForThiefAdvanced());
    }

    @Override
    public ArrayList<District> chooseMagicianAction() {
        return (strategy.chooseMagicianActionAdvanced());
    }

    @Override
    public District chooseDistrictToDestroy(Player playerToDestroy) {
        return (strategy.chooseDistrictToDestroyAdvanced(playerToDestroy));
    }
}
