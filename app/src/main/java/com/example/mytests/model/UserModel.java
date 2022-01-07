package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class UserModel {

    @DocumentId
    private String user_id;
    private String role;
    private String email;
    private String username;


    public UserModel(String userId, String role, String email, String username) {
        this.user_id = userId;
        this.role = role;
        this.email = email;
        this.username = username;
    }

    public UserModel() {
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
