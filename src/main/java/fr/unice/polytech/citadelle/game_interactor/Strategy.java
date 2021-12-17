package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;

public class Strategy {
    Player player;
    Board board;
    Predict predict;
    int numberOfCharacter;
    public Strategy(int numberOfCharacter,Board board,Player player){
        predict=new Predict(board);
        this.numberOfCharacter=numberOfCharacter;
        this.board=board;
        this.player=player;
    }

    public Strategy(int numberOfCharacter,Board board,Player player, Predict predict){
        predict=new Predict(board);
        this.numberOfCharacter=numberOfCharacter;
        this.board=board;
        this.player=player;
        this.predict = predict;
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
        ArrayList<String> listOfCharacterToNotKill=new ArrayList<>();
        listOfCharacterToNotKill.add("Assassin");
        listOfCharacterToNotKill.addAll(board.getListOfPlayerWhoHasAlreadyPlayedStringVersion());
        //listOfCharacterToNotKill.addAll(board.getDeckCharacter().getBurnedAndVisibleCharacters());

        Player playerWithClosestScore=findThePlayerWithClosestScoreAssassin();
        return getAPrediction(playerWithClosestScore, listOfCharacterToNotKill);

    }

    public Character chooseCharacterForThiefAdvanced(){
        ArrayList<String> listOfCharacterToNotSteal=new ArrayList<>();
        listOfCharacterToNotSteal.add("Assassin");
        listOfCharacterToNotSteal.add("Thief");
        listOfCharacterToNotSteal.addAll(board.getListOfPlayerWhoHasAlreadyPlayedStringVersion());
        Player playerWithMostGolds=findThePlayerWithMostGolds();
        return(predict.predictWhoIsPlayer(playerWithMostGolds,listOfCharacterToNotSteal));

    }

    private Player findThePlayerWithMostGolds() {
        ArrayList<Player> listOfPlayer= board.getListOfPlayer();
        int k=0;
        //We want to find the player with the PredictedScore the closest to the score of our Player
        int mostGoldsThatAPlayerHas=listOfPlayer.get(k).getGolds();
        Player playerWithMostGold=listOfPlayer.get(k);

        for (Player playerComparing : listOfPlayer) {
            if (playerWithMostGold.equals(this.player)){
                playerWithMostGold = playerComparing;
                mostGoldsThatAPlayerHas = player.getGolds();
            }
            else if (!playerComparing.equals(this.player)){
                int goldOfPlayerComparing = playerComparing.getGolds();
                if (mostGoldsThatAPlayerHas > goldOfPlayerComparing) {
                    playerWithMostGold = playerComparing;
                    mostGoldsThatAPlayerHas = player.getGolds();}
            }
        }
        return (playerWithMostGold);
    }

    public Player findThePlayerWithClosestScoreAssassin(){
        int predictedScore=playerPredictScore(player);
        ArrayList<Player> listOfPlayer=board.getListOfPlayer();
        int k=0;
        //We want to find the player with the PredictedScore the closest to the score of our Player
        int scoreDiffenreceWithClosestScore=abs(playerPredictScore(listOfPlayer.get(k))-predictedScore);
        Player playerWithClosestScore=listOfPlayer.get(k);
        while (playerWithClosestScore.equals(this.player)){
            scoreDiffenreceWithClosestScore=abs(playerPredictScore(listOfPlayer.get(k))-predictedScore);
            playerWithClosestScore=listOfPlayer.get(k);
            k++;
        }

        for (Player playerComparing : listOfPlayer) {
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
        String colorOfDistrictToDestroy = has4districtWithSameColour(playerToDestroy);
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
        int playerToDestroyCitySize = playerToDestroy.getCity().getSizeOfCity();
        ArrayList <District> playerToDestroyCity = playerToDestroy.getCity().getBuiltDistrict();
        int playerGolds = player.getGolds();
        District districtToDestroy = null;

        // Warlord can't destroy a completed city.
        if (playerToDestroyCitySize >= 8)
            return null;

        for (District districtToCheck : playerToDestroyCity){
            // Check if the current district isn't a keep and check if the player has enough money to destroy the district.
            if((!districtToCheck.getName().equals("Keep")) && districtToCheck.getValue() - 1 <= playerGolds){
                districtToDestroy =
                        warlordInterestScore(districtToCheck, playerToDestroy) > warlordInterestScore(districtToDestroy, playerToDestroy)
                        ? districtToCheck : districtToDestroy;
            }

        }
        return districtToDestroy;
    }
    public ArrayList<Integer> chooseMagicianAction() {
        return(new ArrayList());
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
    public String has4districtWithSameColour(Player player){
        String[] listOfColour = {"Blue", "Red", "Green", "Yellow", "Purple"};
        for (String colour : listOfColour){
            if (countNumberOfDistrictWithColor(player, colour) >= 3)
                return colour;
        }
        return null;
    }

    public Character getAPrediction(Player player, ArrayList<String> listOfCharacterToNotKill){
    	return predict.predictWhoIsPlayer(player,listOfCharacterToNotKill);
    }

	public Predict getPredict() {
		return predict;
	}

	public Optional<Character> getCharacterOfPlayer(Player player) {
		return board.gethashOfViewCharacters().get(player);
	}
}
