package com.termorganizer.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String courseInstructorName;
    private String courseInstructorPhone;
    private String courseInstructorEmail;
    private String courseNotes;
    private int termID;

    public Course(int courseID, String courseTitle, String startDate, String endDate, String courseStatus, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getCourseStatus() { return courseStatus; }

    public void setCourseStatus(String courseStatus) { this.courseStatus = courseStatus; }

    public String getCourseInstructorName() { return courseInstructorName; }

    public void setCourseInstructorName(String courseInstructorName) { this.courseInstructorName = courseInstructorName; }

    public String getCourseInstructorPhone() { return courseInstructorPhone; }

    public void setCourseInstructorPhone(String courseInstructorPhone) { this.courseInstructorPhone = courseInstructorPhone; }

    public String getCourseInstructorEmail() { return courseInstructorEmail; }

    public void setCourseInstructorEmail(String courseInstructorEmail) { this.courseInstructorEmail = courseInstructorEmail; }

    public String getCourseNotes() { return courseNotes; }

    public void setCourseNotes(String courseNotes) { this.courseNotes = courseNotes; }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }
}
