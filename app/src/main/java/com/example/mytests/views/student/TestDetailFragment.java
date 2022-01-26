package com.example.mytests.views.student;

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

import com.example.mytests.databinding.FragmentTestDetailBinding;
import com.example.mytests.model.TestModel;
import com.example.mytests.viewmodel.TestViewModel;

import java.util.List;


public class TestDetailFragment extends Fragment {

    private FragmentTestDetailBinding binding;
    private NavController navController;
    private int position;
    private TestViewModel viewModel;
    private String subjectId;
    private String testId;
    private long totalQueCount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTestDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        position = TestDetailFragmentArgs.fromBundle(getArguments()).getPosition();
        //subjectId = (TestFragmentArgs.fromBundle(getArguments()).getSubjectId());

        // subjectId jak narazie musi istnieć w bazie
        viewModel.getTestLiveData("subject").observe(getViewLifecycleOwner(), new Observer<List<TestModel>>() {
            @Override
            public void onChanged(List<TestModel> testModels) {
                TestModel test = testModels.get(position);
                binding.txtDetailFragmentTitle.setText(test.getTitle());
                //Glide.with(view)
                //        .load(test.getImage())
                //        .into(binding.imageDetailFragment);
                //binding.txtDetailFragmentDifficulty.setText(quiz.getDifficulty());
                //binding.txtNumOfQuestions.setText(String.valueOf(test.getQuestions()));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBarDetail.setVisibility(View.GONE);
                    }
                }, 2000);

                totalQueCount = 2;//test.getQuestions();
                testId = test.getTestId();
                Log.e("testId ", testId);
            }
        });

        binding.btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestDetailFragmentDirections.ActionDetailFragmentToQuestionFragment action =
                        TestDetailFragmentDirections.actionDetailFragmentToQuestionFragment();
                action.setTestId(testId);
                action.setTotalQueCount(totalQueCount);
                navController.navigate(action);
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }


}