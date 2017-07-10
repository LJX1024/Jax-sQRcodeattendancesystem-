package com.jax.android.jaxsqrcodeattendancesystemsecond;

/**
 * Created by lijiaxin on 6/17/17.
 */


import java.io.Serializable;
import java.util.Collection;


public class User {

    private String userName;

    private String name;

    private String password;

    private String androidDeviceID;

    private int studentID;

    private Collection<Course> courses;

    public User(String userName, String name, String password, int studentID){
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.studentID = studentID;

    }
    public User(){

    }
    public String getName(){
        return name;
    }

    public void setName(String pname) { name = pname;}

    public String getUserName() {return userName;}
    public void setUserName(String pUserName){ userName = pUserName;}

    public String getPassword(){return password;}
    public void setPassword(String pPassword){ password = pPassword;}

    public String getAndroidDeviceID(){return androidDeviceID;}
    public void setAndroidDeviceID(String pAndroidDeviceID){ androidDeviceID = pAndroidDeviceID;}

    public int getStudentID(){return studentID;}
    public void setStudentID(int psetStudentID){studentID = psetStudentID;}

    public Collection<Course> getCourses(){return courses;}

    public void setCourses(Collection<Course> pCourse){ courses = pCourse;}


}
