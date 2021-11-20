package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class SolverModel {

    @DocumentId
    private String solverId;
    
    private Boolean isSolved;
    private Integer scores;

    private ArrayList<AnswerModel> answers;

    // ?
    public SolverModel(ArrayList<AnswerModel> answers) {
        this.answers = answers;
    }

    public SolverModel(String solverId, Boolean isSolved, Integer scores) {
        this.solverId = solverId;
        this.isSolved = isSolved;
        this.scores = scores;
    }

    public String getSolverId() {
        return solverId;
    }

    public void setSolverId(String solverId) {
        this.solverId = solverId;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public ArrayList<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerModel> answers) {
        this.answers = answers;
    }
}
