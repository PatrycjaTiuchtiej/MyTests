package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class TestModel {

    @DocumentId
    private String testId;

    private String title;
    private ArrayList<QuestionModel> questions;

    public TestModel() {
    }

    public TestModel(String testId, String title, ArrayList<QuestionModel> questions) {
        this.testId = testId;
        this.title = title;
        this.questions = questions;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionModel> questions) {
        this.questions = questions;
    }
}
