package com.example.mytests.model;

import com.google.firebase.firestore.DocumentId;
import java.util.ArrayList;

public class SubjectModel {

    @DocumentId
    private String subjectId;

    private String name, description, teacherId;
    private ArrayList<String> students;
    private ArrayList<TestModel> tests;

    public SubjectModel() {
    }

    public SubjectModel(String subjectId, String name, String description, String ownerId,
                        ArrayList<String> students, ArrayList<TestModel> tests) {
        this.subjectId = subjectId;
        this.name = name;
        this.description = description;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String ownerId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
