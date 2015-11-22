package com.bmanager.data_access;

import com.bmanager.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class FileHandler {

    /**
     * Reads a file and converts the contents into an observable list of the Player object
     * @param file The file to read from
     * @return An observable list array
     * @throws Exception
     */
    //Needs to be re written
    public static ObservableList<Player> loadFromFile(File file) throws Exception {

        ObservableList<Player> players = FXCollections.observableArrayList();

        //Checks if the file exists first
        if (! file.exists()) {
            return null;
        } else {

            List<String> lines = Files.readAllLines(file.toPath());

            //Loops through each line in the file that was read
            for (String line : lines) {

                try {

                    //Parses the csv file by splitting by a comma
                    String[] chunks = line.split(",");

                    //Each item in the chunks array represents a certain attribute for the viewplayers
                    int id = new Integer(chunks[0]);
                    String firstname = chunks[1];
                    String lastname = chunks[2];
                    int age = new Integer(chunks[3]);

                    double height = new Double(chunks[4]);
                    String type = chunks[5];
                    int team = new Integer(chunks[3]);

                    //Uses the data read to create a new instance of a viewplayers
                    Player p = new Player(id, firstname, lastname, LocalDate.now(), height, type, team); //fix this

                    //Adds the viewplayers to the observable list
                    players.add(p);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return players;
    }

    /**
     * Saves a list of players to a specified file
     * @param players The list of players to save
     * @param saveLocation The location to save the items to
     * @throws IOException
     */
    public static void saveToLocation(List<Player> players, String saveLocation) throws IOException {

        StringBuilder sb = new StringBuilder();

        //For each viewplayers, create a CSV representation of the object
        for (Player player : players) {
            sb.append(player.getId()).append(",");
            sb.append(player.getFirstName()).append(",");
            sb.append(player.getLastName()).append(",");
            sb.append(player.getAge()).append(",");
            sb.append(player.getHeight()).append(",");
            sb.append(player.getPlayerType()).append(",");
            sb.append(player.getTeam());
            sb.append(System.lineSeparator());
        }

        //Saves the content of the string builder to a file
        Files.write(Paths.get(saveLocation), sb.toString().getBytes());
    }
}