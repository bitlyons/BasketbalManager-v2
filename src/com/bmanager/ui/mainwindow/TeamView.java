package com.bmanager.ui.mainwindow;

import com.bmanager.models.Team;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TeamView extends Pane {

    //Generic logo when on the Team View
    ImageView genericLogo = new ImageView(new Image("com/bmanager/resources/logo.png"));

    //image view for options button
    ImageView viewOptions = new ImageView(new Image("com/bmanager/resources/cog.png"));

    //image Search
    ImageView viewSearch = new ImageView(new Image("com/bmanager/resources/search.png"));

    // TableView to hold Teams
    TableView<Team> tableTeams = new TableView<>();

    //Columns for the table
    TableColumn<Team, Integer> columnTeamId, columnTeamMemberNumbers;
    TableColumn<Team, String> columnTeamName,columnTeamCity;

    //ContextMenu for right Click
    ContextMenu contextMenu = new ContextMenu();

    //MenuItems for ContextMenu
    MenuItem itemViewTeam = new MenuItem("View Team");
    MenuItem itemAddTeam = new MenuItem("Add New Team");
    MenuItem itemEditTeam = new MenuItem("Edit Team");
    MenuItem itemDeleteTeam = new MenuItem("Delete Team");

    //options menu
    MenuButton optionsMenu = new MenuButton();

    MenuItem save = new MenuItem("Save Database");
    MenuItem loadDatabase = new MenuItem(("Load Database ..."));
    MenuItem saveLocation = new MenuItem("Set Save Location");

    MenuItem print = new MenuItem("Print current Team");
    MenuItem about = new MenuItem("About");
    Menu menuTheme = new Menu("Theme");

    //css radio buttons
    RadioMenuItem radioCssLight = new RadioMenuItem("Light Theme");
    RadioMenuItem radioCssDark = new RadioMenuItem("Dark Theme");

    //Buttons
    Button buttonTeams = new Button("View All Players");
    Button buttonSearch = new Button();

    public TeamView(){
        sceneLayout();
    }

    @SuppressWarnings("unchecked")
    private void sceneLayout() {

        //Logo
        genericLogo.relocate(232,14);
        genericLogo.setFitHeight(124);
        genericLogo.setFitWidth(120);
        genericLogo.preserveRatioProperty();

        //Table Columns

        //view Team Id
        columnTeamId = new TableColumn<>("ID");
        columnTeamId.setCellValueFactory(new PropertyValueFactory<>("teamId"));
        columnTeamId.setPrefWidth(73);

        columnTeamName = new TableColumn<>("Team Name");
        columnTeamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        columnTeamName.setPrefWidth(260);

        columnTeamCity = new TableColumn<>("City");
        columnTeamCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnTeamCity.setPrefWidth(170);

        columnTeamMemberNumbers = new TableColumn<>("Members");
        columnTeamMemberNumbers.setCellValueFactory(new PropertyValueFactory<>("teamMemberNumber"));
        columnTeamMemberNumbers.setPrefWidth(78);

        //add all the columns to the table
        tableTeams.getColumns().addAll(columnTeamId, columnTeamName, columnTeamCity, columnTeamMemberNumbers);
        //locate the table
        tableTeams.relocate(11,151);
        tableTeams.prefHeight(271);
        tableTeams.prefWidth(578);

        //right click menu
        contextMenu.getItems().addAll(itemViewTeam, itemAddTeam, itemEditTeam, itemDeleteTeam);

        //Save Button
        buttonTeams.relocate(232,563);
        buttonTeams.setPrefWidth(107);
        buttonTeams.setPrefHeight(35);

        //Css menu
        menuTheme.getItems().addAll(radioCssLight, radioCssDark);

        //Options Button
        optionsMenu.getItems().addAll(save, loadDatabase, saveLocation, about); //menuTheme, print,
        optionsMenu.setGraphic(viewOptions);
        viewOptions.setFitHeight(20);
        viewOptions.setFitWidth(20);
        optionsMenu.relocate(555,570);
        optionsMenu.setTooltip(new Tooltip("Settings Menu"));



        //Search Button
        buttonSearch.setGraphic(viewSearch);
        viewSearch.setFitHeight(20);
        viewSearch.setFitWidth(20);
        buttonSearch.relocate(0,570);
        buttonSearch.setTooltip(new Tooltip("Search"));

        this.getChildren().addAll(genericLogo, tableTeams, buttonTeams,optionsMenu, buttonSearch);
    }
}
