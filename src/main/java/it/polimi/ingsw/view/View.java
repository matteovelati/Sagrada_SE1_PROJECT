package it.polimi.ingsw.view;

public abstract class View {

    private int choose1;
    private int choose2;


    public int getChoose1() {
        return choose1;
    }

    public int getChoose2() {
        return choose2;
    }

    public void setChoose1(int choose1) {
        this.choose1 = choose1;
    }

    public void setChoose2(int choose2) {
        this.choose2 = choose2;
    }
}
