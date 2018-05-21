package it.polimi.ingsw.model;

import java.io.Serializable;

public class Window implements Serializable {

    private boolean isEmpty;
    private String name;
    private int difficulty;
    private Space[][] window;

    public Window(int x){
        isEmpty = true;
        window = new Space[4][5];
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                window[i][j] = new Space(Colors.W, 0);
            }
        }

        switch (x){
            case 1:
                this.name = "Bellesguard";
                this.difficulty = 3;
                window[0][0].setColor(Colors.B);
                window[0][1].setValue(6);
                window[0][4].setColor(Colors.Y);
                window[1][1].setValue(3);
                window[1][2].setColor(Colors.B);
                window[2][1].setValue(5);
                window[2][2].setValue(6);
                window[2][3].setValue(2);
                window[3][1].setValue(4);
                window[3][3].setValue(1);
                window[3][4].setColor(Colors.G);
                break;

            case 2:
                this.name = "Battlo";
                this.difficulty = 5;
                window[0][2].setValue(6);
                window[1][1].setValue(5);
                window[1][2].setColor(Colors.B);
                window[1][3].setValue(4);
                window[2][0].setValue(3);
                window[2][1].setColor(Colors.G);
                window[2][2].setColor(Colors.Y);
                window[2][3].setColor(Colors.P);
                window[2][4].setValue(2);
                window[3][0].setValue(1);
                window[3][1].setValue(4);
                window[3][2].setColor(Colors.R);
                window[3][3].setValue(5);
                window[3][4].setValue(3);
                break;

            case 3:
                this.name = "Chromatic Splendor";
                this.difficulty = 4;
                window[0][2].setColor(Colors.G);
                window[1][0].setValue(2);
                window[1][1].setColor(Colors.Y);
                window[1][2].setValue(5);
                window[1][3].setColor(Colors.B);
                window[1][4].setValue(1);
                window[2][1].setColor(Colors.R);
                window[2][2].setValue(3);
                window[2][3].setColor(Colors.P);
                window[3][0].setValue(1);
                window[3][2].setValue(6);
                window[3][4].setValue(4);
                break;

            case 4:
                this.name = "Comitas";
                this.difficulty = 5;
                window[0][0].setColor(Colors.Y);
                window[0][2].setValue(2);
                window[0][4].setValue(6);
                window[1][1].setValue(4);
                window[1][3].setValue(5);
                window[1][4].setColor(Colors.Y);
                window[2][3].setColor(Colors.Y);
                window[2][4].setValue(5);
                window[3][0].setValue(1);
                window[3][1].setValue(2);
                window[3][2].setColor(Colors.Y);
                window[3][3].setValue(3);
                break;

            case 5:
                this.name = "Fulgor del Cielo";
                this.difficulty = 5;
                window[0][1].setColor(Colors.B);
                window[0][2].setColor(Colors.R);
                window[1][1].setValue(4);
                window[1][2].setValue(5);
                window[1][4].setColor(Colors.B);
                window[2][0].setColor(Colors.B);
                window[2][1].setValue(2);
                window[2][3].setColor(Colors.R);
                window[2][4].setValue(5);
                window[3][0].setValue(6);
                window[3][1].setColor(Colors.R);
                window[3][2].setValue(3);
                window[3][3].setValue(1);
                break;

            case 6:
                this.name = "Luz Celestial";
                this.difficulty = 3;
                window[0][2].setColor(Colors.R);
                window[0][3].setValue(5);
                window[1][0].setColor(Colors.P);
                window[1][1].setValue(4);
                window[1][3].setColor(Colors.G);
                window[1][4].setValue(3);
                window[2][0].setValue(6);
                window[2][3].setColor(Colors.B);
                window[3][1].setColor(Colors.Y);
                window[3][2].setValue(2);
                break;

            case 7:
                this.name = "Lux Mundi";
                this.difficulty = 6;
                window[0][2].setValue(1);
                window[1][0].setValue(1);
                window[1][1].setColor(Colors.G);
                window[1][2].setValue(3);
                window[1][3].setColor(Colors.B);
                window[1][4].setValue(2);
                window[2][0].setColor(Colors.B);
                window[2][1].setValue(5);
                window[2][2].setValue(4);
                window[2][3].setValue(6);
                window[2][4].setColor(Colors.G);
                window[3][1].setColor(Colors.B);
                window[3][2].setValue(5);
                window[3][3].setColor(Colors.G);
                break;

            case 8:
                this.name = "Lux Astram";
                this.difficulty = 5;
                window[0][1].setValue(1);
                window[0][2].setColor(Colors.G);
                window[0][3].setColor(Colors.P);
                window[0][4].setValue(4);
                window[1][0].setValue(6);
                window[1][1].setColor(Colors.P);
                window[1][2].setValue(2);
                window[1][3].setValue(5);
                window[1][4].setColor(Colors.G);
                window[2][0].setValue(1);
                window[2][1].setColor(Colors.G);
                window[2][2].setValue(5);
                window[2][3].setValue(3);
                window[2][4].setColor(Colors.P);
                break;

            case 9:
                this.name = "Industria";
                this.difficulty = 5;
                window[0][0].setValue(1);
                window[0][1].setColor(Colors.R);
                window[0][2].setValue(3);
                window[0][4].setValue(6);
                window[1][0].setValue(5);
                window[1][1].setValue(4);
                window[1][2].setColor(Colors.R);
                window[1][3].setValue(2);
                window[2][2].setValue(5);
                window[2][3].setColor(Colors.R);
                window[2][4].setValue(1);
                window[3][3].setValue(3);
                window[3][4].setColor(Colors.R);
                break;

            case 10:
                this.name = "Symphony of Light";
                this.difficulty = 6;
                window[0][0].setValue(2);
                window[0][2].setValue(5);
                window[0][4].setValue(1);
                window[1][0].setColor(Colors.Y);
                window[1][1].setValue(6);
                window[1][2].setColor(Colors.P);
                window[1][3].setValue(2);
                window[1][4].setColor(Colors.R);
                window[2][1].setColor(Colors.B);
                window[2][2].setValue(4);
                window[2][3].setColor(Colors.G);
                window[3][1].setValue(3);
                window[3][3].setValue(5);
                break;

            case 11:
                this.name = "Water of Life";
                this.difficulty = 6;
                window[0][0].setValue(6);
                window[0][1].setColor(Colors.B);
                window[0][4].setValue(1);
                window[1][1].setValue(5);
                window[1][2].setColor(Colors.B);
                window[2][0].setValue(4);
                window[2][1].setColor(Colors.R);
                window[2][2].setValue(2);
                window[2][3].setColor(Colors.B);
                window[3][0].setColor(Colors.G);
                window[3][1].setValue(6);
                window[3][2].setColor(Colors.Y);
                window[3][3].setValue(3);
                window[3][4].setColor(Colors.P);
                break;

            case 12:
                this.name = "Sun's Glory";
                this.difficulty = 6;
                window[0][0].setValue(1);
                window[0][1].setColor(Colors.P);
                window[0][2].setColor(Colors.Y);
                window[0][4].setValue(4);
                window[1][0].setColor(Colors.P);
                window[1][1].setColor(Colors.Y);
                window[1][4].setValue(6);
                window[2][0].setColor(Colors.Y);
                window[2][3].setValue(5);
                window[2][4].setValue(3);
                window[3][1].setValue(5);
                window[3][2].setValue(4);
                window[3][3].setValue(2);
                window[3][4].setValue(1);
                break;

            case 13:
                this.name = "Via Lux";
                this.difficulty = 4;
                window[0][0].setColor(Colors.Y);
                window[0][2].setValue(6);
                window[1][1].setValue(1);
                window[1][2].setValue(5);
                window[1][4].setValue(2);
                window[2][0].setValue(3);
                window[2][1].setColor(Colors.Y);
                window[2][2].setColor(Colors.R);
                window[2][3].setColor(Colors.P);
                window[3][2].setValue(4);
                window[3][3].setValue(3);
                window[3][4].setColor(Colors.R);
                break;

            case 14:
                this.name = "Virtus";
                this.difficulty = 5;
                window[0][0].setValue(4);
                window[0][2].setValue(2);
                window[0][3].setValue(5);
                window[0][4].setColor(Colors.G);
                window[1][2].setValue(6);
                window[1][3].setColor(Colors.G);
                window[1][4].setValue(2);
                window[2][1].setValue(3);
                window[2][2].setColor(Colors.G);
                window[2][3].setValue(4);
                window[3][0].setValue(5);
                window[3][1].setColor(Colors.G);
                window[3][2].setValue(1);
                break;

            case 15:
                this.name = "Gravitas";
                this.difficulty = 5;
                window[0][0].setValue(1);
                window[0][2].setValue(3);
                window[0][3].setColor(Colors.B);
                window[1][1].setValue(2);
                window[1][2].setColor(Colors.B);
                window[2][0].setValue(6);
                window[2][1].setColor(Colors.B);
                window[2][3].setValue(4);
                window[3][0].setColor(Colors.B);
                window[3][1].setValue(5);
                window[3][2].setValue(2);
                window[3][4].setValue(1);
                break;

            case 16:
                this.name = "Firelight";
                this.difficulty = 5;
                window[0][0].setValue(3);
                window[0][1].setValue(4);
                window[0][2].setValue(1);
                window[0][3].setValue(5);
                window[1][1].setValue(6);
                window[1][2].setValue(2);
                window[1][4].setColor(Colors.Y);
                window[2][3].setColor(Colors.Y);
                window[2][4].setColor(Colors.R);
                window[3][0].setValue(5);
                window[3][2].setColor(Colors.Y);
                window[3][3].setColor(Colors.R);
                window[3][4].setValue(6);
                break;

            case 17:
                this.name = "Shadow Thief";
                this.difficulty = 5;
                window[0][0].setValue(6);
                window[0][1].setColor(Colors.P);
                window[0][4].setValue(5);
                window[1][0].setValue(5);
                window[1][2].setColor(Colors.P);
                window[2][0].setColor(Colors.R);
                window[2][1].setValue(6);
                window[2][3].setColor(Colors.P);
                window[3][0].setColor(Colors.Y);
                window[3][1].setColor(Colors.R);
                window[3][2].setValue(5);
                window[3][3].setValue(4);
                window[3][4].setValue(3);
                break;

            case 18:
                this.name = "Ripples of Light";
                this.difficulty = 5;
                window[0][3].setColor(Colors.R);
                window[0][4].setValue(5);
                window[1][2].setColor(Colors.P);
                window[1][3].setValue(4);
                window[1][4].setColor(Colors.B);
                window[2][1].setColor(Colors.B);
                window[2][2].setValue(3);
                window[2][3].setColor(Colors.Y);
                window[2][4].setValue(6);
                window[3][0].setColor(Colors.Y);
                window[3][1].setValue(2);
                window[3][2].setColor(Colors.G);
                window[3][3].setValue(1);
                window[3][4].setColor(Colors.R);
                break;

            case 19:
                this.name = "Firmitas";
                this.difficulty = 5;
                window[0][0].setColor(Colors.P);
                window[0][1].setValue(6);
                window[0][4].setValue(3);
                window[1][0].setValue(5);
                window[1][1].setColor(Colors.P);
                window[1][2].setValue(3);
                window[2][1].setValue(2);
                window[2][2].setColor(Colors.P);
                window[2][3].setValue(1);
                window[3][1].setValue(1);
                window[3][2].setValue(5);
                window[3][3].setColor(Colors.P);
                window[3][4].setValue(4);
                break;

            case 20:
                this.name = "Aurorae Magnificus";
                this.difficulty = 5;
                window[0][0].setValue(5);
                window[0][1].setColor(Colors.G);
                window[0][2].setColor(Colors.B);
                window[0][3].setColor(Colors.P);
                window[0][4].setValue(2);
                window[1][0].setColor(Colors.P);
                window[1][4].setColor(Colors.Y);
                window[2][0].setColor(Colors.Y);
                window[2][2].setValue(6);
                window[2][4].setColor(Colors.P);
                window[3][0].setValue(1);
                window[3][3].setColor(Colors.G);
                window[3][4].setValue(4);
                break;

            case 21:
                this.name = "Sun Catcher";
                this.difficulty = 3;
                window[0][1].setColor(Colors.B);
                window[0][2].setValue(2);
                window[0][4].setColor(Colors.Y);
                window[1][1].setValue(4);
                window[1][3].setColor(Colors.R);
                window[2][2].setValue(5);
                window[2][3].setColor(Colors.Y);
                window[3][0].setColor(Colors.G);
                window[3][1].setValue(3);
                window[3][4].setColor(Colors.P);
                break;

            case 22:
                this.name = "Fractal Drops";
                this.difficulty = 3;
                window[0][1].setValue(4);
                window[0][3].setColor(Colors.Y);
                window[0][4].setValue(6);
                window[1][0].setColor(Colors.R);
                window[1][2].setValue(3);
                window[2][2].setColor(Colors.R);
                window[2][3].setColor(Colors.P);
                window[2][4].setValue(1);
                window[3][0].setColor(Colors.B);
                window[3][1].setColor(Colors.Y);
                break;

            case 23:
                this.name = "Kaleidoscopic Dream";
                this.difficulty = 4;
                window[0][0].setColor(Colors.Y);
                window[0][1].setColor(Colors.B);
                window[0][4].setValue(1);
                window[1][0].setColor(Colors.G);
                window[1][2].setValue(5);
                window[1][4].setValue(4);
                window[2][0].setValue(3);
                window[2][2].setColor(Colors.R);
                window[2][4].setColor(Colors.G);
                window[3][0].setValue(2);
                window[3][3].setColor(Colors.B);
                window[3][4].setColor(Colors.Y);
                break;

            case 24:
                this.name = "Aurora Sagradis";
                this.difficulty = 4;
                window[0][0].setColor(Colors.R);
                window[0][2].setColor(Colors.B);
                window[0][4].setColor(Colors.Y);
                window[1][0].setValue(4);
                window[1][1].setColor(Colors.P);
                window[1][2].setValue(3);
                window[1][3].setColor(Colors.G);
                window[1][4].setValue(2);
                window[2][1].setValue(1);
                window[2][3].setValue(5);
                window[3][2].setValue(6);
                break;

            default:
                System.out.println("Errore colorazione facciata");
                break;
        }
    }

    public boolean getIsEmpty(){
        return isEmpty;
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

    public void setIsEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }

    public void setWindow(Dice dice, int i, int j){
        this.window[i][j].setDice(dice);
    }

    public boolean neighboursColorRestriction(Dice dice, int i, int j){
        //return false se a N S E W c'è un dado dello stesso colore
        if (i == 0) {
            if (j == 0)
                return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
            else {
                if (j == 4)
                    return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
                else
                    return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
            }
        }
        else {
            if (i == 3) {
                if (j == 0)
                    return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())));
                else {
                    if (j == 4)
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())));
                    else
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())));
                }
            }
            else {
                if (j == 0)
                    return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
                else {
                    if (j == 4)
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
                    else
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getColor().equals(dice.getColor())) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getColor().equals(dice.getColor())) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getColor().equals(dice.getColor())) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getColor().equals(dice.getColor())));
                }
            }
        }
    }

    public boolean neighboursNumberRestriction(Dice dice, int i, int j){
        //return false se a N S E W c'è un dado dello stesso numero
        if (i == 0) {
            if (j == 0)                                                //.getDice().getValue()
                return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
            else {
                if (j == 4)
                    return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
                else
                    return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
            }
        }
        else {
            if (i == 3) {
                if (j == 0)
                    return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()));
                else {
                    if (j == 4)
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()));
                    else
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()));
                }
            }
            else {
                if (j == 0)
                    return!((!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
                else {
                    if (j == 4)
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
                    else
                        return!((!window[i][j-1].getIsEmpty() && window[i][j - 1].getDice().getValue() == dice.getValue()) || (!window[i][j+1].getIsEmpty() && window[i][j + 1].getDice().getValue() == dice.getValue()) || (!window[i-1][j].getIsEmpty() && window[i - 1][j].getDice().getValue() == dice.getValue()) || (!window[i+1][j].getIsEmpty() && window[i + 1][j].getDice().getValue() == dice.getValue()));
                }
            }
        }
    }

    public boolean neighboursPositionRestriction(int i, int j){
        //return false se non vi sono dadi adiacienti
        if (i == 0) {
            if (j == 0)
                return!(window[i][j+1].getIsEmpty() && window[i+1][j+1].getIsEmpty() && window[i+1][j].getIsEmpty());
            else {
                if (j == 4)
                    return!(window[i][j-1].getIsEmpty() && window[i+1][j-1].getIsEmpty() && window[i+1][j].getIsEmpty());
                else
                    return!(window[i][j-1].getIsEmpty() && window[i+1][j-1].getIsEmpty() && window[i+1][j].getIsEmpty() && window[i+1][j+1].getIsEmpty() && window[i][j+1].getIsEmpty());
            }
        }
        else {
            if (i == 3) {
                if (j == 0)
                    return!(window[i-1][j].getIsEmpty() && window[i-1][j+1].getIsEmpty() && window[i][j+1].getIsEmpty());
                else {
                    if (j == 4)
                        return!(window[i-1][j].getIsEmpty() && window[i-1][j-1].getIsEmpty() && window[i][j-1].getIsEmpty());
                    else
                        return!(window[i][j-1].getIsEmpty() && window[i-1][j-1].getIsEmpty() && window[i-1][j].getIsEmpty() && window[i-1][j+1].getIsEmpty() && window[i][j+1].getIsEmpty());
                }
            } else {
                if (j == 0)
                    return!(window[i-1][j].getIsEmpty() && window[i-1][j+1].getIsEmpty() && window[i][j+1].getIsEmpty() && window[i+1][j+1].getIsEmpty() && window[i+1][j].getIsEmpty());
                else {
                    if (j == 4)
                        return!(window[i-1][j].getIsEmpty() && window[i-1][j-1].getIsEmpty() && window[i][j-1].getIsEmpty() && window[i+1][j-1].getIsEmpty() && window[i+1][j].getIsEmpty());
                    else
                        return!(window[i-1][j].getIsEmpty() && window[i-1][j-1].getIsEmpty() && window[i][j-1].getIsEmpty() && window[i+1][j-1].getIsEmpty() && window[i+1][j].getIsEmpty() && window[i+1][j+1].getIsEmpty() && window[i][j+1].getIsEmpty() && window[i-1][j+1].getIsEmpty());
                }
            }
        }
    }

    public boolean spaceColorRestriction(Dice dice, int i, int j){
        return !(!window[i][j].getIsEmpty() || (!window[i][j].getColor().equals(dice.getColor()) && !window[i][j].getColor().equals(Colors.W)));

    }

    public boolean spaceNumberRestriction(Dice dice, int i, int j){
        return !(!window[i][j].getIsEmpty() || (window[i][j].getValue() != dice.getValue() && window[i][j].getValue() != 0));

    }

    public boolean verifyFirstDiceRestriction(Dice dice, int i, int j){
        return ( (((i == 0 || i == 3) && (j >= 0 && j <= 4)) || ((j == 0 || j == 4) && (i >= 0 && i <= 3))) && this.spaceColorRestriction(dice, i , j) && this.spaceNumberRestriction(dice, i, j) );
    }

    public boolean verifyAllRestrictions(Dice dice, int i, int j){
        return (this.neighboursColorRestriction(dice, i, j) && this.neighboursNumberRestriction(dice, i, j) && this.neighboursPositionRestriction(i, j) && this.spaceColorRestriction(dice, i, j) && this.spaceNumberRestriction(dice, i, j));
    }

}
