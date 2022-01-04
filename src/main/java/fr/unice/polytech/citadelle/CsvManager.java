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

    String winner() {
        return leaderboard.get(0).getName();
    }

    void write(String[] args) throws Exception {
        String csv = "results.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        //Create record
        String [] record = "4,David,Miller,Australia,30".split(",");
        //Write the record to file
        writer.writeNext(record);

        //close the writer
        writer.close();
    }
}
