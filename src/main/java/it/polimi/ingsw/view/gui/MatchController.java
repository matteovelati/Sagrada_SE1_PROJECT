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

public abstract class MatchController {

    @FXML
    protected GridPane draft, toolcards, publicObjectives, roundtrack, grid;
    @FXML
    protected AnchorPane clientWindow;
    @FXML
    protected Label message, errorMessage, tokens;
    @FXML
    protected HBox buttons, draftArea, windowArea, roundtrackArea, left;
    @FXML
    protected VBox middle;
    @FXML
    protected Button pickDice, useToolcard, endTurn, showDices, restartButton;
    @FXML
    protected Region region1, region2, region3;
    @FXML
    protected TextField input;

    static final String GREEN = "#00FF00";
    static final String RED = "#FF0000";
    static final String BLUE = "#0000FF";
    static final String DICEPATH = "images/dices/";
    static final String PRIVATEOBJECTIVEPATH = "images/private_obj/";
    static final String PUBLICOBJECTIVEPATH = "images/public_obj/";
    static final String TOOLCARDPATH = "images/toolcards/";
    static final String WINDOWPATH = "images/windows/";
    static final String PNG = ".png";

    ViewGUI viewGUI;
    String path;
    int firstMove;
    boolean enableRoundtrack;

    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    /**
     * shows the draft
     * @throws RemoteException if the reference could not be accessed
     */
    void refreshDraft() throws RemoteException {
        int diceIndex;
        for(Node diceImage : draft.getChildren()){
            diceIndex = GridPane.getColumnIndex(diceImage);
            if(diceIndex < viewGUI.getGameModel().getField().getDraft().getDraft().size()) {
                path = DICEPATH + viewGUI.getDraftDice(diceIndex) + PNG;
                loadImage(path, 60, 60, diceImage, 0);
            }else{
                ((ImageView) diceImage).setImage(null);
            }
        }
    }

    void loadWindowImage(String player, int width, int height, AnchorPane window) throws RemoteException {
        String path = WINDOWPATH + viewGUI.getPlayerWindow(player) + PNG;
        loadImage(path, width, height, window, 1);
    }

    void loadImage(String path, int width, int height, Node element, int type){//type 0: imageview, type 1: anchorpane
        Image image = new Image(getClass().getResourceAsStream(path), width, height, false, true);

        if(type == 0)
            ((ImageView)element).setImage(image);
        else if(type == 1) {
            BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            ((AnchorPane) element).setBackground(new Background(bg));
        }
    }

