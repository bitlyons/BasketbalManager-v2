package com.bmanager.data_access;

import com.bmanager.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileHandler {

    /**
     * Reads a file and converts the contents into an observable list of the Player object
     * @param file The file to read from
     * @return An observable list array
     * @throws Exception
     */
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

                    //Each item in the chunks array represents a certain attribute for the player
                    int id = new Integer(chunks[0]);
                    String firstname = chunks[1];
                    String lastname = chunks[2];
                    int age = new Integer(chunks[3]);

                    double height = new Double(chunks[4]);
                    String type = chunks[5];
                    String team = chunks[6];

                    //Uses the data read to create a new instance of a player
                    Player p = new Player(id, firstname, lastname, age, height, type, team);

                    //Adds the player to the observable list
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

        //For each player, create a CSV representation of the object
        for (Player player : players) {
            sb.append(player.getId()).append(",");
            sb.append(player.getFirstname()).append(",");
            sb.append(player.getLastname()).append(",");
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