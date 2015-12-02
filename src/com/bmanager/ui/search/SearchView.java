package com.bmanager.ui.search;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class SearchView extends GridPane{

    Text info = new Text("select your search criteria below");
    ComboBox searchTerm = new ComboBox();

    //Text Fields
    TextField textSearchTerm = new TextField();

    Button buttonSubmit = new Button("Submit");
    Button buttonCancel = new Button("Cancel");

    //HBox for buttons
    HBox buttonsAdd = new HBox(20);
    HBox buttonsSub = new HBox(20);

    Node top;

    public SearchView(){
        sceneLayout();
    }


    private void sceneLayout(){
        this.add(info, 2,1);
        this.add(searchTerm, 1, 2);
        this.add(textSearchTerm,2,2);


        this.add(buttonsAdd, 2,6);

        buttonsSub.getChildren().addAll(buttonSubmit,buttonCancel);
        this.add(buttonsSub,2,7);

    }
}
