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
    private String ipAddress;
    private boolean wrongIP = false;

    /**
     * initializes the first gui screen
     */
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
        chooseMatchType();
    }

    /**
     * refreshes the window based on the actual state
     * @param event start button event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void startButtonClicked(ActionEvent event) throws IOException {
        if(state == 0)
            matchSelected();
        else if(state == 1)
            connectionSelected();
        else if(state == 2)
            ipInsertion();
        else if(state == 3)
            singlePlayerSetup();
        else if(state == 4)
            usernameInserted();
    }

    /**
     * refreshes the window based on the actual state
     * @param e input action event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void inputEnter(ActionEvent e) throws IOException {
        if(state == 2)
            ipInsertion();
        else if(state == 4)
            usernameInserted();
    }

    /**
     * sets the viewGUI reference
     * @param viewGUI the viewGUI reference
     */
    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    /**
     * sets if the ip address inserted is wrong
     * @param wrongIP
     */
    public void setWrongIP(boolean wrongIP) {
        this.wrongIP = wrongIP;
    }

    /**
     * sets the client username
     * @param s username
     */
    private void setUser(String s){
        viewGUI.setUser(s);
        container.getChildren().removeAll(startButton, username, error);
        message.setVisible(true);
        message.setText("YOU HAVE BEEN ADDED TO THIS GAME!\nIT WILL START IN A FEW MOMENTS");
        if(!viewGUI.getSinglePlayer())
            lobby.setVisible(true);
    }

    /**
     * hides all if the game is already started
     */
    private void gameStarted(){
        container.getChildren().removeAll(message, lobby, startButton, username, radioButton1, radioButton2, radioButton3, radioButton4, radioButton5);
        error.setVisible(true);
        error.setText("OPS! THE GAME IS ALREADY STARTED!\nCOME BACK LATER!");
    }

    /**
     * verifies if the client can continue joining the match
     * @return true if the client can join the match, otherwise false
     * @throws RemoteException if the reference could not be accessed
     */
    private boolean checkState() throws RemoteException {
        if((viewGUI.getSinglePlayer() && viewGUI.getNetwork().getMultiPlayerStarted()) || (!viewGUI.getSinglePlayer() && viewGUI.getNetwork().getSinglePlayerStarted())) {
            gameStarted();
            return false;
        }
        if(!viewGUI.checkLobby()){
            if(!viewGUI.reconnecting()) {
                gameStarted();
                return false;
            }
        }
        return true;
    }

    /**
     * changes the screen scene
     * @param mainStage where the schene must be loaded
     * @throws IOException
     */
    void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/selectwindow.fxml"));
        Parent selectWindow = loader.load();

        SelectWindowController selectWindowController = loader.getController();
        selectWindowController.setViewGUI(viewGUI);
        viewGUI.setSelectWindowController(selectWindowController);
        selectWindowController.init();
        viewGUI.setChoose1(1);
        if(viewGUI.getSinglePlayer()) {
            if(connectionType==1)
                viewGUI.getNetwork().startTimerSP(viewGUI);
        }
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

    /**
     * show a text in the lobby
     */
    void printLobby(){
        lobby.setText("GAMERS IN THE LOBBY:\n");
    }

    /**
     * show the player username
     * @param s player username
     */
    void addPrint(String s){
        lobby.setText(lobby.getText() + s + "\n");
    }

    /**
     * hides all and show the string error
     * @param s string error
     */
    void printError(String s){
        container.getChildren().removeAll(startButton, message, lobby, username, radioButton1, radioButton2);
        error.setVisible(true);
        error.setText(s);
    }

    /**
     * enables to select the match type you want to play
     */
    private void chooseMatchType(){
        error.setVisible(false);
        lobby.setVisible(false);
        username.setVisible(false);
        message.setText("SELECT THE KIND OF MATCH YOU WANNA PLAY");
        radioButton1.setText("SINGLEPLAYER");
        radioButton2.setText("MULTIPLAYER");
        startButton.setText("OK");
    }

    /**
     * enables to select the connection type (RMI or SOCKET)
     */
    private void chooseConnection(){
        message.setText("CHOOSE YOUR CONNECTION:");
        radioButton1.setText("RMI");
        radioButton2.setText("SOCKET");
    }

    /**
     * enables to insert the server ip addres
     */
    private void setIpIndex(){
        radioButton1.setVisible(false);
        radioButton2.setVisible(false);
        username.setVisible(true);
        username.setPromptText("IP address");
        message.setText("INSERT THE IP ADDRESS");
    }

    /**
     * enables to select the difficulty (only for single player)
     */
    private void chooseDifficulty(){
        username.setVisible(false);
        radioButton1.setVisible(true);
        radioButton2.setVisible(true);
        radioButton3.setVisible(true);
        radioButton4.setVisible(true);
        radioButton5.setVisible(true);
        radioButton1.setText("BEGINNER");
        radioButton2.setText("EASY");
        message.setText("CHOOSE A LEVEL OF DIFFICULTY");
    }

    /**
     * enables to insert the username
     */
    private void insertUsername(){
        radioButton1.setVisible(false);
        radioButton2.setVisible(false);
        radioButton3.setVisible(false);
        radioButton4.setVisible(false);
        radioButton5.setVisible(false);
        message.setVisible(false);
        username.clear();
        username.setVisible(true);
        username.setPromptText("username");
    }

    /**
     * verifies the type of match selected
     */
    private void matchSelected(){
        if(radioButton1.isSelected()){          //SINGLEPLAYER
            viewGUI.setSinglePlayer(true);
        }
        else if(radioButton2.isSelected()){     //MULTIPLAYER
            viewGUI.setSinglePlayer(false);
        }
        state = 1;
        chooseConnection();
    }

    /**
     * verifies the type of connection selected
     */
    private void connectionSelected(){
        if(radioButton1.isSelected()){
            connectionType = 1;             //RMI
        }
        else if(radioButton2.isSelected()){
            connectionType = 2;             //SOCKET
        }

        state = 2;
        setIpIndex();
    }

    /**
     * check if the ip is correct and create/rejoin a match
     * @throws RemoteException if the reference could not be accessed
     */
    private void ipInsertion() throws RemoteException {
        if(connectionType == 1)
            viewGUI.setRMIConnection(username.getText());
        else if(connectionType == 2) {
            ipAddress = username.getText();
        }

        if(!wrongIP) {
            if (viewGUI.getSinglePlayer()) {
                if (connectionType == 1) {
                    if (viewGUI.getNetwork().getMultiPlayerStarted())
                        gameStarted();
                }
                state = 3;
                chooseDifficulty();
            } else {
                if (connectionType == 1) {
                    if (viewGUI.getNetwork().getSinglePlayerStarted())
                        gameStarted();
                    else {
                        viewGUI.createMultiPlayerMatch();
                        state = 4;
                        try {
                            if (checkState()) {
                                insertUsername();
                            }
                        } catch (RemoteException e) {
                            //do nothing
                        }
                    }
                } else {
                    viewGUI.setSocketConnection(ipAddress);
                    if(!wrongIP) {
                        if (viewGUI.getNetwork().getSinglePlayerStarted())
                            gameStarted();
                        else {
                            state = 4;
                            try {
                                if (checkState())
                                    insertUsername();
                            } catch (RemoteException e) {
                                //do nothing
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * verifies the difficulty selected and start/rejoin the game
     * @throws RemoteException if the reference could not be accessed
     */
    private void singlePlayerSetup() throws RemoteException {
        if(radioButton1.isSelected())
            viewGUI.setLevel(1);
        else if(radioButton2.isSelected())
            viewGUI.setLevel(2);
        else if(radioButton3.isSelected())
            viewGUI.setLevel(3);
        else if(radioButton4.isSelected())
            viewGUI.setLevel(4);
        else if(radioButton5.isSelected())
            viewGUI.setLevel(5);

        if(connectionType == 2)
            viewGUI.setSocketConnection(ipAddress);
        else
            viewGUI.createSinglePlayerMatch();

        if(!wrongIP) {
            state = 4;
            try {
                if (checkState())
                    insertUsername();
            } catch (RemoteException e) {
                //do nothing
            }
        }
        else
            container.getChildren().removeAll(radioButton3, radioButton4, radioButton5);
    }

    /**
     * verifies if the username is correct.
     * verifies if the username not already exists.
     * verifies if the user is reconnecting.
     * If it's all verified start/rejoin the match, otherwise stop the client
     * @throws IOException any exception thrown by the underlying OutputStream
     */
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
                            if(connectionType == 1) {
                                viewGUI.getNetwork().startTimerSP(viewGUI);
                                viewGUI.notifyNetwork();
                            }else {
                                new Thread(()-> {
                                    try {
                                        viewGUI.updateSocketSP();
                                    } catch (IOException e1){
                                        System.out.println("OPS, AN ERROR OCCURRED. PLEASE RESTART THE GAME");
                                    }
                                    catch (ClassNotFoundException e) {
                                        //
                                    }
                                }).start();
                            }
                        } else {
                            if (viewGUI.getSocketConnection()) {
                                new Thread(() -> {
                                    try {
                                        viewGUI.updateSocket();
                                    } catch (IOException e) {
                                        //
                                    } catch (ClassNotFoundException e) {
                                        //
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
                                    if(viewGUI.getSinglePlayer())
                                        viewGUI.updateSocketSP();
                                    else
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