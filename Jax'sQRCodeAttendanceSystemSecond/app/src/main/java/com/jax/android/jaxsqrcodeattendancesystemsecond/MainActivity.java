package com.jax.android.jaxsqrcodeattendancesystemsecond;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/*
* THis class is used for testing.
* it has nothing to do with the whole project
* */
public class MainActivity extends AppCompatActivity {
    String TAG = "JAX:";

    private ProgressBar mProgressView;
    private Button markAttenbutton, viewTopicsbutton;
    User user = new User();
    Course currentCourse = new Course("CS101", "Operateing system");
    //DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("user");
    //Query query =  mDatabaseReference.orderByChild("userName").equalTo("jax1024");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mProgressView = (ProgressBar)findViewById(R.id.login_progress);
        // Get the data from previous intent
        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("USER_INFO"), User.class);
        if (user.getUserName() != null){

            TextView welcomeTextView = (TextView) findViewById(R.id.welcomeText);
            String name = user.getName();
            welcomeTextView.setText(name + " "+ "!");

           // AsynCallTask task = new AsynCallTask();
           // task.execute();

        }
        else{
            // close the acitivty
            this.finish();
        }

        viewTopicsbutton = (Button) findViewById(R.id.ViewTopicsBtn);
        viewTopicsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String myJson = gson.toJson(user);
                Intent intent = new Intent(getApplicationContext(), ViewTopics.class);
                intent.putExtra("USER_INFO", myJson);
                startActivity(intent);
            }
        });
        markAttenbutton = (Button) findViewById(R.id.markAttendanceBtn);
        markAttenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open the QR code Generate page

                Gson gson = new Gson();
                String myJson = gson.toJson(user);
                Intent intent = new Intent(getApplicationContext(), GenerateQRcode.class);
                intent.putExtra("USER_INFO", myJson);
                startActivity(intent);
            }
        });

    }
    private class AsynCallTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG, "doInBackground");
            Collection<Course> courses = new ArrayList<Course>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = new Date();
            String dataString = format.format(new Date());
            try {

                Log.w("JAX SMA AT", nowDate.toString());
            } catch (Exception e) {
                Log.w("JAX SM", e.toString());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            }
            Date startDate = new Date(), endDate = new Date();
            //get all courses
            courses = user.getCourses();
            for (Course c : courses) {  // ******it must has data.*****
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                try {
                    startDate = df.parse(format.format(nowDate) + " " + c.getCourseStartTime());
                    endDate = df.parse(format.format(nowDate) + " " + c.getCourseEndTime());

                    Log.w(TAG, "startDate: " + startDate.toString());
                    Log.w(TAG, "EndDate: " + endDate.toString());

                } catch (Exception e) {
                    Log.w(TAG, e.toString());
                }

                if (nowDate.after(startDate) && nowDate.before(endDate)) {
                    currentCourse = c;
                    break;
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute");

            mProgressView.setVisibility(View.GONE);

            EditText currentCourseTV = (EditText) findViewById(R.id.currentCourse);

            Button markAttenbutton = (Button) findViewById(R.id.markAttendanceBtn);
            // if the current course not found
            if(currentCourseTV != null){
                currentCourseTV.setText(currentCourse.getCourseName());
                markAttenbutton.setEnabled(true);
            }

            else {
                currentCourseTV.setText("No Ongoing Course");
                markAttenbutton.setEnabled(false);
            }

        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i(TAG, "onProgressUpdate");
            mProgressView.animate();
        }
    }

}
