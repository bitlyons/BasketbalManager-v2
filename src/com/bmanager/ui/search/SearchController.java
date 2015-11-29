package com.bmanager.ui.search;


import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SearchController {

    public Stage window = new Stage();
    int numberOfsearchTerms =1;
    private SearchView view = new SearchView();

    public SearchController() {
        setupWindow();
        initializeButtons();
    }

    private void initializeButtons() {
        view.buttonAddNewSearchTerm.setOnAction(e ->{
            searchComboBox();
            numberOfsearchTerms++;
            addSearchTerm();
        });

        view.buttonRemoveSearchTerm.setOnAction(e-> {
            searchComboBox();
            numberOfsearchTerms--;
            removeSearchTerm();
        });
    }

    private void addSearchTerm() {

        if (numberOfsearchTerms < 1) numberOfsearchTerms = 1; //make sure we always have 1 search term
        switch (numberOfsearchTerms) {
            case 1: view.buttonRemoveSearchTerm.setDisable(true);

            case 2:
                view.add(view.comboSearchTerm2, 1, 2);
                view.add(view.textSearchTerm2,2,2);
                view.buttonRemoveSearchTerm.setDisable(false);
                break;
            case 3:
                view.add(view.comboSearchTerm3, 1, 3);
                view.add(view.textSearchTerm3,2,3);
                break;
            case 4:
                view.add(view.comboSearchTerm4, 1, 4);
                view.add(view.textSearchTerm4,2,4);
                break;
            case 5:
                view.add(view.comboSearchTerm5, 1, 5);
                view.add(view.textSearchTerm5,2,5);
                view.buttonAddNewSearchTerm.setDisable(true);
                break;
            }
    }


    private void removeSearchTerm() {
        if (numberOfsearchTerms < 1) numberOfsearchTerms = 1; //make sure we always have 1 search term
        switch (numberOfsearchTerms) {
            case 1:
                view.getChildren().remove(view.comboSearchTerm2);
                view.getChildren().remove(view.textSearchTerm2);
                view.buttonRemoveSearchTerm.setDisable(true);
                break;
            case 2:
                view.getChildren().remove(view.comboSearchTerm3);
                view.getChildren().remove(view.textSearchTerm3);
                break;
            case 3:
                view.getChildren().remove(view.comboSearchTerm4);
                view.getChildren().remove(view.textSearchTerm4);
                break;
            case 4:
                view.getChildren().remove(view.comboSearchTerm5);
                view.getChildren().remove(view.textSearchTerm5);
                view.buttonAddNewSearchTerm.setDisable(false);
                break;
        }
    }

    private void setupWindow(){

        Scene scene = new Scene(view, 200,200);
        window.setTitle("Search");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL); //stop user from interacting with other windows
       // window.setOnCloseRequest(e -> userQuit = true);
    }


    private void searchComboBox(){
       //TODO write this method , each time a new search term is added
        //remove the term entered in the last comboBox, then disable that comboBox
    }

}