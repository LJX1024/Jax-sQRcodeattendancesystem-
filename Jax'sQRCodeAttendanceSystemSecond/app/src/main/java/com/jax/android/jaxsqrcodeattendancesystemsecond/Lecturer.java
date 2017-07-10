package com.jax.android.jaxsqrcodeattendancesystemsecond;

import java.util.Collection;

/**
 * Created by lijiaxin on 6/20/17.
 */

public class Lecturer {
    private String emailID;

    private Collection<Course> courses;
    public String getEmailID() {return emailID;}
    public void setEmailID(String emailid ) { emailID = emailid; }
    public Collection<Course > getCourse (){ return courses;}
    public void setCourses(Collection<Course> courses) { this.courses = courses;}
}
