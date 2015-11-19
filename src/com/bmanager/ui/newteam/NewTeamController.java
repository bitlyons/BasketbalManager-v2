package com.bmanager.ui.newteam;

import com.bmanager.controllers.Main;
import com.bmanager.misc.AlertBox;
import com.bmanager.models.Team;
import com.bmanager.ui.viewteams.TeamController;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewTeamController {
    private NewTeamView view;
    private Stage window;
    ObservableList<Team> teamDB = Main.teamDB;
    boolean isEdit = false;
    int index = 0;

    public NewTeamController(NewTeamView view, Stage window){
        this.view = view;
        this.window = window;
        initializeButtons();
    }

    private void initializeButtons() {
        //Button to pick the image file on the filesystem for the logo
        view.buttonLogoURL.setOnAction(e->{
            FileChooser urlPicker = new FileChooser();
            urlPicker.setTitle("Pick Team Logo");

            //filter to only image files NOTE: only using jpg, bmps and png here
            FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("Image file (png/bmp/jpg",
                    "*.png", "*.PNG", "*.bmp", "*.BMP", "*.jpg", "*.JPG");
            urlPicker.getExtensionFilters().add(filterPNG);

            //Pick the file
            try{
            File url = urlPicker.showOpenDialog(null);
            view.textTeamLogo.setText(url.getPath());}
            catch (Exception f){
                //TODO do something here
                System.out.println("No file chosen");
                //AlertBox.show("Error", "No File Chosen", "You did not pick a file");
            }
        });

        view.buttonSubmit.setOnAction(e->{
            checkTeam();
        });

        view.buttonCancel.setOnAction(e->{
            //todo write something here
            window.close();
        });
    }

    //TODO Add checks here, if all is valid, add to database, for now now checks are done;
    public void checkTeam(){
//        int teamID = Integer.parseInt(view.textTeamId.getText());

        int teamID = 1;//temp till a method to generate number is formed
        String teamName = view.textTeamName.getText();
        String teamCity = view.textTeamCity.getText();
        String teamLogo = view.textTeamLogo.getText();
        //TODO add checks here on info to make sure they meet the requirements . url should be ether a http://, https:// , file  or /
        Team newTeam = new Team(teamID, teamName, teamCity, teamLogo);
        if(!isEdit) {
            teamDB.add(newTeam);
        }
        else{
            teamDB.set(index, newTeam);
        }
        window.close();
    }

    public void editTeam(Team editTeam, int index){
        isEdit = true;
        this.index = index;
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

    }
}
