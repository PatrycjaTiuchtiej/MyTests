package com.example.mytests.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytests.model.SubjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectRepository {

    private onFirestoreTaskComplate onFirestoreTaskComplate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //private Query subjects = db.collection("Subjects").whereEqualTo("teacher_id", currentUserId);
    //private Query subjects = db.collection("Subjects").whereArrayContains("students", currentUserId);
    private Query subjects;
    private String roleCollection = "Students";


    public SubjectRepository(onFirestoreTaskComplate onFirestoreTaskComplate) {
        this.onFirestoreTaskComplate= onFirestoreTaskComplate;
    }


    public void deleteSubject(String subjectId)
    {
        db.collection("Subjects").document(subjectId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DELETE_SUBJECT", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DELETE_SUBJECT", "Error deleting document", e);
                    }
                });
    }

    public void addSubject(String name) {

        Map<String, Object> subject = new HashMap<>();
        subject.put("name", name);
        subject.put("teacher_id", currentUserId);

        db.collection("Subjects").document(name)
                .set(subject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD_SUBJECT", "Document Subject successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD_SUBJECT", "Error writing document", e);
                    }
                });
    }

    public void getSubjectData() {

        //setSubjectsOption();
        DocumentReference role = db.collection("Teachers").document(currentUserId);
        role.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("ROLE", "DocumentSnapshot data: " + document.getData());
                        roleCollection = "Teachers";
                        subjects = db.collection("Subjects").whereEqualTo("teacher_id", currentUserId);
                        Log.d("ROLE", "teacher");

                        subjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()){

                                    onFirestoreTaskComplate.subjectDataLoaded(task.getResult()
                                            .toObjects(SubjectModel.class));

                                }
                                else {
                                    onFirestoreTaskComplate.onError(task.getException());
                                }

                            }
                        });
                    }
                    else {
                        Log.d("ROLE", "No such document");
                        roleCollection = "Students";
                        subjects = db.collection("Subjects").whereArrayContains("students", currentUserId);
                        Log.d("ROLE", "student");
                        subjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()){

                                    onFirestoreTaskComplate.subjectDataLoaded(task.getResult()
                                            .toObjects(SubjectModel.class));


                                }else {

                                    onFirestoreTaskComplate.onError(task.getException());

                                }

                            }
                        });
                    }
                }
                else {
                    Log.d("ROLE", "get failed with ", task.getException());
                }
            }
        });
        /*
        subjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    onFirestoreTaskComplate.subjectDataLoaded(task.getResult()
                            .toObjects(SubjectModel.class));


                }else {

                    onFirestoreTaskComplate.onError(task.getException());

                }

            }
        });*/

    }

    public interface onFirestoreTaskComplate{

        void subjectDataLoaded(List<SubjectModel> subjectModels);
        void onError(Exception e);
    }
}
