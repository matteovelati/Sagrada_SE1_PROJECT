package it.polimi.ingsw.view.cli;

import java.io.Serializable;

public class PrintSelectMove2 implements Serializable {

    /**
     * prints the client's possible choices
     */
    public static void print(){
        System.out.println("CHOOSE YOUR MOOVE! (-1 TO ABORT)");
        System.out.println("1) PICK A DICE FROM THE DRAFT OR USE A TOOLCARD");
        System.out.println("0) END YOUR TURN\n");
    }
}
