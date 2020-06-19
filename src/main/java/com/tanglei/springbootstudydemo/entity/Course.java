package com.tanglei.springbootstudydemo.entity;

public class Course {
    private String cName;
    private String cId;
    private Student student;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cName='" + cName + '\'' +
                ", cId='" + cId + '\'' +
                ", student=" + student +
                '}';
    }
}
