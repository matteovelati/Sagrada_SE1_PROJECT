package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.States;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.rmi.RemoteException;

public class MPMatchController extends MatchController {

    private static final String FINALSCORE = "'S FINAL SCORE IS: ";

    @FXML
    private GridPane player2windowGrid, player3windowGrid, player4windowGrid;
    @FXML
    private AnchorPane player2window, player3window, player4window;
    @FXML
    private Label player2label, player3label, player4label;
    @FXML
    private VBox right;
    @FXML
    private Button rejoinButton;
    @FXML
    private ImageView privateObjective;

    private boolean rejoined;

    /**
     * initializes the match screen of the multi player
     * @throws RemoteException if the reference could not be accessed
     */
    void init() throws RemoteException {
        loadToolCards();
        loadPublicObjectives();
        setDraftDimension(viewGUI.getNumberOfPlayers());
        refreshDraft();
        loadWindowImage(viewGUI.getUser(), 300, 265, clientWindow);
        loadPrivateObjective();
        refreshTokens();
        setOtherPlayerNumber();
        loadOtherPlayerWindowImages();
        if(!viewGUI.getGameModel().getField().getRoundTrack().getGrid().isEmpty())
            recreateRoundtrack();
        rejoined = false;
        enableRoundtrack = false;


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
        restartButton.managedProperty().bind(restartButton.visibleProperty());

        errorMessage.setVisible(false);
        input.setVisible(false);
        rejoinButton.setVisible(false);
        restartButton.setVisible(false);
    }

    /**
     * hides the interactive window due to a server disconnection
     */
    void serverDown(){
        setInactive();
        rejoinButton.setVisible(false);
        message.setText("SEEMS LIKE THE SERVER HAS BEEN SHUT DOWN");
    }

    /**
     * sets the cards for the game
     * @throws RemoteException if the reference could not be accessed
     */

    private void loadToolCards() throws RemoteException {
        for(int i=0; i<3; i++){
            ImageView card = new ImageView();
            path = TOOLCARDPATH + viewGUI.getGameModel().getField().getToolCards().get(i).getNumber() + PNG;
            loadImage(path, 162, 226, card, 0);
            card.setOnMouseClicked(e -> {
                try {
                    toolcardClick(e);
                } catch (IOException e1) {
                    //do nothing
                }
            });
            toolcards.add(card, 0, i);
        }
    }

    /**
     * loads the public objectives images for the multi player
     * @throws RemoteException if the reference could not be accessed
     */
    private void loadPublicObjectives() throws RemoteException {
        for(int i=0; i<3; i++){
            ImageView card = new ImageView();
            path = PUBLICOBJECTIVEPATH + viewGUI.getGameModel().getField().getPublicObjectives().get(i).getIdNumber() + PNG;
            loadImage(path, 162, 226, card, 0);
            publicObjectives.add(card, 0, i);
        }
    }

    /**
     * loads the private objectives images for the multi player
     * @throws RemoteException if the reference could not be accessed
     */
    private void loadPrivateObjective() throws RemoteException {
        Player player = viewGUI.findPlayer(viewGUI.getUser());
        path = PRIVATEOBJECTIVEPATH + player.getPrivateObjectives().get(0).getColor() + PNG;
        loadImage(path, 173, 241, privateObjective, 0);
    }

    /**
     * sets the draft dimension based on the number of players in game
     * @param numberOfPlayers the number of players in game
     */
    private void setDraftDimension(int numberOfPlayers){
        createDraftSpace(0);
        for(int i=0; i<numberOfPlayers*2; i++) {
            createDraftSpace(i+1);
        }
    }

    /**
     * creates the space to allocate a die
     * @param columnIndex the index of the column
     */
    private void createDraftSpace(int columnIndex){
        ImageView space = new ImageView();
        space.setFitWidth(60);
        space.setFitHeight(60);
        space.setOnMouseClicked(e -> {
            try {
                draftClick(e);
            } catch (IOException e1) {
                //do nothing
            }
        });
        draft.getColumnConstraints().add(new ColumnConstraints(60));
        draft.add(space, columnIndex, 0);
    }

