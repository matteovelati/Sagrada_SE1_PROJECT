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

import java.io.IOException;
import java.rmi.RemoteException;

public class MatchController {

    @FXML
    private GridPane draft, toolcards, publicObjectives, roundtrack, grid, player2windowGrid, player3windowGrid, player4windowGrid;
    @FXML
    private AnchorPane clientWindow, player2window, player3window, player4window;
    @FXML
    private Label message, tokens, errorMessage, player2label, player3label, player4label;
    @FXML
    private HBox buttons, draftArea, windowArea, roundtrackArea, left;
    @FXML
    private VBox middle, right;
    @FXML
    private Button pickDice, useToolcard, endTurn, showDices, rejoinButton;
    @FXML
    private ImageView privateObjective;
    @FXML
    private Region region1, region2, region3;
    @FXML
    private TextField input;

    private ViewGUI viewGUI;
    private int firstMove = 0;
    private boolean enableRoundtrack = false;

    void setViewGUI(ViewGUI viewGUI){
        this.viewGUI = viewGUI;
    }

    void init() throws RemoteException {
        setCards(toolcards, "toolcards");
        setCards(publicObjectives, "public_obj");
        setDraftDimension(viewGUI.getNumberOfPlayers());
        refreshDraft();
        loadWindowImage(viewGUI.getUser(), 300, 265, clientWindow);
        loadCardImage("private_obj", viewGUI.getCardId("private_obj", 0), privateObjective, 173, 241);
        refreshTokens();
        setOtherPlayerNumber();
        loadOtherPlayerWindowImages();

        errorMessage.managedProperty().bind(errorMessage.visibleProperty());
        input.managedProperty().bind(input.visibleProperty());
        draftArea.managedProperty().bind(draftArea.visibleProperty());
        windowArea.managedProperty().bind(windowArea.visibleProperty());
        tokens.managedProperty().bind(tokens.visibleProperty());
        region1.managedProperty().bind(region1.visibleProperty());
        region2.managedProperty().bind(region2.visibleProperty());
        buttons.managedProperty().bind(buttons.visibleProperty());
        roundtrackArea.managedProperty().bind(roundtrackArea.visibleProperty());
        rejoinButton.managedProperty().bind(rejoinButton.visibleProperty());
        left.managedProperty().bind(left.visibleProperty());
        right.managedProperty().bind(right.visibleProperty());

        errorMessage.setVisible(false);
        input.setVisible(false);
        rejoinButton.setVisible(false);
    }

    private void setCards(GridPane type, String folder) throws RemoteException {
        for(Node card : type.getChildren()){
            loadCardImage(folder, viewGUI.getCardId(folder, GridPane.getRowIndex(card)), (ImageView)card, 162, 226);
        }
    }

    private void setDraftDimension(int numberOfPlayers){
        createDraftSpace(0);
        for(int i=0; i<numberOfPlayers*2; i++) {
            createDraftSpace(i+1);
        }
    }

    private void createDraftSpace(int columnIndex){
        ImageView space = createEmptyImageView(60, 60);
        space.setOnMouseClicked(e -> {
            try {
                draftClick(e);
            } catch (RemoteException e1) {
                //do nothing
            }
        });
        draft.getColumnConstraints().add(new ColumnConstraints(60));
        draft.add(space, columnIndex, 0);
    }

    private void refreshDraft() throws RemoteException {
        for(Node diceImage : draft.getChildren()){
            if(viewGUI.checkDraftSize(GridPane.getColumnIndex(diceImage))) {
                loadDiceImageFromDraft(GridPane.getColumnIndex(diceImage), (ImageView)diceImage, 60, 60);
            }else{
                ((ImageView) diceImage).setImage(null);
            }
        }
    }

    private void loadWindowImage(String player, int width, int height, AnchorPane window) throws RemoteException {
        String path = "images/windows/" + viewGUI.getPlayerWindow(player) + ".png";
        loadImage(path, width, height, window, 1);
    }

    private void loadDiceImageFromDraft(int column, ImageView diceImage, int width, int height) throws RemoteException {
        String path = "images/dices/" + viewGUI.getDraftDice(column) + ".png";
        loadImage(path, width, height, diceImage, 0);
    }

    private void loadCardImage(String folder, String id, ImageView container, int width, int height){
        String path = "images/" + folder + "/" + id + ".png";
        loadImage(path, width, height, container, 0);
    }

    private void loadImage(String path, int width, int height, Node element, int type){//type 0: imageview, type 1: anchorpane
        Image image = new Image(getClass().getResourceAsStream(path), width, height, false, true);

        if(type == 0)
            ((ImageView)element).setImage(image);
        else if(type == 1) {
            BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            ((AnchorPane) element).setBackground(new Background(bg));
        }
    }