    void useToolcardView() throws IOException {
        System.out.println("usa toolcard");
        viewGUI.getChoices().clear();
        refreshDraft();
        System.out.println("prima switch");

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
                enableRoundtrack = true;
                showRoundtrackDices();
                message.setText("SELECT A DICE FROM THE ROUNDTRACK");
                break;
            default:                    //GLAZING HAMMER
                viewGUI.notifyNetwork();
                break;
        }
    }

    void useToolcard2View() throws RemoteException {
        refreshDraft();
        setWindowGrid();
        switch(viewGUI.getSelectedToolcardId()){
            case 1: case 5: case 10:        //GROZING PLIERS & LENS CUTTER & GRINDING STONE
                try {
                    viewGUI.getChoices().remove(2);
                    viewGUI.getChoices().remove(2);
                } catch (IndexOutOfBoundsException e){
                    //DO NOTHING
                }
                message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                clientWindow.setDisable(false);
                break;
            case 4:                     //LATHEKIN
                try {
                    viewGUI.getChoices().remove(4);
                    viewGUI.getChoices().remove(4);
                    viewGUI.getChoices().remove(4);
                    viewGUI.getChoices().remove(4);
                } catch (IndexOutOfBoundsException e){
                    //DO NOTHING
                }
                message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                clientWindow.setDisable(false);
                break;
            case 6:                     //FLUX BRUSH
                try {
                    viewGUI.getChoices().remove(1);
                    viewGUI.getChoices().remove(1);
                } catch (IndexOutOfBoundsException e){
                    //DO NOTHING
                }
                message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                showErrorMessage("YOU HAVE ROLLED THE DICE IN POSITION: " + (viewGUI.getChoices().get(0)+1));
                clientWindow.setDisable(false);
                break;
            case 11:            //FLUX REMOVER
                message.setText("COLOR IS: " + viewGUI.getDraftDiceColor(viewGUI.getChoices().get(0)));
                showErrorMessage("CHOOSE A NEW VALUE FOR THE DICE");
                input.setVisible(true);
                break;
            case 12:            //TAP WHEEL
                try {
                    viewGUI.getChoices().remove(5);
                    viewGUI.getChoices().remove(5);
                    viewGUI.getChoices().remove(5);
                    viewGUI.getChoices().remove(5);
                    viewGUI.getChoices().remove(5);
                } catch (IndexOutOfBoundsException e){
                    //DO NOTHING
                }
                endTurn.setDisable(true);
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

    void endRoundView() throws RemoteException {
        addDiceToRoundtrack(0, viewGUI.getRound()-1);
        if(viewGUI.getDraft().size() > 1){
            for(int i=1; i<viewGUI.getDraft().size(); i++){
                if(i == roundtrack.getRowConstraints().size()) {
                    roundtrack.getRowConstraints().add(new RowConstraints(5));
                }
                addDiceToRoundtrack(i, viewGUI.getRound()-1);
            }
        }
    }

    void restartView(){
        restartButton.setVisible(true);
    }

    public void endTurnButton(ActionEvent e) throws IOException {
        if(endTurn.getText().equals("END TURN")) {
            firstMove = 0;
            viewGUI.setChoose1(0);
            buttons.setDisable(true);
            viewGUI.notifyNetwork();
        }
        else if(endTurn.getText().equals("ABORT")){
            errorMessage.setVisible(false);
            input.setVisible(false);
            pickDice.setText("PICK A DICE");
            useToolcard.setText("USE A TOOLCARD");
            enableRoundtrack = false;
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

    public void roundtrackButtonClick(ActionEvent e){
        errorMessage.setVisible(false);
        if(showDices.getText().equals("SHOW DICES")) {
            showRoundtrackDices();
        }
        else if(showDices.getText().equals("HIDE DICES")){
            hideRoundtrackDices();
        }
    }

    public void draftClick(MouseEvent e) throws IOException {
        ImageView selected = (ImageView) e.getSource();
        errorMessage.setVisible(false);

        switch (viewGUI.getGameState()) {
            case SELECTDRAFT: case SELECTDIE:
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
                    enableRoundtrack = true;
                    showRoundtrackDices();
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 6 || viewGUI.getSelectedToolcardId() == 11) {
                    viewGUI.notifyNetwork();
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 8 || viewGUI.getSelectedToolcardId() == 9){
                    clientWindow.setDisable(false);
                    message.setText("CHOOSE WHERE YOU WANT TO PUT THE DICE");
                    break;
                }
                else if(viewGUI.getSelectedToolcardId() == 10){
                    viewGUI.getChoices().add(0);
                    viewGUI.notifyNetwork();
                    break;
                }
                break;

            default:
                assert false;
        }
    }

    public void windowClick(MouseEvent e) throws IOException {
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

            default:
                assert false;
        }
    }

    public void roundtrackClick(MouseEvent e) throws IOException {
        ImageView selected = (ImageView) e.getSource();
        int index = findElementIndex(GridPane.getRowIndex(selected), GridPane.getColumnIndex(selected));
        viewGUI.getChoices().add(index);
        enableRoundtrack = false;
        hideRoundtrackDices();
        if(viewGUI.getSelectedToolcardId() == 12) {
            message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
            clientWindow.setDisable(false);
        }
        else if(viewGUI.getSelectedToolcardId() == 5) {
            String s = DICEPATH + viewGUI.getDraftDiceColor(viewGUI.getChoices().get(0)) + viewGUI.getDraftDiceValue(viewGUI.getChoices().get(0)) + PNG;
            Image image = new Image(getClass().getResourceAsStream(s));
            selected.setImage(image);
            viewGUI.notifyNetwork();
        }
    }

    public void inputEnter(ActionEvent e) throws IOException {
        int value = isInt(input);
        if(value != 0){
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

    public int isInt(TextField input){
        try{
            int i = Integer.parseInt(input.getText());
            return i;
        }catch(NumberFormatException e){
            return 0;
        }
    }

    public void refresh() throws RemoteException {
        refreshDraft();
        setWindowGrid();
    }

    public void setWindowGrid() throws RemoteException {
        for(Node child : grid.getChildren()){
            if(viewGUI.checkWindowEmptyCell(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)))
                ((ImageView) child).setImage(null);
            else {
                String s = DICEPATH + viewGUI.getWindowDiceColor(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + viewGUI.getWindowDiceValue(GridPane.getRowIndex(child), GridPane.getColumnIndex(child)) + PNG;
                Image image = new Image(getClass().getResourceAsStream(s));
                ((ImageView) child).setImage(image);
            }
        }
    }

    public void enableAllButtons(){
        buttons.setDisable(false);
        pickDice.setDisable(false);
        useToolcard.setDisable(false);
        endTurn.setDisable(false);
    }

    public void setStandardTexts(){
        endTurn.setText("END TURN");
        message.setText("CHOOSE YOUR MOVE");
    }

    public void showRoundtrackDices(){
        showDices.setText("HIDE DICES");
        for (RowConstraints row : roundtrack.getRowConstraints())
            row.setPrefHeight(57);
        AnchorPane.setTopAnchor(roundtrack, 115.0);
        roundtrack.setVgap(3.0);
        draftArea.setVisible(false);
        windowArea.setVisible(false);
        tokens.setVisible(false);
        region1.setVisible(false);
        region2.setVisible(false);
        buttons.setVisible(false);
        if(enableRoundtrack)
            roundtrack.setDisable(false);
        middle.setAlignment(Pos.TOP_CENTER);
    }

    public void hideRoundtrackDices(){
        showDices.setText("SHOW DICES");
        for (RowConstraints row : roundtrack.getRowConstraints())
            row.setPrefHeight(5);
        AnchorPane.setTopAnchor(roundtrack, 141.0);
        roundtrack.setVgap(0.0);
        draftArea.setVisible(true);
        windowArea.setVisible(true);
        tokens.setVisible(true);
        region1.setVisible(true);
        region2.setVisible(true);
        buttons.setVisible(true);
        roundtrack.setDisable(true);
        middle.setAlignment(Pos.CENTER);
    }

    public void showErrorMessage(String s){
        errorMessage.setVisible(true);
        errorMessage.setText(s);
    }

    public void addDiceToRoundtrack(int row, int column) throws RemoteException {
        ImageView dice = new ImageView();
        dice.setFitHeight(57);
        dice.setFitWidth(57);
        path = DICEPATH + viewGUI.getDraftDice(row) + PNG;
        loadImage(path, 57, 57, dice, 0);
        dice.setOnMouseClicked(e -> {
            try {
                roundtrackClick(e);
            } catch (IOException e1) {
                //do nothing
            }
        });
        roundtrack.add(dice, column, row);
    }

    public int findElementIndex(int row, int column){
        int counter = 0;
        for(Node child : roundtrack.getChildren()){
            if (GridPane.getColumnIndex(child) == column && GridPane.getRowIndex(child) == row)
                break;
            counter++;
        }
        return counter;
    }

    public void error(String error){
        errorMessage.setVisible(true);
        errorMessage.setText(error);
    }

    public void restartButtonClicked(ActionEvent e) throws Exception {
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close();
        new ViewGUI().start(new Stage());
    }

    public void recreateRoundtrack() throws RemoteException {
        int roundtrackSize = viewGUI.getGameModel().getField().getRoundTrack().getGrid().size();
        int necessaryColumn = viewGUI.getGameModel().getField().getRoundTrack().getRound()-1;
        int necessaryRow;
        int row = 0;
        int column = 0;
        String diceName;

        if(roundtrackSize % necessaryColumn == 0)
            necessaryRow = roundtrackSize/necessaryColumn - 1;
        else
            necessaryRow = roundtrackSize/necessaryColumn;

        for(int i=0; i<viewGUI.getGameModel().getField().getRoundTrack().getGrid().size(); i++){
            diceName = viewGUI.getRoundtrackDice(i);
            reAddDice(row, column, diceName);
            if(row == necessaryRow){
                row = 0;
                column++;
            }
            else if(row == roundtrack.getRowConstraints().size()-1) {
                roundtrack.getRowConstraints().add(new RowConstraints(5));
                row++;
            }
            else
                row++;
        }
    }

    public void reAddDice(int row, int column, String diceName) {
        ImageView dice = new ImageView();
        dice.setFitHeight(57);
        dice.setFitWidth(57);
        String path = DICEPATH + diceName + PNG;
        loadImage(path, 57, 57, dice, 0);
        dice.setOnMouseClicked(e -> {
            try {
                roundtrackClick(e);
            } catch (IOException e1) {
                //do nothing
            }
        });
        roundtrack.add(dice, column, row);
    }
}
