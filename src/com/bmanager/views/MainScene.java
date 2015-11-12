package com.bmanager.views;

import com.bmanager.controllers.MainController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainScene extends Stage {

    public MainScene(MainController baseController, Stage primaryStage){

        setupStage(primaryStage);
    }

    private void setupStage(Stage primaryStage) {
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 489, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
