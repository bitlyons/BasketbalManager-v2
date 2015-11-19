package com.bmanager.ui.viewplayers;

import com.bmanager.data_access.FileHandler;
import com.bmanager.misc.AlertBox;
import com.bmanager.models.Player;
import com.bmanager.ui.newplayer.NewPlayerController;
import com.bmanager.ui.newplayer.NewPlayerView;
import com.bmanager.ui.viewteams.TeamController;
import com.bmanager.ui.viewteams.TeamView;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.File;

/**
 * This class deals with all the user interactions in the PlayerView.
 */
public class PlayerController {
    private PlayerView view;
    Stage window;

    /** Constructor, **/
    public PlayerController(PlayerView view, Stage window) {
        this.view = view;
        this.window = window;
        view.tablePlayers.setItems(loadSavedFile());
        initializeContextMenu();
        initializeButtons();
        initializeOptions();
    }


    /** This method deals with the right click menu **/
    private void initializeContextMenu() {

        //Using lambda statements to set an on action listener
        view.itemAddPlayer.setOnAction(e -> addNewPlayer());
        //view.itemEditPlayer.setOnAction(e -> editSelectedPlayer());
        view.itemDeletePlayer.setOnAction(e -> deleteSelectedPlayer());

        //The context menu will show at the location where the mouse was clicked
        view.tablePlayers.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                view.contextMenu.show(view.tablePlayers, e.getScreenX(), e.getScreenY());
            else
                view.contextMenu.hide();
        });
    }

    /** This method deals with the Buttons **/
    private void initializeButtons(){
        //Button that returns to the Team View
        view.buttonReturn.setOnAction(e->{
            TeamView teamView = new TeamView();
            Scene scene = new Scene(teamView);
            TeamController teamController = new TeamController(teamView, window);
            window.setScene(scene);
            window.sizeToScene();
            window.setResizable(false);
            window.setTitle("Basketball Manager : Teams");
        });

        //Button to go to the next team
        view.buttonNext.setOnAction(e-> {
        //TODO write code
        });

        //Button to go to the next team
        view.buttonPrev.setOnAction(e-> {
            //TODO Write code
        });
    }

    /**  controls the options menu  **/
    private void initializeOptions(){
        //save option
        view.save.setOnAction(e->{
            //todo write to save the database
        });

        //exports current team to html
        view.export.setOnAction(e->{
            //TODO write code to export to html, method call from FileHandler or new class in data_access
        });

        //pick save location
        view.saveLocation.setOnAction(e-> {
            //TODO write folder selector
        });

        view.loadDatabase.setOnAction(e->{
            //TODO filepicker then load file
        });

        view.print.setOnAction(e->{
            //TODO write code for printing
        });


    }

    //TODO rewrite this to deal with only loading players from a specific team
    private ObservableList<Player> loadSavedFile() {
        try {
            return FileHandler.loadFromFile(new File("players.csv"));
        } catch (Exception e) {
            //Shows an error if any occur
            AlertBox.show("Error", "File save error", "There was an error whilst loading saved file..");

            e.printStackTrace();
            return null;
        }
    }

    /** addNewPlayer Opens a new window to create a new Player for the database**/
    private void addNewPlayer() {
        Stage newPlayerWindow = new Stage();
        NewPlayerView view = new NewPlayerView();
        Scene scene = new Scene(view);
        newPlayerWindow.setScene(scene);
        NewPlayerController playerController = new NewPlayerController(view, newPlayerWindow);
        newPlayerWindow.setTitle("Add New Player");
        newPlayerWindow.setMinWidth(300);
        newPlayerWindow.setResizable(false);
        newPlayerWindow.show();
    }


/**
    private void editSelectedPlayer() {
        Player selectedPlayer = view.tablePlayers.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            AlertBox.show("Notice", "Notice", "Please select a viewplayers to edit");
        } else {
            Player editedPlayer = view.playerWindow.editPlayer(selectedPlayer);

            //If the returned viewplayers is null it means the user cancelled edit

            if (editedPlayer != null) {
                int index = view.tablePlayers.getItems().indexOf(selectedPlayer);
                view.tablePlayers.getItems().set(index, editedPlayer);
            }
        }
    }
**/

    //TODO write the following Method
    private void deleteSelectedPlayer(){System.out.println("Delete");}

    /** bad way of doing it
    public void viewAll(){
        view.allPlayers();
    }
*/
}
