package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.rmi.RemoteException;

public class MatchController {

    @FXML
    private GridPane draft, toolcards, publicObjectives, roundtrack, grid;
    @FXML
    private AnchorPane clientWindow;
    @FXML
    private Label message;
    @FXML
    private HBox buttons;
    @FXML
    private Button pickDice, useToolcard, endTurn;

    private ViewGUI viewGUI;

    public void init() throws RemoteException {
        createDraft();
        setDraft();
        setToolCards();
        setPublicObjectives();
        setWindow();
    }

    public void waitTurn(){
        buttons.setDisable(true);
        clientWindow.setDisable(true);
        draft.setDisable(true);
        toolcards.setDisable(true);
        publicObjectives.setDisable(true);
        roundtrack.setDisable(true);
        message.setText("WAIT YOUR TURN");
    }

    public void selectMove1View(){
        buttons.setDisable(false);
        message.setText("CHOOSE YOUR MOVE");
    }

    public void selectDraftView(){
        draft.setDisable(false);
        endTurn.setDisable(false);
        endTurn.setText("ABORT");
        message.setText("SELECT A DICE");
    }

    public void putDiceInWindowView(){
        endTurn.setDisable(false);
        endTurn.setText("ABORT");
        clientWindow.setDisable(false);
        message.setText("PUT THE DICE IN YOUR WINDOW");
    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    private void setDraft() throws RemoteException {
        for(Node child : draft.getChildren()){
            if(viewGUI.checkDraftSize(GridPane.getColumnIndex(child))) {
                String s = "images/dices/" + viewGUI.getDraftDiceColor(GridPane.getColumnIndex(child)) + viewGUI.getDraftDiceValue(GridPane.getColumnIndex(child)) + ".png";
                Image image = new Image(getClass().getResourceAsStream(s));
                ((ImageView) child).setImage(image);
            }else{
                ((ImageView) child).setImage(null);

            }
        }
    }

    private void createDraft() throws RemoteException {
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

    private void setWindowGrid() throws RemoteException {
        for(Node child : grid.getChildren()){
            if(viewGUI.checkEmptyCell(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)))
                ((ImageView) child).setImage(null);
            else {
                String s = "images/dices/" + viewGUI.getWindowDiceColor(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + viewGUI.getWindowDiceValue(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + ".png";
                Image image = new Image(getClass().getResourceAsStream(s));
                ((ImageView) child).setImage(image);
            }
        }
    }

    public void pickDiceButton(ActionEvent e) throws IOException {
        System.out.println("ok");
        viewGUI.setChoose1(1);
        buttons.setDisable(true);
        viewGUI.notifyNetwork();
    }

    public void useToolcardButton(MouseEvent e) throws IOException {
        System.out.println("ok1");
        viewGUI.setChoose1(2);
        buttons.setDisable(true);
        viewGUI.notifyNetwork();
    }

    public void endTurnButton(MouseEvent e) throws RemoteException {
        System.out.println("ok2");
        if(endTurn.getText() == "END TURN") {
            viewGUI.setChoose1(0);
            buttons.setDisable(true);
            viewGUI.notifyNetwork();
        }
        else if(endTurn.getText() == "ABORT"){
            viewGUI.setChoose1(-1);
            endTurn.setText("END TURN");
            buttons.setDisable(true);
            viewGUI.notifyNetwork();
        }
    }

    public void selectDice(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        viewGUI.setChoose1(GridPane.getColumnIndex(selected)+1);
        draft.setDisable(true);
        viewGUI.notifyNetwork();
        endTurn.setDisable(true);
        endTurn.setText("END TURN");
    }

    public void windowClicked(MouseEvent e) throws RemoteException {
        if(viewGUI.getGameState().equals(States.PUTDICEINWINDOW)){
            ImageView selected = (ImageView) e.getSource();
            viewGUI.setChoose1(GridPane.getRowIndex(selected)+1);
            viewGUI.setChoose2(GridPane.getColumnIndex(selected)+1);
            clientWindow.setDisable(true);
            endTurn.setDisable(true);
            endTurn.setText("END TURN");
        }
    }

    public void error(String error){
        message.setText(error);
    }

    public void refresh() throws RemoteException {
        setDraft();
        setWindowGrid();
    }
}
