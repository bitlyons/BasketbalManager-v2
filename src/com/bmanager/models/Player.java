package com.bmanager.models;

//Represents each basketball player in the application
public class Player {

    //The attributes each player can have
    private int id;
	private String firstname, lastname;
	private int age;

	private double height;
	private String playerType;
	private String team;

    //Empty constructor
    public Player() {

    }

    //Alternate constructor
	public Player(int id, String firstname, String lastname, int age, double height, String playerType, String team) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;

		this.height = height;
		this.playerType = playerType;
		this.team = team;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
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

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}