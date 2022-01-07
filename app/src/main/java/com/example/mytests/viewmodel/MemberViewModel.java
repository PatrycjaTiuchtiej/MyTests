package com.example.mytests.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mytests.model.UserModel;
import com.example.mytests.repository.MemberRepository;

import java.util.List;

public class MemberViewModel extends ViewModel implements MemberRepository.onFirestoreTaskComplate {

    private MutableLiveData<List<UserModel>> memberLiveData = new MutableLiveData<>();

    private MemberRepository repository = new MemberRepository(this);


    public MemberViewModel() {

    }

    public void setSubjectId(String subjectId){
        repository.setSubjectId(subjectId);
    }

    public void setMemberId(String email){
        repository.setMemberId(email);
    }

    public void addMember(/*String subjectId, String memberId*/){
        repository.addMember(/*subjectId, memberId*/);
    }

    public void deleteMember(String subjectId, String memberId){
        repository.deleteMember(/*subjectId, memberId*/);
    }

    public MutableLiveData<List<UserModel>> getMemberLiveData(List<String> members) {
        //this.setSubjectId(subjectId);
        repository.getMemberData(members);
        return memberLiveData;
    }

    @Override
    public void memberDataLoaded(List<UserModel> memberModels) {

        memberLiveData.setValue(memberModels);

    }

    @Override
    public void onError(Exception e) {

        Log.e("MemberERROR","onError: "+e.getMessage());

    }
}


