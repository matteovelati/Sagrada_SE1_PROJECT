package it.polimi.ingsw.model;

public class ToolCard extends Card {

    private boolean isUsed;

    public ToolCard (int idNumber){
        super(idNumber);
        switch(idNumber){
            case 1:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.P);
                super.setName("Grozing Pliers");
                super.setDescription("After drafting, increase or decrease the value of the drafted die by ONE." +
                        " ONE may not change to SIX, or SIX to ONE.\n");
                break;
            case 2:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.B);
                super.setName("Eglomise Brush");
                super.setDescription("Move any ONE die in your window ignoring color restrictions.\n");
                break;
            case 3:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.R);
                super.setName("Copper Foil Burnisher");
                super.setDescription("Move any ONE die in your window ignoring value restrictions.\n");
                break;
            case 4:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.Y);
                super.setName("Lathekin");
                super.setDescription("Move exactly TWO dice, obeying all placement restrictions.\n");
                break;
            case 5:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.G);
                super.setName("Lens Cutter");
                super.setDescription("After drafting, swap the drafted die with a die from the Round Track.\n");
                break;
            case 6:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.P);
                super.setName("Flux Brush");
                super.setDescription("After drafting, re-roll the drafted die." +
                        "If it cannot be placed, return it to the Draft Pool.\n");
                break;
            case 7:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.B);
                super.setName("Glazing Hammer");
                super.setDescription("Re-roll all dice in the Draft Pool." +
                        " This may only be used on your second turn before drafting.\n");
                break;
            case 8:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.R);
                super.setName("Running Pliers");
                super.setDescription("After your first turn, immediately draft a die." +
                        " Skip your next turn this round.\n");
                break;
            case 9:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.Y);
                super.setName("Cork-backed Straightedge");
                super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\n");
                break;
            case 10:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.G);
                super.setName("Grinding Stone");
                super.setDescription("After drafting, flip the die to its opposite side.\n");
                break;
            case 11:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.P);
                super.setName("Flux Remover");
                super.setDescription("After-drafting, return the die to the Dice Bag and pull ONE die from the bag.\n" +
                        "Choose a value and place the new dice, obeying all placement restrictions, or return it to the Draft Pool.\n");
                break;
            case 12:
                super.setIdNumber(idNumber);
                this.isUsed = false;
                super.setColor(Colors.B);
                super.setName("Tap Wheel");
                super.setDescription("Move up to TWO dice of the same color that match the color of a die on the Round Track.\n" +
                        "You must obey all placement restrictions.\n");
                break;
            default:
                System.out.println("Errore creazione toolcards");
                break;
        }

    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

}
