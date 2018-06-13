package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class POLightShadesTest {

    private PublicObjective po = new POLightShades();
    private Window window;

    @Before
    public void before() {


        Dice dice1 = new Dice(Colors.R);    //00
        dice1.modifyValue(4);
        Dice dice2 = new Dice(Colors.R);    //11
        dice2.modifyValue(4);
        Dice dice3 = new Dice(Colors.R);    //02
        dice3.modifyValue(4);
        Dice dice4 = new Dice(Colors.P);    //01
        dice4.modifyValue(6);
        Dice dice5 = new Dice(Colors.P);    //10
        dice5.modifyValue(5);
        Dice dice6 = new Dice(Colors.B);    //12
        dice6.modifyValue(6);
        Dice dice7 = new Dice(Colors.Y);    //20
        dice7.modifyValue(6);
        Dice dice8 = new Dice(Colors.B);    //30
        dice8.modifyValue(1);
        Dice dice9 = new Dice(Colors.B);    //21
        dice9.modifyValue(2);
        Dice dice10 = new Dice(Colors.Y);    //31
        dice10.modifyValue(4);
        Dice dice11 = new Dice(Colors.B);    //32
        dice11.modifyValue(2);
        Dice dice12 = new Dice(Colors.B);    //23
        dice12.modifyValue(2);
        Dice dice13 = new Dice(Colors.B);    //34
        dice13.modifyValue(2);
        Dice dice14 = new Dice(Colors.Y);    //33
        dice14.modifyValue(1);
        Dice dice15 = new Dice(Colors.G);    //24
        dice15.modifyValue(4);
        Dice dice16 = new Dice(Colors.P);    //03
        dice16.modifyValue(5);
        Dice dice17 = new Dice(Colors.Y);    //14
        dice17.modifyValue(3);
        Dice dice18 = new Dice(Colors.G);    //13
        dice18.modifyValue(1);
        Dice dice19 = new Dice(Colors.B);    //04
        dice19.modifyValue(1);

        window = new Window(6);
        window.setWindow(dice1, 0, 0);
        window.setWindow(dice4, 0, 1);
        window.setWindow(dice3, 0, 2);
        window.setWindow(dice16, 0, 3);
        window.setWindow(dice19, 0, 4);
        window.setWindow(dice5, 1, 0);
        window.setWindow(dice2, 1, 1);
        window.setWindow(dice6, 1, 2);
        window.setWindow(dice18, 1, 3);
        window.setWindow(dice17, 1, 4);
        window.setWindow(dice7, 2, 0);
        window.setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        window.setWindow(dice12, 2, 3);
        window.setWindow(dice15, 2, 4);
        window.setWindow(dice8, 3, 0);
        window.setWindow(dice10, 3, 1);
        window.setWindow(dice11, 3, 2);
        window.setWindow(dice14, 3, 3);
        window.setWindow(dice13, 3, 4);
    }

    @Test

    public void calculateScore() {
        before();
        assertEquals(8, po.calculateScore(window));
    }
}