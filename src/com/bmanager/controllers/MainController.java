package com.bmanager.controllers;

import com.bmanager.data_access.FileHandler;
import com.bmanager.misc.AlertBox;
import com.bmanager.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

//The controller for the main program. This is where you can view/add the players, but also
//have the option to edit and delete existing players

public class MainController implements Initializable {

    @FXML
    private TableView<Player> tablePlayers;
    @FXML
    private TableColumn<Player, Integer> columnId, columnAge, columnHeight;
    @FXML
    private TableColumn<Player, String> columnFirstname, columnLastname, columnTeam, columnType;

    private PlayerWindowController playerWindow;

    /**
     * The method that is called when the fxml file has been fulled loaded and processed.
     * This then allows me to access the ui elements without a null reference exception being thrown
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Initializes the multi purpose player window
        playerWindow = new PlayerWindowController();
        initializeContextMenu();

        //Initializing the columns first
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));

        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("playerType"));

        tablePlayers.setItems(loadSavedFile());
    }

    /***
     * Initializes the context menu when the left mouse is clicked whilst on table view
     */
    private void initializeContextMenu() {

        ContextMenu contextMenu = new ContextMenu();

        //The different menu items for the context menu
        MenuItem itemAddPlayer = new MenuItem("Add New Player");
        MenuItem itemEditPlayer = new MenuItem("Edit Player");
        MenuItem itemDeletePlayer = new MenuItem("Delete Player");

        //Using lambda statements to set an on action listener
        itemAddPlayer.setOnAction(e -> addNewPlayer());
        itemEditPlayer.setOnAction(e -> editSelectedPlayer());
        itemDeletePlayer.setOnAction(e -> deleteSelectedPlayer());

        contextMenu.getItems().addAll(itemAddPlayer, itemEditPlayer, itemDeletePlayer);

        //A multi-line lambda for when the mouse is clicked
        //The context menu will show at the location where the mouse was clicked

        tablePlayers.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                contextMenu.show(tablePlayers, e.getScreenX(), e.getScreenY());
            else
                contextMenu.hide();
        });
    }

    /**
     *
     * @return a list of players from a file
     */
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

    /**
     * Opens the multi-purpose window that enables the user to add a new basketball player
     */
    private void addNewPlayer() {
        Player p = playerWindow.getNewPlayer(tablePlayers.getItems());

        //If the returned player is null it means the user cancelled out
        if (p != null) {

            //To fix a bug where there was no file to read to begin with
            //Therefore trying to add would result in a null reference exception

            if (tablePlayers.getItems() == null) {
                ObservableList<Player> player = FXCollections.observableArrayList();
                player.add(p);

                tablePlayers.setItems(player);
            } else {
                tablePlayers.getItems().add(p);
            }
        }
    }

    /**
     * Opens the multi-purpose window that enables the user to edit the current selected player
     */
    private void editSelectedPlayer() {
        Player selectedPlayer = tablePlayers.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null) {
            AlertBox.show("Notice", "Notice", "Please select a player to edit");
        } else {
            Player editedPlayer = playerWindow.editPlayer(selectedPlayer);

            //If the returned player is null it means the user cancelled edit

            if (editedPlayer != null) {
                int index = tablePlayers.getItems().indexOf(selectedPlayer);
                tablePlayers.getItems().set(index, editedPlayer);
            }
        }
    }

    /**
     * Deletes the selected player from the table view
     */
    private void deleteSelectedPlayer() {

        Player selectedPlayer = tablePlayers.getSelectionModel().getSelectedItem();

        if (selectedPlayer == null)
            AlertBox.show("Notice", "Notice", "Please select a player to delete");
        else
            tablePlayers.getItems().remove(selectedPlayer);
    }

    /**
     * The method that is called whe the save button is clicked
     * @param event
     */
    @FXML
    private void btnSave_onAction(ActionEvent event) {

        try {

            //Saves the list of players to a file

            FileHandler.saveToLocation(tablePlayers.getItems(), "players.csv");
            AlertBox.show("Notice", "Notice", "Items successfully saved!");

        } catch (Exception e ) {
            AlertBox.show("Error", "Notice", "An error occurred whilst saving file...");
            e.printStackTrace();
        }
    }
}