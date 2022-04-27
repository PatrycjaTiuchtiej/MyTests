package com.example.mytests.views.teacher;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mytests.databinding.FragmentAddTestDialogBinding;
import com.example.mytests.viewmodel.TestViewModel;

public class AddTestDialogFragment extends Fragment {

    private NavController navController;
    FragmentAddTestDialogBinding binding;
    private TestViewModel viewModel;
    private String subjectId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddTestDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    //}

    //@Override
    //public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //binding = FragmentAddTestDialogBinding.inflate(LayoutInflater.from(getContext()));
        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setView(binding.getRoot());

        //binding.dialogTextView.setText("I am Dialog's TextView");

        binding.addQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testTitle = binding.addTestTitle.getText().toString();
                if(!testTitle.isEmpty())
                {
                    viewModel.setSubjectId(subjectId);
                    //viewModel.addTest(testTitle);
                    Log.w("ADD_TEST", testTitle);
                    //Snackbar.make(view, "Test title added successfully", Snackbar.LENGTH_LONG)
                    //        .setAction("Action", null).show();

                    AddTestDialogFragmentDirections.ActionAddTestDialogFragmentToAddQuestionFragment action =
                            AddTestDialogFragmentDirections.actionAddTestDialogFragmentToAddQuestionFragment();

                    //action.setPosition(position);
                    navController.navigate(action);
                }
            }
        });

        //return builder.create();

       /* // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //???

        builder.setMessage(binding.)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }

}