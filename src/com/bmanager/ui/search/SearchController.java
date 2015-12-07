package com.bmanager.ui.search;

import com.bmanager.misc.AlertBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.regex.Pattern;


public class SearchController {

    public Stage window = new Stage();
    private SearchView view = new SearchView();
    private boolean exited = false;
    private String searchValue, searchTerm;

    //constrictor
    public SearchController() {
        setupWindow();
        setupCombo();
        initializeButtons();
        window.setOnCloseRequest(e -> exited = true);
    }

    //add listeners to the buttons
    private void initializeButtons() {
        view.buttonSubmit.setOnAction(e -> submit());
        view.buttonCancel.setOnAction(e-> {
            exited = true;
            window.close();
        });
    }


    //creates the wuindow for the search stage.
    private void setupWindow() {

        Scene scene = new Scene(view, 300, 100);
        window.setTitle("Search");
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL); //stop user from interacting with other windows
    }

    //populates the combo box
    private void setupCombo(){
        view.searchTerm.getItems().addAll("First Name", "Last Name", "Age", "Height");
        view.searchTerm.setValue(view.searchTerm.getItems().get(0));
    }


    //when the user presses submit this method is run
    private void submit() {
        searchValue = view.textSearchTerm.getText();
        searchTerm = view.searchTerm.getValue().toString();

        if(searchTerm.equals("Age") && ! Pattern.matches("\\d{2}", searchValue))
            AlertBox.show("Error", "Incorrect Value entered for age", "Age may only contain  2 digits  eg: as 05 or 19");
        else if(searchTerm.equals("Height") && ! Pattern.matches("\\d+(\\.\\d+)?", searchValue))
            AlertBox.show("Error", "Incorrect Value entered for Height", "Height must be a decimal place number eg 1.9");
        else window.close();
    }


    public Boolean exited(){
        return exited;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getSearchOption() {
        return searchTerm;
    }
}