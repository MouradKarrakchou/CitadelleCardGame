package fr.unice.polytech.citadelle;

import java.io.FileWriter;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;

public class CsvManager {

    ArrayList<Player> leaderboard;

    CsvManager (ArrayList<Player> leaderboard) {
        this.leaderboard = leaderboard;
    }

    void write() throws Exception {
        String csv = "results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        //Create record
        String [] record = "BotType,1,2,3,4,5,6,7,8,Win rate,Games played".split(",");
        //Write the record to file
        writer.writeNext(record);


        String toWrite = "";
        int numberOfPlayers = leaderboard.size();
        for(int i = 0; i < numberOfPlayers; i++) {
            toWrite += leaderboard.get(i).getName() + ",";
            for(int j = 0; j < i; j++) {
                toWrite += "0,";
            }
            toWrite += 1 + ",";
            for(int j = 0; j < numberOfPlayers; j++) {
                toWrite += "0,";
            }
        }
        record = toWrite.split(",");
        writer.writeNext(record);

        //close the writer
        writer.close();
    }
}
