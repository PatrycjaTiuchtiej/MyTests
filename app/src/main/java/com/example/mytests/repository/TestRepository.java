package com.example.mytests.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytests.model.TestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRepository {

    private onFirestoreTaskComplate onFirestoreTaskComplate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String subjectId;
    private String testId;
    //private CollectionReference reference = db.collection("Subjects");

    public TestRepository(onFirestoreTaskComplate onFirestoreTaskComplate) {
        this.onFirestoreTaskComplate= onFirestoreTaskComplate;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

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

    public void addTest(String title){

        DocumentReference subjectRef = db.collection("Subjects").document(subjectId);
        Map<String, Object> test = new HashMap<>();
        test.put("title", title);

        subjectRef.collection("Tests").document(title)
                .set(test)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD_TEST", "Document Test successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD_TEST", "Error writing document", e);
                    }
                });
    }

    public interface onFirestoreTaskComplate{

        void testDataLoaded(List<TestModel> quizListModels);
        void onError(Exception e);
    }
}

