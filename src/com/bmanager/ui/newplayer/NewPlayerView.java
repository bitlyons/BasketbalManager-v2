package com.bmanager.ui.newplayer;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



/**
 * This class Deals with displaying the New Player Window. all user interaction is done with NewPlayerController
 */
public class NewPlayerView extends GridPane{

    //Labels for input Fields
    Label labelPlayerID = new Label("ID : ");
    Label labelFirstName = new Label("First Name :");
    Label labelLastName = new Label("Last Name :");
    Label labelDOB = new Label("Date Of Birth :");
    Label labelHeight = new Label("Height(m) :");
    Label labelPlayerType = new Label("Player Type : ");
    Label labelTeam = new Label("Team : ");

    //Input Fields
    TextField textPlayerID = new TextField();
    TextField textFirstName = new TextField();
    TextField textLastName = new TextField();
    TextField textDOB = new TextField();
    TextField textHeight = new TextField();

    //Combo boxes
    ComboBox comboPlayerType = new ComboBox();
    ComboBox comboTeams = new ComboBox();

    //Buttons
    Button buttonAddPlayer = new Button("Add Player");
    Button buttonCancel = new Button("Cancel");

    public NewPlayerView(){
        sceneLayout();
    }

    private void sceneLayout(){
        this.setHgap(10);
        this.setVgap(20);

        //add all the fields to the window, label, then textfield

        //playerID. disabled text input
        this.add(labelPlayerID, 1, 1);
        this.add(textPlayerID, 2, 1);
        textPlayerID.setDisable(true);
        textPlayerID.setMaxWidth(55);

        //First Name
        this.add(labelFirstName, 1, 2);
        this.add(textFirstName, 2, 2);

        //Last Name
        this.add(labelLastName, 1, 3);
        this.add(textLastName, 2, 3);

        //Date Of Birth, add prompt text to show how to enter the dob.
        this.add(labelDOB, 1, 4);
        this.add(textDOB, 2, 4);
        textDOB.setPromptText("DD/MM/YYYY");

        //Height
        this.add(labelHeight, 1 ,5);
        this.add(textHeight, 2, 5);

        //Type
        this.add(labelPlayerType, 1, 6);
        this.add(comboPlayerType, 2, 6);
        comboPlayerType.setMaxWidth(56);

        //Team
        this.add(labelTeam, 1, 7);
        this.add(comboTeams, 2, 7);
        comboTeams.setMaxWidth(56);

        //Buttons
        HBox buttons = new HBox(10, buttonAddPlayer, buttonCancel);
        buttonAddPlayer.setPrefHeight(34);
        buttonAddPlayer.setPrefWidth(90);
        buttonCancel.setPrefHeight(34);
        this.add(buttons,2,8);

        //Player Type

    }
}
