package com.bmanager.ui.viewplayers;

import com.bmanager.models.Player;
import com.bmanager.models.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This class deals with displaying all the info in relation to the viewplayers view.
 * it utilizes PlayerController for user interactions.
 */
public class PlayerView extends Pane {
    ArrayList<Player> playerDB = new ArrayList<>();
    ObservableList<Team> teamDB = FXCollections.observableArrayList();

    //logo
    ImageView viewLogo = new ImageView();

    //image logo that will be replaced with view teams logo, setting a default one
    Image imageLogo = new Image("com/bmanager/resources/logo.png");

    //image for options button
    ImageView viewOptions = new ImageView("com/bmanager/resources/cog.png");

    //image for next/previous Team
    ImageView viewNext = new ImageView("com/bmanager/resources/right.png");
    ImageView viewPrev = new ImageView("com/bmanager/resources/left.png");

    // TableView to hold players
    TableView<Player> tablePlayers = new TableView<>();

    //Columns for the tableView
    TableColumn<Player, Integer> columnPlayerId, columnAge;
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

    //Buttons
    Button buttonReturn = new Button("Return to Teams");
    Button buttonNext = new Button();
    Button buttonPrev = new Button();
    Button buttonSearch = new Button();

    //SearchField
    TextField textSearch = new TextField();

    //constructor
    public PlayerView(){
        sceneLayout();

    }

    /** this method creates the layout used by the scene*/
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

        //options
        optionsMenu.getItems().addAll(save,export,saveLocation, loadDatabase, print);
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

        this.getChildren().addAll(viewLogo, tablePlayers, buttonReturn, optionsMenu, buttonPrev, buttonNext);
    }
/* not using this atm
    protected void allPlayers(){
        //Team column
        columnTeam = new TableColumn<>("Team");
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("team")); // possibly need to rewite this
        columnTeam.setPrefWidth(81);

        //resize other columns to adjust for this column
        columnFirstName.setPrefWidth(96);
        columnLastName.setPrefWidth(97);
        columnAge.setPrefWidth(79);
        columnHeight.setPrefWidth(61);
        columnType.setPrefWidth(87);

        tablePlayers.getColumns().add(columnTeam);
    } */
}
