package it.polimi.ingsw.model;

import java.io.Serializable;

public class PrivateObjective extends Card implements Serializable {

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

    public int calculateScore(Player player){                   //+1 per ogni dado dello stesso colore,
        int score = 0;                                          //+n segnalini favore residui,
        for (int i = 0; i < 4; i++){                            //-1 per ogni casella vuota
            for (int j = 0; j < 5; j++){
                if (!player.getWindow().getWindow()[i][j].getIsEmpty()) {
                    if (player.getPrivateObjective().getColor().equals(player.getWindow().getWindow()[i][j].getDice().getColor()))
                    score++;
                }
                else
                    score--;
            }
        }
        score += player.getTokens();
        return score;
    }

}
