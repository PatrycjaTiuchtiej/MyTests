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

import com.example.mytests.databinding.FragmentDeleteTestDialogBinding;
import com.example.mytests.viewmodel.TestViewModel;
import com.google.android.material.snackbar.Snackbar;

public class DeleteTestDialogFragment extends DialogFragment {

    private NavController navController;
    FragmentDeleteTestDialogBinding binding;
    private TestViewModel viewModel;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = FragmentDeleteTestDialogBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(binding.getRoot());

        //binding.dialogTextView.setText("I am Dialog's TextView");

        binding.deleteTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mock subject id
                String subjectId = "matma";
                String testId = "";
                //delete action
                //viewModel.deleteTest(subjectId, testId);
                Log.w("DELETE_TEST", testId);
                Snackbar.make(view, "Test deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dismiss();
            }
        });

        binding.cancelDeleteTestButton.setOnClickListener(new View.OnClickListener() {
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
                .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }

}