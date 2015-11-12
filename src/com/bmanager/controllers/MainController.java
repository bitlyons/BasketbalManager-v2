package com.bmanager.controllers;

import com.bmanager.models.Player;
import com.bmanager.views.MainScene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**  This is The controller for the main program.   **/

public class MainController{
    private MainScene mainWindow;
    //private PlayerWindowController playerWindow;

    private TableView<Player> tablePlayers;
    private TableColumn<Player, Integer> columnId, columnAge, columnHeight;
    private TableColumn<Player, String> columnFirstname, columnLastname, columnTeam, columnType;

    /** This method calls the classes that create the scenes **/
    public void startProgram(Stage primaryStage){
        mainWindow = new MainScene(this, primaryStage);
       // playerWindow = new PlayerWindowController();
    }
}