package com.bmanager.controllers;

import com.bmanager.ui.mainwindow.MainWindowController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


//The main entry point for this javafx application
public class Main extends Application {
    public Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        //adds an icon for the window
        window.getIcons().add(new Image("com/bmanager/resources/logo.png"));

       @SuppressWarnings("unused")
       MainWindowController controller = new MainWindowController(window);

        window.setOnCloseRequest(e -> {
            e.consume(); //stop java from performing the default action (stop it closing the program)
            exitProgram();  //now run our code instead to handle exiting the program
        });
    }//

    /** This Class gets called when the user try's to exit the program **/
    private void exitProgram() {
        //TODO ask user what they want to do
        window.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
/// end of class
