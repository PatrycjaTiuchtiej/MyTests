package com.example.mytests.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mytests.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MemberRepository {
    private MemberRepository.onFirestoreTaskComplate onFirestoreTaskComplate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String memberId;
    private String subjectId;
    //private CollectionReference reference = db.collection("Subjects");

    public MemberRepository(MemberRepository.onFirestoreTaskComplate onFirestoreTaskComplate) {
        this.onFirestoreTaskComplate= onFirestoreTaskComplate;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void getMembersList() {

    }

    public void getMemberData(List<String> members) {

        // to do implement
        db.collection("Students").whereIn("user_id", members)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            onFirestoreTaskComplate.memberDataLoaded(task.getResult()
                                    .toObjects(UserModel.class));

                        }else {

                            onFirestoreTaskComplate.onError(task.getException());

                        }

                    }
                });

    }

    public void setMemberId(String email) {
        db.collection("Students").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            QuerySnapshot query = task.getResult();
                            List<DocumentSnapshot> documents = query.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                memberId = document.get("user_id").toString();
                            }
                        }else {
                            onFirestoreTaskComplate.onError(task.getException());
                        }

                    }
                });
    }

    public void addMember(/*String subjectId, String memberId*/){

        DocumentReference subjectRef = db.collection("Subjects").document(subjectId);
        subjectRef.update("students", FieldValue.arrayUnion(memberId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD_MEMBER", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD_MEMBER", "Error deleting document", e);
                    }
                });
    }

    // przy usuwaniu usunąć też wyniki testów
    public void deleteMember(/*String subjectId, String memberId*/) {

        DocumentReference subjectRef = db.collection("Subjects").document(subjectId);
                subjectRef.update("students", FieldValue.arrayRemove(memberId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DELETE_MEMBER", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DELETE_MEMBER", "Error deleting document", e);
                    }
                });
    }

    public interface onFirestoreTaskComplate{

        void memberDataLoaded(List<UserModel> userListModels);
        void onError(Exception e);
    }
}
