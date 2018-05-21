package it.polimi.ingsw.view.cli;

import java.io.Serializable;

public class PrintSelectMove2 implements Serializable {

    public static void print(){
        System.out.println("CHOOSE YOUR MOOVE!");
        System.out.println("1) PICK A DICE FROM THE DRAFT OR USE A TOOLCARD");
        System.out.println("2) END YOUR TURN\n");
    }
}
