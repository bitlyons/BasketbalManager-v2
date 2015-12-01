package com.bmanager.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

//Represents each basketball viewPlayers in the application
public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	//The attributes each viewPlayers can have
    private int id, team;
	private String firstName, lastName, playerType;
	private long age;
    private LocalDate dob;
	private double height;

    //Empty constructor
    public Player() {

    }

    //Alternate constructor
	public Player(int id, String firstName, String lastName, LocalDate dob, double height, String playerType, int team) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;

		this.height = height;
		this.playerType = playerType;
		this.team = team;
        generateAge();
	}

    /** calculate age based on current date **/
    //might need to move this
    public void generateAge(){
        Period age = Period.between(dob,LocalDate.now());
        this.age = age.getYears();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public long getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}