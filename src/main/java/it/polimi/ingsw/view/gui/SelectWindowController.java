package it.polimi.ingsw.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

public class SelectWindowController {
    @FXML
    private Label text;
    @FXML
    private VBox container;
    @FXML
    private GridPane allWindows;

    private ViewGUI viewGUI;

    public void waitTurn(){
        allWindows.setDisable(true);
        text.setText("WAIT YOUR TURN");
    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    public void selectWindow(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        int selection = GridPane.getRowIndex(selected)*2 + GridPane.getColumnIndex(selected) + 1;
        waitTurn();
        viewGUI.setChoose1(selection);
    }

    public void setActualPlayer(){
        text.setText("CHOOSE A WINDOW PATTERN");
        allWindows.setDisable(false);
    }

}
