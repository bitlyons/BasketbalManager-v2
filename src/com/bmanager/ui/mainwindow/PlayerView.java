package com.bmanager.ui.mainwindow;

import com.bmanager.models.Player;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * This class deals with displaying all the info in relation to the viewplayers view.
 * it utilizes PlayerController for user interactions.
 */
public class PlayerView extends Pane {

    //logo
    ImageView viewLogo = new ImageView();

    //image logo that will be replaced with view teams logo, setting a default one
    Image imageLogo = new Image("com/bmanager/resources/logo.png");

    //image for options button
    ImageView viewOptions = new ImageView("com/bmanager/resources/cog.png");

    //image Search
    ImageView viewSearch = new ImageView(new Image("com/bmanager/resources/search.png"));

    //image for next/previous Team
    ImageView viewNext = new ImageView("com/bmanager/resources/right.png");
    ImageView viewPrev = new ImageView("com/bmanager/resources/left.png");

    // TableView to hold players
    TableView<Player> tablePlayers = new TableView<>();

    //Columns for the tableView
    TableColumn<Player, Integer> columnPlayerId;
    TableColumn<Player, Long> columnAge;
    TableColumn<Player, String> columnFirstName, columnLastName, columnType, columnTeam;
    TableColumn<Player, Double> columnHeight;

    //ContextMenu for right Click
    ContextMenu contextMenu = new ContextMenu();

    //MenuItems for ContextMenu
    MenuItem itemAddPlayer = new MenuItem("Add New Player");
    MenuItem itemEditPlayer = new MenuItem("Edit Player");
    MenuItem itemDeletePlayer = new MenuItem("Delete Player");


    //options menu
    MenuButton optionsMenu = new MenuButton();
    MenuItem save = new MenuItem("Save");
    MenuItem export = new MenuItem("Export To HTML");
    MenuItem saveLocation = new MenuItem("Set Save Location");
    MenuItem loadDatabase = new MenuItem(("Load Database"));
    MenuItem print = new MenuItem("Print current Team");
    MenuItem about = new MenuItem("About");
    Menu menuTheme = new Menu("Theme");

    //css radio buttons
    RadioMenuItem radioCssLight = new RadioMenuItem("Light Theme");
    RadioMenuItem radioCssDark = new RadioMenuItem("Dark Theme");

    //Buttons
    Button buttonReturn = new Button("Return to Teams");
    Button buttonNext = new Button();
    Button buttonPrev = new Button();
    Button buttonSearch = new Button();


    //constructor
    public PlayerView(){
        sceneLayout();

    }

    /** this method creates the layout used by the scene*/
    @SuppressWarnings("unchecked")
    private void sceneLayout() {

        //add logo and resize
        viewLogo.setImage(imageLogo);
        viewLogo.relocate(232,14);
        viewLogo.setFitHeight(124);
        viewLogo.setFitWidth(120);
        viewLogo.preserveRatioProperty();

        //Table Columns

        //view Players Id
        columnPlayerId = new TableColumn<>("ID");
        columnPlayerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPlayerId.setPrefWidth(73);

        //First name
        columnFirstName = new TableColumn<>("First Name");
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnFirstName.setPrefWidth(127);

        //Last Name
        columnLastName = new TableColumn<>("Last Name");
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnLastName.setPrefWidth(127);

        //Age
        columnAge = new TableColumn<>("Age");
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAge.setPrefWidth(70);

        //Height
        columnHeight = new TableColumn<>("Height");
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        columnHeight.setPrefWidth(79);

        //Type
        columnType = new TableColumn<>("Type");
        columnType.setCellValueFactory(new PropertyValueFactory<>("playerType"));
        columnType.setPrefWidth(105);

        //add all the columns to the table
        tablePlayers.getColumns().addAll(columnPlayerId,columnFirstName,columnLastName, columnAge,
                columnHeight, columnType);

        //locate the table
        tablePlayers.relocate(11,151);
        tablePlayers.prefHeight(271);
        tablePlayers.prefWidth(578);

        //right click menu
        contextMenu.getItems().addAll(itemAddPlayer,itemEditPlayer,itemDeletePlayer);

        //Save Button
        buttonReturn.relocate(232,563);
        buttonReturn.setPrefWidth(107);
        buttonReturn.setPrefHeight(35);

        //Css menu
        menuTheme.getItems().addAll(radioCssLight, radioCssDark);

        //options
        optionsMenu.getItems().addAll(save, loadDatabase, saveLocation, export, menuTheme, print, about);
        optionsMenu.setGraphic(viewOptions);
        viewOptions.setFitHeight(20);
        viewOptions.setFitWidth(20);
        optionsMenu.relocate(555,570);
        optionsMenu.setTooltip(new Tooltip("Settings Menu"));

        //previous button
        buttonPrev.setGraphic(viewPrev);
        buttonPrev.relocate(165,55);
        viewPrev.setFitWidth(40);
        viewPrev.setFitHeight(40);

        //buttonNext
        buttonNext.setGraphic(viewNext);
        buttonNext.relocate(360,55);
        viewNext.setFitWidth(40);
        viewNext.setFitHeight(40);

        //Search Button
        buttonSearch.setGraphic(viewSearch);
        viewSearch.setFitHeight(20);
        viewSearch.setFitWidth(20);
        buttonSearch.relocate(0,570);
        buttonSearch.setTooltip(new Tooltip("Search"));

        this.getChildren().addAll(viewLogo, tablePlayers, buttonReturn, optionsMenu, buttonPrev, buttonNext, buttonSearch);
    }
}
