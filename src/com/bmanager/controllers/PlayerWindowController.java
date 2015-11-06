package com.bmanager.controllers;

import com.bmanager.misc.AlertBox;
import com.bmanager.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//A multi purpose controller that can be used to edit an existing player or adding a new player
//This controller is initialized differently to MainController.java
//The code for loading the fxml document is placed inside the class, where in the MainController.java its not, the fxml and controller is set within the class.
//The reason for this different approach is to allow this PlayWindowController to be used an object that can be instantiated and then called in order to retrieve value
//Which in this case is editing and adding a new user. I could of used a static class but that would not be considered good practice.

public class PlayerWindowController implements Initializable {

    //The UI controls declared from the fxml document
    @FXML
    private ComboBox<String> cboTeams, cboPlayerType;
    @FXML
    private TextField txtId, txtFirstname, txtLastname, txtAge, txtHeight;
    @FXML
    private Pane rootNode;
    @FXML
    private Button btn;

    //The stage where the scene is attached to
    private Stage stage;
    private Random rand;

    //Represents if the user cancelled out of the form. Therefore allowing them to exit.
    private boolean userCancelledWindow;

    public PlayerWindowController() {

        rand = new Random();

        try {

            //Loads the fxml document
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bmanager/views/ScenePlayer.fxml"));

            //Sets the controller to this class
            fxmlLoader.setController(this);

            //Loads the fxml file
            fxmlLoader.load();

            //Using the loaded fxml document (view), a stage/window is constructed.
            stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(rootNode));
            stage.sizeToScene();

            //An event handler for when the user tries to close the window
            stage.setOnCloseRequest(e -> userCancelledWindow = true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method that is called when the fxml file has been fulled loaded and processed.
     * This then allows me to access the ui elements without a null reference exception being thrown
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] playersList =
                new String[] { "Chicago Bulls", "San Antonio Spurs", "L.A. Lakes", "Miami Heat", "Boston Celtics" };

        String[] playerTypes =
                new String[] { "Guards", "Centre", "Power Forward", "Small Forward" };

        cboTeams.getItems().addAll(playersList);
        cboPlayerType.getItems().addAll(playerTypes);
    }

    /**
     * Called to let the controller know what type of action it's being called for
     * So it knows how to adapt e.g. changing the names of buttons and window title
     * @param action
     */
    private void setUsageType(ActionType action) {

        userCancelledWindow = false;

        if (action == ActionType.ADD) {
            stage.setTitle("Add Player");
            btn.setText("Add Player");
            clearInputs();

        } else if (action == ActionType.EDIT) {
            stage.setTitle("Edit Player");
            btn.setText("Save changes");
        }
    }

    /**
     * The method that is called when the multi purpose button is clicked
     * @param event
     */
    @FXML
    private void btn_onAction(ActionEvent event) {

        String errorMsg = validateUserInput();
        //Checks if there has been any errors with the data the user has entered

        if (errorMsg != null)
            AlertBox.show("Error", "User error detected", errorMsg);
        else
            stage.close();

        //By closing the stage/window, it allows the form to exit the infinite loop it was placed in by the
        //stage.showAndWait() call
    }

    /**
     * The method called to allow the user to create a new user
     * @param playerList A list of existing users
     * @return The created user
     */
    public Player getNewPlayer(List<Player> playerList) {

        //Sets the usage type
        setUsageType(ActionType.ADD);

        //Generates a unique id for the new user
        int uniqueId = generateUniqueId(playerList);
        txtId.setText(String.valueOf(uniqueId));

        //This means the window will block all events until it's released/closed
        //Therefore it will sit in an infinite loop until it exists
        //It only exists the loop if the cancel button is clicked or when the Add/Edit button is clicked
        stage.showAndWait();

        //If the user cancelled the window it returns null (which is handled by the caller)
        //Else it will get the player by parsing the content of the UI elements/controls
        return userCancelledWindow ? null : getPlayer();
    }

