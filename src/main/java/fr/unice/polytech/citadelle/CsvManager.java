package fr.unice.polytech.citadelle;

import java.io.FileWriter;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Referee;

public class CsvManager {


    ArrayList<Player> leaderboard = new ArrayList<>();

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
            toWrite += String.valueOf(i + 1);
        }
        

        //close the writer
        writer.close();
    }
}
