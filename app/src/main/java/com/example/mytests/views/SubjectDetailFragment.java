package com.example.mytests.views;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mytests.databinding.FragmentSubjectDetailBinding;
import com.example.mytests.model.SubjectModel;
import com.example.mytests.viewmodel.SubjectViewModel;

import java.util.List;


public class SubjectDetailFragment extends Fragment {

    private FragmentSubjectDetailBinding binding;
    private NavController navController;
    private int position;
    private SubjectViewModel viewModel;
    private String subjectId;
    private long totalQusCount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSubjectDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        position = SubjectDetailFragmentArgs.fromBundle(getArguments()).getPosition();

        viewModel.getSubjectLiveData().observe(getViewLifecycleOwner(), new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> subjectModels) {
                SubjectModel subject = subjectModels.get(position);
                binding.txtSubjectDetailFragmentTitle.setText(subject.getName());
                //Glide.with(view)
                //        .load(subject.getImage())
                //        .into(binding.imageDetailFragment);
                //binding.txtDetailFragmentDifficulty.setText(quiz.getDifficulty());
                //binding.txtDetailsFragmentQuestions.setText(String.valueOf(quiz.getQuestions()));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBarDetail.setVisibility(View.GONE);
                    }
                }, 2000);

                //totalQusCount = subject.getQuestions();
                subjectId = subject.getSubjectId();
                Log.e("subjectId ", subjectId);
            }
        });

        binding.btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubjectDetailFragmentDirections.ActionSubjectDetailFragmentToTestFragment action =
                        SubjectDetailFragmentDirections.actionSubjectDetailFragmentToTestFragment();
                action.setSubjectId(subjectId);
                Log.e("subjectId ", subjectId);
                //action.setTotalQueCount(totalQusCount);
                action.setPosition(position);
                navController.navigate(action);
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(SubjectViewModel.class);
    }


}
