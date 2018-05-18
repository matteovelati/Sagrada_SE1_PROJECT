package it.polimi.ingsw.model;

import java.io.Serializable;

public class SchemeCard implements Serializable {

    private Window front;
    private Window back;

    public SchemeCard(int i){

        switch (i){
            case 1:
                this.front = new Window(1);
                this.back = new Window(2);
                break;
            case 2:
                this.front = new Window(3);
                this.back = new Window(4);
                break;
            case 3:
                this.front = new Window(5);
                this.back = new Window(6);
                break;
            case 4:
                this.front = new Window(7);
                this.back = new Window(8);
                break;
            case 5:
                this.front = new Window(9);
                this.back = new Window(13);
                break;
            case 6:
                this.front = new Window(10);
                this.back = new Window(14);
                break;
            case 7:
                this.front = new Window(11);
                this.back = new Window(15);
                break;
            case 8:
                this.front = new Window(12);
                this.back = new Window(16);
                break;
            case 9:
                this.front = new Window(17);
                this.back = new Window(21);
                break;
            case 10:
                this.front = new Window(18);
                this.back = new Window(22);
                break;
            case 11:
                this.front = new Window(19);
                this.back = new Window(23);
                break;
            case 12:
                this.front = new Window(20);
                this.back = new Window(24);
                break;
            default:
                break;
        }

    }

    public Window getFront() {
        return front;
    }

    public Window getBack() {
        return back;
    }
}
