package com.bmanager.ui.newplayer;

import com.bmanager.misc.AlertBox;
import com.bmanager.models.Player;
import com.bmanager.models.Team;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * This class deals with all the controls of adding a new view players in relation to NewPlayerView
 */
public class NewPlayerController {

    public Stage window = new Stage();

    private NewPlayerView view = new NewPlayerView();
    private boolean isEdit = false, userQuit = false;
    private Random rand;
    private Player workingPlayer;
    private ArrayList<Team> teamDB;
    private ArrayList<Player> playerDB;
    private String title = "Add New Player";

    //constructor
    public NewPlayerController(ArrayList<Team> teamDB, ArrayList<Player> playerDB){
        this.teamDB = teamDB;
        this.playerDB = playerDB;
        rand = new Random();

        setupWindow();

        populateTeamCombo();
        typeComboBox();

        view.prefHeight(395);
        view.prefWidth(309);
        buttonHandlers();
        datePicker();
        view.textPlayerID.setText(Integer.toString(generatePlayerID()));
    }

    /** this method handles the user interactions with the buttons **/
    private void buttonHandlers() {
        view.buttonAddPlayer.setOnAction(e -> addPlayer());
        view.buttonCancel.setOnAction(e-> {
            userQuit  = true;
            window.close();}
        );
    }

    
    /** This Method adds all the player types to the type comboBox **/
    private void typeComboBox(){
        String[] playerTypes =
                new String[] { "Guards", "Centre", "Power Forward", "Small Forward" };
        view.comboPlayerType.getItems().addAll(playerTypes);
    }


    /** This method loops though the team list, pulls the name out and adds it to the team combo box **/
   
    private void populateTeamCombo() {
        teamDB.forEach(Team -> view.comboTeams.getItems().add(Team.getTeamName()));

    }

    /** This method deals with the date picker **/
    //TODO disable cells in calender for future dates
    private void datePicker(){
        view.dateDOB.setShowWeekNumbers(false);
        view.dateDOB.setPromptText("dd/mm/yyyy");
        //view.dateDOB.get
        //view.dateDOB.setDayCellFactory();
        }


    /** adds new player to the database **/
    public void addPlayer(){
        // Due to a possible bug in the javafx date picker where it does not update the user inputed data in the text field
        // unless they hit return, the following code is necessary as a workaround
        view.dateDOB.setValue(view.dateDOB.getConverter().fromString(view.dateDOB.getEditor().getText()));

        String errorMsg = validateUserInput();
        //Checks if there has been any errors with the data the user has entered

        if (errorMsg != null)
            AlertBox.show("Error", "User error detected", errorMsg);
        else {
            int playerID;
            try {
                playerID = Integer.parseInt(view.textPlayerID.getText());
            } catch (Exception e) {
                playerID = generatePlayerID();
            }

            String firstName = view.textFirstName.getText();
            String lastName = view.textLastName.getText();
            double height = Double.parseDouble(view.textHeight.getText());
            LocalDate dob = view.dateDOB.getValue();

            String type = view.comboPlayerType.getValue();
            String teamName = view.comboTeams.getValue();
            int teamId;
            try {
                teamId = getTeamId(teamName);
            }
            catch (IndexOutOfBoundsException e){
                teamId = -99;
            }
            //create player
            this.workingPlayer = new Player(playerID, firstName, lastName, dob, height, type, teamId);
            window.close();
        }
    }


    /** this method is used when a player is being edited, it sets a boolean isEdit to true so that when we are adding
     * the player to the database, we know to use .set() rather than .add()
     */
    public void editPlayer(Player player) {
        isEdit = true;
        title = "Edit Player";
        //Set Player fields for editing
        view.textPlayerID.setText(Integer.toString(player.getId()));
        view.textFirstName.setText(player.getFirstName());
        view.textLastName.setText(player.getLastName());
        view.textHeight.setText(Double.toString(player.getHeight()));
        view.dateDOB.setValue(player.getDob());
        view.comboPlayerType.setValue(player.getPlayerType());
        //uses the method getTeamName to find the team name from the teamID
        view.comboTeams.setValue(getTeamName(player.getTeam()));

        //Change the add button text to save
        view.buttonAddPlayer.setText("Save changes");
    }


