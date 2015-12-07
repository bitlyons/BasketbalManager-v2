package com.bmanager.ui.mainwindow;


import com.bmanager.controllers.Main;
import com.bmanager.data_access.FileHandling;
import com.bmanager.misc.AlertBox;
import com.bmanager.models.Player;
import com.bmanager.models.Team;
import com.bmanager.ui.newplayer.NewPlayerController;
import com.bmanager.ui.newteam.NewTeamController;
import com.bmanager.ui.search.SearchController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bmanager.data_access.FileHandling.loadSettings;

/**
 * This class controls the main window. it works by having two different scenes, teamScene and playerScene
 * This is also where the ArrayLists containing the both the team and players is stored.
 **/

public class MainWindowController {

    //boolean to know which view is active, if its true team view is active, false and player view is active.
    boolean teamViewActive;
    // ArrayLists to store teams and players
    private ArrayList<Team> teamDB = new ArrayList<>();
    private ArrayList<Player> playerDB = new ArrayList<>();

    // Observable Lists that will be used for the tableViews backed by the arrayLists above
    private ObservableList<Team> observableTeam = FXCollections.observableList(teamDB);
    private ObservableList<Player> observablePlayers = FXCollections.observableList(playerDB);

    //filtered list for player view
    private FilteredList<Player> filteredPlayers = new FilteredList<>(observablePlayers, p -> true);

    // These objects will be used to store the current selected player or team for editing
    private Team selectedTeam;
    private Player selectedPlayer;

    // These will be used to store the layout of both views
    private TeamView teamView = new TeamView();
    private PlayerView playerView = new PlayerView();

    // store the index of selected team and player, default to -999 till we select something
    private int teamIndex = -999, playerIndex = -999, currentTeamId;

    private String saveLocation;

    //Main Stage
    private Stage window;

    //Scenes for the main Stage
    private Scene teamScene = new Scene(teamView);
    private Scene playerScene = new Scene(playerView);
    private Main parent;


    /**
     * constructor , when its first called it will setup the window to show team
     * we pass it a copy of main so that we can utilize Application.getHostServices().showDocument()
     **/
    public MainWindowController(Stage window, Main parent) {
        this.window = window;
        this.parent = parent;
        displayTeam();
        initialLoad();

        //listeners
        initializeWatchers();
        contextMenu();
        initializeButtons();
        initializeOptions();

        //stop the window from being able to close, then run our own code that asks the user what they want to do.
        window.setOnCloseRequest(e -> {
            e.consume(); //stop java from performing the default action (stop it closing the program)
            exitProgram();  //now run our code instead to handle exiting the program
        });
    }


    /**
     * this method is used to switch the view to the team view
     **/
    private void displayTeam() {
        // teamDB.forEach(p -> p.calculateTeamNumbers(playerDB));
        teamViewActive = true;
        window.setScene(teamScene);
        teamView.tableTeams.setItems(observableTeam);
        window.setTitle("Basketball Manager : Team View");
        window.sizeToScene();
        window.setResizable(false);
        window.show();
        //sets the number of players in a team
        generateTeamNumbers();
    }

    /**
     * this method is used to switch the view to the player view
     **/
    private void displayPlayer() {
        teamViewActive = false;
        window.setScene(playerScene);
        playerView.tablePlayers.setItems(observablePlayers);
        window.setTitle("Basketball Manager : All Players View");
        window.show();
        //This checks the ages of the players are all correct
        playerDB.forEach(Player::generateAge);
        playerView.viewLogo.setImage(playerView.imageLogo);
    }


