package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class ResultModel {

    @DocumentId
    String resultId;
    int score;
    String studentId;
    String studentEmail;
    String testTitle;

    public ResultModel(){}

    public  ResultModel(String resultId, int score, String studentId, String studentEmail, String testTitle)
    {
        this.resultId = resultId;
        this.score = score;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.testTitle = testTitle;
    }

    public String getResultId() { return resultId; }

    public void setResultId(String resultId) { this.resultId = resultId; }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentEmail() { return studentEmail; }

    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public String getTestTitle() { return testTitle; }

    public void setTestTitle(String testTitle) { this.testTitle = testTitle; }
}
