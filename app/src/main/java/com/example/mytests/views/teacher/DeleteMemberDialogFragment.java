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

import com.example.mytests.databinding.FragmentDeleteMemberDialogBinding;
import com.example.mytests.viewmodel.MemberViewModel;
import com.google.android.material.snackbar.Snackbar;

public class DeleteMemberDialogFragment extends DialogFragment {

    private NavController navController;
    FragmentDeleteMemberDialogBinding binding;
    private MemberViewModel viewModel;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = FragmentDeleteMemberDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        //binding.dialogTextView.setText("I am Dialog's TextView");

        binding.deleteMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mock subject id
                String subjectId = "matma";
                //delete action
                //viewModel.deleteMember(subjectId);
                Log.w("DELETE_STUDENT", subjectId);
                Snackbar.make(view, "Student deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        binding.cancelDeleteMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(MemberViewModel.class);
    }

}