package it.polimi.ingsw.model;

public class PublicObjective extends ObjectiveCard {

    public PublicObjective(int idNumber){
        super(idNumber);
        switch (idNumber){
            case 1:
                super.setName("Light Shades");
                super.setDescription("Sets of 1 & 2 anywhere");
                super.setScore(2);
                break;
            case 2:
                super.setName("Medium Shades");
                super.setDescription("Sets of 3 & 4 anywhere");
                super.setScore(2);
                break;
            case 3:
                super.setName("Dark Shades");
                super.setDescription("Sets of 5 & 6 anywhere");
                super.setScore(2);
                break;
            case 4:
                super.setName("Shade Variety");
                super.setDescription("Sets of one of each VALUE anywhere");
                super.setScore(5);
                break;
            case 5:
                super.setName("Color Variety");
                super.setDescription("Sets of one of each COLOR anywhere");
                super.setScore(4);
                break;
            case 6:
                super.setName("Row Shade Variety");
                super.setDescription("Rows with no repeated VALUES");
                super.setScore(5);
                break;
            case 7:
                super.setName("Column Shade Variety");
                super.setDescription("Columns with no repeated VALUES");
                super.setScore(4);
                break;
            case 8:
                super.setName("Row Color Variety");
                super.setDescription("Rows with no repeated COLORS");
                super.setScore(6);
                break;
            case 9:
                super.setName("Column Color Variety");
                super.setDescription("Column with no repeated COLORS");
                super.setScore(5);
                break;
            case 10:
                super.setName("Color Diagonals");
                super.setDescription("Count of diagonally adjacent same-color dice");
                // NIENTE SETSCORE, CALCOLATO ALLA FINE.
                break;
            default:
                System.out.println("errore creazione publicObjectives");
                break;
        }

    }

}
