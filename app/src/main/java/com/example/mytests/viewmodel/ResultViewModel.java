package com.example.mytests.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytests.model.ResultModel;
import com.example.mytests.repository.ResultRepository;

import java.util.List;

public class ResultViewModel extends ViewModel implements ResultRepository.onFirestoreTaskComplate {

    private MutableLiveData<List<ResultModel>> resultLiveData = new MutableLiveData<>();

    private ResultRepository repository = new ResultRepository(this);


    public ResultViewModel() { }

    /*public void setSubjectId(String subjectId){
        repository.setSubjectId(subjectId);
    }*/

    public MutableLiveData<List<ResultModel>> getResultLiveData() {
        repository.getResultTestData();
        return resultLiveData;
    }

    @Override
    public void resultDataLoaded(List<ResultModel> resultModels) {

        resultLiveData.setValue(resultModels);

    }

    @Override
    public void onError(Exception e) {

        Log.e("ResultERROR","onError: "+e.getMessage());

    }
}