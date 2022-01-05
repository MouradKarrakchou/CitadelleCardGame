package fr.unice.polytech.citadelle;

import fr.unice.polytech.citadelle.game.Player;
import fr.unice.polytech.citadelle.game_engine.Controller;
import fr.unice.polytech.citadelle.output.PrintCitadels;

import java.util.ArrayList;

public class Battlefieldator {
    CsvManager csv;

    public Battlefieldator(CsvManager csv){
        this.csv = csv;
    }

    public String getBestBotName() throws Exception {
        String botName = csv.getStatsBot().get(0).getName();
        return botName.substring(0, botName.length() - 2);
    }
}