    private ImageView createEmptyImageView(int width, int height){
        ImageView imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    private void refreshTokens() throws RemoteException {
        int t = viewGUI.getTokens(viewGUI.getUser());
        tokens.setText("TOKENS: " + t);
    }

    private void setOtherPlayerNumber() throws RemoteException {
        if(viewGUI.getNumberOfPlayers() < 4){
            right.getChildren().removeAll(player4label, player4window);
            if(viewGUI.getNumberOfPlayers() < 3)
                right.getChildren().removeAll(player3label, player3window, region3);
        }
    }

    private void loadOtherPlayerWindowImages() throws RemoteException {
        int set=0;
        for(int i = 0; i < viewGUI.getNumberOfPlayers(); i++){
            if(!viewGUI.getPlayerUsername(i).equals("next")) {
                if(set == 0){
                    loadWindowImage(viewGUI.getPlayerUsername(i), 210, 187, player2window);
                    player2label.setText(viewGUI.getPlayerUsername(i));
                    set++;
                }
                else if(set == 1){
                    loadWindowImage(viewGUI.getPlayerUsername(i), 210, 187, player3window);
                    player3label.setText(viewGUI.getPlayerUsername(i));
                    set++;
                }
                else if(set == 2){
                    loadWindowImage(viewGUI.getPlayerUsername(i), 210, 187, player4window);
                    player4label.setText(viewGUI.getPlayerUsername(i));
                }
            }
        }
    }

    void waitTurn() throws RemoteException {
        refreshTokens();
        buttons.setDisable(true);
        clientWindow.setDisable(true);
        draft.setDisable(true);
        toolcards.setDisable(true);
        publicObjectives.setDisable(true);
        roundtrack.setDisable(true);
        message.setText("WAIT YOUR TURN");
    }

    void selectMove1View() throws RemoteException {
        buttons.setVisible(true);
        tokens.setVisible(true);
        windowArea.setVisible(true);
        region2.setVisible(true);
        draftArea.setVisible(true);
        region1.setVisible(true);
        roundtrackArea.setVisible(true);
        left.setVisible(true);
        right.setVisible(true);

        firstMove = 0;
        refreshTokens();
        refresh();
        refreshOtherPlayerWindow();
        enableAllButtons();
        setStandardTexts();
    }

    void selectMove2View() throws RemoteException {
        refreshDraft();
        setWindowGrid();
        refreshTokens();
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

    void selectDraftView(){
        draft.setDisable(false);
        endTurn.setText("ABORT");
        message.setText("SELECT A DICE");
    }

    void putDiceInWindowView(){
        clientWindow.setDisable(false);
        message.setText("PUT THE DICE IN YOUR WINDOW");
    }

    void selectToolcardView(){
        toolcards.setDisable(false);
        endTurn.setText("ABORT");
        message.setText("SELECT A TOOLCARD");
    }

    void useToolcardView() throws RemoteException {
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

    void useToolcard3View() throws RemoteException {
        refreshTokens();
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

    void endMatchView() throws RemoteException {
        middle.getChildren().removeAll(buttons, tokens, windowArea, region2, draftArea, region1, roundtrackArea, input, errorMessage);
        message.setText("YOUR FINAL SCORE IS: " + viewGUI.getPlayerScore(viewGUI.getUser()) + "\n" + player2label.getText() + "'S FINAL SCORE IS: " + viewGUI.getPlayerScore(player2label.getText()));
        if(viewGUI.getNumberOfPlayers() > 2){
            message.setText(message.getText() + "\n" + player3label.getText() + "'S FINAL SCORE IS: " + viewGUI.getPlayerScore(player3label.getText()));
            if(viewGUI.getNumberOfPlayers() == 4)
                message.setText(message.getText() + "\n" + player4label.getText() + "'S FINAL SCORE IS: " + viewGUI.getPlayerScore(player4label.getText()));
        }
    }

    private void showErrorMessage(String s){
        errorMessage.setVisible(true);
        errorMessage.setText(s);
    }

    private void enableAllButtons(){
        buttons.setDisable(false);
        pickDice.setDisable(false);
        useToolcard.setDisable(false);
        endTurn.setDisable(false);
    }

    private void setStandardTexts(){
        endTurn.setText("END TURN");
        message.setText("CHOOSE YOUR MOVE");
    }

    private void addDiceToRoundtrack(int row, int column) throws RemoteException {
        ImageView dice = createEmptyImageView(57, 57);
        loadDiceImageFromDraft(row, dice, 57, 57);
        dice.setOnMouseClicked(e -> {
            try {
                roundtrackClick(e);
            } catch (RemoteException e1) {
                //do nothing
            }
        });
        roundtrack.add(dice, column, row);
    }

    //

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
        switch (pickDice.getText()) {
            case "PICK A DICE":
                viewGUI.setChoose1(1);
                if (viewGUI.getGameState().equals(States.SELECTMOVE1))
                    firstMove = 1;
                pickDice.setDisable(true);
                useToolcard.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "INCREASE":
                viewGUI.getChoices().add(1);
                pickDice.setText("PICK A DICE");
                pickDice.setDisable(true);
                useToolcard.setText("USE A TOOLCARD");
                useToolcard.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "YES":
                errorMessage.setVisible(false);
                endTurn.setDisable(false);
                viewGUI.getChoices().add(1);
                clientWindow.setDisable(false);
                message.setText("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                pickDice.setText("PICK A DICE");
                pickDice.setDisable(true);
                useToolcard.setText("USE A TOOLCARD");
                useToolcard.setDisable(true);
                break;
        }
    }

    public void useToolcardButton(ActionEvent e) throws IOException {
        switch (useToolcard.getText()) {
            case "USE A TOOLCARD":
                if (viewGUI.getGameState().equals(States.SELECTMOVE1)) {
                    viewGUI.setChoose1(2);
                    firstMove = 2;
                } else {
                    viewGUI.setChoose1(1);
                }
                pickDice.setDisable(true);
                useToolcard.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "DECREASE":
                viewGUI.getChoices().add(-2);
                useToolcard.setText("USE A TOOLCARD");
                useToolcard.setDisable(true);
                pickDice.setText("PICK A DICE");
                pickDice.setDisable(true);
                viewGUI.notifyNetwork();
                break;
            case "NO":
                errorMessage.setVisible(false);
                endTurn.setDisable(false);
                viewGUI.getChoices().add(2);
                useToolcard.setText("USE A TOOLCARD");
                pickDice.setText("PICK A DICE");
                viewGUI.notifyNetwork();
                break;
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

    private void draftClick(MouseEvent e) throws RemoteException {
        ImageView selected = (ImageView) e.getSource();
        errorMessage.setVisible(false);

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
        enableRoundtrack = false;
        hideRoundtrackDices();
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
        refreshDraft();
        setWindowGrid();
    }

    private void showRoundtrackDices(){
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

    private void hideRoundtrackDices(){
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

    void refreshOtherPlayerWindow() throws RemoteException {
        for(int i=0; i < player2windowGrid.getRowConstraints().size(); i++){
            for(int j=0; j<player2windowGrid.getColumnConstraints().size(); j++){
                if(!viewGUI.checkOtherPlayerWindowEmptyCell(player2label.getText(), i, j)){
                    String s = "images/dices/" + viewGUI.getOtherPlayerDiceColor(player2label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player2label.getText(), i, j) + ".png";
                    Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                    ImageView dice = new ImageView(image);
                    dice.setFitHeight(35);
                    dice.setFitWidth(35);
                    player2windowGrid.add(dice, j, i);
                }
            }
        }
        if(viewGUI.getNumberOfPlayers() > 2){
            for(int i=0; i < player3windowGrid.getRowConstraints().size(); i++){
                for(int j=0; j<player3windowGrid.getColumnConstraints().size(); j++){
                    if(!viewGUI.checkOtherPlayerWindowEmptyCell(player3label.getText(), i, j)){
                        String s = "images/dices/" + viewGUI.getOtherPlayerDiceColor(player3label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player3label.getText(), i, j) + ".png";
                        Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                        ImageView dice = new ImageView(image);
                        dice.setFitHeight(35);
                        dice.setFitWidth(35);
                        player3windowGrid.add(dice, j, i);
                    }
                }
            }
            if(viewGUI.getNumberOfPlayers() == 4){
                for(int i=0; i < player4windowGrid.getRowConstraints().size(); i++){
                    for(int j=0; j<player4windowGrid.getColumnConstraints().size(); j++){
                        if(!viewGUI.checkOtherPlayerWindowEmptyCell(player4label.getText(), i, j)){
                            String s = "images/dices/" + viewGUI.getOtherPlayerDiceColor(player4label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player4label.getText(), i, j) + ".png";
                            Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                            ImageView dice = new ImageView(image);
                            dice.setFitHeight(35);
                            dice.setFitWidth(35);
                            player4windowGrid.add(dice, j, i);
                        }
                    }
                }
            }
        }
    }

    void setInactive(){
        buttons.setVisible(false);
        tokens.setVisible(false);
        windowArea.setVisible(false);
        region2.setVisible(false);
        draftArea.setVisible(false);
        region1.setVisible(false);
        roundtrackArea.setVisible(false);
        input.setVisible(false);
        errorMessage.setVisible(false);
        left.setVisible(false);
        right.setVisible(false);

        message.setText("YOU ARE NOW INACTIVE!\nTO JOIN AGAIN THE MATCH, PRESS THE BUTTON");
        rejoinButton.setVisible(true);
    }

    public void rejoinButtonClicked(ActionEvent e) throws IOException {
        viewGUI.matchRejoined();
        rejoinButton.setVisible(false);
        message.setText("JOINING AGAIN THE MATCH...\nWAIT YOUR TURN");
    }
}
