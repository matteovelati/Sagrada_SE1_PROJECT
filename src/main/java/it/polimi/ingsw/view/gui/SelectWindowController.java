package it.polimi.ingsw.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private ViewGUI viewGUI;

    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    void loadWindowPatterns() throws RemoteException {
        loadImage(0, true, card1front);
        loadImage(0, false, card1back);
        loadImage(1, true, card2front);
        loadImage(1, false, card2back);
    }

    void waitTurn(){
        allWindows.managedProperty().bind(allWindows.visibleProperty());
        allWindows.setVisible(false);
        text.setText("WAIT YOUR TURN");
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

    public void windowClick(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        int selection = GridPane.getRowIndex(selected)*2 + GridPane.getColumnIndex(selected) + 1;
        waitTurn();
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
        if(viewGUI.actualPlayer())
            matchController.selectMove1View();

        Scene startScene;
        startScene = new Scene(match, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        mainStage.setScene(startScene);
        mainStage.setMaximized(true);
        mainStage.setFullScreen(true);
        mainStage.show();
        match.requestFocus();
    }
}
