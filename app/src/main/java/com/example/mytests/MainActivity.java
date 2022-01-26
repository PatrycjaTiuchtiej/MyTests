package com.example.mytests;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytests.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}


/*
package com.example.mytests.views;

        import android.content.Intent;
        import android.os.Bundle;

        import androidx.appcompat.app.AppCompatActivity;

        import com.example.mytests.MainActivity;
        import com.example.mytests.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isCurrentUser();
    }

    private void isCurrentUser() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            Intent intent = new Intent(this, MainActivity.class);
            // user is steel logged in after click back
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            // No user is signed in
        }

        // całe słochanie czy zalogowany
        // https://stackoverflow.com/questions/44583834/firebase-how-to-check-if-user-is-logged-in
        // https://www.ii.pwr.edu.pl/~kopel/inz/2018.01_inz_Jopek.pdf
        // https://github.com/byrmkus/QuizApp_JavaAndroid-MVVM-Firebase
        // https://github.com/developersamuelakram/QuizApp_Firebase_MVVM/tree/master/app/src/main/java/com/example/myquizapp
        // https://github.com/daimajia/AndroidSwipeLayout

        // blog z dobrym info
        // https://myenv.net/blog/android-architecture-components-data-binding/

    }
}*/
