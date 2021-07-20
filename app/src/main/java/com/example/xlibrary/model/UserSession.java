package com.example.xlibrary.model;

public class UserSession {
    public final int uid;
    public final String username;
    public final String email;
    public final String password;
    public final int admin;
    public UserSession(int uid, String username, String email, String password, int admin){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
}
