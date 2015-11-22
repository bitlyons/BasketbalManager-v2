package com.bmanager.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
	private static final long serialVersionUID = 11L;
	
    private int teamId, teamMemberNumber;
    private String teamName, logoUrl, city;

    //constructor
    public Team(int teamId, String teamName,String city, String logoUrl){
        this.teamId = teamId;
        this.teamName = teamName;
        this.logoUrl = logoUrl;
        this.city = city;
    }

    public void calculateTeamNumbers(ArrayList<Player> playerDB){
        //calculate team member size
            List<Integer> numbers = new ArrayList<>();
            playerDB.forEach(player -> {
                if(player.getTeam() == teamId) numbers.add(0);
            });
            this.teamMemberNumber = numbers.size();
    }

    public int getTeamMemberNumber() {
        return teamMemberNumber;
    }

    public void setTeamMemberNumber(int teamMemberNumber) {
        this.teamMemberNumber = teamMemberNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

}
