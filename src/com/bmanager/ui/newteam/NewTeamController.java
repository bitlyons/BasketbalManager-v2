package com.bmanager.ui.newteam;

import com.bmanager.misc.AlertBox;
import com.bmanager.models.Team;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class NewTeamController {
    public Stage window;
    private Random rand = new Random();
    private boolean isEdit = false, userQuit = false;
    private ArrayList<Team> teamDB;
    private String title = "Add New Team";
    private Team workingTeam;
    NewTeamView view = new NewTeamView();

    /**
     * constructor
     **/
    public NewTeamController(ArrayList<Team> teamDB) {
        this.teamDB = teamDB;
        setupWindow();
        initializeButtons();
        if (!isEdit) view.textTeamId.setText(Integer.toString(generateTeamID()));

        window.setOnCloseRequest(e -> {
            e.consume();
            userQuit = true;
            window.close();
        });
    }


    /**
     * this method controls all  the buttons
     **/
    private void initializeButtons() {
        //Button to pick the image file on the filesystem for the logo
        view.buttonLogoURL.setOnAction(e -> {
            FileChooser urlPicker = new FileChooser();
            urlPicker.setTitle("Pick Team Logo");

            //filter to only image files NOTE: only using jpg, bmp and png here
            FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("Image file (png/bmp/jpg",
                    "*.png", "*.PNG", "*.bmp", "*.BMP", "*.jpg", "*.JPG");
            urlPicker.getExtensionFilters().add(filterPNG);
            urlPicker.setInitialDirectory(new File("./TeamIcons/"));
            //Pick the file
            try {
                File url = urlPicker.showOpenDialog(null);
                view.textTeamLogo.setText(url.getCanonicalPath());
            } catch (Exception f) {
                //TODO do something here
                System.out.println("No file chosen");
                //AlertBox.show("Error", "No File Chosen", "You did not pick a file");
            }
        });

        view.buttonSubmit.setOnAction(e -> addTeam());

        view.buttonCancel.setOnAction(e -> {
            userQuit = true;
            window.close();
        });
    }


    //TODO Add checks here, if all is valid, add to database, for now no checks are done;
    private void addTeam() {
        int teamID;
        try {
            teamID = Integer.parseInt(view.textTeamId.getText());
        } catch (Exception e) {
            teamID = generateTeamID();
        }
        String teamName = view.textTeamName.getText();
        String teamCity = view.textTeamCity.getText();
        String teamLogo = view.textTeamLogo.getText();
        //TODO add checks here on info to make sure they meet the requirements . url should be ether a http://, https:// , file  or /
        Boolean nameExists = nameExists(teamName);
        Team newTeam = new Team(teamID, teamName, teamCity, teamLogo);
        if (nameExists || isEdit) {
            if (!teamName.isEmpty()) {
                if (!teamCity.isEmpty()) {
                    if (teamLogo.toLowerCase().endsWith("png") || teamLogo.toLowerCase().endsWith("bmp") || teamLogo.toLowerCase().endsWith("jpg")) {
                        this.workingTeam = newTeam;
                        window.close();
                    } else AlertBox.show("Error", "No sufficient image entered", "Images must be a png/bmp/jpg");
                } else AlertBox.show("Error", "Team City cannot be empty", "Please enter the teams City");
            } else AlertBox.show("Error", "Name cannot be empty", "Please enter a team name");
        } else AlertBox.show("Error", "Name Already exists in database", "Please enter another name");
    }

    public Team getTeam() {
        return workingTeam;
    }

    /**
     * This method is used to edit a team, it works by pulling in the selected team, and the index where it is in the
     * Array List, then setting each field to the values of the player we pulled in. CheckTeam is still used for adding
     * the team to the database
     */

    public void editTeam(Team editTeam) {
        isEdit = true;
        //get all the values from the team we are editing.
        int teamId = editTeam.getTeamId();
        String teamName = editTeam.getTeamName();
        String teamCity = editTeam.getCity();
        String teamURL = editTeam.getLogoUrl();

        //set them as the values in the fields
        view.textTeamId.setText(Integer.toString(teamId));
        view.textTeamName.setText(teamName);
        view.textTeamCity.setText(teamCity);
        view.textTeamLogo.setText(teamURL);
        title = ("Edit Team");
    }


    private int generateTeamID() {
        if (teamDB == null || teamDB.size() == 0) {
            return rand.nextInt(100);
        } else {

            //Extracts the team Id from each team object in the array
            List<Integer> userIds = teamDB.stream().map(Team::getTeamId).collect(Collectors.toList());

            int uniqueId;

            //Keeps looping until a unique Id is generated
            do {
                uniqueId = rand.nextInt(100);
            } while (!existsIn(uniqueId, userIds));

            return uniqueId;
        }
    }


    private boolean existsIn(int generatedId, List<Integer> userIds) {
        for (int id : userIds)
            if (generatedId == id)
                return false;
        return true;
    }

    private boolean nameExists(String name) {
        List<String> teamName = new ArrayList<>();
        teamDB.forEach(team -> {
            if (team.getTeamName().equalsIgnoreCase(name)) teamName.add(team.getTeamName());
        });
        //if it is empty no names where found (true), if it is not, then a name is found(false)
        return teamName.isEmpty();
    }

    private void setupWindow() {
        window = new Stage();
        Scene scene = new Scene(view);
        window.setTitle(title);
        window.setScene(scene);
        window.setMinWidth(300);
        window.initModality(Modality.APPLICATION_MODAL); //stop user from interacting with other windows
        window.setResizable(false);
    }

    public boolean userExited() {
        return this.userQuit;
    }
}