    /** This will check if a player is valid or not **/
    //TODO do data check for valid data also
    private String validateUserInput()
    {
        //the player must be 45 or under (the oldest ever professional player)
        LocalDate today = LocalDate.now();
        LocalDate intDate = LocalDate.parse(today.getYear()-45+"-01-01");
        LocalDate endDate = LocalDate.parse(today.getYear()-5+"-01-01");

        if (view.textFirstName.getText().trim().isEmpty())
            return "First name cannot be left blank";

        if (view.textLastName.getText().trim().isEmpty())
            return "Last name cannot be left blank.";

        //Check if date of birth, make sure the player is under 45 and above 5(in case its used for a kids league)
        if (view.dateDOB.getValue().toString().trim().isEmpty())
            return "DOB cannot be left blank";
        if(view.dateDOB.getValue().isBefore(intDate))
        return "DOB cannot be before " + (today.getYear()-45);

        if(view.dateDOB.getValue().isAfter(endDate))
            return "DOB cannot be before " + (today.getYear()-5);

        if (view.textHeight.getText().trim().isEmpty())
            return "Height cannot be left blank";

        //height must be above 5 feet, but below 9 feet.
        if (Double.parseDouble(view.textHeight.getText()) <1.5 || Double.parseDouble(view.textHeight.getText())>2.7)
            return "Height value is unacceptable";


        if (view.comboPlayerType.getSelectionModel().getSelectedIndex() == -1)
            return "Player's type has not been set!";

        if (view.comboTeams.getSelectionModel().getSelectedIndex() == -1)
            return "Player has not been assigned to a team!";

        //Using regex to check if height entered is a valid double
        if (! Pattern.matches("\\d+(\\.\\d+)?", view.textHeight.getText()))
            return "Height is not a valid value";
        return null;
    }

    //find team id
    private int getTeamId(String teamName){
        List<Integer> teamID = new ArrayList<>();
        teamDB.forEach(team ->{
            if(team.getTeamName().equals(teamName))teamID.add(team.getTeamId());
        });
        return teamID.get(0);
    }

    //find team name
    private String getTeamName(int teamId){
        if(teamId == -99)return "NO TEAM FOUND";
        List<String> teamName = new ArrayList<>();
       teamDB.forEach(Team ->{
            if(Team.getTeamId() == teamId) {
               teamName.add(Team.getTeamName());
           }
        });
        //just in case nothing was added
        if(teamName.isEmpty()) return "NO TEAM FOUND";
        return teamName.get(0);
    }


    /** used to create the scene **/
    private void setupWindow(){

        Scene scene = new Scene(view);
        window.setTitle(title);
        window.setScene(scene);
        window.setMinWidth(300);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL); //stop user from interacting with other windows
        window.setOnCloseRequest(e -> userQuit = true);
    }


    /** generates a unique playerid **/
    private int generatePlayerID(){
        if (playerDB == null || playerDB.size() == 0) {
            return rand.nextInt(10000);
        } else {

            //Extracts the player Id from each team object in the array
            List<Integer> userIds = playerDB.stream().map(Player::getId).collect(Collectors.toList());

            int uniqueId;

            //Keeps looping until a unique Id is generated
            do {
                uniqueId = rand.nextInt(10000);
            } while (! existsIn(uniqueId, userIds));

            return uniqueId;
        }
    }

/** checks that the generated userid is not already in the database */
    private boolean existsIn(int generatedId, List<Integer> userIds) {
        for (int id : userIds)
            if (generatedId == id)
                return false;
        return true;
    }

    /** this is used to make sure the user did not close the window */
    public boolean userExited(){
        return this.userQuit;
    }

    /** since the user did not close the window , this method get called to return the player */
    public Player getPlayer(){
        return workingPlayer;
    }
}
