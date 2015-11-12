package com.bmanager.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//The main entry point for this javafx application
public class Main extends Application {

    @Override
   public void start(Stage primaryStage) throws Exception{
        MainController app = new MainController();
        primaryStage.setTitle("Basketball Manager");

        primaryStage.setResizable(false);
        app.startProgram(primaryStage);

    }
    /* public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/com/bmanager/views/SceneMain.fxml"));


        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("/com/bmanager/resources/logo.png"));
        primaryStage.sizeToScene();

        primaryStage.show();
        primaryStage.toFront();
    } */

    public static void main(String[] args) {
        launch(args);
    }
}
/// end of class
