package com.example.mytests.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mytests.R;
import com.example.mytests.databinding.FragmentSignInBinding;
import com.example.mytests.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    FragmentSignInBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_signInFragment_to_signUpFragment);

            }
        });

        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editEmailSignIn.getText().toString();
                String pass = binding.editPasswordSignIn.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()) {

                    viewModel.signIn(email, pass);
                    Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {

                            if (firebaseUser != null) {
                                // tu spr czy uczen czy nauczyciel
                                //viewModel.setUserRole(firebaseUser.getUid());
                                Log.d("User ID: ", firebaseUser.getUid());
                                String userRole = viewModel.getUserRole();
                                //Log.d("Role: ", userRole);
                                if(userRole=="Teachers")
                                    navController.navigate(R.id.action_signInFragment_to_teacherSubjectFragment);
                                else
                                    navController.navigate(R.id.action_signInFragment_to_studentSubjectFragment);
                            }

                        }
                    });

                } else {

                    Toast.makeText(getContext(), "Please Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*binding.btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.signInWithGoogle();
                Toast.makeText(getContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                    @Override
                    public void onChanged(FirebaseUser firebaseUser) {
                        if (firebaseUser!=null){
                            //navController.navigate(R.id.action_signInFragment_to_subjectFragment);
                        }
                    }
                });

            }
        });*/

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }

}