package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Window;

public class PrintWindow {

    public static void print(Window window){
        System.out.println(window.getName() +"  "+window.getDifficulty());
        for(int i=0; i<4; i++){
            System.out.println("\n| - - - | - - - | - - - | - - - | - - - |");
            System.out.print("|");
            for(int j=0; j<5; j++){
                if(window.getWindow()[i][j].getIsEmpty()){
                    System.out.print("  "+window.getWindow()[i][j].getValue()+" "+window.getWindow()[i][j].getColor()+"  ");
                    System.out.print("|");
                }
                else {
                    System.out.print(" [" + window.getWindow()[i][j].getValue() + " " + window.getWindow()[i][j].getColor() + "] ");
                    System.out.print("|");
                }
            }
        }
        System.out.println("\n| - - - | - - - | - - - | - - - | - - - |");
    }
}
