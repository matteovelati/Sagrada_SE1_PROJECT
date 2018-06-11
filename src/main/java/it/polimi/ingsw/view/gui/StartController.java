package it.polimi.ingsw.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    private Button startButton;
    @FXML
    private Label usernameTaken;
    @FXML
    private TextField username;

    private ViewGUI viewGUI;

    public void startButtonClicked(ActionEvent event) throws IOException {

        usernameTaken.setText("");

        if(!empty(username.getText())) {
            if (viewGUI.checkLobby()) {
                if (viewGUI.verifyUser(username.getText().toUpperCase())) {
                    viewGUI.setUser(username.getText().toUpperCase());
                    startButton.setDisable(true);
                } else
                    usernameTaken.setText("THIS USERNAME ALREADY EXIST");
            } else
                usernameTaken.setText("GAME STARTED!");
        }
        else
            usernameTaken.setText("PLEASE INSER A VALID USERNAME");

        /*System.out.println(username.getText());

        Parent selectWindow = FXMLLoader.load(getClass().getResource("fxml/selectwindow.fxml"));
        Scene startScene;
        startScene = new Scene(selectWindow, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(startScene);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
       */

    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    public boolean empty(String s){
        return s == null || s.trim().isEmpty();
    }
}