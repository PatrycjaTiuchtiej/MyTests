package com.example.mytests.repository;

import androidx.annotation.NonNull;

import com.example.mytests.model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ResultRepository {
    private ResultRepository.onFirestoreTaskComplate onFirestoreTaskComplate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private String memberId;
    //private String subjectId;
    //private CollectionReference reference = db.collection("Subjects");

    public ResultRepository(ResultRepository.onFirestoreTaskComplate onFirestoreTaskComplate) {
        this.onFirestoreTaskComplate= onFirestoreTaskComplate;
    }

    /*public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }*/

    public void getResultTestData() {

        db.collection("Subjects").document("angielski")
                .collection("Tests").document("angielskiTest")
                .collection("Results").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    onFirestoreTaskComplate.resultDataLoaded(task.getResult()
                            .toObjects(ResultModel.class));

                }else {

                    onFirestoreTaskComplate.onError(task.getException());
                }

            }
        });
    }

    public void getResultStudentData() {
    }

        public interface onFirestoreTaskComplate{

        void resultDataLoaded(List<ResultModel> resultListModels);
        void onError(Exception e);
    }
}