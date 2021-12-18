package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.characters_class.*;
import fr.unice.polytech.citadelle.game.*;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.purple_districts.Graveyard;
import fr.unice.polytech.citadelle.game.purple_districts.Observatory;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.Predict;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PredictTest {

    Board board = new Board(new ArrayList<>(), Initializer.createListOfAllCharacter(), new DeckDistrict(), new DeckCharacter());
    Predict predict = new Predict(board);
    ArrayList<Character> allCharacters = board.getListOfCharacter();

    @RepeatedTest(100)
    @Test
    void targetableCharactersForPredictWhoIsPlayerTest() {

        ArrayList<Character> untargetableCharacter = new ArrayList<>();
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.BISHOP_INDEX));
        untargetableCharacter.add(board.getListOfCharacter().get(Initializer.THIEF_INDEX));

        ArrayList<Character> listToReturn = new ArrayList<>();
        for(Character character : allCharacters) {
            if(!character.getName().equals("Bishop") && !character.getName().equals("Thief"))
                listToReturn.add(character);
        }

        assertEquals(listToReturn, predict.targetableCharactersForPredictWhoIsPlayer(untargetableCharacter));
    }

    @RepeatedTest(100)
    @Test
    void allCharactersTest() {
        assertEquals(allCharacters, predict.allCharacters());
    }

    @RepeatedTest(100)
    @Test
    void canBeArchitectTest() {
        Player player = new Player("Player");
        player.addDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.addDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.addDistrict(new District("Battlefield",3,"Red","Soldiery"));

        player.setGolds(6);

        assertEquals(true, predict.canBeArchitect(player, new ArrayList<>()));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeArchitectTest() {
        Player player = new Player("Player");
        player.addDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.addDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.addDistrict(new District("Battlefield",3,"Red","Soldiery"));

        player.setGolds(6);

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Architect());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeBishopTest() {
        Player player = new Player("Player");
        player.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.buildDistrict(new District("Battlefield",3,"Red","Soldiery"));
        player.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.buildDistrict(new District("Battlefield",3,"Red","Soldiery"));

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeBishopTest() {
        Player player = new Player("Player");
        player.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.buildDistrict(new District("Battlefield",3,"Red","Soldiery"));
        player.buildDistrict(new Observatory("Observatory", 5,"Purple","Prestige"));
        player.buildDistrict(new Graveyard("Graveyard", 5,"Purple","Prestige"));
        player.buildDistrict(new District("Battlefield",3,"Red","Soldiery"));

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Bishop());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeKingTest() {
        Player player = new Player("Player");
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeKingTest() {
        Player player = new Player("Player");
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));
        player.buildDistrict(new District("Castle",4,"Yellow","Nobility"));

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new King());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeMerchantTest() {
        Player player = new Player("Player");
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeMerchantTest() {
        Player player = new Player("Player");
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.buildDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Merchant());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeThiefTest() {
        Player player = new Player("Player");
        player.setGolds(3);

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeThiefTest() {
        Player player = new Player("Player");
        player.setGolds(3);

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Thief());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeMagicianTest() {
        Player player = new Player("Player");
        player.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void cannotBeMagicianTest() {
        Player player = new Player("Player");
        player.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Merchant());

        assertEquals(false, predict.canBeArchitect(player, untargetable));
    }

    @RepeatedTest(100)
    @Test
    void canBeWarlordTest() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();

        assertEquals(true, predict.canBeArchitect(player1, untargetable));
    }

    @Test
    void cannotBeWarlordTest() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));
        player2.addDistrict(new District("Docks", 3, "Green", "Trade and Handicrafts"));

        ArrayList<Character> untargetable = new ArrayList<>();
        untargetable.add(new Merchant());

        assertEquals(false, predict.canBeArchitect(player1, untargetable));
    }



}
