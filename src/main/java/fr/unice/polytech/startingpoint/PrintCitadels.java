package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class PrintCitadels {
    public PrintCitadels(){}
    public void win(Player winner){
        Character winnerCharacter=winner.getCharacter();
        System.out.println("The robot " +winner.getName()+" won because his role "+winnerCharacter.getName()+" had a value of "+winnerCharacter.getValue()+" which is the highest value.");
    }
    
    public void chooseRole(Player player){
        System.out.println("The robot "+player.getName()+" choose the character "+player.getCharacter().getName()+" "+player.getCharacter().getValue());
    }
    
    //------new
	public void printStartInformation(ArrayList<Player> listOfPlayer) {
		listOfPlayer.forEach(player -> chooseRole(player));
	}
	//------new

}
