package com.bmanager.ui.viewteams;

import com.bmanager.controllers.Main;
import com.bmanager.models.Player;
import com.bmanager.models.Team;
import com.bmanager.ui.newteam.NewTeamController;
import com.bmanager.ui.newteam.NewTeamView;
import com.bmanager.ui.viewplayers.PlayerController;
import com.bmanager.ui.viewplayers.PlayerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.ArrayList;


public class TeamController {
    ObservableList <Team> teamDB = Main.teamDB;

    Team selectedTeam;
    boolean search = false;
    private TeamView view;
    private Stage window;
    int index;

    public TeamController(TeamView view, Stage window){
        this.view = view;
        this.window = window;
        initializeContextMenu();
        initializeButtons();
        initializeWatchers();

        view.tableTeams.setItems(teamDB);
    }

    private void initializeButtons() {
        //button that shows all teams
        //TODO add column to show teams when this button is clicked
        view.buttonTeams.setOnAction(e->{
            PlayerView playerView = new PlayerView();
            Scene scene = new Scene(playerView);
            PlayerController playerController = new PlayerController(playerView, window);
            window.setScene(scene);
            window.sizeToScene();
            window.setTitle("Basketball Manager : Players");
            //playerController.viewAll();
        });

        //This toggles the search field when the search button is clicked
        view.buttonSearch.setOnAction(e->{
          //TODO Write search features`

        });
    }

    /** This method deals with the right click menu **/
    private void initializeContextMenu() {

        //Using lambda statements to set an on action listener
        view.itemViewTeam.setOnAction(e-> viewTeam());
        view.itemAddTeam.setOnAction(e -> addNewTeam());
        view.itemEditTeam.setOnAction(e -> editSelectedTeam());
        view.itemDeleteTeam.setOnAction(e -> deleteSelectedTeam());

        //The context menu will show at the location where the mouse was clicked
        view.tableTeams.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                view.contextMenu.show(view.tableTeams, e.getScreenX(), e.getScreenY());
            else
                view.contextMenu.hide();
        });
    }


    /** this is used to store the currently selected team for when we wish to edit them **/
    private void initializeWatchers(){
        view.tableTeams.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            selectedTeam =  newValue;
            index = view.tableTeams.getSelectionModel().getFocusedIndex();
        });
    }

    //TODO Write Methods below
    private void viewTeam() {
    }

    private void addNewTeam() {
        NewTeamController team = setupStage("Add New Team");

    }
    private void editSelectedTeam() {
        NewTeamController playerController = setupStage("Edit Team");
        playerController.editTeam(selectedTeam, index);
    }

    private void deleteSelectedTeam() {
        teamDB.remove(view.tableTeams.getSelectionModel().getSelectedItem());

    }

    /** this method was created to cut down on repetition in the create/edit methods. */
    private NewTeamController setupStage(String title){
        Stage newTeamWindow = new Stage();

        NewTeamView view =new NewTeamView();

        NewTeamController playerController = new NewTeamController(view, newTeamWindow);
        Scene scene = new Scene(view);

        newTeamWindow.setTitle(title);
        newTeamWindow.setScene(scene);
        newTeamWindow.setMinWidth(300);
        newTeamWindow.setResizable(false);
        newTeamWindow.show();
        return playerController;
    }
}

