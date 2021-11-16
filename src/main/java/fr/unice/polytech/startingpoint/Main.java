package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        Controller controller=new Controller();
        //controller.lauchGame();
        
        //----new
        controller.initGame();
        
        
        controller.runGame(); 
        //----new
        
      
        //pour tester la méthode sortByScoreAndCharacter_v666
        /*ArrayList<Player> list = new ArrayList<Player>();
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        
        
        p1.setScore(2);
        p2.setScore(2);
        p3.setScore(5);

        p1.setRole(new Character("human", 0));
        p2.setRole(new Character("human", 2));
        p3.setRole(new Character("human", 0));

        list.add(p1);
        list.add(p2);
        list.add(p3);
        
        System.out.println(controller.game.sortByScore_v666(list).toString());*/

        
    }

}