    /**
     * This method deals with the right click menu
     **/
    private void contextMenu() {
        //TeamView Context Menu
        //Using lambda statements to set an on action listener
        teamView.itemViewTeam.setOnAction(e -> viewTeam());
        teamView.itemAddTeam.setOnAction(e -> addNewX());
        teamView.itemEditTeam.setOnAction(e -> editSelectedX());
        teamView.itemDeleteTeam.setOnAction(e -> deleteSelectedX());

        //The context menu will show at the location where the mouse was clicked
        teamView.tableTeams.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                teamView.contextMenu.show(teamView.tableTeams, e.getScreenX(), e.getScreenY());
            else
                teamView.contextMenu.hide();
            //double click open team... putting this here since we already have a listener
            if (e.getClickCount() == 2) viewTeam();
        });

        //PlayerView Context Menu
        playerView.itemAddPlayer.setOnAction(e -> addNewX());
        playerView.itemEditPlayer.setOnAction(e -> editSelectedX());
        playerView.itemDeletePlayer.setOnAction(e -> deleteSelectedX());

        playerView.tablePlayers.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY)
                playerView.contextMenu.show(playerView.tablePlayers, e.getScreenX(), e.getScreenY());
            else
                playerView.contextMenu.hide();
        });
    }

    /**
     * This method adds listeners to all the buttons on both views
     **/
    private void initializeButtons() {
        teamView.buttonTeams.setOnAction(e -> displayPlayer());

        //Button that returns to the Team View
        playerView.buttonReturn.setOnAction(e -> displayTeam());

        //Button to go to the next team
        playerView.buttonNext.setOnAction(e -> nextTeamId());

        //Button to go to the next team
        playerView.buttonPrev.setOnAction(e -> prevTeamId());

        teamView.buttonSearch.setOnAction(e -> search());
        playerView.buttonSearch.setOnAction(e -> search());

    }

    /**
     * This method copies the current selected item and stores the index of it so we can edit it
     **/
    private void initializeWatchers() {
        //Team Table
        teamView.tableTeams.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            selectedTeam = newValue;
            teamIndex = teamView.tableTeams.getSelectionModel().getSelectedIndex();
        });

        //Player Table
        playerView.tablePlayers.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            selectedPlayer = newValue;
            playerIndex = playerView.tablePlayers.getSelectionModel().getSelectedIndex();
        });
    }

    /**
     * controls the options menu
     **/
    private void initializeOptions() {
        //save option
        playerView.save.setOnAction(e -> saveData());
        teamView.save.setOnAction(e -> saveData());

        //exports current team to html
        playerView.export.setOnAction(e -> exportToHTML());

        //pick save location
        playerView.saveLocation.setOnAction(e -> setSaveLocation());
        teamView.saveLocation.setOnAction(e -> setSaveLocation());


        //load database
        playerView.loadDatabase.setOnAction(e -> loadDatabase());
        teamView.loadDatabase.setOnAction(e -> loadDatabase());

        //about
        playerView.about.setOnAction(e -> about());
        teamView.about.setOnAction(e -> about());

    }


    /**Context Menu Methods **/

    /**
     * show only the clicked Team
     **/
    private void viewTeam() {
        if (teamIndex == -999) {
            AlertBox.show("Error", "You have not Selected a team to view", "Nothing to View");
        } else {
            displayPlayer();
            currentTeamId = teamDB.get(teamIndex).getTeamId();
            filteredPlayers.setPredicate(player -> player.getTeam() == teamIndex);
            changeTeam();
        }
    }

    /**
     * This method calls the new team and new Player window
     **/

    private void addNewX() {
        //add new team
        if (teamViewActive) {
            NewTeamController newTeam = new NewTeamController(teamDB);
            //Show the window then wait before we try and add the returned team
            newTeam.window.showAndWait();
            if (!newTeam.userExited()) observableTeam.add(newTeam.getTeam());
        }

        //add new player
        else {
            try {
                if (teamDB.size() == 0) throw new Exception("No teams in database");
                NewPlayerController newPlayer = new NewPlayerController(teamDB, playerDB);
                newPlayer.window.showAndWait();
                if (!newPlayer.userExited()) {
                    observablePlayers.add(newPlayer.getPlayer());
                    filteredPlayers.setPredicate(player -> true); //(return to all players)
                }
            } catch (Exception e) {
                AlertBox.show("Error", e.getMessage(), "Try adding some teams first");
            }
        }
    }

    /**
     * this method  sends the selected team or player to the new team/Player window for editing
     **/
    private void editSelectedX() {
        //Edit team
        if (teamViewActive) {
            if (teamIndex == -999) {
                AlertBox.show("Error", "You have not Selected a team to edit", "Nothing to edit");
            } else {
                NewTeamController editTeam = new NewTeamController(teamDB);
                editTeam.editTeam(selectedTeam);
                editTeam.window.showAndWait();
                if (!editTeam.userExited()) observableTeam.set(teamIndex, editTeam.getTeam());
            }
            //edit player
        } else {
            if (playerIndex == -999) {
                AlertBox.show("Error", "You have not Selected a Player to edit", "Nothing to edit");
            } else {
                NewPlayerController editPlayer = new NewPlayerController(teamDB, playerDB);
                editPlayer.editPlayer(selectedPlayer);
                editPlayer.window.showAndWait();
                if (!editPlayer.userExited())
                    observablePlayers.set(getPlayerIndex(selectedPlayer.getId()), editPlayer.getPlayer());
            }
        }
    }

    /**
     * this method just deletes the currently selected team or player
     **/
    private void deleteSelectedX() {
        String title;
        if (teamViewActive) title = "Team";
        else title = "Player";

        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setTitle("Delete " + title + "?");
        warning.setContentText("Are you sure you want to delete this " + title);

        Optional<ButtonType> result = warning.showAndWait();
        //if the user says yes, delete it otherwise close this dialog window.
        if (result.get() == ButtonType.OK) {
            if (teamViewActive) observableTeam.remove(teamView.tableTeams.getSelectionModel().getSelectedItem());
            else observablePlayers.remove(getPlayerIndex(selectedPlayer.getId()));
        } else {
            warning.close();
        }
    }


    /**
     * The following two methods control the next and previous buttons on the player view
     **/

    private void nextTeamId() {
        //find size of teamDb
        int teamSize = teamDB.size();
        teamIndex++;
        if (teamIndex >= teamSize || teamIndex < 0) {
            playerView.tablePlayers.setItems(observablePlayers);
            playerView.viewLogo.setImage(playerView.imageLogo);

            window.setTitle("Basketball Manager : All Players");
            //since we are adding at the start of the method, the next time we press the button it should be at 0, the first item in the list.
            this.teamIndex = -1;
        } else {
            currentTeamId = teamDB.get(teamIndex).getTeamId();
            changeTeam();
        }
    }


    private void prevTeamId() {
        //find size of teamDb
        int teamSize = teamDB.size();
        teamIndex--;
        if (teamIndex >= teamSize || teamIndex < 0) {
            playerView.tablePlayers.setItems(observablePlayers);
            playerView.viewLogo.setImage(playerView.imageLogo);

            window.setTitle("Basketball Manager : All Players");
            //since we are taking away at the start of the method, the next time we press the button it should be the last index in the list
            this.teamIndex = teamSize;
        } else {
            currentTeamId = teamDB.get(teamIndex).getTeamId();
            changeTeam();
        }
    }

    private void changeTeam() {
        filteredPlayers.setPredicate(player -> player.getTeam() == currentTeamId);
        window.setTitle("Basketball Manager : " + teamDB.get(teamIndex).getTeamName() + " Players");

        // switching from independent PlayerViewController, to merged controller made the following not work
        // playerView.viewLogo.setImage(new Image(teamDB.get(teamIndex).getLogoUrl()));
        // it give a Exception in thread "JavaFX Application Thread" java.lang.IllegalArgumentException: Invalid URL

        String logoURL = teamDB.get(teamIndex).getLogoUrl();
        try {
            playerView.viewLogo.setImage(new Image(logoURL)); //should still work for web urls
        } catch (IllegalArgumentException e) {
            playerView.viewLogo.setImage(new Image(new File(logoURL).toURI().toString())); //should work for local urls
        }
        if (logoURL.equalsIgnoreCase("")) playerView.viewLogo.setImage(playerView.imageLogo);
        playerView.tablePlayers.setItems(filteredPlayers);//used to refresh the table view

    }

    /**
     * options menu methods
     **/
    private void saveData() {
        FileHandling.save(teamDB, playerDB, saveLocation);
    }


    /**
     * this method exports the current players in the player view to a html document
     **/
    private void exportToHTML() {
        List<Player> players = filteredPlayers;
        DirectoryChooser outputLocation = new DirectoryChooser();
        outputLocation.setInitialDirectory(new File("./"));
        outputLocation.setTitle("Choose Output Location");
        String location = outputLocation.showDialog(window).toString() + "/players.HTML";

        try {
            Boolean saved = FileHandling.outputToHtml(players, location);
            System.out.println(location);
            if (saved == false) throw new IOException();
        } catch (IOException e) {
            AlertBox.show("ERROR", "Error", "File Not Saved!");
        }
    }


    /**
     * this method lets the user set where to save the database file, it auto sets the file name to database.dat
     **/
    private void setSaveLocation() {
        DirectoryChooser databaseDir = new DirectoryChooser();
        databaseDir.setTitle("Pick Location To Save The Database");
        checkIfDefaultFolderExists();
        databaseDir.setInitialDirectory(new File("./database/"));
        try {
            saveLocation = databaseDir.showDialog(window).toString() + "/database.dat";
        } catch (Exception e) {
            //Nothing we don't save the location if user exits
        }
    }


    /**
     * this method lets the user select a .dat file and then trys to load it into the arrays
     **/
    private void loadDatabase() {
        FileChooser databasePicker = new FileChooser();
        databasePicker.setTitle("Load Database");

        FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("Database file (dat", "*.DAT", "*.dat");
        databasePicker.getExtensionFilters().add(filterPNG);
        checkIfDefaultFolderExists();
        databasePicker.setInitialDirectory(new File("./database/"));

        try {
            File loadLocation = databasePicker.showOpenDialog(window);
            FileHandling.loadData(loadLocation.toString());
            this.teamDB = FileHandling.teams();
            this.playerDB = FileHandling.players();
            observableTeam = FXCollections.observableList(teamDB);
            observablePlayers = FXCollections.observableList(playerDB);
            filteredPlayers = new FilteredList<>(observablePlayers, p -> true);
            displayTeam();
        } catch (Exception e) {
            //TODO alert user load failed possibly
        }

    }

    /**
     * this method does a simple check to see if the default folder for the database exits, if it does not it creates it
     * this is to avoid a IOException in the case of it not existing when trying to save
     */
    private void checkIfDefaultFolderExists() {
        File theDir = new File("./database/");
        if (!theDir.exists()) {
            try {
                boolean created = theDir.mkdir();
                if (!created) throw new IOException();
            } catch (IOException e) {
                System.out.println("Failed to create dir");
            }
        }
    }

    /**
     * This method is a workaround to getting the player id when using a filtered list
     **/
    private int getPlayerIndex(int playerID) {
        for (int i = 0; i < playerDB.size(); i++) {

            if (playerDB.get(i).getId() == playerID) return i;
        }
        return -1;
    }


    /**
     * this method runs the calculateTeamNumbers() method in Team on each team object in the list
     * We pass it the playerDB so that it can cycle through the playersDB to get the number of players
     * that are on that team
     **/
    private void generateTeamNumbers() {
        teamDB.forEach(p -> p.calculateTeamNumbers(playerDB));
        //force table to notice when there is a change to team numbers by removing then re adding the column
        teamView.tableTeams.getColumns().remove(teamView.columnTeamMemberNumbers);
        teamView.tableTeams.getColumns().add(teamView.columnTeamMemberNumbers);

    }

    /**
     * This method is responsible for the search button
     **/
    private void search() {
        displayPlayer();
        SearchController search = new SearchController();
        search.window.showAndWait();
        if (!search.exited()) {
            String searchOption = search.getSearchOption();
            String searchValue = search.getSearchValue();

            switch (searchOption) {
                case "First Name":
                    filteredPlayers.setPredicate(p -> p.getFirstName().toLowerCase().contains(searchValue));
                    break;
                case "Last Name":
                    filteredPlayers.setPredicate(p -> p.getLastName().toLowerCase().contains(searchValue));
                    break;
                case "Age":
                    filteredPlayers.setPredicate(p -> p.getAge() == Long.parseLong(searchValue));
                    break;
                case "Height":
                    filteredPlayers.setPredicate(p -> p.getHeight() == Double.parseDouble(searchValue));
                    break;
            }


        } else {
            filteredPlayers.setPredicate(p -> true);
        }
        playerView.tablePlayers.setItems(filteredPlayers);

    }


    /**
     * This Class gets called when the user try's to exit the program
     **/
    private void exitProgram() {
        Alert closeWindow = new Alert(Alert.AlertType.CONFIRMATION);
        closeWindow.setTitle("Exiting BasketBall Manager");
        closeWindow.setHeaderText("It looks like you are trying to exit the program\nwould you like to save the database?");
        closeWindow.setContentText("Save will save the database, Discard will discard all changes");

        ButtonType buttonYes = new ButtonType("Save and Exit");
        ButtonType buttonNo = new ButtonType("Discard And Exit");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        closeWindow.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

        Optional<ButtonType> result = closeWindow.showAndWait();
        if (result.get() == buttonYes) {
            try {
                FileHandling.saveSettings(saveLocation, "emptyfornow");
            } catch (Exception e) {
                AlertBox.show("Unable to save", "Error", "Unable to save the settings file");
            }
            saveData();
            window.close();
        } else if (result.get() == buttonNo) {
            window.close();
        }
    }


    /**
     * This method is called when the program is first started, it trys to load the settings file to get the location of
     * the database, it then proceeds to load the lists into memory
     */
    private void initialLoad() {
        //load settings first
        try {
            List<String> settings = loadSettings();
            if (settings != null) this.saveLocation = settings.get(0);
            else saveLocation = "./database/database.dat";
            //todo write css switch

        } catch (Exception e) {
            AlertBox.show("Error", "Error", "Unable to load settings");
            System.err.print(e);
        }

        try {
            FileHandling.loadData(saveLocation);
            this.teamDB = FileHandling.teams();
            this.playerDB = FileHandling.players();
            observableTeam = FXCollections.observableList(teamDB);
            observablePlayers = FXCollections.observableList(playerDB);
            filteredPlayers = new FilteredList<>(observablePlayers, p -> true);
            displayTeam();
        } catch (Exception e) {
            AlertBox.show("Error", "Error", "Unable to load saved Database");
            //todo show dialog that asked the user if they find database or create new one
        }
    }


    /**
     * this method just shows a new window with basic info about the program
     * As it contains two links that will be used to open a new web browser window
     * a copy of main was needed to utilize Application.getHostServices().showDocument()
     * as this class does not extend Application. Application itself cannot be instanced, and getHostServices
     * is not a static class, so this was the easiest work around.
     */
    public void about() {
        Stage aboutWindow = new Stage();
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        Text aboutText = new Text();

        aboutText.setText("This program was made by Vytautas Ziedelis & Brendan Lyons\n" +
                "as part of the Data Structures module group project.\n" +
                "it is a continuation of the solo project by Vytautas Ziedelis\n\n" +
                "It is made using JavaFX 8 for its graphical user interface\n");

        //Links to both our githubs
        Hyperlink lyonsGithub = new Hyperlink("Brendan's GitHub Page");
        Hyperlink ziedelisGithub = new Hyperlink("Vytautas's Github Page");


        HBox github = new HBox(40);
        github.getChildren().addAll(lyonsGithub, ziedelisGithub);
        Button closeButton = new Button("Close");


        layout.getChildren().addAll(aboutText, github, closeButton);
        aboutWindow.setScene(new Scene(layout));
        aboutWindow.show();

        //getHostServices() hardcode the web browsers that each operating system can have, if a linux user does not have firefox
        //it will result in an error, so we are just disabling it for now.
        lyonsGithub.setOnAction(e -> {
            if (!System.getProperty("os.name").startsWith("linux"))
                parent.getHostServices().showDocument("https://github.com/bitlyons");
        });

        ziedelisGithub.setOnAction(e -> {
            if (!System.getProperty("os.name").startsWith("linux"))
                parent.getHostServices().showDocument("https://github.com/snufas");
        });

        closeButton.setOnAction(e -> aboutWindow.close());

    }

}

