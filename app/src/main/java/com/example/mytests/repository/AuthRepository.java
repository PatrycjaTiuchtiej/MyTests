package com.example.mytests.repository;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mytests.R;
import com.example.mytests.model.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class AuthRepository {

    private static final int RC_SIGN_IN = 123;
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<UserModel> modelUserMutableLiveData;
    private MutableLiveData<FirebaseUser> firebaseGoogleMutableLiveData;
    private FirebaseAuth firebaseAuth;
    //private MutableLiveData<Boolean> isTeacher;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userRole;


    public AuthRepository(Application application) {

        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        requestGoogleSignIn();

    }


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }


    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }


    public void signUp(String email, String pass, String role) {

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    Map<String, Object> userId = new HashMap<>();
                    userId.put("user_id", firebaseAuth.getCurrentUser().getUid());
                    db.collection(role).document(firebaseAuth.getCurrentUser().getUid())
                            .set(userId)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("ROLE", "Role successfully added!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("ROLE", "Error adding role", e);
                                }
                            });
                } else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signIn(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setUserRole(String currentUserId) {
        DocumentReference role = db.collection("Teachers").document(currentUserId);
        role.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userRole = "Teachers";
                    } else {
                        userRole = "Students";
                    }
                }
            }
        });
    }

    public String getUserRole() {
        return userRole;
    }

    public void requestGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(application, gso);


    }

    public void sigInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        application.startActivity(signInIntent);

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(signInIntent);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);

            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately

        }

    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) application, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());

                            Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    public void signOut() {
        firebaseAuth.signOut();
    }

}

