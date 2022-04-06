package com.termorganizer.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.termorganizer.Utilities.CourseStatusEnum;

@Entity(tableName = "Courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private CourseStatusEnum courseStatus;
    private String courseInstName;
    private String courseInstPhone;
    private String courseInstEmail;
    private String courseNotes;
    private int termID;
    private String termName;

    @Override
    public String toString() {
        return "EntityCourses{" +
                "courseID=" + courseID +
                ",  courseTitle='" + courseTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", termID='" + termID +'\'' +
                ", termName='" + termName +'\'' +
                    ",courseStatus=" + courseStatus + '\'' +
                ", courseInstructorName='" + courseInstName + '\'' +
                ", courseInstructorPhone='" + courseInstPhone + '\'' +
                ", courseInstructorEmail='" + courseInstEmail + '\'' +
                '}';
    }
    public Course(int courseID, String courseTitle, String startDate, String endDate, int termID, String termName, CourseStatusEnum courseStatus, String courseInstName, String courseInstPhone, String courseInstEmail, String courseNotes) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.courseInstName = courseInstName;
        this.courseInstPhone = courseInstPhone;
        this.courseInstEmail = courseInstEmail;
        this.courseNotes = courseNotes;
        this.termID = termID;
        this.termName = termName;
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public CourseStatusEnum getCourseStatus() { return courseStatus; }

   public void setCourseStatus(CourseStatusEnum courseStatus) { this.courseStatus = courseStatus; }

    public String getCourseInstName() { return courseInstName; }

    public void setCourseInstName(String courseInstName) { this.courseInstName = courseInstName; }

    public String getCourseInstPhone() { return courseInstPhone; }

    public void setCourseInstPhone(String courseInstPhone) { this.courseInstPhone = courseInstPhone; }

    public String getCourseInstEmail() { return courseInstEmail; }

    public void setCourseInstEmail(String courseInstEmail) { this.courseInstEmail = courseInstEmail; }

    public String getCourseNotes() { return courseNotes; }

    public void setCourseNotes(String courseNotes) { this.courseNotes = courseNotes; }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }

    public String getTermName() { return termName;}

    public void setTermName(String termName) { this.termName = termName; }

}
