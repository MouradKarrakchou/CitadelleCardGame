package fr.unice.polytech.citadelle.game_interactor;

import fr.unice.polytech.citadelle.game.Board;
import fr.unice.polytech.citadelle.game.Character;
import fr.unice.polytech.citadelle.game.District;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

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

        Player playerWithClosestScore=findThePlayerWithClosestScoreAssassin();
        return (predict.predictWhoIsPlayer(playerWithClosestScore,listOfCharacterToNotKill));
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
    District chooseDistrictToDestroy(Player player){
        if (player.getCity().getSizeOfCity()==8)
            return(null);
        for(int k=0;k<player.getCity().getSizeOfCity();k++){
            District currentDistrictCheck=player.getCity().getBuiltDistrict().get(k);
            if (currentDistrictCheck.getValue()<this.player.getGolds()-1 && !currentDistrictCheck.getName().equals("DragonGate"))
                return(player.getCity().getBuiltDistrict().get(k));
        }
        return null;
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

}
