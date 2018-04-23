package it.polimi.ingsw.model;

public class Window {

    private String name;
    private int difficulty;
    private Space[][] pattern;

    public Window(String name){
        pattern = new Space[4][5];
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                pattern[i][j] = new Space(Colors.W, 0);
            }
        }

        switch (name){
            case "Bellesguard":
                this.name = name;
                this.difficulty = 3;
                pattern[0][0].setColor(Colors.B);
                pattern[0][1].setValue(6);
                pattern[0][4].setColor(Colors.Y);
                pattern[1][1].setValue(3);
                pattern[1][2].setColor(Colors.B);
                pattern[2][1].setValue(5);
                pattern[2][2].setValue(6);
                pattern[2][3].setValue(2);
                pattern[3][1].setValue(4);
                pattern[3][3].setValue(1);
                pattern[3][4].setColor(Colors.G);
                break;
            case "Battlo":
                this.name = "Battlo";
                this.difficulty = 5;
                pattern[0][2].setValue(6);
                pattern[1][1].setValue(5);
                pattern[1][2].setColor(Colors.B);
                pattern[1][3].setValue(4);
                pattern[2][0].setValue(3);
                pattern[2][1].setColor(Colors.G);
                pattern[2][2].setColor(Colors.Y);
                pattern[2][3].setColor(Colors.P);
                pattern[2][4].setValue(2);
                pattern[3][0].setValue(1);
                pattern[3][1].setValue(4);
                pattern[3][2].setColor(Colors.R);
                pattern[3][3].setValue(5);
                pattern[3][4].setValue(3);
                break;

            case "Chromatic Splendor":
                this.name = "Chromatic Splendor";
                this.difficulty = 4;
                pattern[0][2].setColor(Colors.G);
                pattern[1][0].setValue(2);
                pattern[1][1].setColor(Colors.Y);
                pattern[1][2].setValue(5);
                pattern[1][3].setColor(Colors.B);
                pattern[1][4].setValue(1);
                pattern[2][1].setColor(Colors.R);
                pattern[2][2].setValue(3);
                pattern[2][3].setColor(Colors.P);
                pattern[3][0].setValue(1);
                pattern[3][2].setValue(6);
                pattern[3][4].setValue(4);
                break;

            case "Comitas":
                this.name = "Comitas";
                this.difficulty = 5;
                pattern[0][0].setColor(Colors.Y);
                pattern[0][2].setValue(2);
                pattern[0][4].setValue(6);
                pattern[1][1].setValue(4);
                pattern[1][3].setValue(5);
                pattern[1][4].setColor(Colors.Y);
                pattern[2][3].setColor(Colors.Y);
                pattern[2][4].setValue(5);
                pattern[3][0].setValue(1);
                pattern[3][1].setValue(2);
                pattern[3][2].setColor(Colors.Y);
                pattern[3][3].setValue(3);
                break;

            case "Fulgor del Cielo":
                this.name = "Fulgor del Cielo";
                this.difficulty = 5;
                pattern[0][1].setColor(Colors.B);
                pattern[0][2].setColor(Colors.R);
                pattern[1][1].setValue(4);
                pattern[1][2].setValue(5);
                pattern[1][4].setColor(Colors.B);
                pattern[2][0].setColor(Colors.B);
                pattern[2][1].setValue(2);
                pattern[2][3].setColor(Colors.R);
                pattern[2][4].setValue(5);
                pattern[3][0].setValue(6);
                pattern[3][1].setColor(Colors.R);
                pattern[3][2].setValue(3);
                pattern[3][3].setValue(1);
                break;

            case "Luz Celestial":
                this.name = "Luz Celestial";
                this.difficulty = 3;
                pattern[0][2].setColor(Colors.R);
                pattern[0][3].setValue(5);
                pattern[1][0].setColor(Colors.P);
                pattern[1][1].setValue(4);
                pattern[1][3].setColor(Colors.G);
                pattern[1][4].setValue(3);
                pattern[2][0].setValue(6);
                pattern[2][3].setColor(Colors.B);
                pattern[3][1].setColor(Colors.Y);
                pattern[3][2].setValue(2);
                break;

            case "Lux Mundi":
                this.name = "Lux Mundi";
                this.difficulty = 6;
                pattern[0][2].setValue(1);
                pattern[1][0].setValue(1);
                pattern[1][1].setColor(Colors.G);
                pattern[1][2].setValue(3);
                pattern[1][3].setColor(Colors.B);
                pattern[1][4].setValue(2);
                pattern[2][0].setColor(Colors.B);
                pattern[2][1].setValue(5);
                pattern[2][2].setValue(4);
                pattern[2][3].setValue(6);
                pattern[2][4].setColor(Colors.G);
                pattern[3][1].setColor(Colors.B);
                pattern[3][2].setValue(5);
                pattern[3][3].setColor(Colors.G);
                break;

            case "Lux Astram":
                this.name = "Lux Astram";
                this.difficulty = 5;
                pattern[0][1].setValue(1);
                pattern[0][2].setColor(Colors.G);
                pattern[0][3].setColor(Colors.P);
                pattern[0][4].setValue(4);
                pattern[1][0].setValue(6);
                pattern[1][1].setColor(Colors.P);
                pattern[1][2].setValue(2);
                pattern[1][3].setValue(5);
                pattern[1][4].setColor(Colors.G);
                pattern[2][0].setValue(1);
                pattern[2][1].setColor(Colors.G);
                pattern[2][2].setValue(5);
                pattern[2][3].setValue(3);
                pattern[2][4].setColor(Colors.P);
                break;
            case "Industria":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(1);
                pattern[0][1].setColor(Colors.R);
                pattern[0][2].setValue(3);
                pattern[0][4].setValue(6);
                pattern[1][0].setValue(5);
                pattern[1][1].setValue(4);
                pattern[1][2].setColor(Colors.R);
                pattern[1][3].setValue(2);
                pattern[2][2].setValue(5);
                pattern[2][3].setColor(Colors.R);
                pattern[2][4].setValue(1);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.R);
                break;
            case "Symphony of Light":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(2);
                pattern[0][2].setValue(5);
                pattern[0][4].setValue(1);
                pattern[1][0].setColor(Colors.Y);
                pattern[1][1].setValue(6);
                pattern[1][2].setColor(Colors.P);
                pattern[1][3].setValue(2);
                pattern[1][4].setColor(Colors.R);
                pattern[2][1].setColor(Colors.B);
                pattern[2][2].setValue(4);
                pattern[2][3].setColor(Colors.G);
                pattern[3][1].setValue(3);
                pattern[3][3].setValue(5);
                break;
            case "Water of Life":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(6);
                pattern[0][1].setColor(Colors.B);
                pattern[0][4].setValue(1);
                pattern[1][1].setValue(5);
                pattern[1][2].setColor(Colors.B);
                pattern[2][0].setValue(4);
                pattern[2][1].setColor(Colors.R);
                pattern[2][2].setValue(2);
                pattern[2][3].setColor(Colors.B);
                pattern[3][0].setColor(Colors.G);
                pattern[3][1].setValue(6);
                pattern[3][2].setColor(Colors.Y);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.P);
                break;
            case "Sun's Glory":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(1);
                pattern[0][1].setColor(Colors.P);
                pattern[0][2].setColor(Colors.Y);
                pattern[0][4].setValue(4);
                pattern[1][0].setColor(Colors.P);
                pattern[1][1].setColor(Colors.Y);
                pattern[1][4].setValue(6);
                pattern[2][0].setColor(Colors.Y);
                pattern[2][3].setValue(5);
                pattern[2][4].setValue(3);
                pattern[3][1].setValue(5);
                pattern[3][2].setValue(4);
                pattern[3][3].setValue(2);
                pattern[3][4].setValue(1);
                break;
            case "Via Lux":
                this.name = name;
                this.difficulty = 4;
                pattern[0][0].setColor(Colors.Y);
                pattern[0][2].setValue(6);
                pattern[1][1].setValue(1);
                pattern[1][2].setValue(5);
                pattern[1][4].setValue(2);
                pattern[2][0].setValue(3);
                pattern[2][1].setColor(Colors.Y);
                pattern[2][2].setColor(Colors.R);
                pattern[2][3].setColor(Colors.P);
                pattern[3][2].setValue(4);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.R);
                break;
            case "Virtus":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(4);
                pattern[0][2].setValue(2);
                pattern[0][3].setValue(5);
                pattern[0][4].setColor(Colors.G);
                pattern[1][2].setValue(6);
                pattern[1][3].setColor(Colors.G);
                pattern[1][4].setValue(2);
                pattern[2][1].setValue(3);
                pattern[2][2].setColor(Colors.G);
                pattern[2][3].setValue(4);
                pattern[3][0].setValue(5);
                pattern[3][1].setColor(Colors.G);
                pattern[3][2].setValue(1);
                break;
            case "Gravitas":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(1);
                pattern[0][2].setValue(3);
                pattern[0][3].setColor(Colors.B);
                pattern[1][1].setValue(2);
                pattern[1][2].setColor(Colors.B);
                pattern[2][0].setValue(6);
                pattern[2][1].setColor(Colors.B);
                pattern[2][3].setValue(4);
                pattern[3][0].setColor(Colors.B);
                pattern[3][1].setValue(5);
                pattern[3][2].setValue(2);
                pattern[3][4].setValue(1);
                break;
            case "Firelight":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(3);
                pattern[0][1].setValue(4);
                pattern[0][2].setValue(1);
                pattern[0][3].setValue(5);
                pattern[1][1].setValue(6);
                pattern[1][2].setValue(2);
                pattern[1][4].setColor(Colors.Y);
                pattern[2][3].setColor(Colors.Y);
                pattern[2][4].setColor(Colors.R);
                pattern[3][0].setValue(5);
                pattern[3][2].setColor(Colors.Y);
                pattern[3][3].setColor(Colors.R);
                pattern[3][4].setValue(6);
                break;
            case "Shadow Thief":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(6);
                pattern[0][1].setColor(Colors.P);
                pattern[0][4].setValue(5);
                pattern[1][0].setValue(5);
                pattern[1][2].setColor(Colors.P);
                pattern[2][0].setColor(Colors.R);
                pattern[2][1].setValue(6);
                pattern[2][3].setColor(Colors.P);
                pattern[3][0].setColor(Colors.Y);
                pattern[3][1].setColor(Colors.R);
                pattern[3][2].setValue(5);
                pattern[3][3].setValue(4);
                pattern[3][4].setValue(3);
                break;
            case "Ripples of Light":
                this.name = name;
                this.difficulty = 5;
                pattern[0][3].setColor(Colors.R);
                pattern[0][4].setValue(5);
                pattern[1][2].setColor(Colors.P);
                pattern[1][3].setValue(4);
                pattern[1][4].setColor(Colors.B);
                pattern[2][1].setColor(Colors.B);
                pattern[2][2].setValue(3);
                pattern[2][3].setColor(Colors.Y);
                pattern[2][4].setValue(6);
                pattern[3][0].setColor(Colors.Y);
                pattern[3][1].setValue(2);
                pattern[3][2].setColor(Colors.G);
                pattern[3][3].setValue(1);
                pattern[3][4].setColor(Colors.R);
                break;
            case "Firmitas":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setColor(Colors.P);
                pattern[0][1].setValue(6);
                pattern[0][4].setValue(3);
                pattern[1][0].setValue(5);
                pattern[1][1].setColor(Colors.P);
                pattern[1][2].setValue(3);
                pattern[2][1].setValue(2);
                pattern[2][2].setColor(Colors.P);
                pattern[2][3].setValue(1);
                pattern[3][1].setValue(1);
                pattern[3][2].setValue(5);
                pattern[3][3].setColor(Colors.P);
                pattern[3][4].setValue(4);
                break;
            case "Aurorae Magnificus":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(5);
                pattern[0][1].setColor(Colors.G);
                pattern[0][2].setColor(Colors.B);
                pattern[0][3].setColor(Colors.P);
                pattern[0][4].setValue(2);
                pattern[1][0].setColor(Colors.P);
                pattern[1][4].setColor(Colors.Y);
                pattern[2][0].setColor(Colors.Y);
                pattern[2][2].setValue(6);
                pattern[2][4].setColor(Colors.P);
                pattern[3][0].setValue(1);
                pattern[3][3].setColor(Colors.G);
                pattern[3][4].setValue(4);
                break;
            case "Sun Catcher":
                this.name = name;
                this.difficulty = 3;
                pattern[0][1].setColor(Colors.B);
                pattern[0][2].setValue(2);
                pattern[0][4].setColor(Colors.Y);
                pattern[1][1].setValue(4);
                pattern[1][3].setColor(Colors.R);
                pattern[2][2].setValue(5);
                pattern[2][3].setColor(Colors.Y);
                pattern[3][0].setColor(Colors.G);
                pattern[3][1].setValue(3);
                pattern[3][4].setColor(Colors.P);
                break;
            case "Fractal Drops":
                this.name = name;
                this.difficulty = 3;
                pattern[0][1].setValue(4);
                pattern[0][3].setColor(Colors.Y);
                pattern[0][4].setValue(6);
                pattern[1][0].setColor(Colors.R);
                pattern[1][2].setValue(3);
                pattern[2][2].setColor(Colors.R);
                pattern[2][3].setColor(Colors.P);
                pattern[2][4].setValue(1);
                pattern[3][0].setColor(Colors.B);
                pattern[3][1].setColor(Colors.Y);
                break;
            case "Kaleidoscopic Dream":
                this.name = name;
                this.difficulty = 4;
                pattern[0][0].setColor(Colors.Y);
                pattern[0][1].setColor(Colors.B);
                pattern[0][4].setValue(1);
                pattern[1][0].setColor(Colors.G);
                pattern[1][2].setValue(5);
                pattern[1][4].setValue(4);
                pattern[2][0].setValue(3);
                pattern[2][2].setColor(Colors.R);
                pattern[2][4].setColor(Colors.G);
                pattern[3][0].setValue(2);
                pattern[3][3].setColor(Colors.B);
                pattern[3][4].setColor(Colors.Y);
                break;
            case "Aurora Sagradis":
                this.name = name;
                this.difficulty = 4;
                pattern[0][0].setColor(Colors.R);
                pattern[0][2].setColor(Colors.B);
                pattern[0][4].setColor(Colors.Y);
                pattern[1][0].setValue(4);
                pattern[1][1].setColor(Colors.P);
                pattern[1][2].setValue(3);
                pattern[1][3].setColor(Colors.G);
                pattern[1][4].setValue(2);
                pattern[2][1].setValue(1);
                pattern[2][3].setValue(5);
                pattern[3][2].setValue(6);
                break;
            default:
                System.out.println("Errore colorazione facciata");
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public Space[][] getPattern() {
        return pattern;
    }

    public void setPattern(Dice dice, int i, int j){
        this.pattern[i][j].setDice(dice);
    }

    private boolean colorRestriction(Dice dice, int i, int j){
        //restrizione colore su casella selezionata
        if(!pattern[i][j].getIsEmpty() || (!pattern[i][j].getColor().equals(dice.getColor()) && !pattern[i][j].getColor().equals(Colors.W))){
            return false;
        }
        //restrizione colore su caselle adiacenti N S O E
        else {
            if (i == 0) {
                if (j == 0) {
                    if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (j == 4) {
                        if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() &&pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            } else {
                if (i == 3) {
                    if (j == 0) {
                        if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor()))) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (j == 4) {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor()))) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor()))) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                } else {
                    if (j == 0) {
                        if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (j == 4) {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getColor().equals(dice.getColor())) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getColor().equals(dice.getColor())) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getColor().equals(dice.getColor())) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getColor().equals(dice.getColor()))) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean numberRestriction(Dice dice, int i, int j){
        if(!pattern[i][j].getIsEmpty() || (pattern[i][j].getValue() != dice.getValue() && pattern[i][j].getValue() != 0)){
            return false;
        }
        else{
            if (i == 0) {
                if (j == 0) {
                    if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (j == 4) {
                        if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            } else {
                if (i == 3) {
                    if (j == 0) {
                        if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue())) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (j == 4) {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue())) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue())) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                } else {
                    if (j == 0) {
                        if ((!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (j == 4) {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            if ((!pattern[i][j-1].getIsEmpty() && pattern[i][j - 1].getValue() == dice.getValue()) || (!pattern[i][j+1].getIsEmpty() && pattern[i][j + 1].getValue() == dice.getValue()) || (!pattern[i-1][j].getIsEmpty() && pattern[i - 1][j].getValue() == dice.getValue()) || (!pattern[i+1][j].getIsEmpty() && pattern[i + 1][j].getValue() == dice.getValue())) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean verifyRestriction(Dice dice, int i, int j){
        if(this.colorRestriction(dice, i, j) && this.numberRestriction(dice, i, j) && this.pattern[i][j].getIsEmpty()){
            return true;
        }
        else return false;
    }

    public void print(){
        System.out.println(this.getName() +"  "+this.getDifficulty());
        for(int i=0; i<4; i++){
            System.out.println("\n| - - - | - - - | - - - | - - - | - - - |");
            System.out.print("|");
            for(int j=0; j<5; j++){
                System.out.print("  "+pattern[i][j].getValue()+" "+pattern[i][j].getColor()+"  ");
                System.out.print("|");
            }
        }
        System.out.println("\n| - - - | - - - | - - - | - - - | - - - |");
    }

}
