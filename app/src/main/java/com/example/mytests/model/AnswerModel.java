package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class AnswerModel {

    @DocumentId
    private String answerId;
    
    private String questionId, checkedOption;
    private Boolean isCorrect;

    public AnswerModel() { }

    public AnswerModel(String answerId, String questionId, String checkedOption, Boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.checkedOption = checkedOption;
        this.isCorrect = isCorrect;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCheckedOption() {
        return checkedOption;
    }

    public void setCheckedOption(String checkedOption) {
        this.checkedOption = checkedOption;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
