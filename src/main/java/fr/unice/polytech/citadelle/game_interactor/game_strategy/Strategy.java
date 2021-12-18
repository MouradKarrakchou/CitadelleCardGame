package fr.unice.polytech.citadelle.game_interactor.game_strategy;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_character.Character;
import fr.unice.polytech.citadelle.game_engine.Initializer;
import fr.unice.polytech.citadelle.game_interactor.game_behaviour.Behaviour;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Strategy {
    Player player;
    Board board;
    Predict predict;
    int numberOfCharacter;

    private final ArrayList<Character> listOfAllCharacters = Initializer.createListOfAllCharacter();
    
    public Strategy(int numberOfCharacter,Board board,Player player){
        predict=new Predict(board, player);
        this.numberOfCharacter=numberOfCharacter;
        this.board=board;
        this.player=player;
    }


    public int randomInt(int scope) {
        Random random = new Random();
        return (random.nextInt(scope));
    }


    public Character chooseCharacterForThiefRandom(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
        List<Character> list = hashOfCharacters.keySet().stream().toList();
        Character randomCharacter=list.get(randomInt(8));
        while (randomCharacter.getName().equals("Assassin") || randomCharacter.getName().equals("Thief") || !randomCharacter.isCharacterIsAlive()) {
            randomCharacter=list.get(randomInt(numberOfCharacter));
        }
        return randomCharacter;
    }


    public Character chooseCharacterForAssassinRandom(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters) {
        List<Character> list = hashOfCharacters.keySet().stream().toList();
        Character randomCharacter=list.get(randomInt(8));
        while (randomCharacter.getName().equals("Assassin")) {
            randomCharacter=list.get(randomInt(numberOfCharacter));
        }
        return(randomCharacter);
    }

    public Character chooseCharacterForMagicianRandom(LinkedHashMap<Character, Optional<Behaviour>> hashOfCharacters){
        List<Character> list = hashOfCharacters.keySet().stream().toList();
        Character randomCharacter=list.get(randomInt(numberOfCharacter));
        while (randomCharacter.getName().equals("Magician"))
            randomCharacter=list.get(randomInt(numberOfCharacter));
        return randomCharacter;
    }

    /**
     * Randomly choose a player (self-excluded) to destroy a district.
     * @return The chosen player.
     */
    public Player choosePlayerForWarlordRandom() {
        List<Player> list = board.getListOfPlayer();
        Player randomPlayer=list.get(randomInt(board.getListOfPlayer().size()));
        while (randomPlayer.getName().equals("Warlord")) {
            randomPlayer=list.get(randomInt(numberOfCharacter));
        }
        return randomPlayer;
    }

    /**
     * According to the G.L.P.A. (game leaderboard prediction algorithm), the Warlord will attack the player in front of him.
     * If the G.L.P.A. predicts the player as the first, Warlord will attack the player behind him.
     * @return The player to attack. (can be null if the spell is not used).
     */
    public Player choosePlayerForWarlordAdvanced() {
        List<Player> listOfPlayers = board.getListOfPlayer();

        Player playerToDestroy = null;
        int scoreOfPlayerToDestroy = 0;

        for (Player playerToCheck : listOfPlayers) {
            // For now Warlord will not self-destroy a district.
            if ((playerToCheck != player) && playerPredictScore(playerToCheck) > scoreOfPlayerToDestroy){
                playerToDestroy = playerToCheck;
                scoreOfPlayerToDestroy = playerPredictScore(playerToCheck);
            }
        }
        return playerToDestroy;
    }

    public Character chooseCharacterForAssassinAdvanced(){
        ArrayList<Character> listOfCharacterToNotKill=new ArrayList<>();
        listOfCharacterToNotKill.add(board.getListOfCharacter().get(Initializer.ASSASSIN_INDEX));
        listOfCharacterToNotKill.addAll(board.getListOfPlayerWhoHasAlreadyPlayed());
        listOfCharacterToNotKill.addAll(board.getDeckCharacter().getBurnedAndVisibleCharacters());

        Player playerWithClosestScore=findThePlayerWithClosestScoreAssassin();
        return getAPrediction(playerWithClosestScore, listOfCharacterToNotKill);

    }
    public Character chooseCharacterForThiefAdvanced(){
        ArrayList<Character> listOfCharacterToNotSteal=new ArrayList<>();
        listOfCharacterToNotSteal.add(board.getListOfCharacter().get(Initializer.ASSASSIN_INDEX));
        listOfCharacterToNotSteal.add(board.getListOfCharacter().get(Initializer.THIEF_INDEX));
        Character deadCharacter=board.findDeadCharacter();
        if (deadCharacter!=null) listOfCharacterToNotSteal.add(deadCharacter);
        listOfCharacterToNotSteal.addAll(board.getListOfPlayerWhoHasAlreadyPlayed());
        Player playerWithMostGolds=findThePlayerWithMostGolds();
        return(predict.predictWhoIsPlayer(playerWithMostGolds,listOfCharacterToNotSteal));
    }
    public Character chooseCharacterForMagicianAdvanced(){
        ArrayList<Character> listOfCharacterToNotSteal=new ArrayList<>();
        Player playerWithMostCards=findThePlayerWithMostCards();
        return(predict.predictWhoIsPlayer(playerWithMostCards,listOfCharacterToNotSteal));
    }

    public Player findThePlayerWithMostCards() {
        ArrayList<Player> listOfPlayer= board.getListOfPlayer();
        //We want to find the Player with the most cards
        int mostDistrictCardsThatAPlayerHas=listOfPlayer.get(0).getDistrictCardsSize();
        Player playerWithMostDistrictGames=listOfPlayer.get(0);

        for (Player playerComparing : listOfPlayer) {
            if (playerWithMostDistrictGames.equals(this.player)){
                playerWithMostDistrictGames = playerComparing;
                mostDistrictCardsThatAPlayerHas = playerComparing.getDistrictCardsSize();
            }
            else if (!playerComparing.equals(this.player)){
                int numberOfDistrict = playerComparing.getDistrictCardsSize();
                if (mostDistrictCardsThatAPlayerHas < numberOfDistrict) {
                    playerWithMostDistrictGames = playerComparing;
                    mostDistrictCardsThatAPlayerHas = playerComparing.getDistrictCardsSize();}
            }
        }
        PrintCitadels.printMagicianAdvancedChoice(playerWithMostDistrictGames,player);
        return (playerWithMostDistrictGames);
    }

    public Player findThePlayerWithMostGolds() {
        ArrayList<Player> listOfPlayer= board.getListOfPlayer();
        //In this list we got the Assassin (if there is one) and he can't steal from him
        ArrayList<Player> listOfPlayerToNotTarget=board.getListOfPlayerWhoPlayed();
        //We want to find the Player with the most golds
        int mostGoldsThatAPlayerHas=listOfPlayer.get(0).getGolds();
        Player playerWithMostGold=listOfPlayer.get(0);

        for (Player playerComparing : listOfPlayer) {
            if (playerWithMostGold.equals(this.player) || listOfPlayerToNotTarget.contains(playerWithMostGold)){
                playerWithMostGold = playerComparing;
                mostGoldsThatAPlayerHas = player.getGolds();
            }
            else if (!playerComparing.equals(this.player)&& !listOfPlayerToNotTarget.contains(player)){
                int goldOfPlayerComparing = playerComparing.getGolds();
                if (mostGoldsThatAPlayerHas < goldOfPlayerComparing) {
                    playerWithMostGold = playerComparing;
                    mostGoldsThatAPlayerHas = player.getGolds();}
            }
        }
        PrintCitadels.printThiefAdvancedChoice(playerWithMostGold);
        return (playerWithMostGold);
    }

    public Player findThePlayerWithClosestScoreAssassin(){
        int predictedScore=playerPredictScore(player);
        ArrayList<Player> listOfPlayer=board.getListOfPlayer();
        //We want to find the player with the PredictedScore the closest to the score of our Player
        int scoreDiffenreceWithClosestScore=abs(playerPredictScore(listOfPlayer.get(0))-predictedScore);
        Player playerWithClosestScore=listOfPlayer.get(0);

        for (Player playerComparing : listOfPlayer) {
            if (playerWithClosestScore.equals(this.player))
            {scoreDiffenreceWithClosestScore=abs(playerPredictScore(playerComparing)-predictedScore);
                playerWithClosestScore=playerComparing;}
            if (!playerComparing.equals(this.player)){
                int scoreDifference = abs(playerPredictScore(playerComparing) - predictedScore);
                if (scoreDiffenreceWithClosestScore > scoreDifference) {
                    playerWithClosestScore = playerComparing;
                    scoreDiffenreceWithClosestScore = scoreDifference;}
            }
        }
        PrintCitadels.printAssassinAdvancedChoice(playerWithClosestScore,predictedScore,scoreDiffenreceWithClosestScore);
        return playerWithClosestScore;
    }

    /**
     * For a given district, return the interest score the warlord has in destroying this district.
     * @param district The district to proceed.
     * @return The interest score the district.
     */
    public int warlordInterestScore(District district, Player playerToDestroy) {
        if (district == null)
            return 0;

        // If the player has 4 districts of the same colour.
        String colorOfDistrictToDestroy = has3districtWithSameColour(playerToDestroy);
        if (colorOfDistrictToDestroy != null){
            if (district.getColor().equals(colorOfDistrictToDestroy))
                return 100000;
        }

        int interestScore = 0;
        switch(district.getName()){
            case "Dragon Gate", "University" -> interestScore = 10000;
            case "Haunted City" -> interestScore = 1000;
            case "School of Magic" -> interestScore = 100;
        }
        return interestScore;
    }

    /**
     * For a given player, will analyze player city to choose a district to destroy.
     * @param playerToDestroy The player to proceed.
     * @return The district to destroy (can be null if the spell is not used).
     */
    public District chooseDistrictToDestroy(Player playerToDestroy) {
        //Flags
        boolean playerToDestroyHasCompletedCity = false;
        boolean playerToDestroyIsBishop = false;

        int playerToDestroyCitySize = playerToDestroy.getCity().getSizeOfCity();
        ArrayList<District> playerToDestroyCity = playerToDestroy.getCity().getBuiltDistrict();
        int playerGolds = player.getGolds();
        District districtToDestroy = null;

        // Warlord can't destroy a Bishop district
        String characterName;
        if (playerToDestroy.getCharacter() == null)
            characterName = "";
        else
            characterName = playerToDestroy.getCharacter().getName();

        if (characterName.equals("Bishop"))
            playerToDestroyIsBishop = true;

        // Warlord can't destroy a completed city.
        if (playerToDestroyCitySize >= 8)
            playerToDestroyHasCompletedCity = true;

        if (!playerToDestroyIsBishop && !playerToDestroyHasCompletedCity)
            for (District districtToCheck : playerToDestroyCity) {
                // Check if the current district isn't a keep and check if the player has enough money to destroy the district.
                if ((!districtToCheck.getName().equals("Keep")) && districtToCheck.getValue() - 1 <= playerGolds) {
                    districtToDestroy =
                            warlordInterestScore(districtToCheck, playerToDestroy) > warlordInterestScore(districtToDestroy, playerToDestroy)
                                    ? districtToCheck : districtToDestroy;
                }
            }
        PrintCitadels.printWarlordAdvancedChoice(playerToDestroy, playerToDestroyHasCompletedCity, playerToDestroyIsBishop, districtToDestroy);
        return districtToDestroy;
    }


    public ArrayList<District> chooseMagicianActionForRandom() {
        return(new ArrayList());
    }

    public ArrayList<District> chooseMagicianActionAdvanced() {
        if (isThereAPlayerWithTwoTimesHisDistricts()){
            PrintCitadels.playerHasTwoTimesMoreHisCards(player);
            return (new ArrayList<>());
        }
        else{
            PrintCitadels.playerSwapWithDeck(player);
            ArrayList<District> cardsToBeSwapped=cardToBeSwapped();
            return(cardsToBeSwapped);}
    }

    public ArrayList<District> cardToBeSwapped() {
        ArrayList<District> listDistrictToSwap=new ArrayList<>();
        ArrayList<District> listDistrict=player.getDistrictCards();
        for (District districtToCheck : listDistrict){
            if (shouldBeSwapped(districtToCheck)) listDistrictToSwap.add(districtToCheck);
        }
        if (listDistrictToSwap.size()==0)
        {PrintCitadels.printNoCardsToBeSwapped(player);
            return(null);}
        else
            PrintCitadels.pritCardsToBeSwapped(player,listDistrictToSwap);
        return (listDistrictToSwap);
    }

    private boolean shouldBeSwapped(District districtToCheck) {
        if (player.getCity().getBuiltDistrict().stream().map(district -> district.getName()).toList().contains(districtToCheck.getName()))
            return(true);
        else
            return false;
    }

    public boolean isThereAPlayerWithTwoTimesHisDistricts() {
        ArrayList <Player> listOfPlayer=board.getListOfPlayer();
        for (Player playerComparing : listOfPlayer) {
            if (!playerComparing.equals(this.player) && playerComparing.getDistrictCardsSize()>=2*this.player.getDistrictCardsSize())
            return(true);
            }
        return(false);
    }

    /**
     * @param player The player to process.
     * @return The score formed by summing built district score.
     */
    public int cityDistrictScore(Player player){
        int sum = 0;
        ArrayList<District> playerCity = player.getCity().getBuiltDistrict();

        for (District district : playerCity)
            sum += district.getValue();

        return sum;
    }

    public int playerPredictScore(Player player) {
        // City Score processing.
        int scoreToPredict = cityDistrictScore(player);

        // Add 3 bonus points if the player has 5 built cites of different colors.
        if (hasFiveDistrictColors(player)) scoreToPredict += 3;

        // Predict an approximation of the score of the Player.
        return(scoreToPredict);
    }

    /**
     * @param player The player to process.
     * @return True/False, the player has 5 district colors in its city.
     */
    public boolean hasFiveDistrictColors(Player player){
        ArrayList<District> playerCity = player.getCity().getBuiltDistrict();
        boolean hasBlue = false;
        boolean hasRed = false;
        boolean hasGreen = false;
        boolean hasYellow = false;
        boolean hasPurple = false;

        for (District district : playerCity){
            switch (district.getColor()) {
                case "Blue" -> hasBlue = true;
                case "Red" -> hasRed = true;
                case "Green" -> hasGreen = true;
                case "Yellow" -> hasYellow = true;
                case "Purple" -> hasPurple = true;
            }
        }

        return (hasBlue && hasRed && hasGreen && hasYellow && hasPurple);
    }

    public int countNumberOfDistrictWithColor(Player player, String colour){
        ArrayList<District> playerCity = player.getCity().getBuiltDistrict();
        int count = 0;

        for (District district : playerCity){
            if (district.getColor().equals(colour))
                count++;
        }
        return count;
    }

    /**
     * @param player The player to process.
     * @return The color of the district that the player has more than 3 times.
     */
    public String has3districtWithSameColour(Player player){
        String[] listOfColour = {"Blue", "Red", "Green", "Yellow"};
        for (String colour : listOfColour){
            if (countNumberOfDistrictWithColor(player, colour) >= 3)
                return colour;
        }
        return null;
    }

    public Character getAPrediction(Player player, ArrayList<Character> listOfCharacterToNotKill){
    	return predict.predictWhoIsPlayer(player,listOfCharacterToNotKill);
    }

    public Character chooseCharacter(Behaviour bot) {
        //Choice of Assassin
        int index = chooseAssassin(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of Architect
        index = chooseArchitect(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of Magician
        index = chooseMagician(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of Thief
        index = chooseThief(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of King or Merchant (if they are both equality worth, King is chosen)
        index = chooseKingOrMerchant(bot);
        if (index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of Bishop
        index = chooseBishop(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        //Choice of Warlord (Last one because his spell has not been implemented yet)
        index = chooseWarlord(bot);
        if(index != -1) return board.getDeckCharacter().getDeckCharacter().remove(index);

        return board.getDeckCharacter().getDeckCharacter().remove(0);
    }

    int chooseAssassin(Behaviour bot) {
        //Choice of Assassin
        ArrayList<Character> listOfAssassin = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Assassin"))
                .collect(Collectors.toCollection(ArrayList::new));

        int counter = 0;
        for(Player player : board.getListOfPlayer()) {
            if(player.getCity().getBuiltDistrict().size() == 6 && listOfAssassin.size() != 0 && !player.equals(bot.getPlayer())) {
                for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                    if(character.getName().equals("Assassin")) return counter;
                    counter++;
                }
            }
        }

        return -1;
    }

    int chooseArchitect(Behaviour bot) {
        //Choice of Architect
        ArrayList<Character> listOfArchitect = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Architect"))
                .collect(Collectors.toCollection(ArrayList::new));

        int counter = 0;
        if(bot.getPlayer().getDistrictCardsSize() >= 3 && bot.getPlayer().getGolds() >= 6 && listOfArchitect.size() != 0) {
            for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                if(character.getName().equals("Architect")) return counter;
                counter++;
            }
        }

        return -1;
    }

    int chooseMagician(Behaviour bot) {
        //Choice of Magician
        ArrayList<Character> listOfMagician = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Magician"))
                .collect(Collectors.toCollection(ArrayList::new));

        int counter = 0;
        for(Player player : board.getListOfPlayer()) {
            if(bot.getPlayer().getDistrictCardsSize() < player.getDistrictCardsSize() && listOfMagician.size() != 0 && !player.equals(bot.getPlayer())) {
                for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                    if(character.getName().equals("Magician")) return counter;
                    counter++;
                }
            }
        }

        return -1;
    }

    int chooseThief(Behaviour bot) {
        //Choice of Thief
        ArrayList<Character> listOfThief = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Thief"))
                .collect(Collectors.toCollection(ArrayList::new));

        int counter = 0;
        for(Player player : board.getListOfPlayer()) {
            if(player.getGolds() >= 5 && listOfThief.size() != 0 && !player.equals(bot.getPlayer())) {
                for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                    if(character.getName().equals("Thief")) return counter;
                    counter++;
                }
            }
        }

        return -1;
    }

    int chooseKingOrMerchant(Behaviour bot) {
        //Choice of King or Merchant (if they are both equality worth, King is chosen)
        int counter = 0;
        int index = isThereAFamily(bot);
        if (index!=-1) {
            String nameOfCharacterChosen = listOfAllCharacters.get(index).getName();
            for (Character character : board.getDeckCharacter().getDeckCharacter()) {
                if (character.getName().equals(nameOfCharacterChosen))
                    return counter;
                counter++;
            }
        }

        return -1;
    }

    int chooseBishop(Behaviour bot) {
        //Choice of Bishop
        ArrayList<Character> listOfBishop = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Bishop"))
                .collect(Collectors.toCollection(ArrayList::new));
        int counter = 0;

        if(bot.getPlayer().getCity().getBuiltDistrict().size() >= 6 && listOfBishop.size() != 0) {
            for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                if(character.getName().equals("Bishop")) return counter;
                counter++;
            }
        }

        return -1;
    }

    int chooseWarlord(Behaviour bot) {
        //Choice of Warlord (Last one because his spell has not been implemented yet)
        ArrayList<Character> listOfWarlord = board.getDeckCharacter().getDeckCharacter().stream()
                .filter(character -> character.getName().equals("Warlord"))
                .collect(Collectors.toCollection(ArrayList::new));
        int counter = 0;

        for(Player player : board.getListOfPlayer()) {
            if(player.getCity().getBuiltDistrict().size() == 7 && listOfWarlord.size() != 0 && !player.equals(bot.getPlayer())) {
                for(Character character : board.getDeckCharacter().getDeckCharacter()) {
                    if(character.getName().equals("Warlord")) return counter;
                    counter++;
                }
            }
        }

        return -1;
    }

    /**
     * Check if the player of the given bot has a family in its city.
     * @param bot The given bot to check.
     * @return the integer index value of the family owned by the bot. Return -1 if bot does not own any family.
     */
    public int isThereAFamily(Behaviour bot) {
        Player playerOfBehaviour = bot.getPlayer();
        ArrayList<District> districtsInACity;
        ArrayList<String> nameOfFamilies = new ArrayList<>();

        nameOfFamilies.add("Nobility");
        nameOfFamilies.add("Trade and Handicrafts");

        for (String familyName : nameOfFamilies) {
            districtsInACity = playerOfBehaviour.getCity().getBuiltDistrict();

            ArrayList<District> districtFilter = districtsInACity.stream()
                    .filter(district -> district.getNameOfFamily().equals(familyName))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (familyName.equals("Nobility") && districtFilter.size() == 3)
                return Initializer.KING_INDEX;
            else if (familyName.equals("Trade and Handicrafts") && districtFilter.size() >= 3)
                return Initializer.MERCHANT_INDEX;
        }
        return (-1);
    }


	public Predict getPredict() {
		return predict;
	}

	public Optional<Character> getCharacterOfPlayer(Player player) {
		return board.gethashOfViewCharacters().get(player);
	}
}
