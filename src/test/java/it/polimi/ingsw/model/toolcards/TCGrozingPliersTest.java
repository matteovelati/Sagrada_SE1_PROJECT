package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TCGrozingPliersTest {

    private ToolCard toolCardGP = new TCGrozingPliers();
    private Window window;
    private Field field = Field.getInstance();
    private Draft draft;

    @Before
    public void before() {


        Dice dice1 = new Dice(Colors.R);    //00
        dice1.modifyValue(5);
        Dice dice2 = new Dice(Colors.B);    //01
        dice2.modifyValue(6);
        Dice dice3 = new Dice(Colors.R);    //02
        dice3.modifyValue(2);
        Dice dice4 = new Dice(Colors.R);    //03
        dice4.modifyValue(3);
        Dice dice5 = new Dice(Colors.Y);    //04
        dice5.modifyValue(2);
        Dice dice6 = new Dice(Colors.B);    //21
        dice6.modifyValue(2);
        Dice dice7 = new Dice(Colors.R);    //22
        dice7.modifyValue(5);
        Dice dice8 = new Dice(Colors.R);    //22
        dice8.modifyValue(5);


        Draft.getInstance().addDice(dice1);
        Draft.getInstance().addDice(dice2);
        Draft.getInstance().addDice(dice3);
        Draft.getInstance().addDice(dice4);
        Draft.getInstance().addDice(dice5);



        window = new Window(5);
        window.setWindow(dice1, 0, 0);
        window.setWindow(dice2, 0, 1);
        window.setWindow(dice3, 0, 2);
        window.setWindow(dice4, 0, 3);
        window.setWindow(dice5, 0, 4);
        window.setWindow(dice6, 2, 0);
        window.setWindow(dice7, 2, 1);
    }

    @Test
    public void useToolCard(Field field) {




    }
}