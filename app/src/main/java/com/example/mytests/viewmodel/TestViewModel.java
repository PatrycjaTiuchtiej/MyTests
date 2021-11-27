package com.example.mytests.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytests.model.TestModel;
import com.example.mytests.repository.TestRepository;

import java.util.List;

public class TestViewModel extends ViewModel implements TestRepository.onFirestoreTaskComplate {

    private MutableLiveData<List<TestModel>> testLiveData = new MutableLiveData<>();

    private TestRepository repository = new TestRepository(this);


    public TestViewModel() {

    }

    public void setSubjectId(String subjectId){
        repository.setSubjectId(subjectId);
    }


    public MutableLiveData<List<TestModel>> getTestLiveData(String subjectId ) {
        this.setSubjectId(subjectId);
        repository.getTestData();
        return testLiveData;
    }

    @Override
    public void testDataLoaded(List<TestModel> testModels) {

        testLiveData.setValue(testModels);

    }

    @Override
    public void onError(Exception e) {

        Log.e("TestERROR","onError: "+e.getMessage());

    }
}

