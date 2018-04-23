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

    public void setDice(Dice dice){
        this.dice = dice;
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
        System.out.println("Select a SchemeCard");
        System.out.print("1) ");
        card1.getFront().print();
        System.out.print("\n\n2) ");
        card1.getBack().print();
        System.out.print("\n\n3) ");
        card2.getFront().print();
        System.out.print("\n\n4) ");
        card2.getBack().print();
        int i = in.nextInt();
        switch(i){
            case 1:
                this.window = card1.getFront();
                tokens = window.getDifficulty();
                break;
            case 2:
                this.window = card1.getBack();
                tokens = window.getDifficulty();
                break;
            case 3:
                this.window = card2.getFront();
                tokens = window.getDifficulty();
                break;
            case 4:
                this.window = card2.getBack();
                tokens = window.getDifficulty();
                break;
            default :
                System.out.println("Errore selezione finestra");
                this.chooseWindow(card1, card2);
                break;
        }
    }

    public void pickDice(Draft draft){
        Scanner in;
        in = new Scanner(System.in);
        int j = draft.size()-1;
        System.out.println("Select a dice from 1 to " + (j+1));
        draft.print();
        int i = in.nextInt() - 1;
        if(i >= 0 && i <= j) {
            this.setDice(draft.extract(i));
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
        System.out.println("Select a ToolCard (1, 2, 3)");
        System.out.println("|1| "+toolCards[0].getName()+"  ->PRICE: "+((toolCards[0].getIsUsed()) ? 2 : 1)+"\n"+toolCards[0].getDescription()+
                "|2| "+toolCards[1].getName()+"  ->PRICE: "+((toolCards[1].getIsUsed()) ? 2 : 1)+"\n"+toolCards[1].getDescription()+
                "|3| "+toolCards[2].getName()+"  ->PRICE: "+((toolCards[2].getIsUsed()) ? 2 : 1)+"\n"+toolCards[2].getDescription()+
                "press |0| to NOT USE any cards");
        int i = (in.nextInt())-1;
        if (i >= 0 && i <= 2){
            toolCardSelected = toolCards[i];
        }
        else if (i == -1){
            return false;
        }
        else {
            System.out.println("Errore selezione ToolCard");
            this.selectToolCard(toolCards);
            return false;
        }
        if(toolCardSelected.getIsUsed()){
            if(this.tokens >= 2) {
                this.tokens = this.tokens - 2;
                return true;
            }
            else{
                System.out.println("Segnalini favore non sufficienti");
                this.selectToolCard(toolCards);
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
                this.selectToolCard(toolCards);
                return false;
            }
        }
    }

    public void putDice(){
        this.window.print();
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Select a row to put the dice");
        int i = in.nextInt();
        System.out.println("Select a column to put the dice");
        in = new Scanner(System.in);
        int j = in.nextInt();
        if(i >= 0 && i<=3 && j >= 0 && j<= 4) {
            if (this.window.verifyRestriction(dice, i, j)) {
                this.window.setPattern(dice, i, j);
            } else {
                System.out.println("Restrizione presente. Non puoi inserire il dado in questa posizione. Scegline un'altra.");
                this.putDice();
                return;
            }
        }
        else{
            System.out.println("Errore selezione casella.");
            this.putDice();
        }
    }
}
