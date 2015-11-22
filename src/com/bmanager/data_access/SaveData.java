package com.bmanager.data_access;


import com.bmanager.models.Player;
import com.bmanager.models.Team;
import java.io.*;
import java.util.ArrayList;

/** This is a Temp class to test saving and loading the program.
 *  methods here should be moved to FileHandling**/
public class SaveData {

    static ArrayList<Team> teamDB;
    static ArrayList<Player> playerDB;


    public SaveData(){
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

}
