package fr.unice.polytech.citadelle;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.output.PrintCitadels;

/**
 * Manage the content of the csv file (writing, reading, updating)
 *
 * @author BONNET Kilian, IMAMI Ayoub, KARRAKCHOU Mourad, LE BIHAN Léo
 */
public class CsvManager {

    ArrayList<Player> leaderboard;
    String path;

    /**
     * The leaderboard is used to know which player needs to have his stats updated
     *
     * @param leaderboard results board of the players
     * @param path the path of the csv file
     */
    CsvManager (ArrayList<Player> leaderboard, String path) {
        this.leaderboard = leaderboard;
        this.path = path;
    }

    /**
     * Initialize a CsvManager
     * @param path the path of the csv file
     */
    CsvManager(String path){
        this.path = path;
    }

    /**
     * Main method of this class.
     * It checks:
     * -If the csv file exists
     * -If the file already has some stats
     * -If there is a new player who has never had his stats recorded yet
     *
     * @throws Exception exception
     */
    void saveFile() throws Exception {
        existingFile();
        if(!emptyFile()) {
            updateWinrateAverage(updateRankAll());
            newChallenger();
        }
    }

    /**
     * Check if the csv file exist or not
     *
     * @throws Exception exception
     */
    void existingFile() throws Exception {
        File csvFile = new File(path);
        if(!csvFile.isFile()) createFile();
    }

    /**
     * Check if the csv file already has some stats
     *
     * @return a boolean
     * @throws Exception exception
     */
    boolean emptyFile() throws Exception {
        if(getNumberOfLine() == 1) {
            append();
            return true;
        }

        return false;
    }

    /**
     * Creates and initializes the fields of the csv file
     *
     * @throws Exception exception
     */
    void createFile() throws Exception {
        String csv = path;
        CSVWriter writer = new CSVWriter(new FileWriter(csv));

        //Create record
        String [] record = "BotType,1,2,3,4,5,6,7,Win-rate,Games played,Total score,Average score".split(",");

        //Write the record to file
        writer.writeNext(record);

        //close the writer
        writer.close();
    }


    /**
     * If the csv file is has no stats yet, add the firsts stats
     *
     * @throws Exception exception
     */
    void append() throws Exception {
        String csv = path;
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

    /**
     * Reads the csv file content and returns it
     *
     * @return List of the csv file content
     * @throws Exception exception
     */
    List<String[]> read() throws Exception {
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader(path), ',', '"', 0);

        //Read all rows at once
        return reader.readAll();
    }

    /**
     * Gives the number of lines of the csv file (first line which is the fields include)
     *
     * @return number of lines of the csv file
     * @throws Exception exception
     */
    int getNumberOfLine() throws Exception {
        List<String[]> allRows = read();
        return allRows.size();
    }

    /**
     * Updates the rank of every player who just played
     *
     * @return the updated csv file
     * @throws Exception exception
     */
    List<String[]> updateRankAll() throws Exception {
        List<String[]> allRows = read();

        int rankPosition = 1;
        for(Player player : leaderboard) {
            allRows = updateRankOnePlayer(player, allRows, rankPosition);
            rankPosition++;
        }

        return allRows;
    }

    /**
     * Updates the rank of a given player
     *
     * @param player player who will have his rank updated
     * @param allRows content of csv file
     * @param rankPosition position of the player in the leaderboard
     * @return the updated csv file
     */
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

    /**
     * Updates the win-rate and the average score of the players who just played
     *
     * @param allRows content of the csv file
     * @throws Exception exception
     */
    void updateWinrateAverage(List<String[]> allRows) throws Exception {
        write(average(winrate(allRows)));
    }

    /**
     * Writes on the csv file
     *
     * @param allRows content of the csv file
     * @throws Exception exception
     */
    void write(List<String[]> allRows) throws Exception {
        String csv = path;
        CSVWriter writer = new CSVWriter(new FileWriter(csv));
        for(String[] row : allRows) {
            writer.writeNext(row);
        }
        writer.close();
    }

    /**
     * Calculates the win-rate of each player
     *
     * @param allRows content of the csv file
     * @return the updated csv file
     * @throws Exception exception
     */
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

    /**
     * Calculates the average score of each player
     *
     * @param allRows content of the csv file
     * @return the updated csv file
     * @throws Exception exception
     */
    List<String[]> average(List<String[]> allRows) throws Exception {
        int numberOfLine = getNumberOfLine();

        for(String[] row : allRows.subList(1, numberOfLine)) {
            row[11] = String.valueOf(Integer.parseInt(row[10]) / Integer.parseInt(row[9]));
        }

        return allRows;
    }

    /**
     * Checks if there is a new player who has never had his stats recorded yet
     *
     * @throws Exception exception
     */
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

    /**
     * Adds the new player who has never had his stats recorded yet
     *
     * @param rank rank of the new player in the leaderboard
     * @throws Exception exception
     */
    void addTheNewChallenger(Integer rank) throws Exception {
        String csv = path;
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

    /**
     *Converts the csv file into an arrayList of stats
     *
     * @return an arrayList of stats
     * @throws Exception exception
     */
    public ArrayList<StatsBot> getStatsBot() throws Exception {
        ArrayList<StatsBot> statsBots = new ArrayList<>();
        List<String[]> allRows = read();
        int numberOfLine = getNumberOfLine();

        for(String[] row : allRows.subList(1, numberOfLine)) {
            statsBots.add(new StatsBot(row[0], Integer.parseInt(row[9]), Integer.parseInt(row[1]), Integer.parseInt(row[11])));
        }

        return statsBots;
    }

    /**
     * Displays the content of the csv file
     *
     * @throws Exception exception
     */
    public void printCSV() throws Exception {
        PrintCitadels.startCSV();
        ArrayList<StatsBot> botStatsList = getStatsBot();

        Collections.sort(botStatsList);
        for (StatsBot statsBot : botStatsList)
            PrintCitadels.printStat(statsBot);
    }
}
