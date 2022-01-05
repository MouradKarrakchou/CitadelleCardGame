package fr.unice.polytech.citadelle;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

public class CsvManager {

    ArrayList<Player> leaderboard;

    CsvManager (ArrayList<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    CsvManager(){}

    void saveFile() throws Exception {
        existingFile();
        if(!emptyFile()) {
            updateWinrateAverage(updateRankAll());
            newChallenger();
        }
    }

    void existingFile() throws Exception {
        File csvFile = new File("save\\results.csv");
        if(!csvFile.isFile()) createFile();
    }

    boolean emptyFile() throws Exception {
        if(getNumberOfLine() == 1) {
            append();
            return true;
        }

        return false;
    }

    void createFile() throws Exception {
        String csv = "save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        //Create record
        String [] record = "BotType,1,2,3,4,5,6,7,Win-rate,Games played,Total score,Average score".split(",");

        //Write the record to file
        writer.writeNext(record);

        //close the writer
        writer.close();
    }


    void append() throws Exception {
        String csv = "save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        String [] record;

        String toWrite = "";
        int numberOfPlayers = leaderboard.size();
        for(int i = 0; i < numberOfPlayers; i++) {
            toWrite += leaderboard.get(i).getName() + ",";
            for(int j = 0; j < i; j++) {
                toWrite += "0,";
            }

            toWrite += 1 + ",";

            //8 = maximum of possible players minus one plus WR plus GP
            for(int j = 0; j < 8 - i; j++) {
                toWrite += "0,";
            }

            String score = leaderboard.get(i).getScore() + ",";
            //For the first time, the total score and the average score are equals
            for(int j = 0; j < 2; j++) {
                toWrite += score;
            }

            record = toWrite.split(",");
            writer.writeNext(record);
            toWrite = "";
        }

        //close the writer
        writer.close();

        write(winrate(read()));
    }

    List<String[]> read() throws Exception {
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader("save\\results.csv"), ',', '"', 0);

        //Read all rows at once
        return reader.readAll();
    }

    int getNumberOfLine() throws Exception {
        List<String[]> allRows = read();
        return allRows.size();
    }

    List<String[]> updateRankAll() throws Exception {
        List<String[]> allRows = read();

        int rankPosition = 1;
        for(Player player : leaderboard) {
            allRows = updateRankOnePlayer(player, allRows, rankPosition);
            rankPosition++;
        }

        return allRows;
    }

    List<String[]> updateRankOnePlayer(Player player, List<String[]> allRows, Integer rankPosition) {
        for (String[] row : allRows) {

            if(row[0].equals(player.getName())) {
                String rankToUpdate = row[rankPosition];
                int updatedRank = Integer.parseInt(rankToUpdate);
                updatedRank++;
                row[rankPosition] = String.valueOf(updatedRank);

                int updatedScore = Integer.parseInt(row[10]);
                updatedScore += player.getScore();
                row[10] = String.valueOf(updatedScore);
            }

        }

        return allRows;
    }

    void updateWinrateAverage(List<String[]> allRows) throws Exception {
        write(average(winrate(allRows)));
    }

    void write(List<String[]> allRows) throws Exception {
        String csv = "save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        for(String[] row : allRows) {
            writer.writeNext(row);
        }
        writer.close();
    }

    List<String[]> winrate(List<String[]> allRows) throws Exception {
        int numberOfLine = getNumberOfLine();
        int gamesCounter;

        for(String[] row : allRows.subList(1, numberOfLine)) {
            gamesCounter = Integer.parseInt(row[1]);
            for(int i = 2; i < 8; i++) {
                gamesCounter += Integer.parseInt(row[i]);
            }
            row[9] = String.valueOf(gamesCounter);

            StatsBot statsBot = new StatsBot(row[0], gamesCounter, Integer.parseInt(row[1]), Integer.parseInt(row[11]));

            row[8] = String.valueOf(statsBot.getWinrate());
        }

        return allRows;
    }

    List<String[]> average(List<String[]> allRows) throws Exception {
        int numberOfLine = getNumberOfLine();

        for(String[] row : allRows.subList(1, numberOfLine)) {
            row[11] = String.valueOf(Integer.parseInt(row[10]) / Integer.parseInt(row[9]));
        }

        return allRows;
    }

<<<<<<< HEAD
    void newChallenger() throws Exception {
        List<String[]> allRows = read();
        ArrayList<String> alreadyPlayed = new ArrayList<>();

        for(String[] row : allRows) {
            alreadyPlayed.add(row[0]);
        }

        int rank = 0;
        for(Player player : leaderboard) {
            if(!alreadyPlayed.contains(player.getName())) addTheNewChallenger(rank);
            rank++;
        }
    }

    void addTheNewChallenger(Integer rank) throws Exception {
        String csv = "save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        String [] record;

        String toWrite = "";
        toWrite += leaderboard.get(rank).getName() + ",";
        for(int j = 0; j < rank; j++) {
            toWrite += "0,";
        }

        toWrite += 1 + ",";

        for(int j = 0; j < 8 - rank; j++) {
            toWrite += "0,";
        }

        String score = leaderboard.get(rank).getScore() + ",";
        for(int j = 0; j < 2; j++) {
            toWrite += score;
        }

        record = toWrite.split(",");
        writer.writeNext(record);
        writer.close();
        write(winrate(read()));
    }


    ArrayList<StatsBot> getStatsBot() throws Exception {
=======
    public ArrayList<StatsBot> getStatsBot() throws Exception {
>>>>>>> d73b063988214454f108fa0440395e26956d3a83
        ArrayList<StatsBot> statsBots = new ArrayList<>();
        List<String[]> allRows = read();
        int numberOfLine = getNumberOfLine();

        for(String[] row : allRows.subList(1, numberOfLine)) {
            statsBots.add(new StatsBot(row[0], Integer.parseInt(row[9]), Integer.parseInt(row[1]), Integer.parseInt(row[11])));
        }

        return statsBots;
    }

    public void printCSV() throws Exception {
        PrintCitadels.startCSV();
        ArrayList<StatsBot> botStatsList = getStatsBot();

        Collections.sort(botStatsList);
        for (StatsBot statsBot : botStatsList)
            PrintCitadels.printStat(statsBot);
    }
}
