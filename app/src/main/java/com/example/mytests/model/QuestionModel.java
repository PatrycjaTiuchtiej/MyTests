package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;

public class QuestionModel {

    @DocumentId
    private String questionId;

    private String text, optionA, optionB, optionC, optionD, correctOption;
    //private long timer;

    public QuestionModel() { }

    public QuestionModel(String questionId, String text, String correctOption,
                         String optionA, String optionB, String optionC, String optionD) {
        this.questionId = questionId;
        this.text = text;
        this.correctOption = correctOption;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        //this.timer = timer;
    }

    public String getQuestionId() { return questionId; }

    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getOptionA() { return optionA; }

    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }

    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }

    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }

    public void setOptionD(String optionD) { this.optionD = optionD; }

    public String getCorrectOption() { return correctOption; }

    public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }
}
