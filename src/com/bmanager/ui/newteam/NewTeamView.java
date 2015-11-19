package com.bmanager.ui.newteam;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class NewTeamView extends GridPane{

    //image view for url
    ImageView viewUrl = new ImageView(new Image("com/bmanager/resources/reorder.png"));

    //Labels for input Fields
    Label labelTeamID = new Label("ID : ");
    Label labelTeamName = new Label("Team Name : ");
    Label labelTeamCity = new Label("Team City : ");
    Label labelTeamLogo = new Label("Team Logo Url : ");

    //TextFields
    TextField textTeamId = new TextField();
    TextField textTeamName = new TextField();
    TextField textTeamCity = new TextField();
    TextField textTeamLogo = new TextField();

    //Buttons
    Button buttonSubmit = new Button("Submit");
    Button buttonLogoURL = new Button();
    Button buttonCancel = new Button("Cancel");

    public NewTeamView(){
        sceneLayout();
    }

    private void sceneLayout() {
        this.setHgap(10);
        this.setVgap(20);

        //TeamID
        this.add(labelTeamID, 1, 1);
        this.add(textTeamId, 2, 1);
        textTeamId.setDisable(true);
        textTeamId.setMaxWidth(55);

        //TeamName
        this.add(labelTeamName, 1, 2);
        this.add(textTeamName, 2, 2);

        //TeamCity
        this.add(labelTeamCity, 1, 3);
        this.add(textTeamCity, 2, 3);

        //Team Logo
        this.add(labelTeamLogo, 1,4);
        this.add(textTeamLogo,2,4);
        this.add(buttonLogoURL,3,4);

        //skin buttonLogoURL
        buttonLogoURL.setGraphic(viewUrl);
        viewUrl.setFitWidth(15);
        viewUrl.setFitHeight(15);

        //submit/cancel buttons
        HBox buttons = new HBox(10, buttonSubmit, buttonCancel);
        buttonSubmit.setPrefHeight(34);
        buttonSubmit.setPrefWidth(90);
        buttonCancel.setPrefHeight(34);
        this.add(buttons,2,5);
    }
}
