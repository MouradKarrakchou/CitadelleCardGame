package fr.unice.polytech.citadelle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;

public class CsvManager {

    ArrayList<Player> leaderboard;

    CsvManager (ArrayList<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    void saveFile() throws Exception {
        existingFile();
        if(!emptyFile()) updateWinrate(update());
    }

    void existingFile() throws Exception {
        File csvFile = new File("src\\main\\resources\\save\\results.csv");
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
        String csv = "src\\main\\resources\\save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        //Create record
        String [] record = "BotType,1,2,3,4,5,6,7,Win-rate,Games played".split(",");

        //Write the record to file
        writer.writeNext(record);

        //close the writer
        writer.close();
    }


    void append() throws Exception {
        String csv = "src\\main\\resources\\save\\results.csv";
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
        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\save\\results.csv"), ',', '"', 0);

        //Read all rows at once
        return reader.readAll();
    }

    int getNumberOfLine() throws Exception {
        List<String[]> allRows = read();
        return allRows.size();
    }

    List<String[]> update() throws Exception {
        List<String[]> allRows = read();

        int rankPosition = 1;
        for(Player player : leaderboard) {
            allRows = updateLine(player, allRows, rankPosition);
            rankPosition++;
        }

        return allRows;
    }

    void updateWinrate(List<String[]> allRows) throws Exception {
        write(winrate(allRows));
    }

    void write(List<String[]> allRows) throws Exception {
        String csv = "src\\main\\resources\\save\\results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        for(String[] row : allRows) {
            writer.writeNext(row);
        }
        writer.close();
    }

    List<String[]> updateLine(Player player, List<String[]> allRows, Integer rankPosition) {
        for (String[] row : allRows) {

            if(row[0].equals(player.getName())) {
                String rankToUpdate = row[rankPosition];
                int updatedRank = Integer.parseInt(rankToUpdate);
                updatedRank++;
                row[rankPosition] = String.valueOf(updatedRank);
            }

        }

        return allRows;
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

            StatsBot statsBot = new StatsBot(row[0], gamesCounter, Integer.parseInt(row[1]));

            row[8] = String.valueOf(statsBot.getWinrate());
        }

        return allRows;
    }

}
