package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class TestModel {

    @DocumentId
    private String testId;

    private String title;
    private int questions;

    public TestModel() {
    }

    public TestModel(String testId, String title, int questions) {
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

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }
}
