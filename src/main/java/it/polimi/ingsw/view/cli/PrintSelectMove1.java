package it.polimi.ingsw.view.cli;

import java.io.Serializable;

public class PrintSelectMove1 implements Serializable {

    public static void print(){
        System.out.println("CHOOSE YOUR MOVE!");
        System.out.println("1) PICK A DICE FROM THE DRAFT");
        System.out.println("2) USE A TOOLCARD");
        System.out.println("0) END YOUR TURN\n");
    }
}
