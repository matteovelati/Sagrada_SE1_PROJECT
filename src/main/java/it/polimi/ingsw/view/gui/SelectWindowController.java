package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SelectWindowController {
    @FXML
    private Label text;
    @FXML
    private ImageView card1front, card1back, card2front, card2back;
    @FXML
    private GridPane allWindows;
    @FXML
    private Button rejoin;

    private ViewGUI viewGUI;

    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    void init() throws RemoteException {
        allWindows.managedProperty().bind(allWindows.visibleProperty());
        rejoin.managedProperty().bind(rejoin.visibleProperty());
        rejoin.setVisible(false);
        loadWindowPatterns();
    }

    void loadWindowPatterns() throws RemoteException {
        loadImage(0, true, card1front);
        loadImage(0, false, card1back);
        loadImage(1, true, card2front);
        loadImage(1, false, card2back);
    }

    void waitTurn(){
        allWindows.setVisible(false);
        if(viewGUI.getSinglePlayer()){
            text.setText("STARTING THE GAME");
        }else{
            text.setText("WAIT YOUR TURN");
            viewGUI.setBlockSocketConnection(false);
        }
    }

    void showWindowPatterns(){
        text.setText("CHOOSE A WINDOW PATTERN");
        allWindows.setVisible(true);
    }

    private void loadImage(int schemeCard, boolean frontSide, ImageView window) throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(schemeCard, frontSide) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 350, 313, false, true);
        window.setImage(image);
    }

    public void windowClick(MouseEvent e) throws IOException {
        ImageView selected = (ImageView) e.getSource();
        int selection = GridPane.getRowIndex(selected)*2 + GridPane.getColumnIndex(selected) + 1;
        //waitTurn();
        viewGUI.setChoose1(selection);
        viewGUI.notifyNetwork();
    }

    void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/match.fxml"));
        Parent match = loader.load();

        MatchController matchController = loader.getController();
        matchController.setViewGUI(viewGUI);
        viewGUI.setMatchController(matchController);
        matchController.init();
        matchController.waitTurn();
        if(viewGUI.actualPlayer()) {
            viewGUI.playTimer();
            matchController.selectMove1View();
        }

        Scene startScene;
        startScene = new Scene(match, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        mainStage.setScene(startScene);
        mainStage.setMaximized(true);
        mainStage.setFullScreen(true);
        mainStage.show();
        match.requestFocus();
    }

    void setInactive(){
        allWindows.setVisible(false);
        text.setText("YOU ARE NOW INACTIVE! TO JOIN AGAIN THE MATCH, PRESS THE BUTTON");
        rejoin.setVisible(true);
    }

    public void rejoinButtonClicked(ActionEvent e) throws IOException {
        viewGUI.matchRejoined();
        rejoin.setVisible(false);
        text.setText("JOINING AGAIN THE MATCH...\nWAIT YOUR TURN");
    }
}
