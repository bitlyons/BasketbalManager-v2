package com.bmanager.controllers;

import com.bmanager.models.Player;
import com.bmanager.models.Team;
import com.bmanager.ui.viewplayers.PlayerController;
import com.bmanager.ui.viewplayers.PlayerView;
import com.bmanager.ui.viewteams.TeamController;
import com.bmanager.ui.viewteams.TeamView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

//The main entry point for this javafx application
public class Main extends Application {
    public Stage window;

    //putting these here for now, might not be the best place to put them.
    public static ObservableList<Player> playerDB = FXCollections.observableArrayList();
    public static ObservableList<Team> teamDB = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
/**
        Parent root = FXMLLoader.load(getClass().getResource("/com/bmanager/ui/SceneMain.fxml"));
        primaryStage.setTitle("Basketball Manager");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/com/bmanager/resources/logo.png"));
        primaryStage.sizeToScene();

        primaryStage.show();
        primaryStage.toFront();
        **/

        TeamView view = new TeamView();
        Scene scene = new Scene(view);
        TeamController presenter = new TeamController(view,window);
        window.sizeToScene();
        window.setScene(scene);
        window.setResizable(false);
        window.setTitle("Basketball Manager : Team View");
        window.show();

        window.setOnCloseRequest(e -> {
            e.consume(); //stop java from closing the program
            exitProgram();  //now run our code instead to handle exiting the program
        });
    }

    private void exitProgram() {
        //TODO ask user what they want to do
        window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/// end of class
