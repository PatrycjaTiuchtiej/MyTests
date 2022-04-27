package com.example.mytests.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mytests.R;
import com.example.mytests.databinding.FragmentSignUpBinding;
import com.example.mytests.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;


public class  SignUpFragment extends Fragment {

    private AuthViewModel viewModel;
    private NavController navController;
    FragmentSignUpBinding binding;
    String role = "Teachers";//""Students";
    //Boolean isStudent;
    //Boolean isTeacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater,container,false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_signUpFragment_to_signInFragment);

            }
        });


        /*binding.student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { isStudent = true; }
        });

        binding.teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { isTeacher = true; }
        });*/

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId)
                {
                    case R.id.student:
                        role = "Students";
                        //Snackbar.make(view, role, Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                        break;
                    case R.id.teacher:
                        role = "Teachers";
                        //Snackbar.make(view, role, Snackbar.LENGTH_LONG)
                        //        .setAction("Action", null).show();
                        break;
                }
            }
        });



        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editEmailSignUp.getText().toString();
                String pass = binding.editPasswordSignUp.getText().toString();

                if (!email.isEmpty() && !pass.isEmpty()){

                    //String role = isTeacher ? "Teachers" : "Students";
                    viewModel.signUp(email, pass, role);
                    Toast.makeText(getContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                    viewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {

                            if (firebaseUser!=null){
                                navController.navigate(R.id.action_signUpFragment_to_signInFragment);
                            }

                        }
                    });

                }else {

                    Toast.makeText(getContext(), "Please Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);


    }
}