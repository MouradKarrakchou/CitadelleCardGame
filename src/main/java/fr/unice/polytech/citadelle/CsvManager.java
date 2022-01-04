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
        if(!emptyFile()) update();
    }

    void existingFile() throws Exception {
        File csvFile = new File("src\\main\\resources\\save\\results.csv");
        if(!csvFile.isFile()) createFile();
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

    boolean emptyFile() throws Exception {
        if(getNumberOfLine() == 1) {
            append();
            return true;
        }

        return false;
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

            //6 = maximum of possible players minus one
            for(int j = 0; j < 6 - i; j++) {
                toWrite += "0,";
            }

            //WR GP

            record = toWrite.split(",");
            writer.writeNext(record);
            toWrite = "";
        }

        //close the writer
        writer.close();
    }

    List<String[]> read() throws Exception {
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\save\\results.csv"), ',', '"', 1);

        //Read all rows at once
        return reader.readAll();
    }

    String[] getLine(Integer index) throws Exception {
        List<String[]> allRows = read();

        return allRows.get(index);
    }

    int getNumberOfLine() throws Exception {
        List<String[]> allRows = read();
        return allRows.size();
    }

    void update() throws Exception {
        List<String[]> allRows = read();

        int rankPosition = 1;
        for(Player player : leaderboard) {

                for (String[] row : allRows) {
                    if(row[0].equals(player.getName())) {
                        row[rankPosition] += 1;
                    }
                }

            rankPosition++;
        }
    }



}
