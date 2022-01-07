package com.example.mytests.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytests.model.SubjectModel;
import com.example.mytests.repository.SubjectRepository;

import java.util.List;

public class SubjectViewModel extends ViewModel implements SubjectRepository.onFirestoreTaskComplate {

    private MutableLiveData<List<SubjectModel>> subjectLiveData = new MutableLiveData<>();

    private SubjectRepository repository = new SubjectRepository(this);


    public SubjectViewModel() {
        repository.getSubjectData();
    }


    public MutableLiveData<List<SubjectModel>> getSubjectLiveData() {
        return subjectLiveData;
    }

    @Override
    public void subjectDataLoaded(List<SubjectModel> subjectModels) {

        subjectLiveData.setValue(subjectModels);
    }

    public void addSubject(String name, String description){
        repository.addSubject(name, description);
    }

    public void deleteSubject(String subjectId){
        repository.deleteSubject(subjectId);
    }

    @Override
    public void onError(Exception e) {

        Log.e("SubjectERROR","onError: "+e.getMessage());

    }
}