    /**
     * shows the favor tokens remaining
     * @throws RemoteException if the reference could not be accessed
     */
    private void refreshTokens() throws RemoteException {
        int t = viewGUI.getTokens(viewGUI.getUser());
        tokens.setText("TOKENS: " + t);
    }

    /**
     * sets the number of other player window to show
     * @throws RemoteException if the reference could not be accessed
     */
    private void setOtherPlayerNumber() throws RemoteException {
        if(viewGUI.getNumberOfPlayers() < 4){
            right.getChildren().removeAll(player4label, player4window);
            if(viewGUI.getNumberOfPlayers() < 3)
                right.getChildren().removeAll(player3label, player3window, region3);
        }
    }

    /**
     * loads the other player window patterns
     * @throws RemoteException if the reference could not be accessed
     */
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

    /**
     * disables the interactive buttons to non-actual players
     * shows a WAIT message
     * @throws RemoteException if the reference could not be accessed
     */
    void waitTurn() throws RemoteException {
        refreshTokens();
        buttons.setDisable(true);
        clientWindow.setDisable(true);
        draft.setDisable(true);
        toolcards.setDisable(true);
        publicObjectives.setDisable(true);
        roundtrack.setDisable(true);
        message.setText("WAIT YOUR TURN");
        if(!viewGUI.actualPlayer())
            viewGUI.setBlockSocketConnection(false);
    }

