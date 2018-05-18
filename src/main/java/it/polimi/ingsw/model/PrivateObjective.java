package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PrivateObjective extends Card implements Serializable {

    private ArrayList<Colors> allColors = new ArrayList<>(0);

    public PrivateObjective(){
        Colors colortmp;
        if (allColors.isEmpty()) {
            allColors.add(Colors.B);
            allColors.add(Colors.Y);
            allColors.add(Colors.G);
            allColors.add(Colors.R);
            allColors.add(Colors.P);
        }
        Collections.shuffle(allColors);
        colortmp = allColors.remove(0);


        super.setIdNumber(0);
        switch (colortmp){
            case B:
                super.setName("Shades of Blue");
                super.setDescription("Private sum of values on blue dice");
                super.setColor(Colors.B);
                break;
            case G:
                super.setName("Shades of Green");
                super.setDescription("Private sum of values on green dice\n");
                super.setColor(Colors.G);
                break;
            case P:
                super.setName("Shades of Purple");
                super.setDescription("Private sum of values on purple dice\n");
                super.setColor(Colors.P);
                break;
            case R:
                super.setName("Shades of Red");
                super.setDescription("Private sum of values on red dice\n");
                super.setColor(Colors.R);
                break;
            case Y:
                super.setName("Shades of Yellow");
                super.setDescription("Private sum of values on yellow dice\n");
                super.setColor(Colors.Y);
                break;
            default:
                System.out.println("errore creazioni privateObjectives");
                break;
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
