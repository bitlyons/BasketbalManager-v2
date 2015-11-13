package com.bmanager.controllers;

import javafx.application.Application;
import javafx.stage.Stage;

//The class is used to start the program. it creates an instance of TeamController when it is run
// and creates the mainStage that will be used thorough the program.
public class Main extends Application {

    @Override
   public void start(Stage primaryStage) throws Exception{
        PlayerController app = new PlayerController();
        primaryStage.setResizable(false);
        app.playerView(primaryStage);

        /** when the user try's to exit the program, we will stop it from happening
         * then run our code to ensure that asks the user if they would like to save
         * the data in the database, if they want to quit without saving or cancel the request **/
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            //TODO create popup to check what the user wishes to do.
            primaryStage.close();
        });

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
