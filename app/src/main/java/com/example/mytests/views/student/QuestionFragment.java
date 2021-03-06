package com.example.mytests.views.student;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mytests.R;
import com.example.mytests.databinding.FragmentQuestionBinding;
import com.example.mytests.model.QuestionModel;
import com.example.mytests.viewmodel.QuestionViewModel;

import java.util.HashMap;
import java.util.List;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private FragmentQuestionBinding binding;
    private QuestionViewModel viewModel;
    private NavController navController;
    private String testId;
    private long totalQuestion;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswer = 0;
    private int correctAnswer = 0;
    private int wrongAnswer = 0;

    private String answer = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        testId = (QuestionFragmentArgs.fromBundle(getArguments()).getTestId());
        totalQuestion = QuestionFragmentArgs.fromBundle(getArguments()).getTotalQueCount();

        viewModel.setTestId(testId);
        viewModel.getQuestion();

        binding.btnOptionA.setOnClickListener(this);
        binding.btnOptionB.setOnClickListener(this);
        binding.btnOptionC.setOnClickListener(this);
        binding.btnOptionD.setOnClickListener(this);
        binding.btnNextQuestion.setOnClickListener(this);

        Log.e("TestIdQuestionFragment:", testId);


        binding.btnCloseQuize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_questionFragment_to_resultFragment);
            }
        });

        loadData();
    }

    private void loadData() {
        enableOptions();
        loadQuestion(1);
    }

    private void enableOptions() {

        binding.btnOptionA.setVisibility(View.VISIBLE);
        binding.btnOptionB.setVisibility(View.VISIBLE);
        binding.btnOptionC.setVisibility(View.VISIBLE);
        binding.btnOptionD.setVisibility(View.VISIBLE);

        // enable buttons,hide feedback tv,hide nextQuiz btn
        binding.btnOptionA.setEnabled(true);
        binding.btnOptionB.setEnabled(true);
        binding.btnOptionC.setEnabled(true);
        binding.btnOptionD.setEnabled(true);

        binding.txtAnsFeedbackTv.setVisibility(View.INVISIBLE);
        binding.btnNextQuestion.setVisibility(View.INVISIBLE);
    }

    private void loadQuestion(int i) {

        currentQueNo = i;
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                binding.txtQuizQuestionTv.setText(String.valueOf(currentQueNo)+ ") "+questionModels.get(i - 1).getText());
                binding.btnOptionA.setText(questionModels.get(i - 1).getOptionA());
                binding.btnOptionB.setText(questionModels.get(i - 1).getOptionB());
                binding.btnOptionC.setText(questionModels.get(i - 1).getOptionC());
                binding.btnOptionD.setText(questionModels.get(i - 1).getOptionD());
                binding.txtAnsFeedbackTv.setText(questionModels.get(i - 1).getCorrectOption());
                timer = 20;//questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i - 1).getCorrectOption();

                binding.txtQuestionNumberTv.setText(String.valueOf(currentQueNo));
                startTimer();
            }
        });

        canAnswer = true;
    }

    //progressbar ??n zamanlay??c??s??n?? ayarl??youruz
    private void startTimer() {

        binding.txtCountTimeQuiz.setText(String.valueOf(timer));
        binding.progressBarQuizCount.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {

            @Override
            public void onTick(long l) {
                binding.txtCountTimeQuiz.setText(l / 1000 + "");

                Long persent = l / (timer * 10);  //progressbar ilerlemesinin y??zdesel ifadesi
                binding.progressBarQuizCount.setProgress(persent.intValue());

            }

            @Override
            public void onFinish() {

                canAnswer = false;
                binding.txtAnsFeedbackTv.setText("Times Up! No answer selected");
                notAnswer++;
                showNextBtn();

            }
        }.start();
    }

    private void showNextBtn() {
        if (currentQueNo == totalQuestion) {
            binding.btnNextQuestion.setText("Submit");
            binding.btnNextQuestion.setVisibility(View.VISIBLE);
            binding.btnNextQuestion.setEnabled(true);

        } else {
            binding.btnNextQuestion.setVisibility(View.VISIBLE);
            binding.btnNextQuestion.setEnabled(true);
            binding.txtAnsFeedbackTv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_option_a:
                verifyAnswer(binding.btnOptionA);
                break;
            case R.id.btn_option_b:
                verifyAnswer(binding.btnOptionB);
                break;
            case R.id.btn_option_c:
                verifyAnswer(binding.btnOptionC);
                break;
            case R.id.btn_option_d:
                verifyAnswer(binding.btnOptionD);
                break;
            case R.id.btn_nextQuestion:
                if (currentQueNo == totalQuestion) {
                    submitResults();
                } else {
                    currentQueNo++;
                    loadQuestion(currentQueNo);
                    resetOptions();
                }
                break;
        }

    }

    private void resetOptions() {

        binding.txtAnsFeedbackTv.setVisibility(View.INVISIBLE);
        binding.btnNextQuestion.setVisibility(View.INVISIBLE);
        binding.btnNextQuestion.setEnabled(false);
        binding.btnOptionA.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edit_pg));
        binding.btnOptionB.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edit_pg));
        binding.btnOptionC.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edit_pg));
        binding.btnOptionD.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.edit_pg));
    }

    private void submitResults() {

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("correct", correctAnswer);
        resultMap.put("wrong", wrongAnswer);
        resultMap.put("notAnswered", notAnswer);

        viewModel.addResults(resultMap);

        QuestionFragmentDirections.ActionQuestionFragmentToResultFragment action =
                QuestionFragmentDirections.actionQuestionFragmentToResultFragment();
        action.setTestId(testId);
        navController.navigate(action);

    }

    private void verifyAnswer(Button btn) {

        if (canAnswer) {
            if (answer.contains(btn.getText())) {
                btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.green));
                correctAnswer++;
                binding.txtAnsFeedbackTv.setText("Correct Answer");
            } else {
                btn.setBackground(ContextCompat.getDrawable(getContext(), R.color.red));
                wrongAnswer++;
                binding.txtAnsFeedbackTv.setText("Wrong Answer \n Correct Answer : " + answer);
            }
        }
        canAnswer = false;
        countDownTimer.cancel();
        showNextBtn();

    }
}