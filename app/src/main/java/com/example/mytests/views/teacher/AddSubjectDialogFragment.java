package com.example.mytests.views.teacher;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.mytests.databinding.FragmentAddSubjectDialogBinding;
import com.example.mytests.viewmodel.SubjectViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AddSubjectDialogFragment extends DialogFragment {

    private NavController navController;
    FragmentAddSubjectDialogBinding binding;
    private SubjectViewModel viewModel;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = FragmentAddSubjectDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        //binding.dialogTextView.setText("I am Dialog's TextView");

        binding.addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = binding.addSubjectName.getText().toString();
                String subjectDescript = binding.addSubjectDescript.getText().toString();
                if(!subjectName.isEmpty())
                {
                    viewModel.addSubject(subjectName, subjectDescript);
                    Log.w("ADD_SUBJECT", subjectName);
                    Snackbar.make(view, "Subject added successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return builder.create();

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
                .getInstance(getActivity().getApplication())).get(SubjectViewModel.class);
    }

}