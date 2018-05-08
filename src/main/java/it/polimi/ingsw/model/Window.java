package it.polimi.ingsw.model;

public class Window {

    private String name;
    private int difficulty;
    private Space[][] window;

    public Window(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Space[][] getWindow() {
        return window;
    }

    public void setWindow(Space[][] window) {
        this.window = window;
    }

    private boolean colorRestriction(Dice dice, int i, int j){
        return true;
    }

    private boolean numberRestriction(Dice dice, int i, int j){
        return true;
    }

    public boolean verifyRestriction(Dice dice, int i, int j){
        return true;
    }
}
