package it.polimi.ingsw.model;

import java.io.Serializable;

public class SchemeCard implements Serializable {

    private Window front;
    private Window back;

    public SchemeCard(Window front, Window back){
        this.front = front;
        this.back = back;
    }

    public Window getFront() {
        return front;
    }

    public Window getBack() {
        return back;
    }
}
