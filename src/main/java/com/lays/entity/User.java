package com.lays.entity;

public class User {
    private Integer id;
    private String name;
    private String lastname;
    private String login;
    private String password;
    private String photoUrl;

    public User() {}

    public User(Integer id, String name, String lastname, String login, String password, String photoUrl) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.photoUrl = photoUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
