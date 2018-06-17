package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private Label message, tokens, errorMessage;
    @FXML
    private HBox buttons, draftArea, windowArea;
    @FXML
    private VBox middle;
    @FXML
    private Button pickDice, useToolcard, endTurn, showDices;
    @FXML
    private ImageView privateObjective;
    @FXML
    private Region region1, region2;
    @FXML
    TextField input;

    private ViewGUI viewGUI;
    private int firstMove = 0;

    public void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    public void init() throws RemoteException {
        createDraft();
        setDraft();
        setToolcards();
        setPublicObjectives();
        setWindow();
        setPrivateObjective();
        setTokens();
        errorMessage.managedProperty().bind(errorMessage.visibleProperty());
        errorMessage.setVisible(false);
        input.managedProperty().bind(input.visibleProperty());
        input.setVisible(false);
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

    public void selectMove1View() throws RemoteException {
        setTokens();
        buttons.setDisable(false);
        pickDice.setDisable(false);
        useToolcard.setDisable(false);
        endTurn.setDisable(false);
        endTurn.setText("END TURN");
        message.setText("CHOOSE YOUR MOVE");
    }

    public void selectMove2View() throws RemoteException {
        setDraft();
        setWindowGrid();
        setTokens();
        if(firstMove == 1) {
            pickDice.setDisable(true);
            useToolcard.setDisable(false);
        }
        else if(firstMove == 2) {
            useToolcard.setDisable(true);
            pickDice.setDisable(false);
        }
        endTurn.setDisable(false);
        endTurn.setText("END TURN");
        message.setText("CHOOSE YOUR MOVE");
    }

    public void selectDraftView(){
        draft.setDisable(false);
        pickDice.setDisable(true);
        useToolcard.setDisable(true);
        endTurn.setText("ABORT");
        message.setText("SELECT A DICE");
    }

    public void putDiceInWindowView(){
        endTurn.setDisable(false);
        endTurn.setText("ABORT");
        clientWindow.setDisable(false);
        message.setText("PUT THE DICE IN YOUR WINDOW");
    }

    public void endRoundView() throws RemoteException {
        addDiceToRoundtrack(0, viewGUI.getRound()-1);
        if(viewGUI.getDraft().size() > 1){
            for(int i=1; i<viewGUI.getDraft().size(); i++){
                if(i == roundtrack.getRowConstraints().size()) {
                    roundtrack.getRowConstraints().add(new RowConstraints(5));
                }
                addDiceToRoundtrack(i, viewGUI.getRound()-1);
            }
        }
        if(viewGUI.actualPlayer())
            viewGUI.notifyNetwork();
    }

    public void selectToolcardView(){
        toolcards.setDisable(false);
        pickDice.setDisable(true);
        useToolcard.setDisable(true);
        endTurn.setText("ABORT");
        message.setText("SELECT A TOOLCARD");
    }

    public void useToolcardView() throws RemoteException {
        viewGUI.getChoices().clear();

        switch (viewGUI.getSelectedToolcardId()){
            case 1: case 5: case 6: case 8: case 9: case 10: case 11:  //GROZING PLIERS, LENS CUTTER, FLUX BRUSH, FLUX REMOVER, GRINDING STONE, RUNNING PLIERS, CORKBACKEDSTRAIGHTEDGE
                draft.setDisable(false);
                message.setText("SELECT A DICE FROM THE DRAFT");
                break;
            case 2: case 3: case 4:         //EGLOMISE BRUSH & COPPER FOIL BURNISHER & LATHEKIN
                clientWindow.setDisable(false);
                message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                break;
            case 12:                    //TAP WHEEL
                showRoundtrackDices();
                message.setText("SELECT A DICE FROM THE ROUNDTRACK");
                break;
            default:                    //GLAZING HAMMER
                viewGUI.notifyNetwork();
                break;
        }
    }

    public void useToolcard2View() throws RemoteException {
        setWindowGrid();
        switch(viewGUI.getSelectedToolcardId()){
            case 1: case 5: case 10:        //GROZING PLIERS & LENS CUTTER & GRINDING STONE
                setDraft();
                clientWindow.setDisable(false);
                message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                break;
            case 4:                     //LATHEKIN
                clientWindow.setDisable(false);
                message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                break;
            case 6:                     //FLUX BRUSH
                setDraft();
                errorMessage.setVisible(true);
                errorMessage.setText("YOU HAVE ROLLED THE DICE IN POSITION: " + (viewGUI.getChoices().get(0)+1));
                message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                clientWindow.setDisable(false);
                break;
            case 11:            //FLUX REMOVER
                setDraft();
                message.setText("COLOR IS: " + viewGUI.getDraftDiceColor(viewGUI.getChoices().get(0)));
                errorMessage.setVisible(true);
                errorMessage.setText("CHOOSE A NEW VALUE FOR THE DICE");
                input.setVisible(true);
                break;
            case 12:            //TAP WHEEL
                message.setText("DO YOU WANT TO MOVE ANOTHER DICE?");
                pickDice.setDisable(false);
                pickDice.setText("YES");
                useToolcard.setDisable(false);
                useToolcard.setText("NO");
                break;
            default:
                assert false;
        }
    }

    public void useToolcard3View() throws RemoteException {
        setTokens();
        if(viewGUI.getSelectedToolcardId() == 11){
            setDraft();
            clientWindow.setDisable(false);
            errorMessage.setVisible(true);
            errorMessage.setText("(THE SELECTED DICE IS IN POSITION " + (viewGUI.getChoices().get(0)+1) + ")");
            message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
        }
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

    private void setToolcards() throws RemoteException {
        for(Node child : toolcards.getChildren()){
            String s = "images/toolcards/" + viewGUI.getToolcardId(GridPane.getRowIndex(child))+ ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            ((ImageView) child).setImage(image);
        }
    }

    private void setPublicObjectives() throws RemoteException {
        for(Node child : publicObjectives.getChildren()){
            String s = "images/public_obj/" + viewGUI.getPublicObjId(GridPane.getRowIndex(child))+ ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            ((ImageView) child).setImage(image);
        }
    }

    private void setWindow() throws RemoteException {
        String s = "images/windows/" + viewGUI.getSelectedWindow() + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 300, 265, false, true);
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        clientWindow.setBackground(new Background(bg));
    }

    private void setPrivateObjective() throws RemoteException {
        String s = "images/private_obj/" + viewGUI.getPrivateObj() + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 173, 241, false, true);
        privateObjective.setImage(image);
    }

    private void setTokens() throws RemoteException {
        int t = viewGUI.getTokens();
        tokens.setText("TOKENS: " + t);
    }

    private void setWindowGrid() throws RemoteException {
        for(Node child : grid.getChildren()){
            if(viewGUI.checkWindowEmptyCell(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)))
                ((ImageView) child).setImage(null);
            else {
                String s = "images/dices/" + viewGUI.getWindowDiceColor(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + viewGUI.getWindowDiceValue(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + ".png";
                Image image = new Image(getClass().getResourceAsStream(s));
                ((ImageView) child).setImage(image);
            }
        }
    }

    public void pickDiceButton(ActionEvent e) throws IOException {
        if(pickDice.getText().equals("PICK A DICE")) {
            viewGUI.setChoose1(1);
            if (viewGUI.getGameState().equals(States.SELECTMOVE1))
                firstMove = 1;
            else
                firstMove = 0;
            viewGUI.notifyNetwork();
        }
        else if(pickDice.getText().equals("INCREASE")){
            viewGUI.getChoices().add(1);
            pickDice.setText("PICK A DICE");
            pickDice.setDisable(true);
            useToolcard.setText("USE A TOOLCARD");
            useToolcard.setDisable(true);
            viewGUI.notifyNetwork();
        }
        else if(pickDice.getText().equals("YES")){
            viewGUI.getChoices().add(1);
            clientWindow.setDisable(false);
            message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
            pickDice.setText("PICK A DICE");
            pickDice.setDisable(true);
            useToolcard.setText("USE A TOOLCARD");
            useToolcard.setDisable(true);
        }
    }

    public void useToolcardButton(ActionEvent e) throws IOException {
        if(useToolcard.getText().equals("USE A TOOLCARD")) {
            if (viewGUI.getGameState().equals(States.SELECTMOVE1)) {
                viewGUI.setChoose1(2);
                firstMove = 2;
            }
            else {
                viewGUI.setChoose1(1);
                firstMove = 0;
            }
            viewGUI.notifyNetwork();
        }
        else if(useToolcard.getText().equals("DECREASE")){
            viewGUI.getChoices().add(-2);
            useToolcard.setText("USE A TOOLCARD");
            useToolcard.setDisable(true);
            pickDice.setText("PICK A DICE");
            pickDice.setDisable(true);
            viewGUI.notifyNetwork();
        }
        else if(useToolcard.getText().equals("NO")){
            viewGUI.getChoices().add(2);
            useToolcard.setText("USE A TOOLCARD");
            pickDice.setText("PICK A DICE");
            viewGUI.notifyNetwork();
        }
    }

    public void endTurnButton(ActionEvent e) throws RemoteException {
        if(endTurn.getText().equals("END TURN")) {
            firstMove = 0;
            viewGUI.setChoose1(0);
            buttons.setDisable(true);
            viewGUI.notifyNetwork();
        }
        else if(endTurn.getText().equals("ABORT")){
            errorMessage.setVisible(false);
            input.setVisible(false);
            if(viewGUI.getGameState().equals(States.USETOOLCARD)||viewGUI.getGameState().equals(States.USETOOLCARD2)||viewGUI.getGameState().equals(States.USETOOLCARD3)){
                viewGUI.getChoices().add(0, -1);
                buttons.setDisable(false);
            }
            else {
                viewGUI.setChoose1(-1);
                buttons.setDisable(false);
            }
            viewGUI.notifyNetwork();
        }
    }

    public void roundtrackButtonClick(ActionEvent e) throws RemoteException {
        if(showDices.getText().equals("SHOW DICES")) {
            showRoundtrackDices();
        }
        else if(showDices.getText().equals("HIDE DICES")){
            hideRountrackDices();
        }
    }

    public void draftClick(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();

        switch (viewGUI.getGameState()) {
            case SELECTDRAFT:
                viewGUI.setChoose1(GridPane.getColumnIndex(selected) + 1);
                draft.setDisable(true);
                viewGUI.notifyNetwork();
                break;

            case USETOOLCARD:
                viewGUI.getChoices().add(GridPane.getColumnIndex(selected));
                draft.setDisable(true);
                if(viewGUI.getSelectedToolcardId() == 1) {
                    message.setText("DO YOU WANT TO INCREASE OR DECREASE THE VALUE?");
                    pickDice.setText("INCREASE");
                    useToolcard.setText("DECREASE");
                    pickDice.setDisable(false);
                    useToolcard.setDisable(false);
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 5){
                    message.setText("SELECT A DICE FROM THE ROUNDTRACK");
                    showRoundtrackDices();
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 6 || viewGUI.getSelectedToolcardId() == 11) {
                    viewGUI.notifyNetwork();
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 8 || viewGUI.getSelectedToolcardId() == 9){
                    message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 10){
                    viewGUI.getChoices().add(0);
                    viewGUI.notifyNetwork();
                    break;
                }
        }
    }

    public void windowClick(MouseEvent e) throws RemoteException {
        errorMessage.setVisible(false);
        ImageView selected = (ImageView) e.getSource();

        switch (viewGUI.getGameState()) {
            case PUTDICEINWINDOW:
                viewGUI.setChoose1(GridPane.getRowIndex(selected) + 1);
                viewGUI.setChoose2(GridPane.getColumnIndex(selected) + 1);
                clientWindow.setDisable(true);
                viewGUI.notifyNetwork();
                break;

            case USETOOLCARD: case USETOOLCARD2: case USETOOLCARD3:
                viewGUI.getChoices().add(GridPane.getRowIndex(selected));
                viewGUI.getChoices().add(GridPane.getColumnIndex(selected));
                if (message.getText().equals("SELECT FROM YOUR WINDOW THE DICE TO MOVE"))
                    message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                else if (message.getText().equals("CHOOSE WHERE YOU WANT TO PUT THE DICE")) {
                    clientWindow.setDisable(true);
                    viewGUI.notifyNetwork();
                }
                break;
        }
    }

    public void toolcardClick(MouseEvent e) throws RemoteException{
        errorMessage.setVisible(false);
        ImageView selected = (ImageView) e.getSource();
        viewGUI.setChoose1(GridPane.getRowIndex(selected)+1);
        toolcards.setDisable(true);
        viewGUI.notifyNetwork();
    }

    public void roundtrackClick(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        int index = findElementIndex(GridPane.getRowIndex(selected), GridPane.getColumnIndex(selected));
        viewGUI.getChoices().add(index);
        hideRountrackDices();
        if(viewGUI.getSelectedToolcardId() == 12) {
            message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
            clientWindow.setDisable(false);
        }
        else if(viewGUI.getSelectedToolcardId() == 5) {
            String s = "images/dices/" + viewGUI.getDraftDiceColor(viewGUI.getChoices().get(0)) + viewGUI.getDraftDiceValue(viewGUI.getChoices().get(0)) + ".png";
            Image image = new Image(getClass().getResourceAsStream(s));
            selected.setImage(image);
            viewGUI.notifyNetwork();
        }
    }

    public void inputEnter(ActionEvent e) throws RemoteException {
        if(isInt(input)){
            int value = Integer.parseInt(input.getText());
            if(value>=1 && value<=6){
                viewGUI.getChoices().add(value);
                errorMessage.setVisible(false);
                input.setVisible(false);
                input.setText("");
                viewGUI.notifyNetwork();
            }else
                errorMessage.setText("INSERT A NUMBER FROM 1 TO 6");
        }else{
            errorMessage.setText("INSERT A NUMBER");
        }
    }

    public void error(String error){
        errorMessage.setVisible(true);
        errorMessage.setText(error);
    }

    public void refresh() throws RemoteException {
        setDraft();
        setWindowGrid();
    }

    private void addDiceToRoundtrack(int row, int column) throws RemoteException {
        String s = "images/dices/" + viewGUI.getDraftDiceColor(row) + viewGUI.getDraftDiceValue(row) + ".png";
        Image image = new Image(getClass().getResourceAsStream(s), 57, 57, false, true);
        ImageView dice = new ImageView(image);
        dice.setFitHeight(57);
        dice.setFitWidth(57);
        dice.setOnMouseClicked(e -> {
            try {
                roundtrackClick(e);
            } catch (RemoteException e1) {
                //do nothing
            }
        });
        roundtrack.add(dice, column, row);
    }

    private void showRoundtrackDices() throws RemoteException {
        showDices.setText("HIDE DICES");
        for (RowConstraints row : roundtrack.getRowConstraints())
            row.setPrefHeight(57);
        AnchorPane.setTopAnchor(roundtrack, 115.0);
        roundtrack.setVgap(3.0);
        middle.getChildren().removeAll(draftArea, windowArea, tokens, region1, region2, buttons);
        if(viewGUI.getGameState().equals(States.USETOOLCARD) || viewGUI.getGameState().equals(States.USETOOLCARD2) || viewGUI.getGameState().equals(States.USETOOLCARD3))
            roundtrack.setDisable(false);
    }

    private void hideRountrackDices(){
        showDices.setText("SHOW DICES");
        for (RowConstraints row : roundtrack.getRowConstraints())
            row.setPrefHeight(5);
        AnchorPane.setTopAnchor(roundtrack, 141.0);
        roundtrack.setVgap(0.0);
        middle.getChildren().addAll(region1, draftArea, region2, windowArea, tokens, buttons);
        roundtrack.setDisable(true);
    }

    private int findElementIndex(int row, int column){
        int counter = 0;
        for(Node child : roundtrack.getChildren()){
            if (GridPane.getColumnIndex(child) == column && GridPane.getRowIndex(child) == row)
                break;
            counter++;
        }
        return counter;
    }

    private boolean isInt(TextField input){
        try{
            int i = Integer.parseInt(input.getText());
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

}
