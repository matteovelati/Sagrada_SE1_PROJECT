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

    /**
     * sets the viewGUI reference
     * @param viewGUI the viewGUI reference
     */
    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    /**
     * initializes the select window scene
     * @throws RemoteException if the reference could not be accessed
     */
    void init() throws RemoteException {
        allWindows.managedProperty().bind(allWindows.visibleProperty());
        rejoin.managedProperty().bind(rejoin.visibleProperty());
        rejoin.setVisible(false);
        loadWindowPatterns();
    }

    /**
     * removes all the center graphics and show a message
     */
    void serverDown(){
        allWindows.setVisible(false);
        rejoin.setVisible(false);
        text.setText("SEEMS LIKE THE SERVER HAS BEEN SHUT DOWN");
    }

    /**
     * loads the window patterns images
     * @throws RemoteException if the reference could not be accessed
     */
    void loadWindowPatterns() throws RemoteException {
        loadImage(0, true, card1front);
        loadImage(0, false, card1back);
        loadImage(1, true, card2front);
        loadImage(1, false, card2back);
    }

    /**
     * hides all the select window scene and show a "wait your turn" text
     */
    void waitTurn(){
        allWindows.setVisible(false);
        if(viewGUI.getSinglePlayer()){
            text.setText("STARTING THE GAME");
        }else{
            text.setText("WAIT YOUR TURN");
            viewGUI.setBlockSocketConnection(false);
        }
    }

    /**
     * shows the window patterns
     */
    void showWindowPatterns(){
        text.setText("CHOOSE A WINDOW PATTERN");
        allWindows.setVisible(true);
    }

    /**
     * loads the image based on the parameters
     * @param schemeCard scheme card index from the model
     * @param frontSide true if needs to load the front side image, otherwise the back side image
     * @param window image container
     * @throws RemoteException
     */
    private void loadImage(int schemeCard, boolean frontSide, ImageView window) throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(schemeCard, frontSide) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 350, 313, false, true);
        window.setImage(image);
    }

    /**
     * sets the window pattern selected
     * @param e windowclick event
     * @throws IOException
     */
    public void windowClick(MouseEvent e) throws IOException {
        ImageView selected = (ImageView) e.getSource();
        int selection = GridPane.getRowIndex(selected)*2 + GridPane.getColumnIndex(selected) + 1;
        viewGUI.setChoose1(selection);
        viewGUI.notifyNetwork();
    }

    /**
     * changes the screen scene
     * @param mainStage where the schene must be loaded
     * @throws IOException
     */
    void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/match.fxml"));
        Parent match = loader.load();

        MPMatchController matchController = loader.getController();
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

    /**
     * hides all the select window graphics and show the rejoin button
     */
    void setInactive(){
        allWindows.setVisible(false);
        text.setText("YOU ARE NOW INACTIVE! TO JOIN AGAIN THE MATCH, PRESS THE BUTTON");
        rejoin.setVisible(true);
    }

    /**
     * hide the rejoin button and add again the player to the match
     * @param e rejoin button event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void rejoinButtonClicked(ActionEvent e) throws IOException {
        viewGUI.matchRejoined();
        rejoin.setVisible(false);
        text.setText("JOINING AGAIN THE MATCH...\nWAIT YOUR TURN");
    }
}
