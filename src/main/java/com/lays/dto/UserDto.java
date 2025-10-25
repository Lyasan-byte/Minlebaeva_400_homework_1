package com.lays.dto;

public class UserDto {

    private String name;
    private String login;
    private String image;

    public UserDto(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public UserDto(String name, String login, String image) {
        this.name = name;
        this.login = login;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }


    public String getLogin() {
        return login;
    }
}