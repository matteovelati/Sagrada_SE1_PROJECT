package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    VBox container;
    @FXML
    private GridPane allWindows;

    private ViewGUI viewGUI;

    public void init() throws RemoteException {
        createWindow1();
        createWindow2();
        createWindow3();
        createWindow4();
    }

    public void waitTurn(){
        container.getChildren().remove(allWindows);
        text.setText("WAIT YOUR TURN");
    }

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    public void selectWindow(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        int selection = GridPane.getRowIndex(selected)*2 + GridPane.getColumnIndex(selected) + 1;
        waitTurn();
        viewGUI.setSelectedWindow(selection);
        viewGUI.setChoose1(selection);
        viewGUI.notifyNetwork();
    }

    public void setActualPlayer(){
        text.setText("CHOOSE A WINDOW PATTERN");
        container.getChildren().add(allWindows);
    }

    private void createWindow1() throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(0, true) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s));
        card1front.setImage(image);
    }

    private void createWindow2() throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(0, false) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s));
        card1back.setImage(image);
    }

    private void createWindow3() throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(1, true) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s));
        card2front.setImage(image);
    }

    private void createWindow4() throws RemoteException {
        String s = "images/windows/" + viewGUI.getWindowId(1, false) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s));
        card2back.setImage(image);
    }

    public void changeScene(Stage mainStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/match.fxml"));
        Parent selectWindow = loader.load();

        MatchController matchController = loader.getController();
        matchController.setViewGUI(viewGUI);
        viewGUI.setMatchController(matchController);
        matchController.init();
        matchController.waitTurn();
        if(viewGUI.actualPlayer())
            matchController.selectMove1View();

        Scene startScene;
        startScene = new Scene(selectWindow, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        Stage primaryStage = mainStage;
        primaryStage.setScene(startScene);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
