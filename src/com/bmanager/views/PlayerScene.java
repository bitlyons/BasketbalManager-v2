package com.bmanager.views;

import com.bmanager.controllers.PlayerController;
import com.bmanager.models.Player;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlayerScene {
    private ImageView logo;
    private TableView<Player> tablePlayers = new TableView();
    private TableColumn<Player, Integer> columnPlayerId, columnAge;
    private TableColumn<Player, String> columnFirstname, columnLastName, columnType, columnTeam;
    private TableColumn<Player, Double> columnHeight;
    private ContextMenu contextMenu;
    private MenuItem itemAddPlayer;
    private Button saveButton;


    public Button getSaveButton() {
        return saveButton;
    }

    public PlayerScene(PlayerController baseController, Stage primaryStage){

        setupStage(primaryStage);
    }

    public MenuItem getItemAddPlayer() {
        return itemAddPlayer;
    }

    private void setupStage(Stage primaryStage) {
        Pane layout = new Pane();
        layout.setPrefSize(620,600);

        //Add Logo
        logo = new ImageView(new Image("com/bmanager/resources/logo.png"));
        logo.relocate(232,14);
        logo.setFitHeight(124);
        logo.setFitWidth(120);
        logo.preserveRatioProperty();

        //TableView

        columnPlayerId = new TableColumn<>("ID");
        columnPlayerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPlayerId.setPrefWidth(73);

        columnFirstname = new TableColumn<>("First Name");
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnFirstname.setPrefWidth(96);

        columnLastName = new TableColumn<>("Last Name");
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnLastName.setPrefWidth(97);

        columnAge = new TableColumn<>("Age");
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAge.setPrefWidth(79);

        columnHeight = new TableColumn<>("Height");
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        columnAge.setPrefWidth(79);

        columnType = new TableColumn<>("Type");
        columnType.setCellValueFactory(new PropertyValueFactory<>("playerType"));
        columnType.setPrefWidth(87);

        //Todo fix team
        TableColumn<Player, String> columnTeam = new TableColumn<>("Team");
        columnTeam.setPrefWidth(81);


        tablePlayers.getColumns().addAll(columnPlayerId, columnFirstname, columnLastName, columnAge, columnHeight,
                                            columnType, columnTeam);
        tablePlayers.relocate(11,151);
        tablePlayers.prefHeight(271);
        tablePlayers.prefWidth(578);

        //Right Click Menu
        contextMenu = new ContextMenu();
        itemAddPlayer = new MenuItem("Add New Player");
        MenuItem itemEditPlayer = new MenuItem("Edit Player");
        MenuItem itemDeletePlayer = new MenuItem("Delete Player");
        contextMenu.getItems().addAll(itemAddPlayer, itemEditPlayer, itemDeletePlayer);

        //Save Button
        saveButton = new Button("Save changes");
        saveButton.relocate(232,563.0);

        layout.getChildren().addAll(logo, tablePlayers, saveButton);
        Scene scene = new Scene(layout);

        primaryStage.setTitle("Basketball Manager : Player View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