    /**
     * enables all the buttons for the first selection and refreshes the targetScore, the players
     * window and the draft
     * @throws RemoteException if the reference could not be accessed
     */
    void selectMove1View() throws RemoteException {
        if(rejoined) {
            if (!roundtrack.getChildren().isEmpty())
                roundtrack.getChildren().clear();
            recreateRoundtrack();
            rejoined = false;
        }
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

    /**
     * enables all the possible buttons for the second player move and refreshes the draft
     * @throws RemoteException if the reference could not be accessed
     */
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

    /**
     * enables to select a die from the draft and the abort button
     */
    void selectDraftView(){
        draft.setDisable(false);
        endTurn.setText("ABORT");
        message.setText("SELECT A DICE");
    }

    /**
     * enables to put a die in your window and the abort button
     */
    void putDiceInWindowView(){
        clientWindow.setDisable(false);
        message.setText("PUT THE DICE IN YOUR WINDOW");
    }

    /**
     * enables to select a toolcard and the abort button
     */
    void selectToolcardView(){
        toolcards.setDisable(false);
        endTurn.setText("ABORT");
        message.setText("SELECT A TOOLCARD");
    }

    /**
     * shows the message of the third stage of the toolcard selected and enables the elements that can be selected
     * @throws RemoteException if the reference could not be accessed
     */
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

    /**
     * removes all the center screen graphics and show the players final score
     * @throws IOException
     */
    void endMatchView() throws IOException {
        middle.getChildren().removeAll(buttons, tokens, windowArea, region2, draftArea, region1, roundtrackArea, input, errorMessage);
        message.setText("YOUR FINAL SCORE IS: " + viewGUI.getPlayerScore(viewGUI.getUser()) + "\n" + player2label.getText() + FINALSCORE + viewGUI.getPlayerScore(player2label.getText()));
        if(viewGUI.getNumberOfPlayers() > 2){
            message.setText(message.getText() + "\n" + player3label.getText() + FINALSCORE + viewGUI.getPlayerScore(player3label.getText()));
            if(viewGUI.getNumberOfPlayers() == 4)
                message.setText(message.getText() + "\n" + player4label.getText() + FINALSCORE + viewGUI.getPlayerScore(player4label.getText()));
        }
        if(viewGUI.actualPlayer())
            viewGUI.notifyNetwork();
    }

    /**
     * decides what to do when the pickDiceButton is clicked
     * @param e pick die button event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void pickDiceButton(ActionEvent e) throws IOException {
        switch (pickDice.getText()) {
            case PICKADICE:
                viewGUI.setChoose1(1);
                if (viewGUI.getGameState().equals(States.SELECTMOVE1))
                    firstMove = 1;
                pickDice.setDisable(true);
                useToolcard.setDisable(true);
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

    /**
     * decides what to do when the toolcard button is clicked
     * @param e useToolcard button event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void useToolcardButton(ActionEvent e) throws IOException {
        switch (useToolcard.getText()) {
            case USEATOOLCARD:
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

    /**
     * sets the toolcard selected from the user
     * @param e toolcard selected event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void toolcardClick(MouseEvent e) throws IOException {
        errorMessage.setVisible(false);
        ImageView selected = (ImageView) e.getSource();
        viewGUI.setChoose1(GridPane.getRowIndex(selected)+1);
        toolcards.setDisable(true);
        viewGUI.notifyNetwork();
    }

    /**
     * loads the images of the dices in the other players window
     * @throws RemoteException if the reference could not be accessed
     */
    void refreshOtherPlayerWindow() throws RemoteException {
        for(int i=0; i < player2windowGrid.getRowConstraints().size(); i++){
            for(int j=0; j<player2windowGrid.getColumnConstraints().size(); j++){
                if(!viewGUI.checkOtherPlayerWindowEmptyCell(player2label.getText(), i, j)){
                    String s = DICEPATH + viewGUI.getOtherPlayerDiceColor(player2label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player2label.getText(), i, j) + PNG;
                    Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                    ImageView dice = new ImageView(image);
                    dice.setFitHeight(35);
                    dice.setFitWidth(35);
                    player2windowGrid.add(dice, j, i);
                }
            }
            if(viewGUI.checkOtherPlayerOnline(player2label.getText()))
                player2label.setTextFill(Color.web(GREEN));
            else
                player2label.setTextFill(Color.web(RED));
            if(viewGUI.checkOtherPlayerActual(player2label.getText()))
                player2label.setTextFill(Color.web(BLUE));
        }
        if(viewGUI.getNumberOfPlayers() > 2){
            for(int i=0; i < player3windowGrid.getRowConstraints().size(); i++){
                for(int j=0; j<player3windowGrid.getColumnConstraints().size(); j++){
                    if(!viewGUI.checkOtherPlayerWindowEmptyCell(player3label.getText(), i, j)){
                        String s = DICEPATH + viewGUI.getOtherPlayerDiceColor(player3label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player3label.getText(), i, j) + PNG;
                        Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                        ImageView dice = new ImageView(image);
                        dice.setFitHeight(35);
                        dice.setFitWidth(35);
                        player3windowGrid.add(dice, j, i);
                    }
                }
            }
            if(viewGUI.checkOtherPlayerOnline(player3label.getText()))
                player3label.setTextFill(Color.web(GREEN));
            else
                player3label.setTextFill(Color.web(RED));
            if(viewGUI.checkOtherPlayerActual(player3label.getText()))
                player3label.setTextFill(Color.web(BLUE));
            if(viewGUI.getNumberOfPlayers() == 4){
                for(int i=0; i < player4windowGrid.getRowConstraints().size(); i++){
                    for(int j=0; j<player4windowGrid.getColumnConstraints().size(); j++){
                        if(!viewGUI.checkOtherPlayerWindowEmptyCell(player4label.getText(), i, j)){
                            String s = DICEPATH + viewGUI.getOtherPlayerDiceColor(player4label.getText(), i, j) + viewGUI.getOtherPlayerDiceValue(player4label.getText(), i, j) + PNG;
                            Image image = new Image(getClass().getResourceAsStream(s), 35, 35, false, true);
                            ImageView dice = new ImageView(image);
                            dice.setFitHeight(35);
                            dice.setFitWidth(35);
                            player4windowGrid.add(dice, j, i);
                        }
                    }
                }
                if(viewGUI.checkOtherPlayerOnline(player4label.getText()))
                    player4label.setTextFill(Color.web(GREEN));
                else
                    player4label.setTextFill(Color.web(RED));
                if(viewGUI.checkOtherPlayerActual(player4label.getText()))
                    player4label.setTextFill(Color.web(BLUE));
            }
        }
    }

    /**
     * hides all the screen graphics and shows only the rejoin button
     */
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

    /**
     * hide the rejoin button and add again the player to the match
     * @param e rejoin button event
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public void rejoinButtonClicked(ActionEvent e) throws IOException {
        viewGUI.matchRejoined();
        rejoinButton.setVisible(false);
        message.setText("JOINING AGAIN THE MATCH...\nWAIT YOUR TURN");
        rejoined = true;
    }
}
