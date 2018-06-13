package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.rmi.RemoteException;

public class MatchController {

    @FXML
    GridPane draft, toolcards, publicObjectives;
    @FXML
    AnchorPane clientWindow;

    private ViewGUI viewGUI;

    public void init() throws RemoteException {
        setDraft();
        createDraft();
        setToolCards();
        setPublicObjectives();
        setWindow();
    }

    public void waitTurn(){

    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    private void createDraft() throws RemoteException {
        for(Node child : draft.getChildren()){
            String s = "images/dices/" + viewGUI.getDiceColor(GridPane.getColumnIndex(child)) + viewGUI.getDiceValue(GridPane.getColumnIndex(child)) + ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            ((ImageView) child).setImage(image);
        }
    }

    private void setDraft() throws RemoteException {
        if(viewGUI.nPlayers()>2){
            for(int i=2; i<viewGUI.nPlayers(); i++) {
                ImageView space = new ImageView();
                space.setFitHeight(60);
                space.setFitWidth(60);
                draft.getColumnConstraints().add(new ColumnConstraints(60));
                draft.add(space, i*2+1, 0);
                ImageView space2 = new ImageView();
                space2.setFitHeight(60);
                space2.setFitWidth(60);
                draft.getColumnConstraints().add(new ColumnConstraints(60));
                draft.add(space2, i*2+2, 0);
            }
       }
    }

    private void setToolCards() throws RemoteException {
        for(Node child : toolcards.getChildren()){
            String s = "images/toolcards/" + viewGUI.getToolcardId(GridPane.getRowIndex(child))+ ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            ((ImageView) child).setImage(image);
        }
    }

    private void setPublicObjectives() throws RemoteException {
        for(Node child : publicObjectives.getChildren()){
            String s = "images/objectives/" + viewGUI.getPublicObjId(GridPane.getRowIndex(child))+ ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            ((ImageView) child).setImage(image);
        }
    }

    private void setWindow() {
        String s = "images/windows/" + viewGUI.getSelectedWindow() + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 300, 270, false, true);
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        clientWindow.setBackground(new Background(bg));
    }
}
