package com.jax.android.jaxsqrcodeattendancesystemlecturer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button buttonLogin;
    private TextView registerHere;

    boolean validated = false;
    String TAG = "TAG: ";
    public Lecturer lecturer = new Lecturer();
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("lecturer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        registerHere = (TextView)findViewById(R.id.tvRegisterHere);

        //Login Button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncLoginTask task  = new AsyncLoginTask();
                task.execute();
                System.out.println("There are " + "User name : " +" " +  lecturer.getLecturerName() + " "+ "user password " + " :"
                        + lecturer.getPassword());


            }
        });

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private class AsyncLoginTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Log.i(TAG,"doInBackground");
           /* if(validated){
                // open next activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }*/
            return null;
        }

        @Override
        protected void onPreExecute() {
            String mUsername = username.getText().toString();
            String mPassword = password.getText().toString();
            Log.i(TAG, "onPreExecute");
            Query query =  mDatabaseReference.orderByChild("lecturerName").equalTo(mUsername);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    lecturer = dataSnapshot.getValue(Lecturer.class);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            if (mUsername != "" && mUsername.equals(lecturer.getLecturerName())){

                if (password.getText().length()!= 0 && mPassword.equals(lecturer.getPassword())){
                    validated = true;

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "User name and password don't match", Toast.LENGTH_LONG);
                    toast.show();
                    validated = false;
                }

            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "please enter User name!", Toast.LENGTH_LONG);
                toast.show();
                validated = false;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
           /* if (validated){

                Toast toast = Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Error Occured!", Toast.LENGTH_LONG);
                toast.show();
            }*/

            if (lecturer.getLecturerName() != null && lecturer != null) {
                Gson gson = new Gson();
                String myJson = gson.toJson(lecturer);
                Intent intent = new Intent( getApplicationContext(), LecturerMainActivity.class);
                intent.putExtra("Lecturer_INFO", myJson);
                startActivity(intent);
            }
            else {

                Context context = getApplicationContext();
                CharSequence text = "Invalid User Name or Password!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);

                toast.show();

            }

        }
    }
}
