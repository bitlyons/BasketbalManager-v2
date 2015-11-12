package com.bmanager.views;

import com.bmanager.controllers.PlayerController;
import com.bmanager.models.Player;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlayerScene extends Stage {


    public PlayerScene(PlayerController baseController, Stage primaryStage){

        setupStage(primaryStage);
    }

    private void setupStage(Stage primaryStage) {
        Pane layout = new Pane();
        layout.setPrefSize(620,600);

        //Add Logo
        ImageView logo = new ImageView(new Image("com/bmanager/resources/logo.png"));
        logo.relocate(232,14);
        logo.setFitHeight(124);
        logo.setFitWidth(120);
        logo.preserveRatioProperty();


        //TableView
        TableView<Player> tablePlayers = new TableView();
        TableColumn<Player, Integer> columnPlayerId = new TableColumn<>("ID");
        columnPlayerId.setPrefWidth(73);

        TableColumn<Player, String> columnFirstname = new TableColumn<>("First Name");
        columnFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnFirstname.setPrefWidth(96);

        TableColumn<Player, String> columnLastName = new TableColumn<>("Last Name");
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnLastName.setPrefWidth(97);

        TableColumn<Player, Integer> columnAge = new TableColumn<>("Age");
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAge.setPrefWidth(79);

        TableColumn<Player, Double> columnHeight = new TableColumn<>("Height");
        columnHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        columnAge.setPrefWidth(79);

        TableColumn<Player, String> columnType = new TableColumn<>("Type");
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

        Button saveButton = new Button("Save changes");
        saveButton.relocate(232,563.0);

        layout.getChildren().addAll(logo, tablePlayers, saveButton);
        Scene scene = new Scene(layout);


        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
