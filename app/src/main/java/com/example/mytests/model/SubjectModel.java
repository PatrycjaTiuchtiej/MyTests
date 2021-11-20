package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;
import java.util.ArrayList;

public class SubjectModel {

    @DocumentId
    private String subjectId;

    private String name, teacherId;
    private ArrayList<String> students;
    private ArrayList<TestModel> tests;

    public SubjectModel() {
    }

    public SubjectModel(String subjectId, String name, String ownerId, ArrayList<String> students, ArrayList<TestModel> tests) {
        this.subjectId = subjectId;
        this.name = name;
        this.teacherId = ownerId;
        this.students = students;
        this.tests = tests;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return teacherId;
    }

    public void setOwnerId(String ownerId) {
        this.teacherId = ownerId;
    }

    public ArrayList<TestModel> getTests() {
        return tests;
    }

    public void setTests(ArrayList<TestModel> tests) {
        this.tests = tests;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }
}
