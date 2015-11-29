package com.bmanager.ui.search;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SearchView extends GridPane{
    //Labels
    Label labelSelectionChoice = new Label("Please Pick a search Category");
    Label labelSearchTerm = new Label("Search Term : ");

    //ComboBoxes
    ComboBox comboSearchTerm = new ComboBox();
    ComboBox comboSearchTerm2 = new ComboBox();
    ComboBox comboSearchTerm3 = new ComboBox();
    ComboBox comboSearchTerm4 = new ComboBox();
    ComboBox comboSearchTerm5 = new ComboBox();

    //Text Fields
    TextField textSearchTerm = new TextField();
    TextField textSearchTerm2 = new TextField();
    TextField textSearchTerm3 = new TextField();
    TextField textSearchTerm4 = new TextField();
    TextField textSearchTerm5 = new TextField();

    //Buttons
    Button buttonAddNewSearchTerm = new Button();
    Button buttonRemoveSearchTerm = new Button();
    Button buttonSubmit = new Button("Submit");
    Button buttonCancel = new Button("Cancel");

    //images for the buttons
    ImageView imageAdd = new ImageView(new Image("./com/bmanager/resources/plus26.png"));
    ImageView imageRemove = new ImageView(new Image("./com/bmanager/resources/cross41.png"));

    //HBox for buttons
    HBox buttonsAdd = new HBox(20);
    HBox buttonsSub = new HBox(20);

    public SearchView(){
        sceneLayout();
    }


    private void sceneLayout(){

        this.add(comboSearchTerm, 1, 1);
        this.add(textSearchTerm,2,1);


        buttonAddNewSearchTerm.setGraphic(imageAdd);
        imageAdd.setFitHeight(20);
        imageAdd.setFitWidth(20);

        buttonRemoveSearchTerm.setDisable(true);
        buttonRemoveSearchTerm.setGraphic(imageRemove);
        imageRemove.setFitHeight(20);
        imageRemove.setFitWidth(20);

        buttonsAdd.getChildren().addAll(buttonAddNewSearchTerm, buttonRemoveSearchTerm);

        this.add(buttonsAdd, 2,6);

        buttonsSub.getChildren().addAll(buttonSubmit,buttonCancel);
        this.add(buttonsSub,2,7);

    }
}
