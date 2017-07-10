package com.jax.android.jaxsqrcodeattendancesystemlecturer;

/**
 * Created by lijiaxin on 7/10/17.
 */

public class Lecturer {
    private String lecturerName;
    private int lecturerID;
    private String password;

    public Lecturer(String lecturerName, int lecturerID, String password){
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;
        this.password = password;
    }
    public Lecturer(){}

    public String getLecturerName() {
        return lecturerName;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public String getPassword() {
        return password;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
