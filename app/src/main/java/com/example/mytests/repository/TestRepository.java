package com.example.mytests.repository;

import androidx.annotation.NonNull;

import com.example.mytests.model.TestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class TestRepository {

    private onFirestoreTaskComplate onFirestoreTaskComplate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String subjectId = "Subject0";
    //private CollectionReference reference = db.collection("Subjects");

    public TestRepository(onFirestoreTaskComplate onFirestoreTaskComplate) {
        this.onFirestoreTaskComplate= onFirestoreTaskComplate;
    }

    //public void setSubjectId(String subjectId) {
    //    this.subjectId = subjectId;
    //}

    public void getTestData() {

        //reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        db.collection("Subjects").document(subjectId)
                .collection("Tests").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    onFirestoreTaskComplate.testDataLoaded(task.getResult()
                            .toObjects(TestModel.class));

                }else {

                    onFirestoreTaskComplate.onError(task.getException());

                }

            }
        });

    }

    public interface onFirestoreTaskComplate{

        void testDataLoaded(List<TestModel> quizListModels);
        void onError(Exception e);
    }
}

