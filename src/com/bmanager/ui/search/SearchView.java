package com.bmanager.ui.search;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SearchView extends GridPane{
    Label labelSelectionChoice = new Label("Please Pick a search Category");
    Label labelSearchTerm = new Label("Search Term : ");

    //ComboBoxes used to narrow the search
    ComboBox searchTerm1 = new ComboBox();
    ComboBox searchTerm2 = new ComboBox();
    ComboBox searchTerm3 = new ComboBox();
    ComboBox searchTerm4 = new ComboBox();

}
