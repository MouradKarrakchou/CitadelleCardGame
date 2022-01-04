package fr.unice.polytech.citadelle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;

public class CsvManager {

    ArrayList<Player> leaderboard;

    CsvManager (ArrayList<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    void saveFile() throws Exception {
        existingFile();
        write();
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

    void write() throws Exception {
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

}
