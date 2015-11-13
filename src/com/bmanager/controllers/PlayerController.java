package com.bmanager.controllers;

import com.bmanager.views.PlayerScene;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**  This Class deals with the Team, and displaying teamView   **/

public class PlayerController {
    private PlayerScene mainWindow;

    /** This method calls the classes that create the scenes **/
    public void playerView(Stage primaryStage){
        mainWindow = new PlayerScene(this, primaryStage);
        MenuItem itemAddPlayer = mainWindow.getItemAddPlayer();

        Button saveButton = mainWindow.getSaveButton();
        saveButton.setOnAction(e -> System.out.println("Test"));

        itemAddPlayer.setOnAction(e -> System.out.println("Test")); //addNewPlayer()
        //itemEditPlayer.setOnAction(e -> editSelectedPlayer());
        //itemDeletePlayer.setOnAction(e -> deleteSelectedPlayer());
       // playerWindow = new PlayerWindowController();
    }
}