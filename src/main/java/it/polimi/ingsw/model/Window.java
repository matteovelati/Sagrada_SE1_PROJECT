package it.polimi.ingsw.model;

public class Window {

    private String name;
    private int difficulty;
    private Space[][] pattern;

    public Window(String name){
        pattern = new Space[4][5];
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                pattern[i][j] = new Space(Colors.WHITE, 0);
            }
        }

        switch (name){
            case "Bellesguard":
                this.name = name;
                this.difficulty = 3;
                pattern[0][0].setColor(Colors.BLUE);
                pattern[0][1].setValue(6);
                pattern[0][4].setColor(Colors.YELLOW);
                pattern[1][1].setValue(3);
                pattern[1][2].setColor(Colors.BLUE);
                pattern[2][1].setValue(5);
                pattern[2][2].setValue(6);
                pattern[2][3].setValue(2);
                pattern[3][1].setValue(4);
                pattern[3][3].setValue(1);
                pattern[3][4].setColor(Colors.GREEN);
                break;
            case "Industria":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(1);
                pattern[0][1].setColor(Colors.RED);
                pattern[0][2].setValue(3);
                pattern[0][4].setValue(6);
                pattern[1][0].setValue(5);
                pattern[1][1].setValue(4);
                pattern[1][2].setColor(Colors.RED);
                pattern[1][3].setValue(2);
                pattern[2][2].setValue(5);
                pattern[2][3].setColor(Colors.RED);
                pattern[2][4].setValue(1);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.RED);
                break;
            case "Symphony of Light":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(2);
                pattern[0][2].setValue(5);
                pattern[0][4].setValue(1);
                pattern[1][0].setColor(Colors.YELLOW);
                pattern[1][1].setValue(6);
                pattern[1][2].setColor(Colors.PURPLE);
                pattern[1][3].setValue(2);
                pattern[1][4].setColor(Colors.RED);
                pattern[2][1].setColor(Colors.BLUE);
                pattern[2][2].setValue(4);
                pattern[2][3].setColor(Colors.GREEN);
                pattern[3][1].setValue(3);
                pattern[3][3].setValue(5);
                break;
            case "Water of Life":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(6);
                pattern[0][1].setColor(Colors.BLUE);
                pattern[0][4].setValue(1);
                pattern[1][1].setValue(5);
                pattern[1][2].setColor(Colors.BLUE);
                pattern[2][0].setValue(4);
                pattern[2][1].setColor(Colors.RED);
                pattern[2][2].setValue(2);
                pattern[2][3].setColor(Colors.BLUE);
                pattern[3][0].setColor(Colors.GREEN);
                pattern[3][1].setValue(6);
                pattern[3][2].setColor(Colors.YELLOW);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.PURPLE);
                break;
            case "Sun's Glory":
                this.name = name;
                this.difficulty = 6;
                pattern[0][0].setValue(1);
                pattern[0][1].setColor(Colors.PURPLE);
                pattern[0][2].setColor(Colors.YELLOW);
                pattern[0][4].setValue(4);
                pattern[1][0].setColor(Colors.PURPLE);
                pattern[1][1].setColor(Colors.YELLOW);
                pattern[1][4].setValue(6);
                pattern[2][0].setColor(Colors.YELLOW);
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
                pattern[0][0].setColor(Colors.YELLOW);
                pattern[0][2].setValue(6);
                pattern[1][1].setValue(1);
                pattern[1][2].setValue(5);
                pattern[1][4].setValue(2);
                pattern[2][0].setValue(3);
                pattern[2][1].setColor(Colors.YELLOW);
                pattern[2][2].setColor(Colors.RED);
                pattern[2][3].setColor(Colors.PURPLE);
                pattern[3][2].setValue(4);
                pattern[3][3].setValue(3);
                pattern[3][4].setColor(Colors.RED);
                break;
            case "Virtus":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(4);
                pattern[0][2].setValue(2);
                pattern[0][3].setValue(5);
                pattern[0][4].setColor(Colors.GREEN);
                pattern[1][2].setValue(6);
                pattern[1][3].setColor(Colors.GREEN);
                pattern[1][4].setValue(2);
                pattern[2][1].setValue(3);
                pattern[2][2].setColor(Colors.GREEN);
                pattern[2][3].setValue(4);
                pattern[3][0].setValue(5);
                pattern[3][1].setColor(Colors.GREEN);
                pattern[3][2].setValue(1);
                break;
            case "Gravitas":
                this.name = name;
                this.difficulty = 5;
                pattern[0][0].setValue(1);
                pattern[0][2].setValue(3);
                pattern[0][3].setColor(Colors.BLUE);
                pattern[1][1].setValue(2);
                pattern[1][2].setColor(Colors.BLUE);
                pattern[2][0].setValue(6);
                pattern[2][1].setColor(Colors.BLUE);
                pattern[2][3].setValue(4);
                pattern[3][0].setColor(Colors.BLUE);
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
                pattern[1][4].setColor(Colors.YELLOW);
                pattern[2][3].setColor(Colors.YELLOW);
                pattern[2][4].setColor(Colors.RED);
                pattern[3][0].setValue(5);
                pattern[3][2].setColor(Colors.YELLOW);
                pattern[3][3].setColor(Colors.RED);
                pattern[3][4].setValue(6);
                break;
                //da fare le altre caselle
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

}
