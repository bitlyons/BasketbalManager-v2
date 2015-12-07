package com.bmanager.controllers;

import com.bmanager.ui.mainwindow.MainWindowController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


//The main entry point for this javafx application
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //adds an icon for the window
        primaryStage.getIcons().add(new Image("com/bmanager/resources/logo.png"));

       @SuppressWarnings("unused") //stops the compiler from complaining that controller is never used,
       MainWindowController controller = new MainWindowController(primaryStage, this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

