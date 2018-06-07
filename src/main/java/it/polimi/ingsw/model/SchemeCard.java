package it.polimi.ingsw.model;

import java.io.Serializable;

public class SchemeCard implements Serializable {

    private Window front;
    private Window back;

    /**
     * creates a SchemeCard object with 2 Window objects
     * @param i the index of windows
     */
    public SchemeCard(int i){
        this.front = new Window((i*2)-1);
        this.back = new Window(i*2);
    }

    /**
     * gets the front window
     * @return the front window
     */
    public Window getFront() {
        return front;
    }

    /**
     * gets the back window
     * @return the back window
     */
    public Window getBack() {
        return back;
    }
}
