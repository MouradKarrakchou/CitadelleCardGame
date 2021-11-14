package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class PrintCitadels {
    public PrintCitadels(){}
    
    public void chooseRole(Player player,Character role){
        System.out.println("The robot "+player.getName()+" choose the character "+role.getName()+" "+role.getValue());
    }
    public void chooseDistrict(Player player,District district){
        System.out.println("The robot "+player.getName()+" choose the character "+district.getName()+" "+district.getValue());
    }

	public void printRanking(ArrayList<Player> listOfPlayer) {
		listOfPlayer.forEach(player -> System.out.println(player.getName()+" rank :"+player.getRank()+" with a score of "+ player.getScore()));
	}

    /*
    public void winByCharacter(Player winner){
        Character winnerCharacter=winner.getCharacter();
        System.out.println("The robot " +winner.getName()+" won because his role "+winnerCharacter.getName()+" had a value of "+winnerCharacter.getValue()+" which is the highest value.");
    }*/
}
