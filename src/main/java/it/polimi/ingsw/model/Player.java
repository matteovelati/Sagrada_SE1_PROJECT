package it.polimi.ingsw.model;

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

    public boolean chooseWindow(SchemeCard card1, SchemeCard card2, int i){
        /*Scanner in;
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
        int i = in.nextInt();*/
        switch(i){
            case 1:
                this.window = card1.getFront();
                tokens = window.getDifficulty();
                return true;
            case 2:
                this.window = card1.getBack();
                tokens = window.getDifficulty();
                return true;
            case 3:
                this.window = card2.getFront();
                tokens = window.getDifficulty();
                return true;
            case 4:
                this.window = card2.getBack();
                tokens = window.getDifficulty();
                return true;
            default :
                return false;
                /*System.out.println("Errore selezione finestra");
                this.chooseWindow(card1, card2, i);
                break;*/
        }
    }

    public void pickDice(Draft draft, int i){
        this.setDice(draft.extract(i));
    }

    public boolean selectToolCard(ToolCard[] toolCards, int i){
        if (i >= 0 && i <= 2){
            toolCardSelected = toolCards[i];
        }
        if(toolCardSelected.getIsUsed()){
            if(this.tokens >= 2) {
                this.tokens = this.tokens - 2;
                return true;
            }
            else{
                /*System.out.println("Segnalini favore non sufficienti");
                this.selectToolCard(toolCards, i);*/
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
                /*System.out.println("Segnalini favore non sufficienti");
                toolCardSelected = null;*/
                return false;
            }
        }
    }

    public boolean putDice(int i, int j){
        /*this.window.print();
        Scanner in;
        in = new Scanner(System.in);
        System.out.println("Select a row to put the dice");
        int i = in.nextInt();
        System.out.println("Select a column to put the dice");
        in = new Scanner(System.in);
        int j = in.nextInt();*/
        if(i >= 0 && i<=3 && j >= 0 && j<= 4) {
            if (this.window.verifyRestriction(dice, i, j)) {
                this.window.setWindow(dice, i, j);
                return true;
            } else {
                /*System.out.println("Restrizione presente. Non puoi inserire il dado in questa posizione. Scegline un'altra.");
                this.putDice();*/
                return false;
            }
        }
        else{
            //System.out.println("Errore selezione casella.");
            //this.putDice();
            return false;
        }
    }


}
