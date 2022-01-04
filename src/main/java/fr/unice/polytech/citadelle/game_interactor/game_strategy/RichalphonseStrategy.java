package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;

public class RichalphonseStrategy {
    Board board;
    Player currentPlayer;

    public RichalphonseStrategy(Board board, Player player) {
        this.board = board;
        this.currentPlayer = player;
    }

    public Character chooseCharacterWithStrategy() {
        return null;
    }
}
