package it.polimi.ingsw.model;

import java.io.Serializable;

public class Window implements Serializable {

    private int idNumber;
    private boolean isEmpty;
    private String name;
    private int difficulty;
    private Space[][] window;

    /**
     * creates a Window object that is a spaces's matrix composed by 4 rows and 5 columns
     * initializes each space with '0' value, 'W' color and 'empty' boolean
     * according to x, sets the name, the difficulty and some restriction to the window's spaces
     * @param x the idnumber of window to be created
     */
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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

            case 11:
                this.name = "Symphony of Light";
                this.difficulty = 6;
                this.idNumber = x;
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

            case 13:
                this.name = "Water of Life";
                this.difficulty = 6;
                this.idNumber = x;
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

            case 15:
                this.name = "Sun's Glory";
                this.difficulty = 6;
                this.idNumber = x;
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

            case 10:
                this.name = "Via Lux";
                this.difficulty = 4;
                this.idNumber = x;
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

            case 12:
                this.name = "Virtus";
                this.difficulty = 5;
                this.idNumber = x;
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

            case 14:
                this.name = "Gravitas";
                this.difficulty = 5;
                this.idNumber = x;
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
                this.idNumber = x;
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
                this.idNumber = x;
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

            case 19:
                this.name = "Ripples of Light";
                this.difficulty = 5;
                this.idNumber = x;
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

            case 21:
                this.name = "Firmitas";
                this.difficulty = 5;
                this.idNumber = x;
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

            case 23:
                this.name = "Aurorae Magnificus";
                this.difficulty = 5;
                this.idNumber = x;
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

            case 18:
                this.name = "Sun Catcher";
                this.difficulty = 3;
                this.idNumber = x;
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

            case 20:
                this.name = "Fractal Drops";
                this.difficulty = 3;
                this.idNumber = x;
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

            case 22:
                this.name = "Kaleidoscopic Dream";
                this.difficulty = 4;
                this.idNumber = x;
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
                this.idNumber = x;
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
                break;
        }
    }

    /**
     * gets if the window is empty or not
     * @return true if the window is empty, false otherwise
     */
    public boolean getIsEmpty(){
        return isEmpty;
    }

    /**
     * gets the window's name
     * @return the window's name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the window's difficulty
     * @return the window's difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * gets the spaces's matrix of the window
     * @return the spaces's matrix of the window
     */
    public Space[][] getWindow() {
        return window;
    }

    /**
     * gets the id number of the window
     * @return the id number of the window
     */
    public int getIdNumber(){
        return idNumber;
    }

    /**
     * sets if the window is empty or not
     * @param isEmpty the boolean to be set
     */
    public void setIsEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    }

    /**
     * adds the die to the i,j window's position
     * checks and sets if the window is empty or not
     * @param dice the die to be added
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     */
    public void setWindow(Dice dice, int i, int j){
        this.window[i][j].setDice(dice);
        for (Space[] matrix : window){
            for (Space space : matrix){
                if (!space.getIsEmpty()){
                    this.setIsEmpty(false);
                    return;
                }
            }
        }
        this.setIsEmpty(true);
    }

    /**
     * check if there is a die of the same color of the die to be placed in the adjacent positions of i,j (N-W-S-E)
     * @param dice the die to be set
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false if there is at least one die of the same color adjacent
     */
    public boolean neighboursColorRestriction(Dice dice, int i, int j){
        for (int x = -1; x < 2; x += 2) {
            try {
                if (dice.getColor().equals(window[i + x][j].getDice().getColor()))
                    return false;
            } catch (Exception e) {
                //DO NOTHING
            }
        }
        for (int y = -1; y < 2; y += 2) {
            try {
                if (dice.getColor().equals(window[i][j + y].getDice().getColor()))
                    return false;
            } catch (Exception e) {
                //DO NOTHING
            }
        }
        return true;
    }

    /**
     * check if there is a die of the same value of the die to be placed in the adjacent positions of i,j (N-W-S-E)
     * @param dice the die to be set
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false if there is at least one die of the same value adjacent
     */
    public boolean neighboursNumberRestriction(Dice dice, int i, int j){
        for (int x = -1; x < 2; x += 2) {
            try {
                if (dice.getValue() == (window[i + x][j].getDice().getValue()))
                    return false;
            } catch (Exception e) {
                //DO NOTHING
            }
        }
        for (int y = -1; y < 2; y += 2) {
            try {
                if (dice.getValue() == (window[i][j + y].getDice().getValue()))
                    return false;
            } catch (Exception e) {
                //DO NOTHING
            }
        }
        return true;
    }

    /**
     * check if there is at least one die near by the i,j position (N-NW-W-SW-S-SE-E-NE)
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false if there isn't a die nearby
     */
    public boolean neighboursPositionRestriction(int i, int j){
        for (int y = -1; y < 2; y += 2) {
            try {
                if (!window[i][j + y].getIsEmpty())
                    return true;
            } catch (Exception e) {
                //DO NOTHING
            }
        }
        for (int x = -1; x < 2; x += 2){
            for (int y = -1; y < 2; y ++){
                try {
                    if (!window[i + x][j + y].getIsEmpty())
                        return true;
                } catch (Exception e) {
                    //DO NOTHING
                }
            }
        }
        return false;
    }

    /**
     * checks if the die to be placed verifies the window's space i,j color restriction
     * @param dice the die to be placed
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false if the color is not the same or the space isn't empty
     */
    public boolean spaceColorRestriction(Dice dice, int i, int j){
        return !(!window[i][j].getIsEmpty() || (!window[i][j].getColor().equals(dice.getColor()) && !window[i][j].getColor().equals(Colors.W)));

    }

    /**
     * checks if the die to be placed verifies the window's space i,j value restriction
     * @param dice the die to be placed
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false if the value is not the same or the space isn't empty
     */
    public boolean spaceNumberRestriction(Dice dice, int i, int j){
        return !(!window[i][j].getIsEmpty() || (window[i][j].getValue() != dice.getValue() && window[i][j].getValue() != 0));

    }

    /**
     * verifies if the first die can be placed according to all game restrictions
     * @param dice the die to be placed
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false otherwise
     */
    public boolean verifyFirstDiceRestriction(Dice dice, int i, int j){
        return ( (((i == 0 || i == 3) && (j >= 0 && j <= 4)) || ((j == 0 || j == 4) && (i >= 0 && i <= 3))) && this.spaceColorRestriction(dice, i , j) && this.spaceNumberRestriction(dice, i, j));
    }

    /**
     * verifies if the die can be placed according to all game restrictions
     * @param dice the die to be placed
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @return true if the die can be placed, false otherwise
     */
    public boolean verifyAllRestrictions(Dice dice, int i, int j){
        return (this.neighboursColorRestriction(dice, i, j) && this.neighboursNumberRestriction(dice, i, j) && this.neighboursPositionRestriction(i, j) && this.spaceColorRestriction(dice, i, j) && this.spaceNumberRestriction(dice, i, j));
    }

}
