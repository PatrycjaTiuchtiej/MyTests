package com.example.mytests.repository;

import androidx.annotation.NonNull;

import com.example.mytests.model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class QuestionRepository {


    private FirebaseFirestore db;
    private String testId;
    //private HashMap<String, Long> resultHashMap = new HashMap<>();
    private OnQuestionLoad onQuestionLoad;
    //private OnResultAdded onResultAdded;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //private OnResultLoad onResultLoad;

    public QuestionRepository(OnQuestionLoad onQuestionLoad/*, OnResultAdded onResultAdded,OnResultLoad onResultLoad*/) {

        db = FirebaseFirestore.getInstance();
        this.onQuestionLoad = onQuestionLoad;
        //this.onResultAdded = onResultAdded;
        //this.onResultLoad = onResultLoad;


    }

    /*public void addResults(HashMap<String, Object> resultMap) {
        db.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onResultAdded.onSubmit();
                } else {
                    onResultAdded.onError(task.getException());
                }
            }
        });
    }*/

    /*public void getResults() {
        db.collection("Quiz").document(quizId)
                .collection("results").document(currentUserId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    resultHashMap.put("correct", task.getResult().getLong("correct"));
                    resultHashMap.put("wrong", task.getResult().getLong("wrong"));
                    resultHashMap.put("notAnswered", task.getResult().getLong("notAnswered"));

                    onResultLoad.onResultLoad(resultHashMap);
                } else {

                    onResultLoad.onError(task.getException());

                }
            }
        });
    }*/

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public void getQuestions() {

        db.collection("Subjects").document("Subject0")
                .collection("Tests").document(testId)
                .collection("Questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));

                } else {
                    onQuestionLoad.onError(task.getException());
                }


            }
        });

    }

    /*public interface OnResultLoad {
        void onResultLoad(HashMap<String, Long> resultHashMap);
        void onError(Exception e);
    }*/

    public interface OnQuestionLoad {
        void onLoad(List<QuestionModel> questionModels);

        void onError(Exception e);
    }

    /*public interface OnResultAdded {

        boolean onSubmit();
        void onError(Exception e);
    }*/
}