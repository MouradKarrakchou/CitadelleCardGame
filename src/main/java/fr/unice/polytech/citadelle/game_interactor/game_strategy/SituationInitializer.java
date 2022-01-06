package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import com.sun.jdi.ArrayReference;
import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game_character.Character;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Optional;

public class SituationInitializer {
    public static ArrayList<Situation> generateAllSituations(Board board){
        ArrayList<Situation> AllSituations=new ArrayList<>();
        ArrayList<Character> characterAvailable;
        ArrayList<Character> characterNotAvailable;
        Character characterToChoose;
        String description;
        Situation situation;
        //Creating default situation (if no other situation is available)
        AllSituations.add(new Situation("No Perfect Setup has been found",Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),0));

        //Situation1: Choose the Assassin to kill the Thief because someone has a lot of golds.
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        description=new String("Situation1: Choose the Assassin to kill the Thief because someone has a lot of golds.");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),1);
        situation.setExistRichPlayer(true);
        AllSituations.add(situation);


        //Situation2:Guess that someone who is close to winning the game took the Thief
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Thief"));
        description=new String("Situation2:Guess that someone who is close to winning the game took the Thief ");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Thief")),Optional.empty(),2);
        situation.setExistRichPlayer(true);
        AllSituations.add(situation);

        //Situation3:Take Assassin to kill the warlord because I got the lead
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        description=new String("Situation3:Take Assassin to kill the Condotière because I got the lead ");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Warlord")),Optional.empty(),3);
        situation.setiHave6Districts(true);
        AllSituations.add(situation);

        //Situation4:If I think that someone who is going to finish took the warlord
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation4:I think that someone who is going to finish took the warlord ");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Warlord")),Optional.empty(),3);
        AllSituations.add(situation);

        //Situation5:Take The Assassin and kill the Architect if someone is advantaged to pick architect (more then 4 golds,1 district or more,already built 5 district)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterAvailable.add(board.findCharacter("Architect"));
        description=new String("Situation5:Take The Assassin and kill the Architect if someone is advantaged to pick architect (more then 4 golds,1 district or more,already built 5 district)");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Architect")),Optional.empty(),2);
        situation.setAPlayerCouldPlayArchitect(true);
        AllSituations.add(situation);

        //Situation6:Take the Architect if someone is advantaged to pick architect to deny him (more then 4 golds,1 district or more,already built 5 district)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Architect");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation6:Take the Architect if someone is advantaged to pick architect to deny him ");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),2);
        situation.setAPlayerCouldPlayArchitect(true);
        AllSituations.add(situation);

        //Situation7: Take the Architect if im advantaged (more then 4 golds,1 district or more)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Architect");
        characterAvailable.add(characterToChoose);
        description=new String("Situation7: Take the Architect if im advantaged (more then 4 golds,1 district or more");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),3);
        situation.setRichardCouldPlayArchitect(true);
        AllSituations.add(situation);

        //Situation8: Someone is close to finish the game (6 District built) we want to deny him by taking the King
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("King");
        characterAvailable.add(characterToChoose);
        description=new String("Situation8: Someone is close to finish the game (6 District built) we want to deny him by taking the King");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),4);
        situation.setAPlayerHas7Districts(false);
        AllSituations.add(situation);

        //Situation9: Someone is close to finish the game (6 District built) we want to deny him by taking the Assassin(because the king is already taken)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        description=new String("Situation9: Someone is close to finish the game (6 District built) we want to deny him by taking the Assassin(because the king is already taken)");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Bishop")),Optional.empty(),4);
        situation.setAPlayerHas7Districts(false);
        AllSituations.add(situation);

        //Situation10: Someone is close to finish the game (6 District built) we want to deny him by taking the Walord(because the king and assassin are already taken)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation10: Someone is close to finish the game (6 District built) we want to deny him by taking the Warlord(because the king and assassin are already taken)");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),4);
        situation.setAPlayerHas7Districts(false);
        AllSituations.add(situation);

        //Situation11: Someone is close to finish the game (6 District built) we want to deny him by taking the Bishop(because the king and assassin and Warlord are already taken)
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Bishop");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation11: Someone is close to finish the game (6 District built) we want to deny him by taking the Bishop(because the king and assassin and Warlord are already taken)");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),4);
        situation.setAPlayerHas7Districts(false);
        AllSituations.add(situation);


        //Situation12: The bot close to finish the game plays before me and probably took the King we take Assassin
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        description=new String("Situation12: The bot close to finish the game plays before me and probably took the King");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.of(board.findCharacter("King")),Optional.empty(),4);
        situation.setAPlayerHas7Districts(false);
        situation.setAPlayerCloseToWinPlayFirst(true);
        AllSituations.add(situation);

        //Situation13: The bot close to finish the game plays before me and probably took the King we take Warlord
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation13: The bot close to finish the game plays before me and probably took the King we take Warlord");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),4);
        situation.setAPlayerHas7Districts(false);
        situation.setAPlayerCloseToWinPlayFirst(true);
        AllSituations.add(situation);

        //Situation14: The bot close to finish the game plays before me and probably took the King we take Bishop
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Bishop");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation14: The bot close to finish the game plays before me and probably took the King we take Bishop");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),3);
        situation.setAPlayerHas7Districts(false);
        situation.setAPlayerCloseToWinPlayFirst(true);
        AllSituations.add(situation);

        //Situation15: The bot close to finish the game plays before me and probably took the King we take the magician to target him
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Magician");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation15: The bot close to finish the game plays before me and probably took the King we take the magician to target him ");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(-1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),4);
        situation.setAPlayerHas7Districts(false);
        situation.setAPlayerCloseToWinPlayFirst(true);
        AllSituations.add(situation);


        //Situation16: I have 6 district, I try to take the King to pick first next round
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("King");
        characterAvailable.add(characterToChoose);
        description=new String("Situation16: I have 6 district, I try to take the King to pick first next round");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),5);
        situation.setiHave6Districts(true);
        AllSituations.add(situation);

        //Situation17: I have 6 districts, I pick Assassin to kill the Warlord and protect my self
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        description=new String("Situation17: I have 6 districts, I pick Assassin to kill the Warlord and protect my self");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Warlord")),Optional.empty(),4);
        situation.setiHave6Districts(true);
        AllSituations.add(situation);


        //Situation18: I have 6 district, I take the Warlord to not be targeted by it and focus my ennemies
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation18: I have 6 district, I take the Warlord to not be targeted by it and focus my ennemies");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),3);
        situation.setiHave6Districts(true);
        AllSituations.add(situation);

        //Situation19: I have 6 district, I take the Bishop to be safe from the Warlord
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Bishop");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("King"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation19: I have 6 district, I take the Bishop to be safe from the Warlord");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),3);
        situation.setiHave6Districts(true);
        AllSituations.add(situation);

        //Situation20: I have 7 districts, I take the Assassin to build my last district
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        description=new String("Situation20: I have 7 districts, I take the Assassin to build my last district");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Bishop")),Optional.empty(),8);
        situation.setiHave7Districts(true);
        AllSituations.add(situation);

        //Situation22: I have 7 districts, I take the Warlord to build my last district
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation22: I have 7 districts, I take the Warlord to build my last district");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),8);
        situation.setiHave7Districts(true);
        AllSituations.add(situation);

        //Situation21: I have 7 districts, I take the Bishop to build my last district
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Bishop");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation21: I have 7 districts, I take the Bishop to build my last district");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),8);
        situation.setiHave7Districts(true);
        AllSituations.add(situation);

        //Situation23: The Player playing second have 7districts i take Assassin and target him
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Bishop"));
        description=new String("Situation23: The Player playing second have 7districts i take Assassin and target him");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Warlord")),Optional.empty(),6);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(1);
        AllSituations.add(situation);

        //Situation24: The Player playing second have 7districts i take Assassin and target him
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation24: The Player playing second have 7districts i take Assassin and target him");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Bishop")),Optional.empty(),6);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(1);
        AllSituations.add(situation);

        //Situation25: The Player playing third have 7districts I take Walord
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterAvailable.add(board.findCharacter("Assassin"));
        characterAvailable.add(board.findCharacter("Bishop"));
        description=new String("Situation25: The Player playing third have 7districts I take Walord");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(2);
        situation.setPlayOrder(1);
        AllSituations.add(situation);

        //Situation26: The Player playing third have 7districts I take Assassin
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterAvailable.add(board.findCharacter("Warlord"));
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Bishop"));
        description=new String("Situation26: The Player playing third have 7districts I take Assassin");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Thief")),Optional.empty(),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(2);
        situation.setPlayOrder(1);
        AllSituations.add(situation);

        //Situation27: The Player playing third have 7districts I take Warlord
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterAvailable.add(board.findCharacter("Bishop"));
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation27: The Player playing third have 7districts I take Warlord");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(2);
        situation.setPlayOrder(1);
        AllSituations.add(situation);


        //Situation28: The Player playing third have 7districts I take Assassin to not let him swap his cards
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterAvailable.add(board.findCharacter("Bishop"));
        characterAvailable.add(board.findCharacter("Magician"));
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation28: The Player playing third have 7districts I take Assassin to not let him swap his cards");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Magician")),Optional.empty(),7);
        situation.setAPlayerHasLotOfCard(true);
        situation.setAPlayerCloseToWinHasFewCard(true);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(2);
        situation.setPlayOrder(1);
        AllSituations.add(situation);

        //Situation29: The Player playing third have 7districts I take Assassin to kill him
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation29: The Player playing third have 7districts I take Assassin to kill him");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.of(board.findCharacter("Bishop")),Optional.empty(),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(2);
        situation.setAPlayerCloseToWinPlayFirst(false);
        AllSituations.add(situation);

        //Situation30: The Player playing third have 7districts I take Warlord to destroy his district
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Warlord");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Bishop"));
        characterNotAvailable.add(board.findCharacter("Assassin"));
        description=new String("Situation30: The Player playing third have 7districts I take Warlord to destroy his district");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(2);
        situation.setAPlayerCloseToWinPlayFirst(false);
        AllSituations.add(situation);


        //Situation31: The Player playing third have 7districts I take Magician to steal his cards
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Magician");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation31: The Player playing third has 7districts I take Warlord to destroy his district");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.of(true),7);
        situation.setAPlayerCloseToWinHasFewCard(false);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(2);
        AllSituations.add(situation);

        //Situation32: The Player playing third have 7districts I take Bishop to deny him
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Bishop");
        characterAvailable.add(characterToChoose);
        characterNotAvailable=new ArrayList<>();
        characterNotAvailable.add(board.findCharacter("Assassin"));
        characterNotAvailable.add(board.findCharacter("Warlord"));
        description=new String("Situation32: The Player playing third have 7districts I take Bishop to deny him");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.of(characterNotAvailable),Optional.of(1),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),7);
        situation.setAPlayerHas7Districts(true);
        situation.setDistanceBetweenRichardAndAPlayerCloseToWin(1);
        situation.setPlayOrder(2);
        AllSituations.add(situation);


        //Situation33: If I have a lot of Cards compared to others I take Assassin to kill Magician to cover my self
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Assassin");
        characterAvailable.add(characterToChoose);
        description=new String("Situation33: If I have a lot of Cards compared to others I take Assassin to kill Magician to cover my self");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Magician")),Optional.empty(),4);
        situation.setiHaveLotOfCard(true);
        AllSituations.add(situation);

        //Situation34: We are in the first rounds, I take Thief to win some golds
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Thief");
        characterAvailable.add(characterToChoose);
        description=new String("Situation34: We are in the first rounds, I take Thief to win some golds");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Architect")),Optional.empty(),1);
        situation.setRoundNumberLess5(true);
        AllSituations.add(situation);

        //Situation35: I have not much Cards i should take Magician
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Magician");
        characterAvailable.add(characterToChoose);
        description=new String("Situation35: I have not much Cards i should take Magician");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.of(board.findCharacter("Architect")),Optional.empty(),3);
        situation.setAPlayerHasLotOfCard(true);
        situation.setiHaveFewCard(true);
        situation.setTargetPlayerHasMostCard(Optional.of(true));
        AllSituations.add(situation);

        //Situation36: There are few Players in the game so i will pick the King to try to stay king next rounds.
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("King");
        characterAvailable.add(characterToChoose);
        description=new String("Situation36: There are few Players in the game so i will pick the King to try to stay king next rounds.");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),4);
        situation.setRichardDontPlayFirst(true);
        situation.setNumberOfPlayerLess5(true);
        AllSituations.add(situation);

        //Situation37: I pick Merchant because i have no better choices and it gives me some golds.
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Merchant");
        characterAvailable.add(characterToChoose);
        description=new String("Situation37: I pick Merchant because i have no better choices and it gives me some golds.");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),1);
        AllSituations.add(situation);

        //Situation38: I have a lot of golds i will try to take Architect to build a lot.
        characterAvailable=new ArrayList<>();
        characterToChoose=board.findCharacter("Architect");
        characterAvailable.add(characterToChoose);
        description=new String("Situation38: I have a lot of golds i will try to take Architect to build a lot.");
        situation=new Situation(description,Optional.of(characterAvailable),Optional.empty(),Optional.empty(),Optional.of(characterToChoose),Optional.empty(),Optional.empty(),4);
        situation.setRichardHasMoreOf6Golds(true);
        AllSituations.add(situation);

        return(AllSituations);
    }
}