    /**
     * Called when the user wants to edit a player.
     * @param p The current player to edit
     * @return The edited player
     */
    public Player editPlayer(Player p) {

        //Sets the usage type
        setUsageType(ActionType.EDIT);

        //Sets the attributes of the player to the UI elements
        txtId.setText(String.valueOf(p.getId()));
        txtFirstname.setText(p.getFirstname());
        txtLastname.setText(p.getLastname());
        txtAge.setText(String.valueOf(p.getAge()));
        txtHeight.setText(String.valueOf(p.getHeight()));

        cboPlayerType.getSelectionModel().select(p.getPlayerType());
        cboTeams.getSelectionModel().select(p.getTeam());

        //This means the window will block all events until it's released/closed
        //Therefore it will sit in an infinite loop until it exists
        //It only exists the loop if the cancel button is clicked or when the Add/Edit button is clicked
        stage.showAndWait();

        //If the user cancelled the window it returns null (which is handled by the caller)
        //Else it will get the player by parsing the content of the UI elements/controls
        return userCancelledWindow ? null : getPlayer();
    }

    /**
     * Creates a new player by using the values/content from the UI elements
     * @return Player
     */
    private Player getPlayer() {

        Player player = new Player();

        player.setId(Integer.parseInt(txtId.getText()));
        player.setFirstname(txtFirstname.getText());
        player.setLastname(txtLastname.getText());
        player.setAge(Integer.parseInt(txtAge.getText()));
        player.setHeight(Double.parseDouble(txtHeight.getText()));
        player.setPlayerType(cboPlayerType.getSelectionModel().getSelectedItem());
        player.setTeam(cboTeams.getSelectionModel().getSelectedItem());

        return player;
    }

    /**
     * Used to validate the user input. Returns an error message if there is any
     * @return The error message
     */
    private String validateUserInput() {

        if (txtFirstname.getText().trim().isEmpty())
            return "Username cannot be left blank";

        if (txtLastname.getText().trim().isEmpty())
            return "Lastname cannot be left blank.";

        if (txtAge.getText().trim().isEmpty())
            return "Age cannot be left blank";

        if (txtHeight.getText().trim().isEmpty())
            return "Height cannot be left blank";

        if (cboPlayerType.getSelectionModel().getSelectedIndex() == -1)
            return "Player's type has not been set!";

        if (cboTeams.getSelectionModel().getSelectedIndex() == -1)
            return "Player has not been assigned to a team!";

        //Using regex to check if the age entered is a valid integer
        if (! Pattern.matches("\\d+", txtAge.getText()))
            return "Age is not a valid integer";

        //Using regex to check if height entered is a valid double
        if (! Pattern.matches("\\d+(\\.\\d+)?", txtHeight.getText()))
            return "Height is not a valid value";

        String fullname = txtFirstname.getText() + txtLastname.getText();

        //Ensures that name doesn't have a comma. This is because the data is saved as a comma seperated variable
        if (fullname.contains(","))
            return "Name cannot contain a comma(,). Please remove it";

        return null;
    }

    /**
     * Used to clear the content of all UI input elements
     */
    private void clearInputs() {
        txtId.clear();
        txtFirstname.clear();
        txtLastname.clear();
        txtAge.clear();
        txtHeight.clear();

        cboPlayerType.getSelectionModel().clearSelection();
        cboTeams.getSelectionModel().clearSelection();
    }

    /**
     * Generates a unique Id for a new player
     * @param players A list of existing users
     * @return new unique id
     */
    private int generateUniqueId(List<Player> players) {

        if (players == null || players.size() == 0) {
            return rand.nextInt(100000);
        } else {

            //Extracts the user Id from each player object in the array
            List<Integer> userIds = players.stream().map(Player::getId).collect(Collectors.toList());

            int uniqueId;

            //Keeps looping until a unique Id is generated
            do {
                uniqueId = rand.nextInt(100000);
            } while (! existsIn(uniqueId, userIds));

            return uniqueId;
        }
    }

    /**
     * Checks if the generated user Id exists in an existing list of userIds
     * @param generatedId The generated id
     * @param userIds An existing list of players userIds
     * @return the boolean resultant of operation
     */
    private boolean existsIn(int generatedId, List<Integer> userIds) {
        for (int id : userIds)
            if (generatedId == id)
                return false;

        return true;
    }

    //A enum used to let the controller know what type of action it's being called for
    private enum ActionType {
        ADD,
        EDIT
    }
}