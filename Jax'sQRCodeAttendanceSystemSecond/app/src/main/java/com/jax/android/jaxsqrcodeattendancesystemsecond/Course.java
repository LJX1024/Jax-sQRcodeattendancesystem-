package com.jax.android.jaxsqrcodeattendancesystemsecond;

/**
 * Created by lijiaxin on 6/18/17.
 */




public class Course {

    private String CourseId;

    private String courseName;

    private String courseStartTime;

    private String courseEndTime;

    /*
     * @ElementList private Collection<ManualStudent> Students;
     */
    Course( String CourseId, String courseName){
        this.CourseId = CourseId;
        this.courseName = courseName;
    }

    Course(){

    }
    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(String courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public String getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime = courseEndTime;
    }



}

