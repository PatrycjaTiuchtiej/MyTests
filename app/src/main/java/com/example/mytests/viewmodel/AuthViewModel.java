package com.example.mytests.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mytests.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    private AuthRepository repository;


    public AuthViewModel(@NonNull Application application) {
        super(application);

        repository = new AuthRepository(application);
        currentUser = repository.getCurrentUser();
        firebaseUserMutableLiveData = repository.getFirebaseUserMutableLiveData();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void signUp(String email, String pass, String role) {
        repository.signUp(email, pass, role);
    }

    public void signIn(String email, String pass) {
        repository.signIn(email, pass);
    }

    public void setUserRole(String currentUserId) { repository.setUserRole(currentUserId); }

    public String getUserRole() { return repository.getUserRole(); }

    /*public void signInWithGoogle() {
        repository.sigInGoogle();
    }*/

    public void signOut() {
        repository.signOut();
    }
}

