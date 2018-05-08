package it.polimi.ingsw.model;

public class PrivateObjective extends Card {

    public PrivateObjective(Colors color){
        super(color);
        super.setIdNumber(0);
        switch (color){
            case B:
                super.setName("Shades of Blue");
                super.setDescription("Private sum of values on blue dice");
                break;
            case G:
                super.setName("Shades of Green");
                super.setDescription("Private sum of values on green dice\n");
                break;
            case P:
                super.setName("Shades of Purple");
                super.setDescription("Private sum of values on purple dice\n");
                break;
            case R:
                super.setName("Shades of Red");
                super.setDescription("Private sum of values on red dice\n");
                break;
            case Y:
                super.setName("Shades of Yellow");
                super.setDescription("Private sum of values on yellow dice\n");
                break;
            default:
                System.out.println("errore creazioni privateObjectives");
                break;
        }

    }

}
