package com.example.mytests.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytests.model.QuestionModel;
import com.example.mytests.repository.QuestionRepository;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements QuestionRepository.OnQuestionLoad,
        QuestionRepository.OnResultAdded, QuestionRepository.OnResultLoad {

    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private QuestionRepository repository ;
    private MutableLiveData<HashMap<String,Long>> resultMutableLiveData;

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData=new MutableLiveData<>();
        repository=new QuestionRepository(this, this, this);
    }

    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public void addResults(HashMap<String ,Object> resultMap){
        repository.addResults(resultMap);
    }

    public void getResults(){
        repository.getResults();
    }

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public void setTestId(String testId){
        repository.setTestId(testId);
    }

    public void setSubjectId(String subjectId){
        repository.setSubjectId(subjectId);
    }

    public void getQuestion(){
        repository.getQuestions();
    }

    public void addQuestion(String questionText, String optA, String optB, String optC, String optD, String correctOpt)
    { repository.addQuestion(questionText, optA, optB, optC, optD, correctOpt); }

    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onResultLoad(HashMap<String, Long> resultHashMap) {
        resultMutableLiveData.setValue(resultHashMap);

    }

    @Override
    public void onError(Exception e) {
        Log.e("QuestionError :","onError"+e.getMessage());
    }


}

