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

import com.example.mytests.databinding.FragmentAddMemberDialogBinding;
import com.example.mytests.viewmodel.MemberViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AddMemberDialogFragment extends DialogFragment {

    private NavController navController;
    FragmentAddMemberDialogBinding binding;
    private MemberViewModel viewModel;
    private String subjectId;

    public AddMemberDialogFragment(String subjectId){
        this.subjectId = subjectId;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = FragmentAddMemberDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        //binding.dialogTextView.setText("I am Dialog's TextView");

        binding.addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memberEmail = binding.addMemberEmail.getText().toString();
                if(!memberEmail.isEmpty())
                {
                    viewModel.setMemberId(memberEmail);
                    viewModel.setSubjectId(subjectId);
                    viewModel.addMember();
                    Log.w("ADD_MEMBER", memberEmail);
                    Snackbar.make(view, "Member added successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    dismiss();
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
                .getInstance(getActivity().getApplication())).get(MemberViewModel.class);
    }

}