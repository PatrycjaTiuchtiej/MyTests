package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class UserModel {

    @DocumentId
    private String userId;
    private String role;
    private String email;


    public UserModel(String userId, String role, String email) {
        this.userId = userId;
        this.role = role;
        this.email = email;
    }

    public UserModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
