package com.bmanager.data_access;


import com.bmanager.models.Player;
import com.bmanager.models.Team;
import com.sun.org.apache.xalan.internal.xsltc.dom.EmptyFilter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** This is a Temp class to test saving and loading the program.
 *  methods here should be moved to FileHandling**/
public class FileHandling {

    static ArrayList<Team> teamDB;
    static ArrayList<Player> playerDB;

    public FileHandling(){
    }

    //save the ArrayLists
    public static boolean save(ArrayList<Team> teamDB, ArrayList<Player> playerDB, String saveLocation) {
           try
        {
            //output the file
            FileOutputStream output = new FileOutputStream(saveLocation);
            ObjectOutputStream outputStream = new ObjectOutputStream(output);
            outputStream.writeObject(teamDB);
            outputStream.writeObject(playerDB);
            outputStream.close();
            output.close();
        }

        catch (IOException e)
        {
            System.err.println("IOException: " + e.getMessage() ); //output to terminal for debugging
            return false;
            //removing this line for now.
            //Popups.alert("Warning","Save Error\nFile/location Not found"); //popup for end user.
        }
        return true;
    }

    @SuppressWarnings("unchecked")
	public static void loadData(String loadLocation) {
        try{

            FileInputStream fileIn = new FileInputStream(loadLocation);
            ObjectInputStream streamIn = new  ObjectInputStream(fileIn);
            teamDB = (ArrayList<Team>) streamIn.readObject();
            playerDB = (ArrayList<Player>) streamIn.readObject();
            streamIn.close();
            fileIn.close();
        }
        catch(IOException e) {
            System.out.println("File Not Found");
        }

       catch(ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
   public static ArrayList<Team> teams(){
       return teamDB;
   }

    public static  ArrayList<Player> players(){
        return playerDB;
    }


    //This method works by taking in a list, then building a string from each of the players in that list with html
    //tags between each item to output it to a table.
    public static boolean outputToHtml(List<Player> players, String saveLocation){
        StringBuilder sb = new StringBuilder();

        sb.append("<html><body>These Players were outputed from BasketBall Manager<br><table border=\"1\" style=\"width:100%\">");
        sb.append("<tr><th>Player Id</th><th>Name</th><th> Age</th> <th>Height</th><th>Type</th></tr>");
        for (Player player : players) {
            sb.append("<TR><TD>");
            sb.append(player.getId()).append("</td>");
            sb.append("<td>" + player.getFirstName() + " " + player.getLastName()).append("</td>");
            sb.append("<td>" + player.getAge()).append("</td>");
            sb.append("<td>" + player.getHeight()).append("</td>");
            sb.append("<td>" + player.getPlayerType()).append("</td>");
            sb.append("</TR>");
            sb.append(System.lineSeparator());
        }
        sb.append("</table></body></html>");
        try {
            Files.write(Paths.get(saveLocation), sb.toString().getBytes());
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    /**
     * Reads a file  and creates a list that will be used to save the settings
     * @throws Exception
     * originally we were going to implement a css switcher, but ran out of time before it was implanted,
     * the seating's file still contains a reference to it, but is never saved in the program.
     */
    public static List<String> loadSettings() throws Exception {

        File file = new File("./config/settings.ini");

        //Checks if the file exists first
        if (! file.exists()) {
            return null;
        } else {
            List<String> settings = new ArrayList<>();
            List<String> lines = Files.readAllLines(file.toPath());

            //Loops through each line in the file that was read
            for (String line : lines) {

                try {

                    //Parses the file by splitting by a comma
                    String[] chunks = line.split(",");

                    //add each line to the list
                    String readIn = chunks[0];
                    settings.add(readIn);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return settings;
        }
    }


    /** this saves the settings **/
    public static void saveSettings(String saveLoacation, String cssTheme) throws IOException {
        String output = saveLoacation + "," + cssTheme + ",";

        //Saves the content of the string builder to a file
        Files.write(Paths.get("./config/settings.ini"), output.getBytes());
    }

}
