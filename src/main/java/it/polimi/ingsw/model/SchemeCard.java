package it.polimi.ingsw.model;

import java.io.Serializable;

public class SchemeCard implements Serializable {

    private Window front;
    private Window back;

    public SchemeCard(int i){
        this.front = new Window((i*2)-1);
        this.back = new Window(i*2);
    }

    public Window getFront() {
        return front;
    }

    public Window getBack() {
        return back;
    }
}
