package com.jax.android.jaxsqrcodeattendancesystemsecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewTopics extends AppCompatActivity {
    User user = new User();
    ProgressBar mProgressView;
    Spinner spinner;
    TableLayout tableLayout;
    ArrayList<String> list = new ArrayList<String>();
    Lecturer lectuer = new Lecturer();
    String courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_topics);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout1);
        spinner = (Spinner)findViewById(R.id.courseSelector);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseId = spinner.getSelectedItem().toString();
                //GetTopicsAsyncTask task = new GetTopicsAsyncTask();
                //task.execute("GET");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("USER_INFO"), User.class);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
        //GetTopicsAsyncTask task = new GetTopicsAsyncTask();
        //.execute("GET_COURSE");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_topics, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public String getLecturer(User user ){
        
        return null;
    }

}
