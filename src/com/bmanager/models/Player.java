package com.bmanager.models;

//Represents each basketball player in the application
public class Player {

    //The attributes each player can have
    private int id;
	private String firstName, lastName, playerType;
	//TODO Store age as a D.O.B
    private int age;
	private double height;
    //TODO change team to teamID which references A team in the team table
    private  String team;

    //Empty constructor
    public Player() {

    }

    //Alternate constructor
	public Player(int id, String firstName, String lastName, int age, double height, String playerType, String team) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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