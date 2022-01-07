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

import com.example.mytests.databinding.FragmentAddQuestionBinding;
import com.example.mytests.viewmodel.QuestionViewModel;
import com.example.mytests.viewmodel.TestViewModel;

public class AddQuestionFragment extends Fragment{

    private FragmentAddQuestionBinding binding;
    private NavController navController;
    private TestViewModel testViewModel;
    private QuestionViewModel questionViewModel;
    private String subjectId;
    //private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAddQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        //position = TestFragmentArgs.fromBundle(getArguments()).getPosition();
        subjectId = (TestFragmentArgs.fromBundle(getArguments()).getSubjectId());
        Log.e("subjectId ", subjectId);
        //subjectId = "angielski";
        //viewModel.setSubjectId(subjectId);

        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to dopisać losowanie kolejności odpowiedzi
                String questionText = binding.editQuestionText.getText().toString();
                String optA = binding.editOptionA.getText().toString();
                String optB = binding.editOptionB.getText().toString();
                String optC = binding.editOptionC.getText().toString();
                String optD = binding.editOptionD.getText().toString();
                String correctOpt = binding.editOptionC.getText().toString();
                if(!questionText.isEmpty() && !optA.isEmpty() && !optB.isEmpty()
                        && !optC.isEmpty() && !optD.isEmpty())
                {
                    questionViewModel.setSubjectId("");
                    questionViewModel.setTestId("");
                    questionViewModel.addQuestion(questionText, optA, optB, optC, optD, correctOpt);
                    AddQuestionFragmentDirections.ActionAddQuestionFragmentToAddQuestionFragment action =
                            AddQuestionFragmentDirections.actionAddQuestionFragmentToAddQuestionFragment();
                    navController.navigate(action);
                }
            }
        });

        binding.btnSaveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // save test
                AddQuestionFragmentDirections.ActionAddQuestionFragmentToTestFragment action =
                        AddQuestionFragmentDirections.actionAddQuestionFragmentToTestFragment();
                action.setSubjectId(subjectId);
                navController.navigate(action);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);

        //testViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
        //        .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }
}
