package it.polimi.ingsw.model;

import java.util.Scanner;

public class Move {

    private int threshold;

    public Move(){

    }

    public int chooseMove1(Player player, Field field){
        threshold = field.getDraft().size();
        Scanner in;
        in = new Scanner(System.in);
        int j = field.getDraft().size()-1;
        System.out.println("Choose your next move");
        System.out.println("You can pick a dice from 1 to " + (j + 1) + ":");
        field.getDraft().print();
        System.out.println("You can use a tool card from "+(j+2)+" to "+(j+4)+":");
        System.out.println("|"+(j+2)+"| "+field.getToolCard()[0].getName()+"  ->PRICE: "+((field.getToolCard()[0].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[0].getDescription()+
                "|"+(j+3)+"| "+field.getToolCard()[1].getName()+"  ->PRICE: "+((field.getToolCard()[1].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[1].getDescription()+
                "|"+(j+4)+"| "+field.getToolCard()[2].getName()+"  ->PRICE: "+((field.getToolCard()[2].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[2].getDescription());
        System.out.println("PRESS |0| PASS YOUR TURN !");
        int i = in.nextInt()-1;
        if(i >= 0 && i <= j) {
            player.pickDice(field.getDraft(), i);
            return i;
        }
        else if(i>j && i<=(j+3)){
            player.selectToolCard(field.getToolCard(), (i-threshold));
            return i;
        }
        else if(i == -1){
            System.out.println("hai scelto di saltare il tuo turno");
            return i;
        }
        else{
            System.out.println("selection error 1\n");
            this.chooseMove1(player, field);
            return -2;
        }
    }

    public void chooseMove2(Player player, Field field){
        Scanner in;
        in = new Scanner(System.in);
        int j = field.getDraft().size()-1;
        System.out.println("Choose your next move");
        System.out.println("You can pick a dice from 1 to " + (j + 1) + ":");
        field.getDraft().print();
        System.out.println("PRESS |0| PASS YOUR TURN !");
        int i = in.nextInt()-1;
        if(i >= 0 && i <= j) {
            player.pickDice(field.getDraft(), i);
        }
        else if(i == -1){
            System.out.println("hai scelto di saltare il tuo turno");
            return;
        }
        else{
            System.out.println("selection error 2\n");
            this.chooseMove2(player, field);
            return;
        }
    }

    public void chooseMove3(Player player, Field field){
        Scanner in;
        in = new Scanner(System.in);
        int j = field.getDraft().size();
        System.out.println("Choose your next move");
        System.out.println("You can use a tool card from "+(j+2)+" to "+(j+4)+":");
        System.out.println("|"+(j+2)+"| "+field.getToolCard()[0].getName()+"  ->PRICE: "+((field.getToolCard()[0].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[0].getDescription()+
                "|"+(j+3)+"| "+field.getToolCard()[1].getName()+"  ->PRICE: "+((field.getToolCard()[1].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[1].getDescription()+
                "|"+(j+4)+"| "+field.getToolCard()[2].getName()+"  ->PRICE: "+((field.getToolCard()[2].getIsUsed()) ? 2 : 1)+"\n"+field.getToolCard()[2].getDescription());
        System.out.println("PRESS |0| PASS YOUR TURN !");
        int i = in.nextInt()-1;
        if(i>j && i<=(j+3)){
            player.selectToolCard(field.getToolCard(), (i-threshold));
            return;
        }
        else if(i == -1){
            System.out.println("hai scelto di saltare il tuo turno");
            return;
        }
        else{
            System.out.println("selection error 3\n");
            this.chooseMove3(player, field);
            return;
        }
    }



}
