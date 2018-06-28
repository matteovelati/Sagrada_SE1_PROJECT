package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrivateObjectiveTest {

    private SchemeCard sc1;

    @Before
    public void setUp() {
        sc1 = new SchemeCard(3);
    }

    @Test
    public void calculateScore1() {
        //PURPLE
        setUp();
        Player player1 = new Player("1", Colors.P);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(6-1+3, player1.getPrivateObjectives().get(0).calculateScoreMP(player1));
        //expected: colored dice - empty spaces + tokens remaining
    }
    @Test
    public void calculateScore2() {
        //RED
        setUp();
        Player player1 = new Player("1", Colors.R);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(6-1+3, player1.getPrivateObjectives().get(0).calculateScoreMP(player1));
        //expected: colored dice - empty spaces + tokens remaining
    }
    @Test
    public void calculateScore3() {
        //YELLOW
        setUp();
        Player player1 = new Player("1", Colors.Y);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(8-1+3, player1.getPrivateObjectives().get(0).calculateScoreMP(player1));
        //expected: colored dice - empty spaces + tokens remaining
    }
    @Test
    public void calculateScore4() {
        //BLUE
        setUp();
        Player player1 = new Player("1", Colors.B);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(14-1+3, player1.getPrivateObjectives().get(0).calculateScoreMP(player1));
        //expected: colored dice - empty spaces + tokens remaining
    }
    @Test
    public void calculateScore5() {
        //GREEN
        setUp();
        Player player1 = new Player("1", Colors.G);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(4-1+3, player1.getPrivateObjectives().get(0).calculateScoreMP(player1));
        //expected: colored dice - empty spaces + tokens remaining
    }

    @Test
    public void calculateScore6() {
        //GREEN && RED
        setUp();
        Player player1 = new Player("1", Colors.G);
        player1.setPrivateObjectives(Colors.R);
        player1.setWindow(sc1, null, 2);

        Dice dice1 = new Dice(Colors.R);    //00
        Dice dice2 = new Dice(Colors.R);    //11
        Dice dice3 = new Dice(Colors.R);    //02
        Dice dice4 = new Dice(Colors.P);    //01
        Dice dice5 = new Dice(Colors.P);    //10
        Dice dice6 = new Dice(Colors.B);    //12
        Dice dice7 = new Dice(Colors.Y);    //20
        Dice dice8 = new Dice(Colors.B);    //30
        Dice dice9 = new Dice(Colors.B);    //21
        Dice dice10 = new Dice(Colors.Y);    //31
        Dice dice11 = new Dice(Colors.B);    //32
        Dice dice12 = new Dice(Colors.B);    //23
        Dice dice13 = new Dice(Colors.B);    //34
        Dice dice14 = new Dice(Colors.Y);    //33
        Dice dice15 = new Dice(Colors.G);    //24
        Dice dice16 = new Dice(Colors.P);    //03
        Dice dice17 = new Dice(Colors.Y);    //14
        Dice dice18 = new Dice(Colors.G);    //13
        Dice dice19 = new Dice(Colors.B);    //04
        dice1.modifyValue(2);
        dice2.modifyValue(2);
        dice3.modifyValue(2);
        dice4.modifyValue(2);
        dice5.modifyValue(2);
        dice6.modifyValue(2);
        dice7.modifyValue(2);
        dice8.modifyValue(2);
        dice9.modifyValue(2);
        dice10.modifyValue(2);
        dice11.modifyValue(2);
        dice12.modifyValue(2);
        dice13.modifyValue(2);
        dice14.modifyValue(2);
        dice15.modifyValue(2);
        dice16.modifyValue(2);
        dice17.modifyValue(2);
        dice18.modifyValue(2);
        dice19.modifyValue(2);
        player1.getWindow().setWindow(dice1, 0, 0);
        player1.getWindow().setWindow(dice4, 0, 1);
        player1.getWindow().setWindow(dice3, 0, 2);
        player1.getWindow().setWindow(dice16, 0, 3);
        player1.getWindow().setWindow(dice19, 0, 4);
        player1.getWindow().setWindow(dice5, 1, 0);
        player1.getWindow().setWindow(dice2, 1, 1);
        player1.getWindow().setWindow(dice6, 1, 2);
        player1.getWindow().setWindow(dice18, 1, 3);
        player1.getWindow().setWindow(dice17, 1, 4);
        player1.getWindow().setWindow(dice7, 2, 0);
        player1.getWindow().setWindow(dice9, 2, 1);
        //i=2, j=2 ---> 0W casella vuota
        player1.getWindow().setWindow(dice12, 2, 3);
        player1.getWindow().setWindow(dice15, 2, 4);
        player1.getWindow().setWindow(dice8, 3, 0);
        player1.getWindow().setWindow(dice10, 3, 1);
        player1.getWindow().setWindow(dice11, 3, 2);
        player1.getWindow().setWindow(dice14, 3, 3);
        player1.getWindow().setWindow(dice13, 3, 4);
        assertEquals(6-3, player1.getPrivateObjectives().get(0).calculateScoreSP(player1));
        //expected: RED colored dice - empty spaces
    }


}