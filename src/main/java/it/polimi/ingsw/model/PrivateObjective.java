package it.polimi.ingsw.model;

import java.io.Serializable;

public class PrivateObjective extends Card implements Serializable {

    /**
     * creates a new PrivateObjective setting idnumber, name, description and color
     * each private objective is owned by one and only one player
     * @param color is the color of the private objective card
     */
    public PrivateObjective(Colors color){

        super.setIdNumber(0);
        switch (color){
            case B:
                super.setName("Shades of Blue");
                super.setDescription("Private sum of values on BLUE dice\n");
                super.setColor(Colors.B);
                break;
            case G:
                super.setName("Shades of Green");
                super.setDescription("Private sum of values on GREEN dice\n");
                super.setColor(Colors.G);
                break;
            case P:
                super.setName("Shades of Purple");
                super.setDescription("Private sum of values on PURPLE dice\n");
                super.setColor(Colors.P);
                break;
            case R:
                super.setName("Shades of Red");
                super.setDescription("Private sum of values on RED dice\n");
                super.setColor(Colors.R);
                break;
            case Y:
                super.setName("Shades of Yellow");
                super.setDescription("Private sum of values on YELLOW dice\n");
                super.setColor(Colors.Y);
                break;
            default:
                assert false: "unknown color: " + color;
        }
    }

    /**
     * calculates the score of each player based on the color of his private objective
     * the player gets one more point for each die in his window of the same color of the private objective, one less for every empty box
     * and as many points as the remaining tokens
     * @param player is the player whose score is to be calculated
     * @return the player's final score
     */
    public int calculateScoreMP(Player player){                 //+1 per ogni dado dello stesso colore,
        int score = 0;                                          //+n segnalini favore residui,
        for (int i = 0; i < 4; i++){                            //-1 per ogni casella vuota
            for (int j = 0; j < 5; j++){
                if (!player.getWindow().getWindow()[i][j].getIsEmpty()) {
                    if (player.getPrivateObjectives().get(0).getColor().equals(player.getWindow().getWindow()[i][j].getDice().getColor()))
                    score++;
                }
                else
                    score--;
            }
        }
        score += player.getTokens();
        return score;
    }

    /**
     * calculates the score of the player based on the color of his two private objectives
     * the player gets one more point for each die in his window of the same color of the private objective
     * and three less for every empty box
     * @param player is the player whose score is to be calculated
     * @return the player's best final score based on one private objective
     */
    public int calculateScoreSP(Player player){
        int scoreA = 0;
        int scoreB = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!player.getWindow().getWindow()[i][j].getIsEmpty()) {
                    if (player.getPrivateObjectives().get(0).getColor().equals(player.getWindow().getWindow()[i][j].getDice().getColor()))
                        scoreA++;
                }
                else
                    scoreA -= 3;
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!player.getWindow().getWindow()[i][j].getIsEmpty()) {
                    if (player.getPrivateObjectives().get(1).getColor().equals(player.getWindow().getWindow()[i][j].getDice().getColor()))
                        scoreB++;
                }
                else
                    scoreB -= 3;
            }
        }
        if (scoreA > scoreB)
            return scoreA;
        else
            return scoreB;
    }

}
