package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class StartController {

    @FXML
    private VBox container;
    @FXML
    private Button startButton;
    @FXML
    private Label usernameTaken, lobby;
    @FXML
    private TextField username;

    private ViewGUI viewGUI;

    public void init() {
        try {
            checkState();
        } catch (RemoteException e) {
            //do nothing
        }
    }

    public void startButtonClicked(ActionEvent event) throws IOException {

        usernameTaken.setText("");
        String user = username.getText().trim().toUpperCase();

        if(!user.isEmpty()) {
            if(viewGUI.reconnecting()){
                if(viewGUI.verifyUserCrashed(user)) {
                    setUser(user);
                    viewGUI.reAddPlayer();
                    container.getChildren().removeAll(startButton, username);
                    usernameTaken.setText("JOINING AGAIN THE MATCH");
                }
                else
                    usernameTaken.setText("INSERT A VALID NAME");
            }
            else {
                if (viewGUI.verifyUser(user)) {
                    setUser(user);
                    viewGUI.notifyNetwork();
                } else {
                    usernameTaken.setText("THIS USERNAME ALREADY EXIST");
                    username.clear();
                }
            }
        }
        else {
            usernameTaken.setText("PLEASE INSER A VALID USERNAME");
            username.clear();
        }
    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    public void setUser(String s) throws RemoteException {
        viewGUI.setUser(s);
        container.getChildren().removeAll(startButton, username);
        usernameTaken.setText("YOU HAVE BEEN ADDED TO THIS GAME!\nIT WILL START IN A FEW MOMENTS");
    }

    public void gameStarted(){
        container.getChildren().removeAll(startButton, username);
        usernameTaken.setText("OPS! THE GAME IS ALREADY STARTED!\nCOME BACK LATER!");
    }

    public void checkState() throws RemoteException {
        if(!viewGUI.checkLobby()){
            if(!viewGUI.reconnecting())
                gameStarted();
        }
    }

    public void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/selectwindow.fxml"));
        Parent selectWindow = loader.load();

        SelectWindowController selectWindowController = loader.getController();
        selectWindowController.setViewGUI(viewGUI);
        viewGUI.setSelectWindowController(selectWindowController);
        selectWindowController.init();
        if(!viewGUI.actualPlayer())
            selectWindowController.waitTurn();

        Scene startScene;
        startScene = new Scene(selectWindow, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        Stage primaryStage = mainStage;
        primaryStage.setScene(startScene);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void print(String s){
        lobby.setText(s);
    }

    public void addPrint(String s){
        lobby.setText(lobby.getText() + s + "\n");
    }

}