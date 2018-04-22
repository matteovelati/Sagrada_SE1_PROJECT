package it.polimi.ingsw.model;

import java.util.Scanner;

public class Player {

    private String username;
    private int tokens;
    private Window window;
    private PrivateObjective privateObjective;
    private Dice dice;
    private ToolCard toolCardSelected;

    public Player(String username){
        this.username = username;
    }

    public void setPrivateObjective(PrivateObjective privateObjective) {
        this.privateObjective = privateObjective;
    }

    public Dice getDice() {
        return dice;
    }

    public String getUsername() {
        return username;
    }

    public int getTokens() {
        return tokens;
    }

    public Window getWindow() {
        return window;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    public void chooseWindow(SchemeCard card1, SchemeCard card2){
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Select a SchemeCard (1 or 2)");
        int i = in.nextInt();
        if(i ==1){
            System.out.println("Select a Window (1 or 2)");
            in = new Scanner(System.in);
            i = in.nextInt();
            if(i == 1){
                this.window = card1.getFront();
            }
            else {
                if(i == 2) {
                    this.window = card1.getBack();
                }
                else{
                    System.out.println("Errore selezione finestra");
                    this.chooseWindow(card1, card2);
                    return;
                }
            }
        }
        else{
            if(i == 2) {
                System.out.println("Select a Window (1 or 2)");
                in = new Scanner(System.in);
                i = in.nextInt();
                if (i == 1) {
                    this.window = card2.getFront();
                } else {
                    if(i == 2) {
                        this.window = card2.getBack();
                    }
                    else{
                        System.out.println("Errore selezione finestra");
                        this.chooseWindow(card1, card2);
                        return;
                    }
                }
            }
            else{
                System.out.println("Errore selezione CartaSchema");
                this.chooseWindow(card1, card2);
                return;
            }
        }
        tokens = window.getDifficulty();
    }

    public void pickDice(Draft draft){
        Scanner in;
        in = new Scanner(System.in);
        int j = draft.size()-1;
        System.out.println("Select a dice from 1 to " + (j+1));
        draft.print();
        int i = in.nextInt() - 1;
        if(i >= 0 && i <= j) {
            dice = draft.extract(i);
        }
        else{
            System.out.println("Errore selezione dado");
            this.pickDice(draft);
            return;
        }
    }

    public boolean selectToolCard(ToolCard[] toolCards){
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Select a ToolCard");
        System.out.println(toolCards[0].getDescription()+"\n"+toolCards[1].getDescription()+"\n"+toolCards[2].getDescription());
        int i = in.nextInt();
        toolCardSelected = toolCards[i];
        if(toolCardSelected.getIsUsed()){
            if(this.tokens >= 2) {
                this.tokens = this.tokens - 2;
                return true;
            }
            else{
                System.out.println("Segnalini favore non sufficienti");
                return false;
            }
        }
        else{
            if(this.tokens >= 1){
                this.tokens --;
                toolCards[i].setIsUsed(true);
                return true;
            }
            else{
                System.out.println("Segnalini favore non sufficienti");
                return false;
            }
        }
    }

    public void putDice(){
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Select a row to put the dice");
        int i = in.nextInt();
        System.out.println("Select a column to put the dice");
        in = new Scanner(System.in);
        int j = in.nextInt();
        this.window.getPattern()[i][j].setDice(dice);
    }
}
