package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SPMatchController extends MatchController {

    private final static String PICKADICE = "PICK A DICE";
    private final static String USEATOOLCARD = "USE A TOOLCARD";

    @FXML
    private ImageView privateObjective1, privateObjective2;
    @FXML
    private HBox right;

    void init() throws RemoteException{
        loadToolcards();
        loadPublicObjectives();
        refreshDraft();
        loadWindowImage(viewGUI.getUser(), 300, 265, clientWindow);
        loadPrivateObjectives();
        refreshTargetScore();
        if(!viewGUI.getGameModel().getField().getRoundTrack().getGrid().isEmpty())
            recreateRoundtrack();
        enableRoundtrack = false;

        errorMessage.managedProperty().bind(errorMessage.visibleProperty());
        input.managedProperty().bind(input.visibleProperty());
        draftArea.managedProperty().bind(draftArea.visibleProperty());
        windowArea.managedProperty().bind(windowArea.visibleProperty());
        region1.managedProperty().bind(region1.visibleProperty());
        region2.managedProperty().bind(region2.visibleProperty());
        buttons.managedProperty().bind(buttons.visibleProperty());
        roundtrackArea.managedProperty().bind(roundtrackArea.visibleProperty());
        tokens.managedProperty().bind(tokens.visibleProperty());
        restartButton.managedProperty().bind(restartButton.visibleProperty());
        left.managedProperty().bind(left.visibleProperty());
        right.managedProperty().bind(right.visibleProperty());

        errorMessage.setVisible(false);
        input.setVisible(false);
        restartButton.setVisible(false);
    }

    void serverDown() {
        middle.getChildren().removeAll(buttons, tokens, windowArea, region2, draftArea, region1, roundtrackArea, input, errorMessage);
        message.setText("SEEMS LIKE THE SERVER HAS BEEN SHUT DOWN");
    }

    private void loadToolcards() throws RemoteException {
        int toolcardsNumber = viewGUI.getGameModel().getField().getToolCards().size();
        int row = 1, column = 0;

        addToolcard(0, 0);
        if(toolcardsNumber > 1){
            for(int i=1; i<toolcardsNumber; i++){
                if(i==3){
                    toolcards.getColumnConstraints().add(new ColumnConstraints(162));
                    row = 0;
                    column++;
                }
                if(row == toolcards.getRowConstraints().size()) {
                    toolcards.getRowConstraints().add(new RowConstraints(226));
                }
                addToolcard(row, column);
                row++;
            }
        }
    }

    private void loadPublicObjectives() throws RemoteException {
        for(int i=0; i<2; i++){
            ImageView card = new ImageView();
            path = "images/public_obj/" + viewGUI.getGameModel().getField().getPublicObjectives().get(i).getIdNumber() + ".png";
            loadImage(path, 162, 226, card, 0);
            publicObjectives.add(card, 0, i);
        }
    }

    private void addToolcard(int row, int column) throws RemoteException {
        ImageView card = new ImageView();
        path = "images/toolcards/" + viewGUI.getGameModel().getField().getToolCards().get(row + (column + column*2)).getNumber() + ".png";
        loadImage(path, 162, 226, card, 0);
        card.setOnMouseClicked(e -> {
            try {
                toolcardClick(e);
            } catch (IOException e1) {
                //do nothing
            }
        });
        toolcards.add(card, column, row);
    }

    private void loadPrivateObjectives() throws RemoteException {
        path = "images/private_obj/" + viewGUI.getGameModel().getPlayers().get(0).getPrivateObjectives().get(0).getColor() + ".png";
        loadImage(path, 173, 241, privateObjective1, 0);
        path = "images/private_obj/" + viewGUI.getGameModel().getPlayers().get(0).getPrivateObjectives().get(1).getColor() + ".png";
        loadImage(path, 173, 241, privateObjective2, 0);
    }


    private void refreshTargetScore() throws RemoteException {
        int score = viewGUI.getGameModel().getField().getRoundTrack().calculateRoundTrack();
        tokens.setText("TARGET SCORE: " + score);
    }

    void selectMove1View() throws RemoteException {
        firstMove = 0;
        refreshTargetScore();
        refresh();
        enableAllButtons();
        setStandardTexts();
        toolcards.setDisable(true);
        draft.setDisable(true);
        clientWindow.setDisable(true);
        roundtrack.setDisable(true);
    }

    void selectMove2View() throws RemoteException {
        refreshDraft();
        setWindowGrid();
        if(firstMove == 1) {
            pickDice.setDisable(true);
            useToolcard.setDisable(false);
        }
        else if(firstMove == 2) {
            useToolcard.setDisable(true);
            pickDice.setDisable(false);
        }
        setStandardTexts();
    }

    void selectDraftView(boolean toolcardState){
        abortOnly();
        draft.setDisable(false);
        if(toolcardState)
            message.setText("SELECT A DIE THAT MATCHES THE COLOR OF THE TOOLCARD\nIT WILL BE REMOVED FROM GAME (-1 TO ABORT)");
        else
            message.setText("SELECT A DICE");
    }

    void putDiceInWindowView(){
        abortOnly();
        clientWindow.setDisable(false);
        message.setText("PUT THE DICE IN YOUR WINDOW");
    }

    void selectToolcardView(){
        System.out.println("singolo");
        toolcards.setDisable(false);
        abortOnly();
        message.setText("SELECT A TOOLCARD");
    }

    void useToolcard3View() throws RemoteException {
        refreshDraft();
        if(viewGUI.getSelectedToolcardId() == 11){
            try {
                viewGUI.getChoices().remove(2);
                viewGUI.getChoices().remove(2);
            } catch (IndexOutOfBoundsException e){
                //DO NOTHING
            }
            message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
            showErrorMessage("(THE SELECTED DICE IS IN POSITION " + (viewGUI.getChoices().get(0)+1) + ")");
            clientWindow.setDisable(false);
        }
    }

    void endMatchView() throws IOException {
        middle.getChildren().removeAll(buttons, tokens, windowArea, region2, draftArea, region1, roundtrackArea, input, errorMessage);
        int myscore = viewGUI.getGameModel().getPlayers().get(0).getFinalScore();
        int rtscore = viewGUI.getGameModel().getField().getRoundTrack().calculateRoundTrack();
        message.setText("YOUR FINAL SCORE IS: " + myscore + "\nTHE TARGET SCORE IS: " + rtscore);
        if (myscore > rtscore)
            message.setText(message.getText() + "\nYOU WON!!!");
        else if (myscore == rtscore)
            message.setText(message.getText() + "\nIT'S A DRAW!");
        else
            message.setText(message.getText() + "\nYOU LOST...    :'(");
        viewGUI.notifyNetwork();
    }

    public void pickDiceButton(ActionEvent e) throws IOException {
        switch (pickDice.getText()) {
            case PICKADICE:
                viewGUI.setChoose1(1);
                if (viewGUI.getGameState().equals(States.SELECTMOVE1))
                    firstMove = 1;
                viewGUI.notifyNetwork();
                break;
            case "INCREASE":
                viewGUI.getChoices().add(1);
                pickDice.setText(PICKADICE);
                pickDice.setDisable(true);
                useToolcard.setText(USEATOOLCARD);
                useToolcard.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "YES":
                errorMessage.setVisible(false);
                endTurn.setDisable(false);
                viewGUI.getChoices().add(1);
                clientWindow.setDisable(false);
                message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                pickDice.setText(PICKADICE);
                pickDice.setDisable(true);
                useToolcard.setText(USEATOOLCARD);
                useToolcard.setDisable(true);
                break;
            default:
                assert false;
        }
    }

    public void useToolcardButton(ActionEvent e) throws IOException {
        switch (useToolcard.getText()) {
            case USEATOOLCARD:
                if (viewGUI.getGameState().equals(States.SELECTMOVE1)) {
                    viewGUI.setChoose1(2);
                    firstMove = 2;
                } else {
                    viewGUI.setChoose1(1);
                }
                viewGUI.notifyNetwork();
                break;
            case "DECREASE":
                viewGUI.getChoices().add(-2);
                useToolcard.setText(USEATOOLCARD);
                useToolcard.setDisable(true);
                pickDice.setText(PICKADICE);
                pickDice.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "NO":
                errorMessage.setVisible(false);
                endTurn.setDisable(false);
                viewGUI.getChoices().add(2);
                useToolcard.setText(USEATOOLCARD);
                pickDice.setText(PICKADICE);
                viewGUI.notifyNetwork();
                break;
            default:
                assert false;
        }
    }

    public void toolcardClick(MouseEvent e) throws IOException {
        errorMessage.setVisible(false);
        ImageView selected = (ImageView) e.getSource();
        viewGUI.setChoose1((GridPane.getRowIndex(selected) + GridPane.getColumnIndex(selected)*3)+1);
        toolcards.setDisable(true);
        viewGUI.notifyNetwork();
    }

    private void abortOnly(){
        pickDice.setDisable(true);
        useToolcard.setDisable(true);
        endTurn.setText("ABORT");
    }
}
