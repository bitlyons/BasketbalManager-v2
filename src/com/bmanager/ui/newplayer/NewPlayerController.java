package com.bmanager.ui.newplayer;

import com.bmanager.models.Player;
import javafx.stage.Stage;

import java.util.Random;

/**
 * This class deals with all the controls of adding a new viewplayers in relation to NewPlayerView
 */
public class NewPlayerController {

    private NewPlayerView view;
    //Represents if the user cancelled out of the form. Therefore allowing them to exit.
    private boolean userCancelledWindow;
    private Random rand;
    private Stage window;

    //constructor
    public NewPlayerController(NewPlayerView view, Stage window){
        this.view = view;
        this.window = window;
        rand = new Random();

        view.prefHeight(395);
        view.prefWidth(309);
        buttonHandlers();
        window.setOnCloseRequest(e -> userCancelledWindow = true);
    }

    /** this method handles the user interactions with the buttons **/
    private void buttonHandlers() {
        view.buttonAddPlayer.setOnAction(e -> System.out.println("Test"));
        view.buttonCancel.setOnAction(e-> {
            userCancelledWindow = true;
            window.close();}
        );
    }


    private Player getPlayer() {

        Player player = new Player();

        player.setId(Integer.parseInt(view.textPlayerID.getText()));
        player.setFirstName(view.textFirstName.getText());
        player.setLastName(view.textLastName.getText());
        player.setAge(Integer.parseInt(view.textDOB.getText()));
        player.setHeight(Double.parseDouble(view.textHeight.getText()));
        //player.setPlayerType(view.comboPlayerType.getSelectionModel().getSelectedItem());
        //player.setTeam(cboTeams.getSelectionModel().getSelectedItem());

        return player;
    }

}
