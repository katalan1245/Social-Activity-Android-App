package com.example.smartness.Model;

public class User {

    public String username;
    public String password;
    public String id;
    public String type;

    public User(String username, String password, String  id, String type){
        this.username = username;
        this.password = password;
        this.id = id;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
