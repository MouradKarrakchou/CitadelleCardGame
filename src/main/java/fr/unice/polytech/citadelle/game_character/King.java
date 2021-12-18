package fr.unice.polytech.citadelle.game_character;

import java.util.LinkedHashMap;
import java.util.Optional;

import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * The player who has the king will choose a character first next round.
 * He will also win (+1) gold for each Nobility district.
 *
 * @author BONNET Killian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class King extends Character {
    public King(){
        super("King", 4 );
    }

    @Override
    public void spellOfTurn(Behaviour bot, LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        PrintCitadels.printKingSpell(bot.getPlayer());
        super.spellOfTurnDistrictFamily(bot,"King","Nobility");
    }}
