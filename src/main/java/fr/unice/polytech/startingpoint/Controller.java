package fr.unice.polytech.startingpoint;

public class Controller {
    Game game;
    PrintCitadelles printC;
    Controller(){
        game=new Game(new Player("robot1"),new Player("robot2"));
        printC=new PrintCitadelles();
    }
    void lauchGame(){
        game.startRound();
        printC.chooseRole(game.getRobot1());
        printC.chooseRole(game.getRobot2());
        printC.win(game.getWinner());
    }

}
