package com.termorganizer.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.termorganizer.Utilities.AssessmentTypeEnum;

@Entity(tableName = "Assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentTitle;
    private AssessmentTypeEnum assessmentType;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private int courseID;
    private String courseTitle;

    @Override
    public String toString() {
        return "Assessment{" + "assessmentID=" + assessmentID + "assessmentTitle" + assessmentTitle + "assessmentType" + assessmentType + "assessmentStartDate" + assessmentStartDate + "assessmentEndDate"+ assessmentEndDate + "courseID=" + courseID + "courseTitle" + courseTitle +'}';
    }

    public Assessment(int assessmentID, String assessmentTitle, AssessmentTypeEnum assessmentType, String assessmentStartDate, String assessmentEndDate, int courseID, String courseTitle) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseID = courseID;
        this.courseTitle = courseTitle;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public AssessmentTypeEnum getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(AssessmentTypeEnum assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public  int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
}


