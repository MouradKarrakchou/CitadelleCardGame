package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;
import java.util.Collections;

public class Battlefieldator {
    private final CsvManager csv;
    private int nbRusher = 0;
    private int nbInvestor = 0;
    private int nbRichalphonse = 0;
    private int nbStrategator = 0;

    public Battlefieldator(CsvManager csv){
        this.csv = csv;
    }

    public String getBestBotName() throws Exception {
        ArrayList<StatsBot> statsBots = csv.getStatsBot();

        if(statsBots.size() == 0)
            return "RobotStrategator";

        Collections.sort(statsBots);
        String botName = statsBots.get(0).getName();
        return botName.substring(0, botName.length() - 2);
    }

    public String getSecondBotName() throws Exception {
        ArrayList<StatsBot> statsBots = csv.getStatsBot();

        if(statsBots.size() < 2)
            return "RobotRichalphonse";

        Collections.sort(statsBots);
        String botName = statsBots.get(1).getName();
        return botName.substring(0, botName.length() - 2);
    }

    public void runTheBattle() throws Exception {
        PrintCitadels.activateLevelWarning();
        String bestBotName = getBestBotName();

        initializeBestBot();
        Controller controller = new Controller();
        controller.initGame(nbRichalphonse, nbInvestor, nbRusher, nbStrategator);
        ArrayList<Player> leaderboard = controller.runGame();

        PrintCitadels.activateLevelInfo();
        PrintCitadels.printBattlefield(leaderboard, bestBotName);
    }

    private void initializeBestBot() throws Exception {
        switch (getBestBotName()) {
            case "RobotInvestor" -> nbInvestor = 4;
            case "RobotRusher" -> nbRusher = 4;
            case "RobotStrategator" -> nbStrategator = 4;
            case "RobotRichalphonse" -> nbRichalphonse = 4;
        }
    }

    private void initializeBotSecondBattle(String bestBot, String secondBestBot){
        ArrayList<String> nameList = new ArrayList<>();
        nameList.add(bestBot);
        nameList.add(secondBestBot);
        for(String botName : nameList)
            switch (botName) {
                case "RobotInvestor" -> nbInvestor = 1;
                case "RobotRusher" -> nbRusher = 1;
                case "RobotStrategator" -> nbStrategator = 1;
                case "RobotRichalphonse" -> nbRichalphonse = 1;
            }
        nbRusher = 2;
    }


    public void runTheSecondBattle() throws Exception {
        PrintCitadels.activateLevelWarning();
        Controller controller = new Controller();

        String bestBotName = getBestBotName();
        String secondBestBotName = getSecondBotName();

        initializeBotSecondBattle(bestBotName, secondBestBotName);
        controller.initGame(nbRichalphonse, nbInvestor, nbRusher, nbStrategator);
        ArrayList<Player> leaderboard = controller.runGame();

        PrintCitadels.activateLevelInfo();
        PrintCitadels.printBattlefieldSecondBattle(leaderboard, bestBotName, secondBestBotName);

    }
}
