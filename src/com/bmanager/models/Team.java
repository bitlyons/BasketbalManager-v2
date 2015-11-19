package com.bmanager.models;

public class Team {
    private int teamId, teamMemberNumber;
    private String teamName, logoUrl, city;

    //constructor
    public Team(int teamId, String teamName, String logoUrl, String city){
        this.teamId = teamId;

        this.teamName = teamName;
        this.logoUrl = logoUrl;
        this.city = city;
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
