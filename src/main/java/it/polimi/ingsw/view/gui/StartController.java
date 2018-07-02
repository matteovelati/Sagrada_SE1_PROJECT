package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class StartController {

    @FXML
    private VBox container;
    @FXML
    private Button startButton;
    @FXML
    private Label error, message, lobby;
    @FXML
    private TextField username;
    @FXML
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;

    private ViewGUI viewGUI;
    private int state = 0;
    private int connectionType = 0;     //1 RMI; 2 SOCKET

    void init() {
        error.managedProperty().bind(error.visibleProperty());
        message.managedProperty().bind(message.visibleProperty());
        lobby.managedProperty().bind(lobby.visibleProperty());
        username.managedProperty().bind(username.visibleProperty());
        radioButton1.managedProperty().bind(radioButton1.visibleProperty());
        radioButton2.managedProperty().bind(radioButton2.visibleProperty());
        radioButton3.managedProperty().bind(radioButton3.visibleProperty());
        radioButton4.managedProperty().bind(radioButton4.visibleProperty());
        radioButton5.managedProperty().bind(radioButton5.visibleProperty());
        radioButton3.setVisible(false);
        radioButton4.setVisible(false);
        radioButton5.setVisible(false);
        chooseConnection();
    }

    public void startButtonClicked(ActionEvent event) throws IOException {
        if(state == 0)
            connectionSelected();
        else if(state == 1)
            ipInsertion();
        else if(state == 2)
            matchSelected();
        else if(state == 3)
            singlePlayerSetup();
        else if(state == 4)
            usernameInserted();
    }

    public void inputEnter(ActionEvent e) throws IOException {
        if(state == 1)
            ipInsertion();
        else if(state == 4)
            usernameInserted();
    }

    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    private void setUser(String s){
        viewGUI.setUser(s);
        container.getChildren().removeAll(startButton, username, error);
        message.setVisible(true);
        message.setText("YOU HAVE BEEN ADDED TO THIS GAME!\nIT WILL START IN A FEW MOMENTS");
        if(!viewGUI.getSinglePlayer())
            lobby.setVisible(true);
    }

    private void gameStarted(){
        container.getChildren().removeAll(error, lobby, startButton, username, radioButton1, radioButton2);
        error.setVisible(true);
        error.setText("OPS! THE GAME IS ALREADY STARTED!\nCOME BACK LATER!");
    }

    private void checkState() throws RemoteException {
        if(!viewGUI.checkLobby()){
            if(!viewGUI.reconnecting())
                gameStarted();
        }
    }

    void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/selectwindow.fxml"));
        Parent selectWindow = loader.load();

        SelectWindowController selectWindowController = loader.getController();
        selectWindowController.setViewGUI(viewGUI);
        viewGUI.setSelectWindowController(selectWindowController);
        selectWindowController.init();
        viewGUI.setChoose1(1);
        if(viewGUI.getSinglePlayer())
            viewGUI.getNetwork().startTimerSP(viewGUI);
        else {
            if (viewGUI.actualPlayer())
                viewGUI.playTimer();
            else
                selectWindowController.waitTurn();
        }

        Scene startScene;
        startScene = new Scene(selectWindow, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        mainStage.setScene(startScene);
        mainStage.setMaximized(true);
        mainStage.setFullScreen(true);
        mainStage.show();
        selectWindow.requestFocus();
    }

    void printLobby(){
        lobby.setText("GAMERS IN THE LOBBY:\n");
    }

    void addPrint(String s){
        lobby.setText(lobby.getText() + s + "\n");
    }

    void printError(String s){
        container.getChildren().removeAll(startButton, message, lobby, username, radioButton1, radioButton2);
        error.setVisible(true);
        error.setText(s);
    }

    private void chooseConnection(){
        error.setVisible(false);
        lobby.setVisible(false);
        username.setVisible(false);
        message.setText("CHOOSE YOUR CONNECTION:");
        radioButton1.setText("RMI");
        radioButton2.setText("SOCKET");
        startButton.setText("OK");
    }

    private void setIpIndex(){
        radioButton1.setVisible(false);
        radioButton2.setVisible(false);
        username.setVisible(true);
        username.setPromptText("IP address");
        message.setText("INSERT THE IP ADDRESS");
    }

    private void chooseMatchType(){
        username.setVisible(false);
        radioButton1.setVisible(true);
        radioButton2.setVisible(true);
        message.setText("SELECT THE KIND OF MATCH YOU WANNA PLAY");
        radioButton1.setText("SINGLEPLAYER");
        radioButton2.setText("MULTIPLAYER");
    }

    private void chooseDifficulty(){
        radioButton3.setVisible(true);
        radioButton4.setVisible(true);
        radioButton5.setVisible(true);
        radioButton1.setText("BEGINNER");
        radioButton2.setText("EASY");
        message.setText("CHOOSE A LEVEL OF DIFFICULTY");
    }

    private void insertUsername(){
        container.getChildren().removeAll(radioButton1, radioButton2, radioButton3, radioButton4, radioButton5);
        message.setVisible(false);
        username.setVisible(true);
        username.setPromptText("username");
        try {
            checkState();
        } catch (RemoteException e) {
            //do nothing
        }
    }

    private void connectionSelected(){
        if(radioButton1.isSelected()){
            connectionType = 1;             //RMI
        }
        else if(radioButton2.isSelected()){
            connectionType = 2;             //SOCKET
        }

        state = 1;
        setIpIndex();
    }

    private void ipInsertion(){
        if(connectionType == 1)
            viewGUI.setRMIConnection(username.getText());
        else if(connectionType == 2)
            viewGUI.setSocketConnection(username.getText());

        state = 2;
        username.clear();
        chooseMatchType();
    }

    private void matchSelected() throws RemoteException {
        if(radioButton1.isSelected()){          //SINGLEPLAYER
            if(viewGUI.getNetwork().getSinglePlayerStarted()){
                viewGUI.createSinglePlayerMatch(1);
                state = 4;
                insertUsername();
            }
            else {
                state = 3;
                chooseDifficulty();
            }
        }
        else if(radioButton2.isSelected()){     //MULTIPLAYER
            viewGUI.createMultiPlayerMatch();
            state = 4;
            insertUsername();
        }
    }

    private void singlePlayerSetup() throws RemoteException {
        int level = 0;

        if(radioButton1.isSelected())
            level = 1;
        else if(radioButton2.isSelected())
            level = 2;
        else if(radioButton3.isSelected())
            level = 3;
        else if(radioButton4.isSelected())
            level = 4;
        else if(radioButton5.isSelected())
            level = 5;

        viewGUI.createSinglePlayerMatch(level);
        state = 4;
        insertUsername();
    }

    private void usernameInserted() throws IOException {
        error.setVisible(false);
        String user = username.getText().trim().toUpperCase();

        if(!user.isEmpty()) {
            if(!viewGUI.checkLobby()) {
                if (viewGUI.reconnecting()) {
                    if (viewGUI.verifyUserCrashed(user)) {
                        setUser(user);
                        viewGUI.reAddPlayer();
                        container.getChildren().removeAll(startButton, lobby, username, radioButton1, radioButton2);
                        message.setVisible(true);
                        message.setText("JOINING AGAIN THE MATCH");
                        if (viewGUI.getSinglePlayer()) {
                            viewGUI.getNetwork().startTimerSP(viewGUI);
                            viewGUI.notifyNetwork();
                        } else {
                            if (viewGUI.getSocketConnection()) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                viewGUI.updateSocket();
                                            } catch (IOException e) {
                                                //
                                            } catch (ClassNotFoundException e) {
                                                //
                                            }
                                        }
                                    }).start();
                            }
                        }
                    } else {
                        error.setVisible(true);
                        error.setText("INSERT A VALID NAME");
                    }
                }
            }
            else {
                if (viewGUI.verifyUsername(user)) {
                    setUser(user);
                    if(!viewGUI.getSocketConnection())
                        viewGUI.getNetwork().addObserver(viewGUI);
                    viewGUI.notifyNetwork();
                    if(viewGUI.getSocketConnection()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    viewGUI.updateSocket();
                                } catch (IOException e) {
                                    //
                                } catch (ClassNotFoundException e) {
                                    //
                                }
                            }
                        }).start();
                    }
                } else {
                    error.setVisible(true);
                    error.setText("THIS USERNAME ALREADY EXIST");
                    username.clear();
                }
            }
        }
        else {
            error.setVisible(true);
            error.setText("PLEASE INSER A VALID USERNAME");
            username.clear();
        }
    }
}